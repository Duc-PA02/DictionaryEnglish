package com.example.appdictionaryghtk.service.type;


import com.example.appdictionaryghtk.dtos.word_management.type.TypeDTO;

public interface ITypeService {
//    TypeDTO findByID(Integer id) ;
    TypeDTO create(Integer wordID, TypeDTO typeDTO);
    TypeDTO update(Integer typeID, TypeDTO typeDTO);
    void deleteByID(Integer id);

}
