package com.fintech.internship.data.dbase;

import com.fintech.internship.data.pojo.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDB implements IUserDB {

    static Connection getConnection() throws IOException {

        Properties dbProperties = new Properties();
        dbProperties.load(UserDB.class.getResourceAsStream("dbProperties"));

        // не уверена, что правильно коннекшн сделала, но надо именно вот так - через файл
        try {Connection connection = DriverManager.getConnection(dbProperties.getProperty("instance"), dbProperties);

        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }

    public List<User> getUsersFromDB() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id,first_name,second_name,last_name,birthday,gender,inn,postcode,country,area,city,street,house,flat FROM USERS_DATA.USERS JOIN USERS_DATA.ADDRESS ON USERS_DATA.USERS FOREIGN KEY address_id = USERS_DATA.ADDRESS PRIMARY KEY id");
//             PreparedStatement statement2 = connection.prepareStatement("SELECT id,postcode,country,area,city,street,house,flat FROM USERS_DATA.ADDRESS");
             ResultSet resultSet = statement.executeQuery()) {

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
//                int uId = resultSet.getInt(1);
                String first_name = resultSet.getString(2);
                String second_name = resultSet.getString(3);
                String last_name = resultSet.getString(4);
                Date birthday = resultSet.getDate(5);
                String gender = resultSet.getString(6);
                String inn = resultSet.getString(7);
                int postcode = resultSet.getInt(8);
                String country = resultSet.getString(9);
                String area = resultSet.getString(10);
                String city = resultSet.getString(11);
                String street = resultSet.getString(12);
                int house = resultSet.getInt(13);
                int flat = resultSet.getInt(14);


                User user = new User();
                user.setFirstName(first_name);
                user.setSecondName(second_name);
                user.setLastName(last_name);
                user.setDateOfBirth(birthday);
                user.setGender(gender);
                user.setiNN(inn);
                user.setZipcode(postcode);
                user.setCountry(country);
                user.setArea(area);
                user.setCity(city);
                user.setStreet(street);
                user.setHouse(house);
                user.setFlat(flat);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
    }
// мне не понятно почему он здесь хочет return

    @Override
    public void addUserToDB(User user) {

        try (Connection connection = getConnection()) {
            PreparedStatement statement1 = connection.prepareStatement("INSERT INTO USERS_DATA.USERS (first_name,second_name,last_name,birthday,gender,inn) VALUES (?,?,?,?,?,?); SELECT LAST_INSERT_ID();");
            PreparedStatement statement2 = connection.prepareStatement("INSERT INTO USERS_DATA.ADDRESS (postcode,country,area,city,street,house,flat) VALUES (?,?,?,?,?,?,?); SELECT LAST_INSERT_ID();");
            statement1.setString(2, user.getFirstName());
            statement1.setString(3, user.getSecondName());
            statement1.setString(4, user.getLastName());
            statement1.setDate(5, (Date) user.getDateOfBirth());
            statement1.setString(6, user.getGender());
            statement1.setString(7, user.getiNN());
            statement2.setInt(8, user.getZipcode());
            statement2.setString(9, user.getCountry());
            statement2.setString(10, user.getArea());
            statement2.setString(11, user.getCity());
            statement2.setString(12, user.getStreet());
            statement2.setInt(13, user.getHouse());
            statement2.setInt(14, user.getFlat());

        } catch (SQLException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
    }
    @Override
    public User getUserByName(String name) {

        List<User> users = getUsersFromDB();
        for (User user : users) {
            if (user.getFirstName().equals(name)) {
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
        ResultSet result = getUsers.executeQuery("SELECT * FROM USERS_DATA.USERS JOIN USERS_DATA.ADDRESS ON USERS_DATA.USERS FOREIGN KEY address_id = USERS_DATA.ADDRESS PRIMARY KEY id")) {
            while (result.next()) {

                //вот тут не ясно какой параметр надо использовать в скобказ после if
                if (first_name == user.getFirstName()) {
                    result.updateDate("birthday", (Date) user.getDateOfBirth());
                    result.updateString("inn", user.getiNN());
                    result.updateInt("postcode", user.getZipcode());
                    result.updateString("country", user.getCountry());
                    result.updateString("area", user.getArea());
                    result.updateString("city", user.getCity());
                    result.updateString("street", user.getStreet());
                    result.updateInt("house", user.getHouse());
                    result.updateInt("flat", user.getFlat());
                    result.updateRow();
                    userUpdated = true;
                }
            }
        } catch (SQLException e) {
            userUpdated = false;
        } catch (IOException e) {
            e.getMessage();
        }
        return userUpdated;
    }
}
