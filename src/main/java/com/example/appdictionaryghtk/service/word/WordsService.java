package com.example.appdictionaryghtk.service.word;

import com.example.appdictionaryghtk.dtos.word_management.word.WordDetail;
import com.example.appdictionaryghtk.entity.Definitions;
import com.example.appdictionaryghtk.entity.Pronunciations;
import com.example.appdictionaryghtk.entity.Type;
import com.example.appdictionaryghtk.entity.Words;
import com.example.appdictionaryghtk.exceptions.DataNotFoundException;
import com.example.appdictionaryghtk.repository.WordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WordsService implements IWordService {

    WordRepository wordRepository;
//    TypeService typeService;
//    DefinitionService definitionService;
//    PronunciationService pronunciationService;
    ModelMapper mapper;

    @Override
    public Page<WordDetail> findAll(Integer pageNumber, Integer pageSize, String sort) {
        Pageable pageable = null;
        if (sort != null) {
            pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.ASC, sort);
        } else {
            pageable = PageRequest.of(pageNumber-1, pageSize);
        }
        Page<Words> words = wordRepository.findAll(pageable);
        return new PageImpl<>(
                words.stream().map(
                        word->mapper.map(word, WordDetail.class)
                ).toList(), pageable, words.getTotalElements());
    }

    @Override
    public WordDetail findByID(Integer wordID) {
        Words words =  wordRepository.findById(wordID).orElseThrow(()->new DataNotFoundException("Word is not exist"));
        return mapper.map(words, WordDetail.class);
    }

    @Override
    @Transactional
    public WordDetail create(WordDetail wordDetail) {
        Words word = mapper.map(wordDetail, Words.class);
        addTypesOfWord(word.getTypeList(), word);
        word = wordRepository.save(word);
        return mapper.map(word, WordDetail.class);
    }


    @Override
    public WordDetail update(Integer wordID, WordDetail wordDetail) {
        Words existedWord = wordRepository.findById(wordID).orElseThrow(()->new DataNotFoundException("Word is not exist"));
        mapper.map(wordDetail, existedWord);
        existedWord.setId(wordID);
        return mapper.map(wordRepository.save(existedWord), WordDetail.class);
    }

    @Override
    public void deleteByID(Integer id) {
        wordRepository.deleteById(id);
    }

    @Override
    public boolean isExist(WordDetail wordDetail) {
        return wordRepository.existsByName(wordDetail.getName());
    }


    private void addTypesOfWord(List<Type> types, Words word){
        if(word.getTypeList() == null || word.getTypeList().isEmpty())
            throw new DataNotFoundException("type not null");
        for(Type type: types){
            type.setWord(word);
            addDefinitionsOfType(type.getDefinitionsList(), type);
            addPronunciationOfType(type.getPronunciationsList(), type);
        }
    }

    private void addDefinitionsOfType(List<Definitions> definitions, Type type){
        if(definitions ==null || definitions.isEmpty())
            throw new DataNotFoundException("type not null");
        for(Definitions definition: definitions){
            definition.setType(type);
        }
    }

    private void addPronunciationOfType(List<Pronunciations> pronunciations, Type type){
        if(pronunciations ==null || pronunciations.isEmpty())
            throw new DataNotFoundException("type not null");
        for(Pronunciations pronunciation: pronunciations){
            pronunciation.setType(type);
        }
    }



}
