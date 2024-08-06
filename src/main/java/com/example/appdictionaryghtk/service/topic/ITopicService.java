package com.example.appdictionaryghtk.service.topic;

import com.example.appdictionaryghtk.dtos.request.topic.TopicRequest;
import com.example.appdictionaryghtk.dtos.response.topic.TopicAdminResponse;
import com.example.appdictionaryghtk.dtos.response.topic.TopicUserResponse;

import java.util.List;

public interface ITopicService {
    public List<TopicAdminResponse> getAllTopicAdmin();
    public List<TopicAdminResponse> getAllTopicAdminDESCName();
    public List<TopicAdminResponse> getAllTopicAdminASCName();
    public List<TopicUserResponse> getAllTopicUser();
    public List<TopicUserResponse> getAllTopicUserDESCName();
    public List<TopicUserResponse> getAllTopicUserASCName();
    public void deleteTopic(int id);
    public TopicAdminResponse addTopic(TopicRequest topicRequest, int uid);
    public TopicAdminResponse updateTopic(int uid, int tid, TopicRequest topicRequest);
    public List<TopicAdminResponse> searchTopicAdmin(String name);
    public List<TopicUserResponse> searchTopicUser(String name);
}
