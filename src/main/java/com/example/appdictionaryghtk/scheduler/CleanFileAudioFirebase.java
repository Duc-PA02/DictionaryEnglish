package com.example.appdictionaryghtk.scheduler;

import com.example.appdictionaryghtk.service.redis.RedisLockService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

        scheduler.scheduleAtFixedRate(this::cleanupOldFiles, initialDelay, TimeUnit.HOURS.toSeconds(1), TimeUnit.SECONDS);
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

        log.info("Bắt đầu quá trình xóa các tệp trong thư mục audio của Firebase Storage...");

        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ClassPathResource("translate-ghtk-firebase-adminsdk-apy68-d8c6dd470a.json").getInputStream());
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            Bucket bucket = storage.get(BUCKET_NAME);

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime limitTime = now.minusMinutes(60);

            bucket.list().iterateAll().forEach(blob -> {
                if (blob.getName().startsWith(AUDIO_FOLDER)) {
                    LocalDateTime createdTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(blob.getCreateTime()), ZoneId.systemDefault());
                    if (createdTime.isBefore(limitTime)) {
                        blob.delete();
                        log.info("Đã xóa tệp: " + blob.getName() + ", thời gian tạo: " + createdTime);
                    }
                }
            });

            log.info("Quá trình xóa các tệp trước 60 phút hoàn tất.");

        } catch (Exception e) {
            log.error("Lỗi xảy ra trong quá trình xóa tệp: {}", e.getMessage(), e);
        } finally {
            redisLockService.releaseLock(LOCK_KEY, lockValue);
        }
    }

    private long calculateInitialDelay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.plusHours(1).truncatedTo(ChronoUnit.HOURS);
        return ChronoUnit.SECONDS.between(now, nextRun);
    }
}