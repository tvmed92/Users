package com.fintech.internship.data.dbase;

import com.fintech.internship.data.pojo.User;

import java.util.List;

public class UserServices {

    private static IUserDB userDB = new UserDB();

    public static void addUserToDB(User user) {
        if (user != null) {
            userDB.addUserToDB(user);
        }
    }

    public static void updateUser(User user) {
        if (user != null) {
            userDB.updateUser(user);
        }
    }

    public static List<User> getUsersFromDB() { return userDB.getUsersFromDB(); }

    public static User getUserByName(String name) { return userDB.getUserByName(name); }
}
