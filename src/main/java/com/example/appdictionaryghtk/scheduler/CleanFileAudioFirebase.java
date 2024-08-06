package com.example.appdictionaryghtk.scheduler;

import com.example.appdictionaryghtk.service.redis.RedisLockService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
public class CleanFileAudioFirebase {
    private final RedisLockService redisLockService;
    private ScheduledExecutorService scheduler;
    private static final String LOCK_KEY = "file_cleanup_lock";
    private final String lockValue = UUID.randomUUID().toString();
    private static final String BUCKET_NAME = "translate-ghtk.appspot.com";
    private static final String AUDIO_FOLDER = "audio/";

    @PostConstruct
    public void start() {
        scheduler = Executors.newScheduledThreadPool(1);
        long initialDelay = calculateInitialDelay();

        // Chạy ngay lập tức khi khởi động
//        scheduler.execute(this::cleanupOldFiles);

        scheduler.scheduleAtFixedRate(this::cleanupOldFiles, initialDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
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

    public void cleanupOldFiles() {
        if (!redisLockService.acquireLock(LOCK_KEY, lockValue, 120, TimeUnit.MINUTES)) {
            log.info("Một instance khác đang thực hiện công việc xóa tệp.");
            return;
        }

        log.info("Bắt đầu quá trình xóa tất cả tệp trong thư mục audio của Firebase Storage...");

        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ClassPathResource("translate-ghtk-firebase-adminsdk-apy68-c6f9907ae6.json").getInputStream());
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            Bucket bucket = storage.get(BUCKET_NAME);

            bucket.list().iterateAll().forEach(blob -> {
                if (blob.getName().startsWith(AUDIO_FOLDER)) {
                    blob.delete();
//                    log.info("Đã xóa tệp: " + blob.getName());
                }
            });

            log.info("Quá trình xóa tất cả tệp hoàn tất.");

        } catch (Exception e) {
            log.error("Lỗi xảy ra trong quá trình xóa tệp: {}", e.getMessage(), e);
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