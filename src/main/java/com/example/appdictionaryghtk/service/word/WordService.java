package com.example.appdictionaryghtk.service.word;

import com.example.appdictionaryghtk.dtos.response.word.WordWithAntonymSynonymTypeResponse;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.exceptions.EntityExistsException;
import com.example.appdictionaryghtk.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordService implements IWordService {

    private final WordRepository wordRepository;
    private final ModelMapper modelMapper;
    @Override
    public WordWithAntonymSynonymTypeResponse getWordByName(String name) {
        if(!wordRepository.existsByName(name)){
            throw new EntityExistsException("Word doesn't exist");
        }
        return modelMapper.map(wordRepository.findByName(name), WordWithAntonymSynonymTypeResponse.class);
    }

    @Override
    public WordWithAntonymSynonymTypeResponse getWordById(int id) {
        if(!wordRepository.existsById(id)){
            throw new EntityExistsException("Word doesn't exist");
        }
        return modelMapper.map(wordRepository.findById(id).get(), WordWithAntonymSynonymTypeResponse.class);
    }

}
