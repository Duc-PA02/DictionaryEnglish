package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.request.topic.TopicRequest;
import com.example.appdictionaryghtk.dtos.response.DefaultResponse;
import com.example.appdictionaryghtk.dtos.response.topic.TopicAdminResponse;
import com.example.appdictionaryghtk.dtos.response.topic.TopicUserResponse;
import com.example.appdictionaryghtk.service.topic.ITopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class TopicController {
    private final ITopicService iTopicService;

    @GetMapping("/admin/topic")
    public ResponseEntity<DefaultResponse<List<TopicAdminResponse>>> getAllTopicAdmin(@RequestParam(required = false, defaultValue = "id") String sortDirection){
        List<TopicAdminResponse> topicAdminResponses = new ArrayList<>();
        if(sortDirection.equalsIgnoreCase("id")){
            topicAdminResponses = iTopicService.getAllTopicAdmin();
        }else if(sortDirection.equalsIgnoreCase("asc")){
            topicAdminResponses = iTopicService.getAllTopicAdminASCName();
        }else if(sortDirection.equalsIgnoreCase("desc")){
            topicAdminResponses = iTopicService.getAllTopicAdminDESCName();
        }
        return ResponseEntity.ok(DefaultResponse.success("success", topicAdminResponses));
    }

    @GetMapping("/user/topic")
    public ResponseEntity<DefaultResponse<List<TopicUserResponse>>> getAllTopicUser(@RequestParam(required = false, defaultValue = "id") String sortDirection){

        List<TopicUserResponse> topicUserResponses = new ArrayList<>();
        if(sortDirection.equalsIgnoreCase("id")){
            topicUserResponses = iTopicService.getAllTopicUser();
        }else if(sortDirection.equalsIgnoreCase("asc")){
            topicUserResponses = iTopicService.getAllTopicUserASCName();
        }else if(sortDirection.equalsIgnoreCase("desc")){
            topicUserResponses = iTopicService.getAllTopicUserDESCName();
        }
        return ResponseEntity.ok(DefaultResponse.success("success", topicUserResponses));
    }

    @PostMapping("/admin/topic/{uid}")
    public ResponseEntity<DefaultResponse<TopicAdminResponse>> addTopic(@PathVariable int uid, @Valid @RequestBody TopicRequest topicRequest){
        return ResponseEntity.ok(DefaultResponse.success("success", iTopicService.addTopic(topicRequest, uid)));
    }

    @DeleteMapping("/admin/topic/{tid}")
    public ResponseEntity<DefaultResponse<?>> deleteTopic(@PathVariable int tid) {
        iTopicService.deleteTopic(tid);
        return ResponseEntity.ok(DefaultResponse.success("Success", ""));
    }

    @PutMapping("/admin/topic/{uid}/{tid}")
    public ResponseEntity<DefaultResponse<TopicAdminResponse>> updateTopic(@PathVariable int uid, @PathVariable int  tid, @Valid @RequestBody TopicRequest topicRequest){
        return ResponseEntity.ok(DefaultResponse.success("success",iTopicService.updateTopic(uid, tid, topicRequest)));
    }

    @GetMapping("/admin/topic/{name}")
    public ResponseEntity<DefaultResponse<List<TopicAdminResponse>>> getTopicByNameAdmin(@PathVariable String name){
        return ResponseEntity.ok(DefaultResponse.success("success", iTopicService.searchTopicAdmin(name)));
    }

    @GetMapping("/user/topic/{name}")
    public ResponseEntity<DefaultResponse<List<TopicUserResponse>>> getTopicByNameUser(@PathVariable String name){
        return ResponseEntity.ok(DefaultResponse.success("success", iTopicService.searchTopicUser(name)));
    }
}
