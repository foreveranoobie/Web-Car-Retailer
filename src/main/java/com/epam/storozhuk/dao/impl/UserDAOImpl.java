package com.epam.storozhuk.dao.impl;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.dao.UserDAO;
import com.epam.storozhuk.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class);

    public User getUser(String login, String password, Connection connection) throws SQLException {
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(Const.SQL_SELECT_USER_BY_PARAMS)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                LOGGER.debug("Executing query");
                if (rs.next()) {
                    user = getUserFromRequest(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't get user because of exception: " + e.getMessage());
            throw e;
        }
        return user;
    }

    public List<User> getUsers(Connection connection) throws SQLException {
        List<User> usersList = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery(Const.SQL_SELECT_ALL_USERS)) {
                while (rs.next()) {
                    usersList.add(getUserFromRequest(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't get users list because of exception: " + e.getMessage());
            throw e;
        }
        return usersList;
    }

    public boolean checkUserExists(String login, Connection connection) throws SQLException {
        boolean result;
        try (PreparedStatement preparedStatement = connection.prepareStatement(Const.SQL_SELECT_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                result = !rs.next();
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't check user existence because of exception: " + e.getMessage());
            throw e;
        }
        LOGGER.debug("returing ResultSet");
        return result;
    }

    public boolean addUser(User user, Connection connection) throws SQLException {
        LOGGER.debug("started");
        String request = String.format(Const.SQL_SELECT_INSERT_USER, Const.TABLE_USERS_LOGIN, Const.TABLE_USERS_PASSWORD,
                Const.TABLE_USERS_FNAME, Const.TABLE_USERS_LNAME, Const.TABLE_USERS_EMAIL, Const.TABLE_USERS_NEWS, Const.TABLE_USERS_AVATAR);
        try (PreparedStatement st = connection.prepareStatement(request)) {
            st.setString(1, user.getUserName());
            st.setString(2, user.getPassword());
            st.setString(3, user.getFirstName());
            st.setString(4, user.getLastName());
            st.setString(5, user.getEmail());
            st.setBoolean(6, user.isNewsAndUpdates());
            st.setString(7, user.getAvatarPath());
            int result = st.executeUpdate();
            if (result == 0) {
                LOGGER.debug("Insertion to database hasn't changed any rows. Returning false");
                return false;
            }
        } catch (SQLException e) {
            LOGGER.debug("Couldn't add user to database because of exception: " + e.getMessage());
            throw e;
        }
        return true;
    }

    private User getUserFromRequest(ResultSet resultSet) throws SQLException {
        boolean newsLetters = resultSet.getInt(7) == 1;
        return new User(resultSet.getString(2), resultSet.getString(3), resultSet.getString(6),
                resultSet.getString(4), resultSet.getString(5), newsLetters, resultSet.getString(8));
    }
}
