package com.example.appdictionaryghtk.service.type;

import com.example.appdictionaryghtk.dtos.word_management.type.TypeDTO;
import com.example.appdictionaryghtk.entity.Type;
import com.example.appdictionaryghtk.entity.Words;
import com.example.appdictionaryghtk.exceptions.DataNotFoundException;
import com.example.appdictionaryghtk.repository.TypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeService implements ITypeService{

    TypeRepository typeRepository;
    ModelMapper mapper;

    @Override
    public TypeDTO findByID(Integer id) {
        Type type = typeRepository.findById(id).orElseThrow(()->new DataNotFoundException("type is not exist"));
        return mapper.map(type, TypeDTO.class);
    }

    @Override
    public TypeDTO create(Integer wordID, TypeDTO typeDTO) {
        Type type = mapper.map(typeDTO, Type.class);
        type.setWord(new Words());
        type.getWord().setId(wordID);
        return mapper.map(typeRepository.save(type), TypeDTO.class);
    }

    @Override
    public TypeDTO update(Integer typeID, TypeDTO typeDTO) {
        Type type = typeRepository.findById(typeID).orElseThrow(()->new DataNotFoundException("type is not exist"));
        type.setType(typeDTO.getType());
        return mapper.map(typeRepository.save(type), TypeDTO.class);
    }

    @Override
    public void deleteByID(Integer id) {

        typeRepository.deleteById(id);
    }

    @Override
    public List<TypeDTO> findByWordId(Integer wordID) {
        return typeRepository.findByWordId(wordID)
                .stream()
                .map((type)->mapper.map(type, TypeDTO.class))
                .toList();
    }

}
