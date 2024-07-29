package com.example.appdictionaryghtk.service.definition;

import com.example.appdictionaryghtk.dtos.word_management.definition.DefinitionDTO;
import com.example.appdictionaryghtk.entity.Definitions;
import com.example.appdictionaryghtk.entity.Type;
import com.example.appdictionaryghtk.exceptions.DataNotFoundException;
import com.example.appdictionaryghtk.repository.DefinitionRepository;
import com.example.appdictionaryghtk.repository.TypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DefinitionService implements IDefinitionService {

    DefinitionRepository definitionRepository;
    TypeRepository typeRepository;
    ModelMapper mapper;

    @Override
    public DefinitionDTO create(Integer typeID, DefinitionDTO definitionDTO) {
        Definitions definitions = mapper.map(definitionDTO, Definitions.class);
        Type type = typeRepository.findById(typeID).orElseThrow(()->new DataNotFoundException("Type not exist"));
        definitions.setType(type);
        return mapper.map( definitionRepository.save(definitions), DefinitionDTO.class);
    }

    @Override
    public DefinitionDTO update(Integer id, DefinitionDTO definitionDTO) {
        Definitions definitions = definitionRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("definition is not exist"));
        definitions.setDefinition(definitionDTO.getDefinition());
        definitions.setExamples(definitionDTO.getExamples());
        return mapper.map( definitionRepository.save(definitions), DefinitionDTO.class);
    }

    @Override
    public DefinitionDTO findByID(Integer id) {
        Definitions definitions = definitionRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("definition not exist"));
        return mapper.map(definitions, DefinitionDTO.class);
    }

    @Override
    @Transactional
    public void deleteByID(Integer id) {
        definitionRepository.deleteById(id);
    }

}
