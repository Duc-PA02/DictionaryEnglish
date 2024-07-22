package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.word_management.definition.DefinitionDTO;
import com.example.appdictionaryghtk.service.definition.DefinitionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DefinitionController {
    DefinitionService definitionService;

    @GetMapping("/definitions/{id}")
    public ResponseEntity<DefinitionDTO> getDefinitions(@PathVariable("id") Integer id){
        return ResponseEntity.ok(definitionService.findByID(id));
    }

    @PostMapping("/types/{typeID}/definitions")
    public ResponseEntity<DefinitionDTO> createDefinitions(@PathVariable("typeID") Integer typeId, @RequestBody DefinitionDTO definitionDTO){
        return ResponseEntity.ok(definitionService.create(typeId, definitionDTO));
    }

    @PutMapping("/definitions/{definitionsID}")
    public ResponseEntity<DefinitionDTO> updateDefinition(@PathVariable("definitionsID") Integer definitionsID, @RequestBody DefinitionDTO definitionDTO){
        return ResponseEntity.ok(definitionService.update(definitionsID, definitionDTO));
    }

    @DeleteMapping("/definitions/{id}")
    public ResponseEntity<?> deleteDefinitions(@PathVariable("id") Integer id){
        definitionService.deleteByID(id);
        return ResponseEntity.ok("Delete Successful");
    }
}
