package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.word_management.type.TypeDTO;
import com.example.appdictionaryghtk.service.type.TypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeController {

    TypeService typeService;

    @GetMapping("/types/{id}")
    public ResponseEntity<TypeDTO> getType(@PathVariable("id") Integer id){
        return ResponseEntity.ok(typeService.findByID(id));
    }

    @PostMapping("/words/{wordID}/types")
    public ResponseEntity<TypeDTO> createType( @PathVariable("wordID") Integer wordId,@RequestBody TypeDTO typeDTO){
        return ResponseEntity.ok(typeService.create(wordId, typeDTO));
    }

    @PutMapping("/types/{typeID}")
    public ResponseEntity<TypeDTO> updateType(@PathVariable("typeID") Integer typeId, @RequestBody TypeDTO typeDTO){
        return ResponseEntity.ok(typeService.update(typeId, typeDTO));
    }

    @DeleteMapping("/types/{id}")
    public ResponseEntity<?> deleteType(@PathVariable("id") Integer id){
        typeService.deleteByID(id);
        return ResponseEntity.ok("Delete Successful");
    }

}
