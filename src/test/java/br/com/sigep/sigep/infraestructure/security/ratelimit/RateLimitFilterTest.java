package br.com.sigep.sigep.infraestructure.security.ratelimit;

import io.github.bucket4j.ConsumptionProbe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateLimitFilterTest {

    @Mock
    private RateLimiterService rateLimiterService;

    @InjectMocks
    private RateLimitFilter rateLimitFilter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Deve permitir requisicao OPTIONS (CORS preflight) sem consultar rate limiter")
    void devePermitirOptionsSemConsumirRateLimit() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("OPTIONS", "/api/v1/auth/login");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        rateLimitFilter.doFilterInternal(request, response, filterChain);

        verify(rateLimiterService, never()).tentarConsumir(anyString(), anyBoolean());
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    @DisplayName("Deve permitir requisicao quando houver tokens disponiveis e preencher headers")
    void devePermitirRequisicaoQuandoConsumoSucesso() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/v1/auth/login");
        request.setRemoteAddr("192.168.1.100");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        ConsumptionProbe probe = ConsumptionProbe.consumed(4, 5);
        when(rateLimiterService.tentarConsumir("AUTH_LIMIT_192.168.1.100", false)).thenReturn(probe);

        rateLimitFilter.doFilterInternal(request, response, filterChain);

        assertThat(response.getHeader("X-Rate-Limit-Remaining")).isEqualTo("4");
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    @DisplayName("Deve retornar status 429 Too Many Requests quando tokens estiverem esgotados")
    void deveRetornar429QuandoLimiteExcedido() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/v1/auth/login");
        request.setRemoteAddr("192.168.1.100");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        long nanosEspera = 15_000_000_000L; // 15 segundos
        ConsumptionProbe probe = ConsumptionProbe.rejected(0, nanosEspera, 5);
        when(rateLimiterService.tentarConsumir("AUTH_LIMIT_192.168.1.100", false)).thenReturn(probe);

        rateLimitFilter.doFilterInternal(request, response, filterChain);

        assertThat(response.getStatus()).isEqualTo(429);
        assertThat(response.getHeader("Retry-After")).isEqualTo("15");
        assertThat(response.getHeader("X-Rate-Limit-Remaining")).isEqualTo("0");
        assertThat(response.getContentAsString()).contains("\"status\":429");
        assertThat(response.getContentAsString()).contains("Limite de requisições excedido");
    }

    @Test
    @DisplayName("Deve utilizar o username do usuario autenticado como identificador na chave")
    void deveUtilizarUsuarioAutenticadoNaChave() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/processos");
        request.setRemoteAddr("192.168.1.100");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("usuario@sigep.com", null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        ConsumptionProbe probe = ConsumptionProbe.consumed(99, 100);
        when(rateLimiterService.tentarConsumir(eq("USER_LIMIT_usuario@sigep.com"), eq(true))).thenReturn(probe);

        rateLimitFilter.doFilterInternal(request, response, filterChain);

        verify(rateLimiterService).tentarConsumir("USER_LIMIT_usuario@sigep.com", true);
        assertThat(response.getHeader("X-Rate-Limit-Remaining")).isEqualTo("99");
    }

    @Test
    @DisplayName("Deve extrair o IP correto a partir do header X-Forwarded-For")
    void deveExtrairIpDoHeaderXForwardedFor() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/v1/auth/login");
        request.addHeader("X-Forwarded-For", "203.0.113.195, 70.41.3.18, 150.172.238.178");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        ConsumptionProbe probe = ConsumptionProbe.consumed(4, 5);
        when(rateLimiterService.tentarConsumir("AUTH_LIMIT_203.0.113.195", false)).thenReturn(probe);

        rateLimitFilter.doFilterInternal(request, response, filterChain);

        verify(rateLimiterService).tentarConsumir("AUTH_LIMIT_203.0.113.195", false);
    }

    @Test
    @DisplayName("Deve permitir requisicao em fail-open se ocorrer erro de comunicacao com Redis")
    void devePermitirRequisicaoEmFailOpenQuandoErroRedis() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/processos");
        request.setRemoteAddr("192.168.1.100");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        when(rateLimiterService.tentarConsumir(anyString(), anyBoolean()))
                .thenThrow(new RuntimeException("Redis connection timed out"));

        rateLimitFilter.doFilterInternal(request, response, filterChain);

        // Nao lanca erro 500, segue a cadeia em modo resiliente
        assertThat(response.getStatus()).isEqualTo(200);
    }
}
