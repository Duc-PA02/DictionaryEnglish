package com.example.appdictionaryghtk.service.antonym;

import com.example.appdictionaryghtk.dtos.response.word.AntonymDTO;
import com.example.appdictionaryghtk.entity.Antonyms;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.repository.AntonymRepository;
import com.example.appdictionaryghtk.repository.SynonymRepository;
import com.example.appdictionaryghtk.repository.WordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AntonymService implements IAntonymService{

    AntonymRepository antonymRepository;
    WordRepository wordRepository;
//    SynonymRepository synonymRepository;
    ModelMapper mapper;


    @Override
    public AntonymDTO create(Integer wordId, AntonymDTO antonymDTO) {
        Word word = wordRepository.findById(wordId)
                .orElseThrow(()->new RuntimeException("Word is not exist"));
        Antonyms antonyms = mapper.map(antonymDTO, Antonyms.class);
        if( antonymRepository.existsByAntonymAndWord(antonyms.getAntonym(), word) )
            throw new RuntimeException("antonym existed");
//        if( antonymRepository.existsByAntonymAndWord(antonyms.getAntonym(), word) || antonymRepository.existsByAntonymAndWord(word, antonyms.getAntonym()))
//            throw new RuntimeException("antonym existed");
//        if(synonymRepository.existsBySynonymAndWord(antonyms.getAntonym(), word)||synonymRepository.existsBySynonymAndWord(word,antonyms.getAntonym())
//            throw new RuntimeException(antonyms.ge" existed");
        antonyms.setWord(word);
        word.getAntonymsList().add(antonyms);
        return mapper.map(antonymRepository.save(antonyms), AntonymDTO.class);
    }

    @Override
    public void deleteByID(Integer id){
        antonymRepository.deleteById(id);
    }

}
