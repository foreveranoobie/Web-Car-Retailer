package com.epam.storozhuk.dto;

public class UserDTO {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private boolean newsAndUpdates;
    private String avatarPath;

    public UserDTO() {
    }

    public UserDTO(String userName, String password, String email, String firstName, String lastName, boolean newsAndUpdates, String avatarPath) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.newsAndUpdates = newsAndUpdates;
        this.avatarPath = avatarPath;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isNewsAndUpdates() {
        return newsAndUpdates;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNewsAndUpdates(boolean newsAndUpdates) {
        this.newsAndUpdates = newsAndUpdates;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
