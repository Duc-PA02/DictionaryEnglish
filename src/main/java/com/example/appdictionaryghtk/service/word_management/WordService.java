package com.example.appdictionaryghtk.service.word_management;

import com.example.appdictionaryghtk.dtos.word_management.antonym.AntonymDTO;
import com.example.appdictionaryghtk.dtos.word_management.synonym.SynonymDTO;
import com.example.appdictionaryghtk.dtos.word_management.type.TypeDTO;
import com.example.appdictionaryghtk.dtos.word_management.word.WordDTO;
import com.example.appdictionaryghtk.dtos.word_management.word.WordDetail;
import com.example.appdictionaryghtk.entity.Antonyms;
import com.example.appdictionaryghtk.entity.Synonyms;
import com.example.appdictionaryghtk.entity.Type;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.exceptions.MissingPropertyException;
import com.example.appdictionaryghtk.repository.WordRepository;
import com.example.appdictionaryghtk.service.antonym.AntonymService;
import com.example.appdictionaryghtk.service.synonym.SynonymService;
import com.example.appdictionaryghtk.service.type.TypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("wordmanagement")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WordService implements IWordService {

    WordRepository wordRepository;
    TypeService typeServiceImpl;
    AntonymService antonymService;
    SynonymService synonymService;
    ModelMapper mapper;

    public List<WordDTO> getWordsAfter(Integer lastId, int pageSize) {
        return wordRepository.nextPage(lastId, pageSize)
                .stream().map(word -> mapper.map(word,WordDTO.class)).toList();
    }

    public List<WordDTO> getWordsBefore(Integer firstId, int pageSize) {
        List<Word> words = wordRepository.previousPage(firstId, pageSize);
        return words.stream().map(word -> mapper.map(word, WordDTO.class)).toList();
    }

    public Page<WordDTO> getWordsByPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<Word> wordPage = wordRepository.findAll(pageable);
        return wordPage.map(word -> mapper.map(word, WordDTO.class));
    }


    @Override
    public WordDetail findByID(Integer wordID) {
        Word word =  wordRepository.findById(wordID).orElseThrow(()->new RuntimeException("Word is not exist"));
        WordDetail wordDetail = new WordDetail();
        wordDetail.setName(word.getName());
        wordDetail.setId(word.getId());
        wordDetail.setTypeList(
                word.getTypeList().stream().map(
                        type -> mapper.map(type, TypeDTO.class)
                ).toList()
        );
        wordDetail.setAntonymsList(
                word.getAntonymsList().stream().map(
                        antonyms -> mapper.map(antonyms, AntonymDTO.class)
                ).toList()

        );
        wordDetail.setSynonymsList(
                word.getSynonymsList().stream().map(
                        synonyms -> mapper.map(synonyms, SynonymDTO.class)
                ).toList()

        );
        return wordDetail;
    }

    @Override
    public List<WordDTO> findByName(String name) {
        List<Word> words =  wordRepository.findByName(name);
        return words.stream().map(word -> mapper.map(word, WordDTO.class)).toList();
    }

    @Override
    @Transactional
    public WordDetail create(WordDetail wordDetail) {
        if(wordRepository.existsByName(wordDetail.getName())) throw new RuntimeException("Duplicate for word name");
        if(wordDetail.getTypeList().size() <= 0) throw new MissingPropertyException(("Can it nhat 1 type"));
        Word word = mapper.map(wordDetail, Word.class);
        word.setTypeList(new ArrayList<>());
        word.setAntonymsList(new ArrayList<>());
        word.setSynonymsList(new ArrayList<>());
        wordRepository.save(word);
        addTypeOfWord(wordDetail.getTypeList(), word);
        addAntonymOfWord(wordDetail.getAntonymsList(), word);
        addSynonymOfWord(wordDetail.getSynonymsList(), word);
        return mapper.map(word, WordDetail.class);
    }

    @Override
    public void deleteByID(Integer id) {
        wordRepository.deleteById(id);
    }



    @Override
    @Transactional
    public WordDetail update(Integer wordID, WordDetail wordDetail) {
        Word existedWord = wordRepository.findById(wordID).orElseThrow(()->new RuntimeException("Word is not exist"));
        existedWord.setName(wordDetail.getName());

        List<TypeDTO> nonExistTypes = wordDetail.getTypeList()
                                            .stream()
                                            .filter(typeDetailDTO -> typeDetailDTO.getId()==null)
                                            .toList();

        Map<Integer, TypeDTO> updateTypes = wordDetail.getTypeList()
                                                            .stream()
                                                            .filter(typeDetailDTO -> typeDetailDTO.getId()!=null)
                                                            .collect(Collectors.toMap(TypeDTO::getId, typeDetailDTO -> typeDetailDTO));

        List<TypeDTO> deleteTypes = new ArrayList<>();
        for(Type type: existedWord.getTypeList()){
            if(!updateTypes.containsKey(type.getId())){
                deleteTypes.add(mapper.map(type, TypeDTO.class));
            }
        }

        updateTypeOfWord( updateTypes, existedWord);
        deleteTypeOfWord( updateTypes, nonExistTypes, deleteTypes, existedWord);
        addTypeOfWord(nonExistTypes, existedWord);

        List<AntonymDTO> nonExistAntonym = wordDetail.getAntonymsList()
                .stream()
                .filter(antonymDTO -> antonymDTO.getId()==null)
                .toList();

        Map<Integer, AntonymDTO> savedAntonym  = wordDetail.getAntonymsList()
                .stream()
                .filter(antonymDTO -> antonymDTO.getId()!=null)
                .collect(Collectors.toMap(AntonymDTO::getId, antonymDTO -> antonymDTO));


        List<AntonymDTO> deleteAntonym = new ArrayList<>();
        for(Antonyms antonyms: existedWord.getAntonymsList()){
            if(!savedAntonym.containsKey(antonyms.getId())){
                deleteAntonym.add(mapper.map(antonyms, AntonymDTO.class));
            }
        }
        deleteAntonymOfWord(savedAntonym,deleteAntonym, existedWord);
        addAntonymOfWord(nonExistAntonym, existedWord);



        List<SynonymDTO> nonExistSynonym = wordDetail.getSynonymsList()
                .stream()
                .filter(synonymDTO -> synonymDTO.getId()==null)
                .toList();

        Map<Integer, SynonymDTO> savedSynonym  = wordDetail.getSynonymsList()
                .stream()
                .filter(synonymDTO -> synonymDTO.getId()!=null)
                .collect(Collectors.toMap(SynonymDTO::getId, synonymDTO -> synonymDTO));


        List<SynonymDTO> deleteSynonyms = new ArrayList<>();
        for(Synonyms synonyms: existedWord.getSynonymsList()){
            if(!savedSynonym.containsKey(synonyms.getId())){
                deleteSynonyms.add(mapper.map(synonyms, SynonymDTO.class));
            }
        }
        deleteSynonymOfWord(savedSynonym,deleteSynonyms, existedWord);
        addSynonymOfWord(nonExistSynonym, existedWord);

        return mapper.map(wordRepository.save(existedWord), WordDetail.class);
    }

    void addTypeOfWord(List<TypeDTO> nonExistTypes, Word existedWord){
        for(TypeDTO type : nonExistTypes){
            typeServiceImpl.create(existedWord.getId(), type);
        }
    }

    private void updateTypeOfWord(Map<Integer, TypeDTO> updateTypes, Word existedWord){
        for(Type type: existedWord.getTypeList()){
            if(updateTypes.containsKey(type.getId())){
                TypeDTO update = updateTypes.get(type.getId());
                typeServiceImpl.update(type.getId(), update);
            }
        }
    }

    private void deleteTypeOfWord(Map<Integer, TypeDTO> updateTypes, List<TypeDTO> newType, List<TypeDTO> deleteTypes, Word existedWord ){
        if(existedWord.getTypeList().size() + newType.size()- deleteTypes.size() <=0 )
            throw new MissingPropertyException("Phai ton tai it nhat 1 type trong word");
        existedWord.getTypeList().removeIf(type -> !updateTypes.containsKey(type.getId()));
        for(TypeDTO type: deleteTypes){
            typeServiceImpl.deleteByID(type.getId());
        }

    }

    private void addAntonymOfWord(List<AntonymDTO> nonExistAntonyms, Word existedWord){
        for(AntonymDTO antonym : nonExistAntonyms){
            antonymService.create(existedWord.getId(), antonym);
            Antonyms antonyms = new Antonyms();
            antonyms.setAntonym(existedWord);
//            antonymService.create(antonym.getAntonym().getId(), mapper.map(antonyms, AntonymDTO.class));
        }
    }

    private void deleteAntonymOfWord(Map<Integer, AntonymDTO> savedAntonym,
                                     List<AntonymDTO> deleteAntonym, Word existedWord){
        existedWord.getAntonymsList().removeIf(antonym -> !savedAntonym.containsKey(antonym.getId()));
        for(AntonymDTO antonymDTO: deleteAntonym){
            antonymService.deleteByID(antonymDTO.getId());
        }
    }

    private void addSynonymOfWord(List<SynonymDTO> nonExistSynonyms, Word existedWord){
        for(SynonymDTO synonym : nonExistSynonyms){
            synonymService.create(existedWord.getId(), synonym);
        }
    }

    private void deleteSynonymOfWord(Map<Integer, SynonymDTO> savedSynonym,
                                     List<SynonymDTO> deleteSynonym, Word existedWord){
        existedWord.getSynonymsList().removeIf(synonym -> !savedSynonym.containsKey(synonym.getId()));
        for(SynonymDTO synonymDTO: deleteSynonym){
            synonymService.deleteByID(synonymDTO.getId());
        }
    }

}
