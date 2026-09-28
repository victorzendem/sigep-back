package br.com.sigep.sigep.infraestructure.security.ratelimit;

import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Ignora preflight requests de CORS (OPTIONS) para nao travar o navegador
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String uri = request.getRequestURI();

        // Rota de autenticacao publica (login ou registro)
        boolean isAuthEndpoint = uri.startsWith("/api/v1/auth/");

        // Extrai o IP real do cliente
        String ipCliente = extrairIpCliente(request);

        // Se o usuario ja estiver autenticado pelo SecurityFilter, utiliza o identificador dele
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAutenticado = authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal());

        String identificador = isAutenticado ? authentication.getName() : ipCliente;

        // Chave unica no redis prefixada pelo tipo de rota para isolar contadores
        String chave = isAuthEndpoint
                ? "AUTH_LIMIT_" + ipCliente
                : (isAutenticado ? "USER_LIMIT_" + identificador : "GLOBAL_LIMIT_" + ipCliente);

        try {
            ConsumptionProbe probe = rateLimiterService.tentarConsumir(chave, !isAuthEndpoint);

            if (probe.isConsumed()) {
                response.setHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
                filterChain.doFilter(request, response);
            } else {
                retornarTooManyRequests(response, probe);
            }
        } catch (Exception e) {
            log.error("Erro na comunicacao com o Redis para Rate Limiting: {}. Permitindo requisicao (fail-open).", e.getMessage());
            filterChain.doFilter(request, response);
        }
    }

    private void retornarTooManyRequests(HttpServletResponse response, ConsumptionProbe probe) throws IOException {
        long segundosParaEsperar = Math.max(1, TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill()));

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        response.setHeader("Retry-After", String.valueOf(segundosParaEsperar));
        response.setHeader("X-Rate-Limit-Remaining", "0");
        response.setHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(segundosParaEsperar));

        String jsonResponse = String.format(
                "{\"status\":%d,\"mensagem\":\"Limite de requisições excedido. Tente novamente em %d segundos.\",\"timestamp\":\"%s\",\"errors\":[]}",
                HttpStatus.TOO_MANY_REQUESTS.value(),
                segundosParaEsperar,
                LocalDateTime.now()
        );

        response.getWriter().write(jsonResponse);
    }

    private String extrairIpCliente(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isBlank() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else if (ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }
}

