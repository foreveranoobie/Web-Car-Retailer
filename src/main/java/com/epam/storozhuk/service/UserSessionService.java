package com.epam.storozhuk.service;

import com.epam.storozhuk.entity.User;

import javax.servlet.http.HttpSession;

public interface UserSessionService {
    void saveUser(HttpSession session, User user, CartService cartService);

    void deleteUser(HttpSession session);
}
