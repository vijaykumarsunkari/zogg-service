package com.zogg.zoggservice.configuration;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.sync.RedisCommands;
import java.time.Duration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Getter
@Configuration
public class RedisConfig {

    @Value("${redis.clients.host}")
    private String redisHost;

    @Value("${redis.clients.password}")
    private String redisPassword;

    @Value("${redis.clients.port}")
    private Integer redisPort;

    @Value("${redis.clients.database}")
    private Integer redisDatabase;

    @Value("${redis.clients.redisPoolMaxTotal}")
    private Integer redisPoolMaxTotal;

    @Value("${redis.clients.redisTimeout}")
    private Integer redisTimeout;

    @Bean
    public RedisCommands<String, String> createConnection() {

        RedisCommands<String, String> syncCommands = null;

        try {
            RedisURI redisUri =
                    RedisURI.Builder.redis(redisHost, redisPort)
                            .withPassword(redisPassword.toCharArray())
                            .build();

            RedisClient redisClient = RedisClient.create(redisUri);

            redisClient.setDefaultTimeout(Duration.ofMillis(redisTimeout));

            syncCommands = redisClient.connect().sync();

        } catch (Exception e) {
            log.error("Failed to connect to Redis. Reason: {}", e.getMessage());
        }

        return syncCommands;
    }
}
