package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.request.topic.TopicRequest;
import com.example.appdictionaryghtk.dtos.response.DefaultResponse;
import com.example.appdictionaryghtk.dtos.response.topic.TopicAdminResponse;
import com.example.appdictionaryghtk.dtos.response.topic.TopicUserResponse;
import com.example.appdictionaryghtk.service.topic.ITopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class TopicController {
    private final ITopicService iTopicService;

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

    @GetMapping("/admin/topic")
    public ResponseEntity<DefaultResponse<Page<TopicAdminResponse>>> getTopicAdmin(@RequestParam(required = false, defaultValue = "") String name, @RequestParam(required = false, defaultValue = "date_ascending") String sortDirection,
                                                                                   @RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "7") Integer pageSize){
        return ResponseEntity.ok(DefaultResponse.success("success", iTopicService.getTopicAdmin(name, sortDirection, pageNumber, pageSize)));
    }

    @GetMapping("/user/topic")
    public ResponseEntity<DefaultResponse<Page<TopicUserResponse>>> getTopicUser(@RequestParam(required = false, defaultValue = "") String name,  @RequestParam(required = false, defaultValue = "date_ascending") String sortDirection,
                                                                                @RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "7") Integer pageSize){
        return ResponseEntity.ok(DefaultResponse.success("success", iTopicService.getTopicUser(name, sortDirection, pageNumber, pageSize)));
    }

    @GetMapping("/admin/topic/{tid}")
    public ResponseEntity<DefaultResponse<TopicAdminResponse>> getTopicAdminById(@PathVariable int tid){
        return ResponseEntity.ok(DefaultResponse.success("success", iTopicService.getTopicAdminById(tid)));
    }

    @GetMapping("/user/topic/{tid}")
    public ResponseEntity<DefaultResponse<TopicUserResponse>> getTopicUserById(@PathVariable int tid){
        return ResponseEntity.ok(DefaultResponse.success("success", iTopicService.getTopicUserById(tid)));
    }
}
