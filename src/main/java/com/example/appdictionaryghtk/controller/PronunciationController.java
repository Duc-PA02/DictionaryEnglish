package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.word_management.pronunciation.PronunciationDTO;
import com.example.appdictionaryghtk.service.pronunciation.PronunciationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PronunciationController {

    PronunciationService pronunciationService;

    @GetMapping("/pronunciations/{id}")
    public ResponseEntity<PronunciationDTO> getPronunciation(@PathVariable("id") Integer id){
        return ResponseEntity.ok(pronunciationService.findByID(id));
    }

    @PostMapping("/types/{typeID}/pronunciations")
    public ResponseEntity<PronunciationDTO> cratePronun(@PathVariable("typeID") Integer typeId, @RequestBody PronunciationDTO pronunciationDTO){
        return ResponseEntity.ok(pronunciationService.create(typeId, pronunciationDTO));
    }

    @PutMapping("/pronunciations/{pronunciationID}")
    public ResponseEntity<PronunciationDTO> updatePronun(@PathVariable("pronunciationID") Integer pronunciationID, @RequestBody PronunciationDTO pronunciationDTO){
        return ResponseEntity.ok(pronunciationService.update(pronunciationID, pronunciationDTO));
    }

    @DeleteMapping("/pronunciations/{id}")
    public ResponseEntity<?> deletePronunciation(@PathVariable("id") Integer id){
        pronunciationService.deleteByID(id);
        return ResponseEntity.ok("Delete Successful");
    }

}
