package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.response.word.TypeDTO;
import com.example.appdictionaryghtk.dtos.response.word.WordWithAntonymSynonymTypeDTO;
import com.example.appdictionaryghtk.entity.Type;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.service.word.TypeService;
import com.example.appdictionaryghtk.service.word.WordService;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/english")
public class WordController {
    private ModelMapper modelMapper;

    private WordService wordService;

    private TypeService typeService;
    public WordController(ModelMapper modelMapper, WordService wordService, TypeService typeService) {
        this.modelMapper = modelMapper;
        this.wordService = wordService;
        this.typeService = typeService;
    }

    @GetMapping("/search")
    public String getWord(Model model, @Param("txtSearch") String txtSearch){


        Word word = wordService.getWordByName(txtSearch);
        WordWithAntonymSynonymTypeDTO wordDTO = modelMapper.map(word, WordWithAntonymSynonymTypeDTO.class);

        model.addAttribute("txtSearch", txtSearch);
        model.addAttribute("word", wordDTO);
        return "index1";
    }

    @GetMapping("/home")
    public String home(Model model){
        Word word = new Word();
        model.addAttribute("word",word);
        String name = "";
        model.addAttribute("name", name);
        return "index1";
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> getWordByName(@PathVariable String name){
        Word word = wordService.getWordByName(name);

        WordWithAntonymSynonymTypeDTO wordDTO = modelMapper.map(word, WordWithAntonymSynonymTypeDTO.class);
        return ResponseEntity.ok(wordDTO);
    }
    @GetMapping("/type/{id}")
    public ResponseEntity<List<TypeDTO>> getTypeByWordId(@PathVariable Integer id){
        List<Type> types = typeService.getTypeByWordId(id);
        List<TypeDTO> typeDTOS = new ArrayList<>();
        for (Type type : types){
            typeDTOS.add(modelMapper.map(type, TypeDTO.class));
        }
        return ResponseEntity.ok(typeDTOS);
    }
}
