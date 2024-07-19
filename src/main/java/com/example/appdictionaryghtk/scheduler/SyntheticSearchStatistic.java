package com.example.appdictionaryghtk.scheduler;

import com.example.appdictionaryghtk.entity.SearchStatistic;
import com.example.appdictionaryghtk.service.searchStatistic.SearchStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Component
public class SyntheticSearchStatistic {
    //Cập nhật thống kê lịch sử tìm kiếm
    private static final Logger logger = Logger.getLogger(SyntheticSearchStatistic.class.getName());
    private final SearchStatisticService searchStatisticService;

//    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // sau mỗi 24 giờ
//    @Scheduled(fixedRate = 20000) // sau mỗi 20s
//    @Scheduled(cron = "0 0 0 * * ?") // mỗi 0h sáng cập nhật 1 lần
    @Scheduled(cron = "0 * * ? * *") //sau moi phut
    public void performUpdateSearchStatistics() {
        try {
            List<SearchStatistic> updatedStatistics = searchStatisticService.updateSearchStatistics();
            System.out.println("Đã cập nhật SearchStatistic");
        }catch (Exception e){
            logger.severe("Đã xảy ra lỗi trong quá trình cập nhật thống kê tìm kiếm: {}" + e.getMessage());
        }
    }
}
