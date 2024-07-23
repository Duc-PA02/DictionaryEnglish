package com.example.appdictionaryghtk.service.email;

import com.example.appdictionaryghtk.entity.ConfirmEmail;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.exceptions.ConfirmEmailExpired;
import com.example.appdictionaryghtk.exceptions.DataNotFoundException;
import com.example.appdictionaryghtk.repository.ConfirmEmailRepository;
import com.example.appdictionaryghtk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ConfirmEmailService implements IConfirmEmailService{
    private final ConfirmEmailRepository confirmEmailRepository;
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    @Override
    public void sendConfirmEmail(String email, String content) throws DataNotFoundException {
        String subject ="Dictionary GHTK";
        sendEmail(email,subject,"New password: " + content);

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new DataNotFoundException("User not found"));
        ConfirmEmail confirmEmail = ConfirmEmail.builder()
                .user(user)
                .code(generateConfirmCode())
                .requiredTime(LocalDateTime.now())
                .expiredTime(LocalDateTime.now().plusSeconds(120))
                .isConfirm(false)
                .build();
        confirmEmailRepository.save(confirmEmail);
    }

    @Override
    public boolean confirmEmail(String confirmCode) throws Exception {
        ConfirmEmail confirmEmail = confirmEmailRepository.findConfirmEmailByCode(confirmCode);

        if (confirmEmail == null){
            throw new DataNotFoundException("code is incorrect");
        }
        if (isExpired(confirmEmail)){
            throw new ConfirmEmailExpired("code has expired");
        }
        confirmEmail.setIsConfirm(true);
        confirmEmailRepository.save(confirmEmail);
        return true;
    }
    private String generateConfirmCode() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        return String.valueOf(randomNumber);
    }

    public void sendEmail(String to, String subject, String content){
        SimpleMailMessage msg =new SimpleMailMessage();
        msg.setFrom("ducpa2002@gmail.com");
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(content);
        javaMailSender.send(msg);
    }

    public boolean isExpired(ConfirmEmail confirmEmail) {
        return LocalDateTime.now().isAfter(confirmEmail.getExpiredTime());
    }
}
