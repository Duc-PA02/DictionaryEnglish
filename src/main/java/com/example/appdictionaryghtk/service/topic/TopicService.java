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

import java.util.ArrayList;
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
            throw new ObjectNotFoundException("User't not exist");
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
    public List<TopicAdminResponse> getTopicAdmin(String name, String sortDirection) {
        List<Topic> topics = new ArrayList<>();
        if(name==null || name.equalsIgnoreCase("")){
            if(sortDirection.equalsIgnoreCase("id")){
                topics = topicRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            }else if(sortDirection.equalsIgnoreCase("asc")){
                topics = topicRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
            }else if(sortDirection.equalsIgnoreCase("desc")){
                topics = topicRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
            }
        }else{
            if(sortDirection.equalsIgnoreCase("id")){
                topics = topicRepository.findByNameContaining(name, Sort.by(Sort.Direction.DESC, "id"));
            }else if(sortDirection.equalsIgnoreCase("asc")){
                topics = topicRepository.findByNameContaining(name, Sort.by(Sort.Direction.ASC, "name"));
            }else if(sortDirection.equalsIgnoreCase("desc")){
                topics = topicRepository.findByNameContaining(name, Sort.by(Sort.Direction.DESC, "name"));
            }
        }
        return topics.stream().
                map(topic -> modelMapper.map(topic, TopicAdminResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TopicUserResponse> getTopicUser(String name, String sortDirection) {
        List<Topic> topics = new ArrayList<>();
        if(name==null || name.equalsIgnoreCase("")){
            if(sortDirection.equalsIgnoreCase("id")){
                topics = topicRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            }else if(sortDirection.equalsIgnoreCase("asc")){
                topics = topicRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
            }else if(sortDirection.equalsIgnoreCase("desc")){
                topics = topicRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
            }
        }else{
            if(sortDirection.equalsIgnoreCase("id")){
                topics = topicRepository.findByNameContaining(name, Sort.by(Sort.Direction.DESC, "id"));
            }else if(sortDirection.equalsIgnoreCase("asc")){
                topics = topicRepository.findByNameContaining(name, Sort.by(Sort.Direction.ASC, "name"));
            }else if(sortDirection.equalsIgnoreCase("desc")){
                topics = topicRepository.findByNameContaining(name, Sort.by(Sort.Direction.DESC, "name"));
            }
        }
        return topics.stream().
                map(topic -> modelMapper.map(topic, TopicUserResponse.class)).collect(Collectors.toList());
    }
}
