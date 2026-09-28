package br.com.sigep.sigep.infraestructure.security.ratelimit;

import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RateLimiterServiceIntegrationTest {

    private RedisClient redisClient;
    private StatefulRedisConnection<String, byte[]> redisConnection;
    private RateLimiterService rateLimiterService;

    @BeforeAll
    void setup() {
        RateLimiterConfig config = new RateLimiterConfig();
        // Utiliza Redis local na porta padrao 6379
        redisClient = RedisClient.create("redis://localhost:6379");
        redisConnection = config.redisConnection(redisClient);
        ProxyManager<String> proxyManager = config.proxyManager(redisConnection);
        rateLimiterService = new RateLimiterService(proxyManager);
    }

    @AfterAll
    void tearDown() {
        if (redisConnection != null) {
            redisConnection.close();
        }
        if (redisClient != null) {
            redisClient.shutdown();
        }
    }

    @Test
    @DisplayName("Endpoint publico (auth): deve permitir exatamente 5 requisicoes e bloquear a 6a requisicao")
    void devePermitirCincoRequisicoesEBloquearSextaEmEndpointPublico() {
        String chave = "TEST_AUTH_" + UUID.randomUUID();

        // 5 requisicoes permitidas
        for (int i = 1; i <= 5; i++) {
            ConsumptionProbe probe = rateLimiterService.tentarConsumir(chave, false);
            assertThat(probe.isConsumed())
                    .as("Tentativa %d deveria ser permitida", i)
                    .isTrue();
            assertThat(probe.getRemainingTokens())
                    .as("Tokens restantes apos tentativa %d", i)
                    .isEqualTo(5 - i);
        }

        // 6a requisicao deve ser bloqueada
        ConsumptionProbe probeBloqueada = rateLimiterService.tentarConsumir(chave, false);
        assertThat(probeBloqueada.isConsumed()).isFalse();
        assertThat(probeBloqueada.getRemainingTokens()).isZero();
        assertThat(probeBloqueada.getNanosToWaitForRefill()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Endpoint privado: deve permitir consumo com balde de capacidade 100")
    void devePermitirConsumoEmEndpointPrivadoComCapacidadeCem() {
        String chave = "TEST_USER_" + UUID.randomUUID();

        ConsumptionProbe primeiraTentativa = rateLimiterService.tentarConsumir(chave, true);
        assertThat(primeiraTentativa.isConsumed()).isTrue();
        assertThat(primeiraTentativa.getRemainingTokens()).isEqualTo(99);

        ConsumptionProbe segundaTentativa = rateLimiterService.tentarConsumir(chave, true);
        assertThat(segundaTentativa.isConsumed()).isTrue();
        assertThat(segundaTentativa.getRemainingTokens()).isEqualTo(98);
    }
}
