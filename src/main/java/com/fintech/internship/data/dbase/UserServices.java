package com.fintech.internship.data.dbase;

import com.fintech.internship.data.pojo.User;

import java.util.Collections;
import java.util.List;

public class UserServices {

    private static IUserDB userDB = new UserDB();


    public void addUsersToDB (List<User> users){
        for (User user : users){
            List<User> usersByFNSNandP = Collections.singletonList(getUserByName(user.getFirstName(), user.getSecondName(), user.getLastName()));
            if(usersByFNSNandP.isEmpty()){
                addUser(user);
                addAddress(user);
            }else {
                updateUser(user);
                updateAddress(user);
            }
        }
    }

    public static void addUser(User user) {
        if (user != null) {
            userDB.addUser(user);
        }
    }

    public static void addAddress(User user) {
        if (user != null) {
            userDB.addAddress(user);
        }
    }

    public static void updateUser(User user) {
        if (user != null) {
            userDB.updateUser(user);
        }
    }

    public static void updateAddress(User user) {
        if (user != null) {
            userDB.updateAddress(user);
        }
    }

    public static List<User> getUsersFromDB() { return userDB.getUsersFromDB(); }

    public static User getUserByName(String firstName, String secondName, String lastName, String name) { return userDB.getUserByName(name); }
}
