package com.example.appdictionaryghtk.service.favorite_word;

import com.example.appdictionaryghtk.entity.FavoriteWord;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.repository.FavoriteWordRepository;
import com.example.appdictionaryghtk.repository.UserRepository;
import com.example.appdictionaryghtk.repository.WordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteWordService implements IFavoriteWordService {

    private final FavoriteWordRepository favoriteWordRepository;
    private final WordRepository wordRepository;
    private final UserRepository userRepository;

    @Override
    public List<FavoriteWord> getFavoriteWord(int uid) {
        return favoriteWordRepository.findByUserId(uid);
    }

    @Override
    @Transactional
    public void deleteFavoriteWord(int fwid) {
        favoriteWordRepository.deleteById(fwid);
    }

    @Override
    public String addFavoriteWord(int uid, int wid) {
        // Kiểm tra xem từ đã có trong danh sách yêu thích của người dùng chung
        User user = userRepository.findById(uid).get();
        Word word = wordRepository.findById(wid).get();
        if (favoriteWordRepository.existsByWordsAndUser(word, user) ){
            return "Word already in favorites!";
        }
        FavoriteWord favoriteWord = new FavoriteWord();
        favoriteWord.setUser(user);
        favoriteWord.setWords(word);
        favoriteWordRepository.save(favoriteWord);
        return "Word added to favorites successfully.";
    }


}
