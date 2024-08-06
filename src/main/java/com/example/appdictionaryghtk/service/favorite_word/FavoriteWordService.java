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
    public List<FavoriteWordResponse> getFavoriteWordByUid(int uid) {
        List<FavoriteWord> favoriteWords = favoriteWordRepository.findByUserId(uid, Sort.by(Sort.Direction.DESC, "id"));
        return CollectionUtils.isEmpty(favoriteWords) ? Collections.emptyList(): favoriteWords.stream()
                .map(favoriteWord -> modelMapper.map(favoriteWord, FavoriteWordResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<FavoriteWordResponse> getFavoriteWordByUidSortByWordNameDESC(int uid) {
        List<FavoriteWord> favoriteWords = favoriteWordRepository.findByUserId(uid, Sort.by(Sort.Direction.DESC, "words.name"));
        return CollectionUtils.isEmpty(favoriteWords) ? Collections.emptyList(): favoriteWords.stream()
                .map(favoriteWord -> modelMapper.map(favoriteWord, FavoriteWordResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<FavoriteWordResponse> getFavoriteWordByUidSortByWordNameASC(int uid) {
        List<FavoriteWord> favoriteWords = favoriteWordRepository.findByUserId(uid, Sort.by(Sort.Direction.ASC, "words.name"));
        return CollectionUtils.isEmpty(favoriteWords) ? Collections.emptyList(): favoriteWords.stream()
                .map(favoriteWord -> modelMapper.map(favoriteWord, FavoriteWordResponse.class)).collect(Collectors.toList());
    }

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
    public List<FavoriteWordResponse> getFavoriteByUserIdAndWordsNameContaining(int uid, String name) {
        if(!favoriteWordRepository.existsByWordsNameContainingAndUserId(name, uid)){
            throw new EntityExistsException("Word or user doesn't exist");
        }
        List<FavoriteWord> favoriteWords = favoriteWordRepository.findByUserIdAndWordsNameContaining(uid, name);
        return CollectionUtils.isEmpty(favoriteWords) ? Collections.emptyList(): favoriteWords.stream()
                .map(favoriteWord -> modelMapper.map(favoriteWord, FavoriteWordResponse.class)).collect(Collectors.toList());
    }


}
