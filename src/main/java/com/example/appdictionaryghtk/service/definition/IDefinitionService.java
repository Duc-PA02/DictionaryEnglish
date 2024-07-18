package com.example.appdictionaryghtk.service.definition;

import com.example.appdictionaryghtk.dtos.word_management.definition.DefinitionDTO;

import java.util.List;

public interface IDefinitionService {
    DefinitionDTO create(Integer typeID,DefinitionDTO definitionDTO);
    DefinitionDTO update(Integer id, DefinitionDTO definitionDTO);
    DefinitionDTO findByID(Integer id);
    void deleteByID(Integer id);
//    List<DefinitionDTO> findAll();
//    List<DefinitionDTO> findByTypeID(Integer typeID);
}
