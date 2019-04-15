package com.fintech.internship.data.dbase;

import com.fintech.internship.data.pojo.User;

import java.util.List;

public interface IUserDB {

    List<User> getUsersFromDB();

    void addUserToDB(User user);

    boolean updateUser(User user);

    User getUserByName(String name);

}
