package com.example.appdictionaryghtk.service.topic_word;

import com.example.appdictionaryghtk.dtos.response.topicword.TopicWordAdminResponse;
import com.example.appdictionaryghtk.dtos.response.topicword.TopicWordUserResponse;
import com.example.appdictionaryghtk.entity.Topic;
import com.example.appdictionaryghtk.entity.TopicWord;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.exceptions.EntityExistsException;
import com.example.appdictionaryghtk.repository.TopicRepository;
import com.example.appdictionaryghtk.repository.TopicWordRepository;
import com.example.appdictionaryghtk.repository.UserRepository;
import com.example.appdictionaryghtk.repository.WordRepository;
import com.example.appdictionaryghtk.service.topic.TopicService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicWordService implements ITopicWordService{
    private final TopicWordRepository topicWordRepository;
    private final TopicRepository topicRepository;
    private final WordRepository wordRepository;
    private final UserRepository userRepository;
    private final TopicService topicService;
    private final ModelMapper modelMapper;

    @Override
    public List<TopicWordAdminResponse> getWordByTopicAdmin(int tid) {
        if(!topicRepository.existsById(tid)){
            throw new EntityExistsException("Topic doesn't exist");
        }
        List<TopicWord> topicWords =  topicWordRepository.findByTopicId(tid, Sort.by(Sort.Direction.DESC, "id"));
        return CollectionUtils.isEmpty(topicWords) ? Collections.emptyList() : topicWords.stream().
                map(topicWord -> modelMapper.map(topicWord, TopicWordAdminResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TopicWordAdminResponse> getWordByTopicAdminDESCName(int tid) {
        if(!topicRepository.existsById(tid)){
            throw new EntityExistsException("Topic doesn't exist");
        }
        List<TopicWord> topicWords =  topicWordRepository.findByTopicId(tid, Sort.by(Sort.Direction.DESC, "word.name"));
        return CollectionUtils.isEmpty(topicWords) ? Collections.emptyList() : topicWords.stream().
                map(topicWord -> modelMapper.map(topicWord, TopicWordAdminResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TopicWordAdminResponse> getWordByTopicAdminASCName(int tid) {
        if(!topicRepository.existsById(tid)){
            throw new EntityExistsException("Topic doesn't exist");
        }
        List<TopicWord> topicWords =  topicWordRepository.findByTopicId(tid, Sort.by(Sort.Direction.ASC, "word.name"));
        return CollectionUtils.isEmpty(topicWords) ? Collections.emptyList() : topicWords.stream().
                map(topicWord -> modelMapper.map(topicWord, TopicWordAdminResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TopicWordUserResponse> getWordByTopicUser(int tid) {
        if(!topicRepository.existsById(tid)){
            throw new EntityExistsException("Topic doesn't exist");
        }
        List<TopicWord> topicWords =  topicWordRepository.findByTopicId(tid, Sort.by(Sort.Direction.DESC, "id"));
        return CollectionUtils.isEmpty(topicWords) ? Collections.emptyList() : topicWords.stream().
                map(topicWord -> modelMapper.map(topicWord, TopicWordUserResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TopicWordUserResponse> getWordByTopicUserDESCName(int tid) {
        if(!topicRepository.existsById(tid)){
            throw new EntityExistsException("Topic doesn't exist");
        }
        List<TopicWord> topicWords =  topicWordRepository.findByTopicId(tid, Sort.by(Sort.Direction.DESC, "word.name"));
        return CollectionUtils.isEmpty(topicWords) ? Collections.emptyList() : topicWords.stream().
                map(topicWord -> modelMapper.map(topicWord, TopicWordUserResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TopicWordUserResponse> getWordByTopicUserASCName(int tid) {
        if(!topicRepository.existsById(tid)){
            throw new EntityExistsException("Topic doesn't exist");
        }
        List<TopicWord> topicWords =  topicWordRepository.findByTopicId(tid, Sort.by(Sort.Direction.ASC, "word.name"));
        return CollectionUtils.isEmpty(topicWords) ? Collections.emptyList() : topicWords.stream().
                map(topicWord -> modelMapper.map(topicWord, TopicWordUserResponse.class)).collect(Collectors.toList());
    }

    @Override
    public TopicWordAdminResponse addTopicWord(int uid, int tid, int wid) {
        if(!userRepository.existsById(uid)){
            throw new EntityExistsException("User doesn't exist");
        }
        if(!topicRepository.existsById(tid)){
            throw new EntityExistsException("Topic doesn't exist");
        }
        if(!wordRepository.existsById(wid)){
            throw new EntityExistsException("Word doesn't exist");
        }
        if(topicWordRepository.existsByWordIdAndTopicId(wid, tid)){
            throw new EntityExistsException("Word already exist in the topic");
        }
        Topic topic = topicRepository.findById(tid).get();
        User user = userRepository.findById(uid).get();
        Word word = wordRepository.findById(wid).get();
        //TopicRequest topicRequest = modelMapper.map(topic, TopicRequest.class);
        topic.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        topic.setUpdate_by(user);
        topicRepository.saveAndFlush(topic);
        TopicWord topicWord = new TopicWord();
        topicWord.setTopic(topic);
        topicWord.setWord(word);
        return modelMapper.map(topicWordRepository.save(topicWord), TopicWordAdminResponse.class);
    }

    @Override
    public void deleteTopicWord(int twid) {
        if(!topicWordRepository.existsById(twid)){
            throw new EntityExistsException("Topic-word doesn't exist");
        }
        topicWordRepository.deleteById(twid);
    }

    @Override
    public List<TopicWordAdminResponse> getWordByNameAdmin(int tid, String name) {
        if(!topicWordRepository.existsByTopicIdAndWordNameContaining(tid,name)){
            throw new EntityExistsException("Word doesn't exist in the topic");
        }
        List<TopicWord> topicWords =  topicWordRepository.findByTopicIdAndWordNameContaining(tid,name, Sort.by(Sort.Direction.DESC, "id"));
        return CollectionUtils.isEmpty(topicWords) ? Collections.emptyList() : topicWords.stream().
                map(topicWord -> modelMapper.map(topicWord, TopicWordAdminResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TopicWordUserResponse> getWordByNameUser(int tid, String name) {
        if(!topicWordRepository.existsByTopicIdAndWordNameContaining(tid,name)){
            throw new EntityExistsException("Word doesn't exist in the topic");
        }
        List<TopicWord> topicWords =  topicWordRepository.findByTopicIdAndWordNameContaining(tid,name, Sort.by(Sort.Direction.DESC, "id"));
        return CollectionUtils.isEmpty(topicWords) ? Collections.emptyList() : topicWords.stream().
                map(topicWord -> modelMapper.map(topicWord, TopicWordUserResponse.class)).collect(Collectors.toList());
    }

}
