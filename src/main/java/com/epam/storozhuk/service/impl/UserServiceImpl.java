package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.dao.ManagerDB;
import com.epam.storozhuk.dao.UserDAO;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.service.UserService;
import com.epam.storozhuk.util.HashUtil;
import org.apache.log4j.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private UserDAO userDAO;
    private ManagerDB managerDB;

    public UserServiceImpl(UserDAO userDAO, ManagerDB managerDB) {
        this.userDAO = userDAO;
        this.managerDB = managerDB;
    }


    public User loginUser(String login, String password) {
        LOGGER.debug("started");
        User user = managerDB.doNonTransactionQuery((connection) ->
                userDAO.getUser(login, HashUtil.hashPassword(password), connection));
        if (user != null) {
            LOGGER.debug("Got the user with login: " + user.getUserName());
        }
        return user;
    }

    public boolean registerUser(User user) {
        user.setPassword(HashUtil.hashPassword(user.getPassword()));
        Boolean userValuesAreUnique = checkForUserExistence(user);
        LOGGER.debug("User values are unique: " + userValuesAreUnique);
        if (userValuesAreUnique != null && userValuesAreUnique) {
            Boolean result = managerDB.doTransactionQuery((connection) -> userDAO.addUser(user, connection));
            return result != null && result;
        }
        return false;
    }

    private Boolean checkForUserExistence(User user) {
        return managerDB
                .doNonTransactionQuery((connection) -> userDAO.checkUserExists(user.getUserName(), connection));
    }
}
