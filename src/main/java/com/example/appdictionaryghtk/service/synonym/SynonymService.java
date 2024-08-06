package com.example.appdictionaryghtk.service.synonym;

import com.example.appdictionaryghtk.dtos.response.word.SynonymDTO;
import com.example.appdictionaryghtk.entity.Synonyms;
import com.example.appdictionaryghtk.entity.Word;
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
public class SynonymService implements ISynonymService {
    SynonymRepository synonymRepository;
    WordRepository wordRepository;
//    AntonymRepository antonymRepository;
    ModelMapper mapper;


    @Override
    public SynonymDTO create(Integer wordId, SynonymDTO synonymDTO) {
        Word word = wordRepository.findById(wordId).orElseThrow(()->new RuntimeException("Word is not exist"));
        Synonyms synonym = mapper.map(synonymDTO, Synonyms.class);
        if( synonymRepository.existsBySynonymAndWord(synonym.getSynonym(), word))
            throw new RuntimeException("synonym existed");
//        if( synonymRepository.existsBySynonymAndWord(synonym.getSynonym(), word) ||
//                synonymRepository.existsBySynonymAndWord(word, synonym.getSynonym()))
//            throw new RuntimeException("synonym existed");
//        if( antonymRepository.existsByAntonymAndWord(synonym.getSynonym(), word) ||
//                antonymRepository.existsByAntonymAndWord(word, synonym.getSynonym()))
//            throw new RuntimeException(synonym.getSynonym().getName() + " and " + word.getName()+  "is antonym");

        synonym.setWord(word);
        word.getSynonymsList().add(synonym);
        return mapper.map(synonymRepository.save(synonym), SynonymDTO.class);
    }

    @Override
    public void deleteByID(Integer id){
        synonymRepository.deleteById(id);
    }




}
