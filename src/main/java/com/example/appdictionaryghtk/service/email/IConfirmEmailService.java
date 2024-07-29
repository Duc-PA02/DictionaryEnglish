package com.example.appdictionaryghtk.service.email;

public interface IConfirmEmailService {
    void sendConfirmEmail(String email, String content) ;
    boolean confirmEmail(String confirmCode);
}
