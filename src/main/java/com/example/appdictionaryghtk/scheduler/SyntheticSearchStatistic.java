package com.example.appdictionaryghtk.scheduler;

import com.example.appdictionaryghtk.entity.SearchStatistic;
import com.example.appdictionaryghtk.service.redis.RedisLockService;
import com.example.appdictionaryghtk.service.searchStatistic.SearchStatisticService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
public class SyntheticSearchStatistic {
    // Cập nhật thống kê lịch sử tìm kiếm
    private final SearchStatisticService searchStatisticService;
    private final RedisLockService redisLockService;

    private ScheduledExecutorService scheduler;
    private static final String LOCK_KEY = "synthetic_search_statistic_lock";
    private final String lockValue = UUID.randomUUID().toString();

    @PostConstruct
    public void start() {
        scheduler = Executors.newScheduledThreadPool(1);
        // Chạy ngay lập tức khi khởi động
//        scheduler.execute(this::performUpdateSearchStatistics);

        // Tính toán độ trễ ban đầu để chạy vào 12 giờ đêm
        long initialDelay = calculateInitialDelay();
        scheduler.scheduleAtFixedRate(this::performUpdateSearchStatistics, initialDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
    }

    @PreDestroy
    public void stop() {
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(120, TimeUnit.MINUTES)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
        }
    }

    public void performUpdateSearchStatistics() {
        if (!redisLockService.acquireLock(LOCK_KEY, lockValue, 120, TimeUnit.MINUTES)) { // Thời gian khóa lớn hơn thời gian đồng bộ
            log.info("Một instance khác của SyntheticSearchStatistic khác đang thực hiện quá trình đồng bộ.");
            return;
        }
        try {
            List<SearchStatistic> updatedStatistics = searchStatisticService.updateSearchStatistics();
            log.info("Đã cập nhật SearchStatistic");
        } catch (Exception e) {
            log.error("Đã xảy ra lỗi trong quá trình cập nhật thống kê tìm kiếm: {}", e.getMessage());
        } finally {
            redisLockService.releaseLock(LOCK_KEY, lockValue);
        }
    }

    // đồng bộ lúc 0h
    private long calculateInitialDelay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.withHour(0).withMinute(0).withSecond(0);
        if (now.isAfter(nextRun)) {
            nextRun = nextRun.plusDays(1);
        }
        return ChronoUnit.SECONDS.between(now, nextRun);
    }
}