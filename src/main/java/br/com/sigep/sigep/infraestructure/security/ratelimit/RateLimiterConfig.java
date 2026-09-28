package br.com.sigep.sigep.infraestructure.security.ratelimit;

import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfig {

    @Value("${spring.data.redis.host:${REDIS_HOST:localhost}}")
    private String redisHost;

    @Value("${spring.data.redis.port:${REDIS_PORT:6379}}")
    private int redisPort;

    @Bean(destroyMethod = "shutdown")
    public RedisClient redisClient() {
        return RedisClient.create(String.format("redis://%s:%d", redisHost, redisPort));
    }

    @Bean(destroyMethod = "close")
    public io.lettuce.core.api.StatefulRedisConnection<String, byte[]> redisConnection(RedisClient redisClient) {
        return redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));
    }

    @Bean
    public ProxyManager<String> proxyManager(io.lettuce.core.api.StatefulRedisConnection<String, byte[]> redisConnection) {
        return LettuceBasedProxyManager.builderFor(redisConnection)
                .withExpirationStrategy(
                        ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofMinutes(1))
                )
                .build();
    }
}