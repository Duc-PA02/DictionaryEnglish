package com.example.appdictionaryghtk.dtos.request.topic;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TopicRequest {
    @NotBlank(message = "Name cannot be blank")
    String name;
}
