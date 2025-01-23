package com.zogg.zoggservice.service;

import com.zogg.zoggservice.configuration.RedisConfig;
import com.zogg.zoggservice.service.interfaces.RedisService;
import com.zogg.zoggservice.utils.CommonUtils;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    private final RedisCommands<String, String> connection;

    RedisServiceImpl(RedisConfig redisConfig) {
        this.connection = redisConfig.createConnection();
    }

    @Override
    public Boolean setWithExpiry(String key, String value, Long expiryInSecs) {

        if (connection == null) {
            return null;
        }

        try {

            String result = this.connection.set(key, value, SetArgs.Builder.nx().ex(expiryInSecs));

            if ("OK".equals(result)) {
                return true;
            } else {
                log.warn(String.format("Key %s already exists and was not set.", key));
            }

        } catch (Exception e) {

            throw CommonUtils.logAndGetException(
                    String.format("REDIS_FAILURE: Set failed due to error: %s", e.getMessage()), e);
        }

        return false;
    }

    @Override
    public Optional<String> get(String key) {

        if (connection == null) {
            return Optional.empty();
        }

        try {

            String cacheValue = this.connection.get(key);

            if (Objects.isNull(cacheValue)) {
                return Optional.empty();
            }

            return Optional.of(cacheValue);

        } catch (Exception e) {

            log.error(String.format("REDIS_FAILURE: Get failed due to error: %s", e.getMessage()));
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getDel(String key) {

        if (connection == null) {
            return Optional.empty();
        }

        try {
            String cacheValue = this.connection.getdel(key);

            if (Objects.isNull(cacheValue)) {
                return Optional.empty();
            }

            return Optional.of(cacheValue);

        } catch (Exception e) {
            throw CommonUtils.logAndGetException(
                    String.format("REDIS_FAILURE: Get failed due to error: %s", e.getMessage()), e);
        }
    }

    @Override
    public void delete(String key) {
        try {
            this.connection.del(key);
        } catch (Exception e) {
            log.error(
                    String.format(
                            "Delete cache method failed for key: %s, due to error: %s",
                            key, e.getMessage()));
        }
    }

    public void releaseLocks(List<String> keys) {
        for (String key : keys) {
            try {
                delete(key);
            } catch (Exception e) {
                throw CommonUtils.logAndGetException(
                        String.format(
                                "Failed to release lock for key: {}. Error: {}",
                                key,
                                e.getMessage()),
                        e);
            }
        }
    }
}
