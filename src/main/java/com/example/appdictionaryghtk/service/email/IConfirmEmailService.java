package com.example.appdictionaryghtk.service.email;

public interface IConfirmEmailService {
    void sendConfirmEmail(String email, String content) throws Exception;
    boolean confirmEmail(String confirmCode) throws Exception;
}
