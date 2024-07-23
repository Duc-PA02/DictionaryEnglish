package com.example.appdictionaryghtk.scheduler;

import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.repository.WordRepository;
import com.example.appdictionaryghtk.service.elasticsearch.ElasticsearchService;
import com.example.appdictionaryghtk.service.redis.RedisLockService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@RequiredArgsConstructor
@Component
@Slf4j
public class ElasticsearchPoller {

    private final ElasticsearchService esService;
    private final WordRepository wordRepository;
    private final RedisLockService redisLockService;

    private ScheduledExecutorService scheduler;
    private static final String LOCK_KEY = "elasticsearch_poller_lock";
    private final String lockValue = UUID.randomUUID().toString();

    @PostConstruct
    public void start() {
        scheduler = Executors.newScheduledThreadPool(1);
        // Chạy ngay lập tức khi khởi động
        scheduler.execute(this::pollAndIndexChanges);

        // Tính toán độ trễ ban đầu để chạy vào 12 giờ đêm
        long initialDelay = calculateInitialDelay();
        scheduler.scheduleAtFixedRate(this::pollAndIndexChanges, initialDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
    }

    @PreDestroy
    public void stop() {
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
        }
    }

    public void pollAndIndexChanges() {
        if (!redisLockService.acquireLock(LOCK_KEY, lockValue, 120, TimeUnit.MINUTES)) { // Thời gian khóa lớn hơn thời gian đồng bộ
            log.info("Một instance khác của ElasticsearchPoller đang thực hiện quá trình đồng bộ.");
            return;
        }

        log.info("Bắt đầu quá trình đồng bộ hóa Elasticsearch...");

        try {
            List<Word> words = wordRepository.findAll();
            log.info("Số lượng từ tìm thấy: {}", words.size());

            for (Word word : words) {
                esService.indexWordData(word);
            }

            log.info("Dữ liệu đã được cập nhật trong Elasticsearch.");

        } catch (IOException e) {
            log.error("Lỗi IOException trong quá trình indexing Elasticsearch: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Lỗi bất ngờ trong quá trình indexing Elasticsearch: {}", e.getMessage(), e);
        } finally {
            redisLockService.releaseLock(LOCK_KEY, lockValue);
        }
    }

    private long calculateInitialDelay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.withHour(0).withMinute(0).withSecond(0);
        if (now.isAfter(nextRun)) {
            nextRun = nextRun.plusDays(1);
        }
        return ChronoUnit.SECONDS.between(now, nextRun);
    }
}