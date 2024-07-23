package com.example.appdictionaryghtk.service.definition;

import com.example.appdictionaryghtk.dtos.word_management.definition.DefinitionDTO;

public interface DefinitionService {
    DefinitionDTO create(Integer typeID,DefinitionDTO definitionDTO);
    DefinitionDTO update(Integer id, DefinitionDTO definitionDTO);
    DefinitionDTO findByID(Integer id);
    void deleteByID(Integer id);
}
