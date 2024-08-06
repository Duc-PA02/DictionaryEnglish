package com.example.appdictionaryghtk.dtos.response.topicword;

import com.example.appdictionaryghtk.dtos.response.topic.TopicAdminResponse;
import com.example.appdictionaryghtk.dtos.response.topic.TopicUserResponse;
import com.example.appdictionaryghtk.dtos.response.word.WordResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicWordUserResponse {
    private int id;
    private TopicUserResponse topic;
    private WordResponse word;
}
