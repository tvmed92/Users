package com.fintech.internship.data.dbase;

import com.fintech.internship.data.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDB implements IUserDB {

    static Connection getConnection() throws SQLException {

        Properties dbProperties = new Properties();
        dbProperties.load(UserDB.class.getResourceAsStream("dbProperties"));

        try {Connection connection = DriverManager.getConnection(dbProperties.getProperty("instance"), dbProperties);

        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }

    public List<User> getUsersFromDB() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id,first_name,second_name,last_name,birthday,gender,inn FROM USERS_DATA.USERS");
             ResultSet resultSet = statement.executeQuery()) {

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                int uId = resultSet.getInt(1);
                String first_name = resultSet.getString(2);
                String second_name = resultSet.getString(3);
                String last_name = resultSet.getString(4);
                Date birthday = resultSet.getDate(5);
                String gender = resultSet.getString(6);
                String inn = resultSet.getString(7);

                User user = new User();
                user.setFirstName(first_name);
                user.setSecondName(second_name);
                user.setLastName(last_name);
                user.setDateOfBirth(birthday);
                user.setGender(gender);
                user.setiNN(inn);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    @Override
    public void addUserToDB(User user) {

        try (Connection connection = getConnection());
        PreparedStatement statement = connection.prepareStatement("INSERT INTO USERS_DATA.USERS (first_name,second_name,last_name,birthday,gender,inn) VALUES (?,?,?,?,?,?)");
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getSecondName());
        statement.setString(4, user.getLastName());
        statement.setDate(5, user.getDateOfBirth());
        statement.setString(6, user.getGender());
        statement.setString(7, user.getiNN());
    } catch (SQLException e) {
        e.getMessage();
    }

    @Override
    public User getUserByName(String name) {

        List<User> users = getUsersFromDB();
        for (User user : users) {
            if (user.getFirstName().equals(first_name),
            user.getSecondName().equals(second_name),
            user.getLastName().equals(last_name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        boolean userUpdated = false;
        try (Connection connection = getConnection();
        Statement getUsers = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        ResultSet result = getUsers.executeQuery("")) {
            while (result.next()) {
                if (id == user.getId()) {
                    result.updateInt("id", user.getId());
                    result.updateDate("birthday", user.getDateOfBirth());
                    result.updateString("inn", user.getiNN());

                    result.updateRow();
                    userUpdated = true;
                }
            }
        } catch (SQLException e) {
            userUpdated = false;
        }
        return userUpdated;
    }
}
