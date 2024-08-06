package com.example.appdictionaryghtk.dtos.response.topic;

import com.example.appdictionaryghtk.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TopicAdminResponse {
    private int id;
    private String name;
    private UserDTO creat_by;
    private Timestamp creat_at;
    private UserDTO update_by;
    private Timestamp update_at;
}
