package com.zogg.zoggservice.service.interfaces;

import java.util.List;
import java.util.Optional;

public interface RedisService {

    Boolean setWithExpiry(String key, String value, Long expiryInSecs);

    Optional<String> get(String key);

    Optional<String> getDel(String key);

    void delete(String key);

    void releaseLocks(List<String> keys);
}
