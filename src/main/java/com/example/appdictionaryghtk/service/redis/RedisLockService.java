package com.example.appdictionaryghtk.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisLockService {

    private final RedisTemplate<String, String> redisTemplate;

    // Tạo khóa
    public boolean acquireLock(String key, String value, long timeout, TimeUnit unit) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
        if (Boolean.TRUE.equals(success)) {
            System.out.println("Khóa " + key + " đã được tạo với giá trị: " + value);
        } else {
            System.out.println("Khóa " + key + " đã tồn tại.");
        }
        return Boolean.TRUE.equals(success);
    }

    // Giải phóng khóa
    public boolean releaseLock(String key, String value) {
        String currentValue = redisTemplate.opsForValue().get(key);
        if (value.equals(currentValue)) {
            redisTemplate.delete(key);
            System.out.println("Khóa " + key + " đã được giải phóng.");
            return true;
        }
        System.out.println("Khóa " + key + " không được giải phóng vì giá trị không khớp.");
        return false;
    }
}