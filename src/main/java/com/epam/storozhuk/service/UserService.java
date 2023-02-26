package com.epam.storozhuk.service;

import com.epam.storozhuk.entity.User;

public interface UserService {
    boolean registerUser(User user);

    User loginUser(String login, String password);
}
