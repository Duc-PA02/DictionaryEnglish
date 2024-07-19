package com.example.appdictionaryghtk.scheduler;

import com.example.appdictionaryghtk.entity.Words;
import com.example.appdictionaryghtk.repository.WordsRepository;
import com.example.appdictionaryghtk.service.elasticsearch.ElasticsearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Component
public class ElasticsearchPoller {

    private static final Logger logger = Logger.getLogger(ElasticsearchPoller.class.getName());

    private final ElasticsearchService esService;

    private final WordsRepository wordsRepository;

    //Cập nhật Words từ mySQL vào elasticsearch
    @Scheduled(fixedDelay = 30000) // Cập nhật liên tục sau khi thành công 30s
    public void pollAndIndexChanges() {
        try {
            List<Words> words = wordsRepository.findAll();
            logger.info("Number of words found : " + words.size());

            for (Words word : words) {
                logger.info("Indexing word: " + word.getId() + " - " + word.getName());
                esService.indexWordData(word);
            }

//            logger.info("Data updated in Elasticsearch.");

        } catch (IOException e) {
            logger.severe("IOException during Elasticsearch indexing: " + e.getMessage());
            e.printStackTrace();
        }
    }
}