package com.example.appdictionaryghtk.service.topic;

import com.example.appdictionaryghtk.dtos.request.topic.TopicRequest;
import com.example.appdictionaryghtk.dtos.response.topic.TopicAdminResponse;
import com.example.appdictionaryghtk.dtos.response.topic.TopicUserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITopicService {
    public void deleteTopic(int id);
    public TopicAdminResponse addTopic(TopicRequest topicRequest, int uid);
    public TopicAdminResponse updateTopic(int uid, int tid, TopicRequest topicRequest);
    public Page<TopicAdminResponse> getTopicAdmin(String name, String sortDirection, Integer pageNumber, Integer pageSize);
    public Page<TopicUserResponse> getTopicUser(String name, String sortDirection, Integer pageNumber, Integer pageSize);
    public TopicAdminResponse getTopicById(int tid);
}
