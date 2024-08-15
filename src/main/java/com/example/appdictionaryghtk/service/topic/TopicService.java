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
import org.springframework.data.domain.*;
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
            throw new EntityExistsException("Topic already exist!");
        }
        Topic topic = new Topic();
        topic.setName(topicRequest.getName());
        topic.setCreat_by(user);
        return modelMapper.map(topicRepository.save(topic), TopicAdminResponse.class);
    }

    @Override
    @Transactional
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
    public Page<TopicAdminResponse> getTopicAdmin(String name, String sortDirection, Integer pageNumber, Integer pageSize) {
        Page<Topic> topics = new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0);;
        if(name==null || name.equalsIgnoreCase("")){
            if(sortDirection.equalsIgnoreCase("date_ascending")){
                topics = topicRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id")));
            }else if(sortDirection.equalsIgnoreCase("date_descending")){
                topics = topicRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
            } else if(sortDirection.equalsIgnoreCase("dictionary_asc")){
                topics = topicRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "name")));
            }else if(sortDirection.equalsIgnoreCase("dictionary_desc")){
                topics = topicRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "name")));
            }
        }else{
            if(sortDirection.equalsIgnoreCase("date_ascending")){
                topics = topicRepository.findByNameContaining(name, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id")));
            }else if(sortDirection.equalsIgnoreCase("date_descending")){
                topics = topicRepository.findByNameContaining(name, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
            }else if(sortDirection.equalsIgnoreCase("dictionary_asc")){
                topics = topicRepository.findByNameContaining(name, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "name")));
            }else if(sortDirection.equalsIgnoreCase("dictionary_desc")){
                topics = topicRepository.findByNameContaining(name, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "name")));
            }
        }

        return topics.map(topic -> modelMapper.map(topic, TopicAdminResponse.class));
    }

    @Override
    public Page<TopicUserResponse> getTopicUser(String name, String sortDirection, Integer pageNumber, Integer pageSize) {
        Page<Topic> topics = new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0);
        if(name==null || name.equalsIgnoreCase("")){
            if(sortDirection.equalsIgnoreCase("date_ascending")){
                topics = topicRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id")));
            }else if(sortDirection.equalsIgnoreCase("date_descending")){
                topics = topicRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
            } else if(sortDirection.equalsIgnoreCase("dictionary_asc")){
                topics = topicRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "name")));
            }else if(sortDirection.equalsIgnoreCase("dictionary_desc")){
                topics = topicRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
            }
        }else{
            if(sortDirection.equalsIgnoreCase("date_ascending")){
                topics = topicRepository.findByNameContaining(name, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id")));
            }else if(sortDirection.equalsIgnoreCase("date_descending")){
                topics = topicRepository.findByNameContaining(name, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
            }else if(sortDirection.equalsIgnoreCase("dictionary_asc")){
                topics = topicRepository.findByNameContaining(name, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "name")));
            }else if(sortDirection.equalsIgnoreCase("dictionary_desc")){
                topics = topicRepository.findByNameContaining(name, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
            }
        }
        return topics.
                map(topic -> modelMapper.map(topic, TopicUserResponse.class));
    }

    @Override
    public TopicAdminResponse getTopicAdminById(int tid) {
        if(!topicRepository.existsById(tid)){
            throw new EntityExistsException("Topic doesn't exist");
        }
        return modelMapper.map(topicRepository.findById(tid), TopicAdminResponse.class);
    }
    @Override
    public TopicUserResponse getTopicUserById(int tid) {
        if(!topicRepository.existsById(tid)){
            throw new EntityExistsException("Topic doesn't exist");
        }
        return modelMapper.map(topicRepository.findById(tid), TopicUserResponse.class);
    }
}
