package br.com.sigep.sigep.infraestructure.security.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    // Gerencia a criacao/recuperacao atomica dos baldes no redis
    private final ProxyManager<String> proxyManager;

    // Regra estrita para apenas 5 requisicoes por minuto no endpoint de login/registro (publico)
    private static final BucketConfiguration CONFIG_PUBLICO = BucketConfiguration.builder()
            .addLimit(Bandwidth.builder()
                    .capacity(5)
                    .refillGreedy(5, Duration.ofMinutes(1))
                    .build())
            .build();

    // Configuracao padrao de 100 requisicoes por minuto para usuarios autenticados (privado)
    private static final BucketConfiguration CONFIG_PRIVADO = BucketConfiguration.builder()
            .addLimit(Bandwidth.builder()
                    .capacity(100)
                    .refillGreedy(100, Duration.ofMinutes(1))
                    .build())
            .build();

    // tenta consumir 1 token para a chave informada (id do usuario ou ip)
    // retorna um ConsumptionProbe contendo:
    //  se foi consumido ou nao
    //  quantos tokens restam
    //  quanto tempo falta para o proximo token em nanossegundos
    public ConsumptionProbe tentarConsumir(String chave, boolean isEndpointAutenticado) {
        Supplier<BucketConfiguration> configSupplier = () -> isEndpointAutenticado ? CONFIG_PRIVADO : CONFIG_PUBLICO;

        return proxyManager.builder()
                .build(chave, configSupplier)
                .tryConsumeAndReturnRemaining(1);
    }
}
