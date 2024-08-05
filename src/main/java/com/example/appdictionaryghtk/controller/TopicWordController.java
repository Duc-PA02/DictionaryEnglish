package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.response.DefaultResponse;
import com.example.appdictionaryghtk.dtos.response.favoriteword.FavoriteWordResponse;
import com.example.appdictionaryghtk.dtos.response.topicword.TopicWordAdminResponse;
import com.example.appdictionaryghtk.dtos.response.topicword.TopicWordUserResponse;
import com.example.appdictionaryghtk.service.topic_word.ITopicWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class TopicWordController {
    public final ITopicWordService iTopicWordService;

    @GetMapping("/admin/topicword/{tid}")
    public ResponseEntity<DefaultResponse<List<TopicWordAdminResponse>>> getWordByTopicAdmin(@PathVariable int tid, @RequestParam(required = false, defaultValue = "id") String sortDirection){
        List<TopicWordAdminResponse> topicWordAdminResponses = new ArrayList<>();

        if(sortDirection.equalsIgnoreCase("id")){
            topicWordAdminResponses = iTopicWordService.getWordByTopicAdmin(tid);
        }else if(sortDirection.equalsIgnoreCase("desc")){
            topicWordAdminResponses = iTopicWordService.getWordByTopicAdminDESCName(tid);
        }else if(sortDirection.equalsIgnoreCase("asc")){
            topicWordAdminResponses = iTopicWordService.getWordByTopicAdminASCName(tid);
        }
        return ResponseEntity.ok(DefaultResponse.success("success", topicWordAdminResponses));
    }

    @GetMapping("/user/topicword/{tid}")
    public ResponseEntity<DefaultResponse<List<TopicWordUserResponse>>> getWordByTopicUser(@PathVariable int tid, @RequestParam(required = false, defaultValue = "id") String sortDirection){


        List<TopicWordUserResponse> topicWordUserResponses = new ArrayList<>();

        if (sortDirection.equalsIgnoreCase("id")) {
            topicWordUserResponses = iTopicWordService.getWordByTopicUser(tid);
        } else if(sortDirection.equalsIgnoreCase("desc")){
            topicWordUserResponses = iTopicWordService.getWordByTopicUserDESCName(tid);
        }else if(sortDirection.equalsIgnoreCase("asc")){
            topicWordUserResponses = iTopicWordService.getWordByTopicUserASCName(tid);
        }
        return ResponseEntity.ok(DefaultResponse.success("success", topicWordUserResponses));
    }

    @PostMapping("/admin/topicword/{uid}/{tid}/{wid}")
    public ResponseEntity<DefaultResponse<TopicWordAdminResponse>> addTopicWord(@PathVariable int uid, @PathVariable int tid, @PathVariable int wid){
        return ResponseEntity.ok(DefaultResponse.success("success", iTopicWordService.addTopicWord(uid, tid, wid)));
    }

    @DeleteMapping("/admin/topicword/{twid}")
    public ResponseEntity<DefaultResponse<?>> deleteTopicWord(@PathVariable int twid){
        iTopicWordService.deleteTopicWord(twid);
        return ResponseEntity.ok(DefaultResponse.success("success", ""));
    }

    @GetMapping("/admin/topicword/{tid}/{name}")
    public ResponseEntity<DefaultResponse<List<TopicWordAdminResponse>>> getWordByNameAdmin(@PathVariable int tid, @PathVariable String name){
        return ResponseEntity.ok(DefaultResponse.success("success", iTopicWordService.getWordByNameAdmin(tid,name)));
    }

    @GetMapping("/user/topicword/{tid}/{name}")
    public ResponseEntity<DefaultResponse<List<TopicWordUserResponse>>> getWordByNameUser(@PathVariable int tid, @PathVariable String name){
        return ResponseEntity.ok(DefaultResponse.success("success", iTopicWordService.getWordByNameUser(tid,name)));
    }
}
