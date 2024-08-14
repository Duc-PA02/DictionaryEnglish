package com.example.appdictionaryghtk.service.favorite_word;

import com.example.appdictionaryghtk.dtos.response.favoriteword.FavoriteWordResponse;
import com.example.appdictionaryghtk.entity.FavoriteWord;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.exceptions.EntityExistsException;
import com.example.appdictionaryghtk.repository.FavoriteWordRepository;
import com.example.appdictionaryghtk.repository.UserRepository;
import com.example.appdictionaryghtk.repository.WordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteWordService implements IFavoriteWordService {

    private final FavoriteWordRepository favoriteWordRepository;
    private final WordRepository wordRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void deleteFavoriteWord(int fwid) {
        favoriteWordRepository.deleteById(fwid);
    }

    @Override
    @Transactional
    public void deleteAllFavoriteByUser(int uid) {
        favoriteWordRepository.deleteByUser_Id(uid);
    }

    @Override
    public FavoriteWordResponse addFavoriteWord(int uid, int wid) {
        User user = userRepository.findById(uid).get();
        Word word = wordRepository.findById(wid).get();

        if (favoriteWordRepository.existsByWordsAndUser(word, user) ){
            throw new EntityExistsException("Word already in favorites!");
        }
        FavoriteWord favoriteWord = new FavoriteWord();
        favoriteWord.setUser(user);
        favoriteWord.setWords(word);
        return modelMapper.map(favoriteWordRepository.save(favoriteWord), FavoriteWordResponse.class);
    }

    @Override
    public List<FavoriteWordResponse> getFavoriteByUserIdAndWordsNameContaining(int uid, String name, String sortDirection) {
        if(!favoriteWordRepository.existsByWordsNameContainingAndUserId(name, uid)){
            throw new EntityExistsException("Word or user doesn't exist");
        }
        List<FavoriteWord> favoriteWords = new ArrayList<>();
        if(name == null || name.equalsIgnoreCase("")){
            if(sortDirection.equalsIgnoreCase("date_ascending")){
                favoriteWords = favoriteWordRepository.findByUserId(uid, Sort.by(Sort.Direction.ASC, "id"));
            }else if(sortDirection.equalsIgnoreCase("date_descending")){
                favoriteWords = favoriteWordRepository.findByUserId(uid, Sort.by(Sort.Direction.DESC, "id"));
            }else if(sortDirection.equalsIgnoreCase("dictionary_asc")){
                favoriteWords = favoriteWordRepository.findByUserId(uid, Sort.by(Sort.Direction.ASC, "words.name"));
            }else if(sortDirection.equalsIgnoreCase("dictionary_desc")){
                favoriteWords = favoriteWordRepository.findByUserId(uid, Sort.by(Sort.Direction.DESC, "words.name"));
            }
        }else{
            if(sortDirection.equalsIgnoreCase("date_ascending")){
                favoriteWords = favoriteWordRepository.findByUserIdAndWordsNameContaining(uid, name, Sort.by(Sort.Direction.ASC, "id"));
            }else if(sortDirection.equalsIgnoreCase("date_descending")){
                favoriteWords = favoriteWordRepository.findByUserIdAndWordsNameContaining(uid, name, Sort.by(Sort.Direction.DESC, "id"));
            }else if(sortDirection.equalsIgnoreCase("dictionary_asc")){
                favoriteWords = favoriteWordRepository.findByUserIdAndWordsNameContaining(uid, name, Sort.by(Sort.Direction.ASC, "words.name"));
            }else if(sortDirection.equalsIgnoreCase("dictionary_desc")){
                favoriteWords = favoriteWordRepository.findByUserIdAndWordsNameContaining(uid, name, Sort.by(Sort.Direction.DESC, "words.name"));
            }
        }
        return CollectionUtils.isEmpty(favoriteWords) ? Collections.emptyList(): favoriteWords.stream()
                .map(favoriteWord -> modelMapper.map(favoriteWord, FavoriteWordResponse.class)).collect(Collectors.toList());
    }


}
