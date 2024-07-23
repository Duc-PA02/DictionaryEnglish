package com.example.appdictionaryghtk.service.pronunciation;

import com.example.appdictionaryghtk.dtos.word_management.pronunciation.PronunciationDTO;
import com.example.appdictionaryghtk.entity.Pronunciations;
import com.example.appdictionaryghtk.entity.Type;
import com.example.appdictionaryghtk.exceptions.DataNotFoundException;
import com.example.appdictionaryghtk.repository.PronunciationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PronunciationService implements IPronunciationService {
    PronunciationRepository pronunciationRepository;
    ModelMapper mapper;


    @Override
    public PronunciationDTO findByID(Integer id)  {
        Pronunciations pronunciations = pronunciationRepository.findById(id)
                .orElseThrow(()->new RuntimeException("pronunciation not exist"));
        return mapper.map(pronunciations, PronunciationDTO.class);
    }

    @Override
    public PronunciationDTO create(Integer typeId, PronunciationDTO pronunciationDTO) {
        Pronunciations pronunciations = mapper.map(pronunciationDTO, Pronunciations.class);
        pronunciations.setType(new Type());
        pronunciations.getType().setId(typeId);
        return mapper.map(pronunciationRepository.save(pronunciations), PronunciationDTO.class);
    }

    @Override
    public PronunciationDTO update(Integer id, PronunciationDTO pronunciationDTO) {
        Pronunciations pronunciations = pronunciationRepository.findById(id)
                .orElseThrow(()->new RuntimeException("pronunciation is not exist"));
        pronunciations.setPronunciation(pronunciationDTO.getPronunciation());
        pronunciations.setAudio(pronunciationDTO.getAudio());
        pronunciations.setRegion(pronunciationDTO.getRegion());
        return mapper.map(pronunciationRepository.save(pronunciations), PronunciationDTO.class);
    }

    @Override
    @Transactional
    public void deleteByID(Integer id) {
        pronunciationRepository.deleteById(id);
    }

}
