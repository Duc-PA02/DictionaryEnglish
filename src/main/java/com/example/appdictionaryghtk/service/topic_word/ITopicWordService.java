package com.example.appdictionaryghtk.service.topic_word;

import com.example.appdictionaryghtk.dtos.response.topicword.TopicWordAdminResponse;
import com.example.appdictionaryghtk.dtos.response.topicword.TopicWordUserResponse;

import java.util.List;

public interface ITopicWordService {

    public List<TopicWordAdminResponse> getWordByTopicAdmin(int tid);
    public List<TopicWordAdminResponse> getWordByTopicAdminDESCName(int tid);
    public List<TopicWordAdminResponse> getWordByTopicAdminASCName(int tid);
    public List<TopicWordUserResponse> getWordByTopicUser(int tid);
    public List<TopicWordUserResponse> getWordByTopicUserDESCName(int tid);
    public List<TopicWordUserResponse> getWordByTopicUserASCName(int tid);
    public TopicWordAdminResponse addTopicWord(int uid, int tid, int wid);

    public void deleteTopicWord(int twid);

    public List<TopicWordAdminResponse> getWordByNameAdmin(int tid, String name);

    public List<TopicWordUserResponse> getWordByNameUser(int tid, String name);

}
