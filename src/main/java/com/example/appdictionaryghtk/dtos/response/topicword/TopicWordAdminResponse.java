package com.example.appdictionaryghtk.dtos.response.topicword;

import com.example.appdictionaryghtk.dtos.response.topic.TopicAdminResponse;
import com.example.appdictionaryghtk.dtos.response.word.WordResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TopicWordAdminResponse {
    private int id;
    private TopicAdminResponse topic;
    private WordResponse word;
}
