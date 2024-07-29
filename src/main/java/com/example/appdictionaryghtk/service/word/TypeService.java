package com.example.appdictionaryghtk.service.word;

import com.example.appdictionaryghtk.entity.Type;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeService {
    public List<Type> getTypeByWordId(int id);
}
