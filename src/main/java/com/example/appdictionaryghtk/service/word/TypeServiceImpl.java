package com.example.appdictionaryghtk.service.word;

import com.example.appdictionaryghtk.entity.Type;
import com.example.appdictionaryghtk.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{
    private TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public List<Type> getTypeByWordId(int id) {
        return typeRepository.findByIdWord(id);
    }
}
