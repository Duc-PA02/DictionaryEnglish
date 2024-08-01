package com.example.appdictionaryghtk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.elasticsearch.client.RestHighLevelClient;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.appdictionaryghtk.repository")
@ComponentScan(basePackages = { "com.example.appdictionaryghtk.service.elasticsearch" })
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        Dotenv dotenv = Dotenv.load();
        String username = dotenv.get("ELASTIC_USERNAME");
        String password = dotenv.get("ELASTIC_PASSWORD");

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .withBasicAuth(username, password)
                .build();

        return RestClients.create(clientConfiguration)
                .rest();
    }
}