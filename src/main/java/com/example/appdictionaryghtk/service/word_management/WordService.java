package com.example.appdictionaryghtk.service.word_management;

import com.example.appdictionaryghtk.dtos.word_management.type.TypeDTO;
import com.example.appdictionaryghtk.dtos.word_management.word.WordDetail;
import com.example.appdictionaryghtk.entity.Type;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.exceptions.MissingPropertyException;
import com.example.appdictionaryghtk.repository.WordRepository;
import com.example.appdictionaryghtk.service.type.TypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
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
    ModelMapper mapper;

    @Override
    public Page<WordDetail> findAll(Integer pageNumber, Integer pageSize, String sort) {
        Pageable pageable = null;
        if (sort != null) {
            pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.ASC, sort);
        } else {
            pageable = PageRequest.of(pageNumber-1, pageSize);
        }
        Page<Word> words = wordRepository.findAll(pageable);
        return new PageImpl<>(
                words.stream().map(
                        word->mapper.map(word, WordDetail.class)
                ).toList(), pageable, words.getTotalElements());
    }

    @Override
    public WordDetail findByID(Integer wordID) {
        Word word =  wordRepository.findById(wordID).orElseThrow(()->new RuntimeException("Word is not exist"));
        return mapper.map(word, WordDetail.class);
    }

    @Override
    @Transactional
    public WordDetail create(WordDetail wordDetail) {
        if(wordRepository.existsByName(wordDetail.getName())) throw new RuntimeException("Duplycate for word name");
        if(wordDetail.getTypeList().size() <= 0) throw new MissingPropertyException(("Can it nhat 1 type"));
        Word word = mapper.map(wordDetail, Word.class);
        word.setTypeList(new ArrayList<>());
        wordRepository.save(word);
        addTypeOfWord(wordDetail.getTypeList(), word);
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
        deleteTypeOfWord( updateTypes, deleteTypes, existedWord);
        addTypeOfWord(nonExistTypes, existedWord);
        return mapper.map(wordRepository.save(existedWord), WordDetail.class);
    }

    private void addTypeOfWord(List<TypeDTO> nonExistTypes, Word existedWord){
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
    private void deleteTypeOfWord(Map<Integer, TypeDTO> updateTypes, List<TypeDTO> deleteTypes, Word existedWord ){
        if(existedWord.getTypeList().size() - deleteTypes.size() <=0 )
            throw new MissingPropertyException("Phai ton tai it nhat 1 type trong word");
        existedWord.getTypeList().removeIf(type -> !updateTypes.containsKey(type.getId()));
        for(TypeDTO type: deleteTypes){
            typeServiceImpl.deleteByID(type.getId());
        }

    }


}
