package com.example.appdictionaryghtk.service.definition;

import com.example.appdictionaryghtk.dtos.word_management.definition.DefinitionDTO;
import com.example.appdictionaryghtk.entity.Definitions;
import com.example.appdictionaryghtk.entity.Type;
import com.example.appdictionaryghtk.exceptions.DataNotFoundException;
import com.example.appdictionaryghtk.repository.DefinitionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DefinitionService implements IDefinitionService {

    DefinitionRepository definitionRepository;
    ModelMapper mapper;

    @Override
    public DefinitionDTO create(Integer typeID, DefinitionDTO definitionDTO) {
        Definitions definitions = mapper.map(definitionDTO, Definitions.class);
        definitions.setType(new Type());
        definitions.getType().setId(typeID);
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
    public void deleteByID(Integer id) {
        definitionRepository.deleteById(id);
    }

    @Override
    public List<DefinitionDTO> findByTypeID(Integer typeID) {
        return definitionRepository.findByTypeId(typeID)
                .stream()
                .map((definition)->mapper.map(definition, DefinitionDTO.class))
                .toList();
    }
}
