package com.example.appdictionaryghtk.service.topic;

import com.example.appdictionaryghtk.dtos.request.topic.TopicRequest;
import com.example.appdictionaryghtk.dtos.response.topic.TopicAdminResponse;
import com.example.appdictionaryghtk.dtos.response.topic.TopicUserResponse;
import com.example.appdictionaryghtk.entity.Topic;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.exceptions.EntityExistsException;
import com.example.appdictionaryghtk.exceptions.ObjectNotFoundException;
import com.example.appdictionaryghtk.repository.TopicRepository;
import com.example.appdictionaryghtk.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicService implements ITopicService{

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<TopicAdminResponse> getAllTopicAdmin() {
        List<Topic> topics = topicRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return CollectionUtils.isEmpty(topics) ? Collections.emptyList() : topics.stream().
                map(topic -> modelMapper.map(topic, TopicAdminResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TopicAdminResponse> getAllTopicAdminDESCName() {
        List<Topic> topics = topicRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        return CollectionUtils.isEmpty(topics) ? Collections.emptyList() : topics.stream().
                map(topic -> modelMapper.map(topic, TopicAdminResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TopicAdminResponse> getAllTopicAdminASCName() {
        List<Topic> topics = topicRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return CollectionUtils.isEmpty(topics) ? Collections.emptyList() : topics.stream().
                map(topic -> modelMapper.map(topic, TopicAdminResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TopicUserResponse> getAllTopicUser() {
        List<Topic> topics = topicRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return CollectionUtils.isEmpty(topics) ? Collections.emptyList() : topics.stream().
                map(topic -> modelMapper.map(topic, TopicUserResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TopicUserResponse> getAllTopicUserDESCName() {
        List<Topic> topics = topicRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        return CollectionUtils.isEmpty(topics) ? Collections.emptyList() : topics.stream().
                map(topic -> modelMapper.map(topic, TopicUserResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TopicUserResponse> getAllTopicUserASCName() {
        List<Topic> topics = topicRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return CollectionUtils.isEmpty(topics) ? Collections.emptyList() : topics.stream().
                map(topic -> modelMapper.map(topic, TopicUserResponse.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTopic(int tid) {
        // Kiểm tra xem topic có tồn tại không
        if(!topicRepository.existsById(tid)){
            throw new EntityExistsException("Topic doesn't exist");
        }
        topicRepository.deleteById(tid);
    }

    @Override
    @Transactional
    public TopicAdminResponse addTopic(TopicRequest topicRequest, int uid) {
        if(!userRepository.existsById(uid)){
            throw new ObjectNotFoundException("User dosen't not exist");
        }
        User user = userRepository.findById(uid).get();
        if(topicRepository.existsByName(topicRequest.getName())){
            throw new EntityExistsException("Topic already in favorites!");
        }
        Topic topic = new Topic();
        topic.setName(topicRequest.getName());
        topic.setCreat_by(user);
        return modelMapper.map(topicRepository.save(topic), TopicAdminResponse.class);
    }

    @Override
    public TopicAdminResponse updateTopic(int uid, int tid, TopicRequest topicRequest) {
        if(!userRepository.existsById(uid)){
            throw new EntityExistsException("User doesn't exist");
        }
        if(!topicRepository.existsById(tid)){
            throw new EntityExistsException("Topic doesn't exist");
        }
        if(topicRepository.existsByName(topicRequest.getName())){
            throw new EntityExistsException("The topic already exist");
        }
        Topic topic = topicRepository.findById(tid).get();
        topic.setUpdate_by(userRepository.findById(uid).get());
        topic.setName(topicRequest.getName());
        return modelMapper.map(topicRepository.save(topic), TopicAdminResponse.class);
    }

    @Override
    public List<TopicAdminResponse> searchTopicAdmin(String name) {
        List<Topic> topics = topicRepository.findByNameContaining(name);
        if(CollectionUtils.isEmpty(topics)){
            throw new EntityExistsException("Search topic does not exist");
        }
        return topics.stream().
                map(topic -> modelMapper.map(topic, TopicAdminResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TopicUserResponse> searchTopicUser(String name) {
        List<Topic> topics = topicRepository.findByNameContaining(name);
        if(CollectionUtils.isEmpty(topics)){
            throw new EntityExistsException("Search topic does not exist");
        }
        return topics.stream().
                map(topic -> modelMapper.map(topic, TopicUserResponse.class)).collect(Collectors.toList());
    }

//    @Override
//    public TopicResponse updateTopic(int tid, TopicRequest topicRequest) {
//        return ;
//    }

//    @Override
//    public TopicDTO updateTopic(int tid, Topic topic) {
//        Topic existingTopic = topicRepository.findById(tid)
//                .orElseThrow(() -> new ObjectNotFoundException("Topic doesn't exist"));
//    }
//    @Override
//    @Transactional
//    public TopicDTO updateTopic(Topic topic) {
//        return topicRepository.saveAndFlush(topic);
//    }

//    @Override
//    public TopicDTO getTopicById(int id) {
//        return topicRepository.findById(id)
//                .map(topic -> modelMapper.map(topic, TopicDTO.class))
//                .orElseThrow(() -> new ObjectNotFoundException("Topic doesn't exist"));
//    }

}
