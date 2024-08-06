package com.example.appdictionaryghtk.service.type;

import com.example.appdictionaryghtk.dtos.word_management.definition.DefinitionDTO;
import com.example.appdictionaryghtk.dtos.word_management.pronunciation.PronunciationDTO;
import com.example.appdictionaryghtk.dtos.word_management.type.TypeDTO;
import com.example.appdictionaryghtk.entity.Definitions;
import com.example.appdictionaryghtk.entity.Pronunciations;
import com.example.appdictionaryghtk.entity.Type;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.exceptions.MissingPropertyException;
import com.example.appdictionaryghtk.repository.TypeRepository;
import com.example.appdictionaryghtk.repository.WordRepository;
import com.example.appdictionaryghtk.service.definition.DefinitionService;
import com.example.appdictionaryghtk.service.pronunciation.PronunciationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeService implements ITypeService {

    TypeRepository typeRepository;
    DefinitionService definitionService;
    PronunciationService pronunciationService;
    WordRepository wordRepository;
    ModelMapper mapper;

    @Override
    @Transactional
    public void deleteByID(Integer id) {
        typeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TypeDTO create(Integer wordID, TypeDTO typeDTO) {
        if(typeDTO.getPronunciationsList().size() <= 0 )
            throw new RuntimeException(("Can it nhat 1 pronunciations"));
        if(typeDTO.getDefinitionsList().size() <= 0 )
            throw new RuntimeException(("Can it nhat 1 definitions"));
        Word word = wordRepository.findById(wordID).orElseThrow(()->new RuntimeException("word is not exist"));

        Type type = new Type();
        type.setType(typeDTO.getType());
        type.setWord(word);
        word.getTypeList().add(type);

        type.setDefinitionsList(new ArrayList<>());
        type.setPronunciationsList(new ArrayList<>());

        addPronunciationsOfType(typeDTO.getPronunciationsList(), type);
        addDefinitionOfType(typeDTO.getDefinitionsList(), type);
        return mapper.map(typeRepository.save(type), TypeDTO.class);
    }

    @Override
    @Transactional
    public TypeDTO update(Integer typeID, TypeDTO typeDTO) {
        Type type = typeRepository.findById(typeID).orElseThrow(()->new RuntimeException("type is not exist"));
        type.setType(typeDTO.getType());
        updateDefinition(type, typeDTO);
        updatePronunciations(type, typeDTO);
        return mapper.map(typeRepository.save(type), TypeDTO.class);
    }

    private void updateDefinition(Type type, TypeDTO typeDTO){
        List<DefinitionDTO> nonExistedDefinitions = typeDTO.getDefinitionsList().stream()
                .filter(definitionDTO -> definitionDTO.getId()==null)
                .toList();

        Map<Integer, DefinitionDTO> updateDefinitions = typeDTO.getDefinitionsList()
                .stream().filter(definitionDTO -> definitionDTO.getId()!=null)
                .collect(Collectors.toMap(DefinitionDTO::getId,definitionDTO -> definitionDTO));

        List<DefinitionDTO> deleteDefinitions = new ArrayList<>();
        for(Definitions existingDefinition : type.getDefinitionsList()){
            if(!updateDefinitions.containsKey(existingDefinition.getId())){
                deleteDefinitions.add(mapper.map(existingDefinition, DefinitionDTO.class));
            }
        }

        updateDefinitionOfType(updateDefinitions, type);
        deleteDefinitionOfType(updateDefinitions, deleteDefinitions, type);
        addDefinitionOfType(nonExistedDefinitions, type);
    }

    private void updatePronunciations(Type type, TypeDTO typeDTO){
        List<PronunciationDTO> nonExistedPronunciations = typeDTO.getPronunciationsList().stream()
                .filter(definitionDTO -> definitionDTO.getId()==null)
                .toList();

        Map<Integer, PronunciationDTO> updatePronunciations = typeDTO.getPronunciationsList()
                .stream().filter(pronunciationDTO -> pronunciationDTO.getId()!=null)
                .collect(Collectors.toMap(PronunciationDTO::getId,pronunciationDTO -> pronunciationDTO));

        List<PronunciationDTO> deletePronunciations = new ArrayList<>();
        for (Pronunciations existingPronunciation : type.getPronunciationsList()) {
            if (!updatePronunciations.containsKey(existingPronunciation.getId())) {
                deletePronunciations.add(mapper.map(existingPronunciation, PronunciationDTO.class));
            }
        }

        updatePronunciationOfType(updatePronunciations, type);
        deletePronunciationsOfType(updatePronunciations, deletePronunciations, type);
        addPronunciationsOfType(nonExistedPronunciations, type);
    }

    private void addDefinitionOfType(List<DefinitionDTO> nonExistedDefinitions, Type type){
        for(DefinitionDTO definitionDTO: nonExistedDefinitions){
            Definitions definitions = mapper.map(definitionDTO, Definitions.class);
            definitions.setType(type);
            type.getDefinitionsList().add(definitions);
        }
    }

    private void updateDefinitionOfType(Map<Integer, DefinitionDTO> updateDefinitions, Type type){
        for (Definitions existingDefinition : type.getDefinitionsList()) {
            if (updateDefinitions.containsKey(existingDefinition.getId())) {
                DefinitionDTO definitionDTO = updateDefinitions.get(existingDefinition.getId());
                existingDefinition.setDefinition(definitionDTO.getDefinition());
                existingDefinition.setExamples(definitionDTO.getExamples());
            }
        }
    }

    private void deleteDefinitionOfType(Map<Integer, DefinitionDTO> updateDefinitions,
                                        List<DefinitionDTO> deleteDefinitions , Type type){
        if(type.getDefinitionsList().size() - deleteDefinitions.size() <=0)
            throw new MissingPropertyException("Phai ton tai it nhat 1 phan tu definitions");
        type.getDefinitionsList().removeIf(definition -> !updateDefinitions.containsKey(definition.getId()));
        for(DefinitionDTO definitionDTO: deleteDefinitions){
            definitionService.deleteByID(definitionDTO.getId());
        }
    }

    private void addPronunciationsOfType(List<PronunciationDTO> nonExistedPronunciations, Type type){
        for(PronunciationDTO pronunciationDTO: nonExistedPronunciations){
            Pronunciations pronunciations = mapper.map(pronunciationDTO, Pronunciations.class);
            pronunciations.setType(type);
            type.getPronunciationsList().add(pronunciations);
        }
    }

    private void deletePronunciationsOfType(Map<Integer, PronunciationDTO> updatePronunciations,
                                            List<PronunciationDTO> deletePronunciations, Type type){
        if(type.getPronunciationsList().size() - deletePronunciations.size() <=0)
            throw new MissingPropertyException("Phai ton tai it nhat 1 pronunciations");
        type.getPronunciationsList().removeIf(pronunciations -> !updatePronunciations.containsKey(pronunciations.getId()));
        for(PronunciationDTO pronunciationDTO: deletePronunciations){
            pronunciationService.deleteByID(pronunciationDTO.getId());
        }
    }

    private void updatePronunciationOfType(Map<Integer, PronunciationDTO> updatePronunciations, Type type){
        for (Pronunciations existingPronunciation : type.getPronunciationsList()) {
            if (updatePronunciations.containsKey(existingPronunciation.getId())) {
                PronunciationDTO pronunciationDTO = updatePronunciations.get(existingPronunciation.getId());
                existingPronunciation.setRegion(pronunciationDTO.getRegion());
                existingPronunciation.setAudio(pronunciationDTO.getAudio());
                existingPronunciation.setPronunciation(pronunciationDTO.getPronunciation());
            }
        }
    }



}
