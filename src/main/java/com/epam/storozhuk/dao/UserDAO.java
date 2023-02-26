package com.epam.storozhuk.dao;

import com.epam.storozhuk.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    User getUser(String login, String password, Connection connection) throws SQLException;

    List<User> getUsers(Connection connection) throws SQLException;

    boolean checkUserExists(String login, Connection connection) throws SQLException;

    boolean addUser(User user, Connection connection) throws SQLException;
}
