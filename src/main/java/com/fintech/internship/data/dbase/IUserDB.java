package com.fintech.internship.data.dbase;

import com.fintech.internship.data.pojo.User;

import java.util.List;

public interface IUserDB {

    List<User> getUsersFromDB();

    void addUsersToDB(List<User> users);

    void addUser(User user);

    void addAddress(User user);

    boolean updateUser(User user);

    boolean updateAddress(User user);

    User getUserByName(String first_name, String second_name, String last_name);

}
