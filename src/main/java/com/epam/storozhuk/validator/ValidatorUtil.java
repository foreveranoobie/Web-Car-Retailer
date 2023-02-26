package com.epam.storozhuk.validator;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.dto.UserDTO;

import java.util.HashMap;
import java.util.Map;

public class ValidatorUtil {
    public static Map<String, String> validateRegisterFields(UserDTO userDTO) {
        Map<String, String> mapForErrors = new HashMap<>();

        if (!isValidLogin(userDTO.getUserName())) {
            mapForErrors.put(Const.LOGIN_ERROR, Const.LOGIN_VALUE_ERROR);
        }

        if (!isValidPassword(userDTO.getPassword())) {
            mapForErrors.put(Const.PASSWORD_ERROR, Const.PASSWORD_VALUE_ERROR);
        }

        if (!isValidFirstName(userDTO.getFirstName())) {
            mapForErrors.put(Const.FIRSTNAME_ERROR, Const.FIRSTNAME_VALUE_ERROR);
        }

        if (!isValidLastName(userDTO.getLastName())) {
            mapForErrors.put(Const.LASTNAME_ERROR, Const.LASTNAME_VALUE_ERROR);
        }

        if (!isValidEmail(userDTO.getEmail())) {
            mapForErrors.put(Const.EMAIL_ERROR, Const.EMAIL_VALUE_ERROR);
        }

        return mapForErrors;
    }

    private static boolean isValidLogin(String login) {
        return login != null && login.matches(Const.LOGIN_PATTERN);
    }

    private static boolean isValidPassword(String password) {
        return password != null && password.matches(Const.PASSWORD_PATTERN);
    }

    private static boolean isValidFirstName(String firstName) {
        return firstName != null && firstName.matches(Const.FNAME_PATTERN);
    }

    private static boolean isValidLastName(String lastName) {
        return lastName != null && lastName.matches(Const.LNAME_PATTERN);
    }

    private static boolean isValidEmail(String email) {
        return email != null && email.matches(Const.EMAIL_PATTERN);
    }
}
