package com.example.appdictionaryghtk.service.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisLockService {

    private final RedisTemplate<String, String> redisTemplate;

    // Tạo khóa
    public boolean acquireLock(String key, String value, long timeout, TimeUnit unit) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
        if (Boolean.TRUE.equals(success)) {
            log.info("Khóa {} đã được tạo với giá trị: {}", key, value);
        } else {
            log.info("Khóa {} đã tồn tại.", key);
        }
        return Boolean.TRUE.equals(success);
    }

    // Giải phóng khóa
    public boolean releaseLock(String key, String value) {
        String currentValue = redisTemplate.opsForValue().get(key);
        if (value.equals(currentValue)) {
            redisTemplate.delete(key);
            log.info("Khóa {} đã được giải phóng.", key);
            return true;
        }
        log.info("Khóa {} không được giải phóng vì giá trị không khớp.", key);
        return false;
    }
}