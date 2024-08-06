package com.example.appdictionaryghtk.service.email;

public interface IConfirmEmailService {
    void sendConfirmEmail(String email, String code) ;
    boolean confirmEmail(String confirmCode);
}
