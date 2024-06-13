package ru.study.base.tgjavabot.service;

public interface UserService {
    void registration(String username, String password);
    String getUserRole(String username);
    void putUserRole(String username, String role);
}
