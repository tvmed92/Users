package com.fintech.internship.data.dbase;

import com.fintech.internship.data.pojo.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDB implements IUserDB {

    static Connection getConnection() throws IOException, SQLException {

//        Properties dbProperties = new Properties();
////        dbProperties.load(UserDB.class.getResourceAsStream("dbProperties"));

        String url = "jdbc:mysql:tcp://localhost:3306";
//        try {
//            Connection connection = DriverManager.getConnection(url, "admin", "admin");
//            DriverManager.getConnection(dbProperties.getProperty("instance"), dbProperties);
//        } catch (SQLException e) {
//            e.getMessage();
//        }
        return DriverManager.getConnection(url, "admin", "admin");
    }

    @Override
    public List<User> getUsersFromDB() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id,first_name,second_name,last_name,birthday,gender,inn,postcode,country,area,city,street,house,flat FROM USERS_DATA.USERS JOIN USERS_DATA.ADDRESS ON users_data.address.id = users_data.users.id");
             ResultSet resultSet = statement.executeQuery()) {

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
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
        } catch (SQLException | IOException e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public void addUsersToDB(List<User> users) {
        for (User user : users) {
            List<User> usersByFNSNandP = Collections.singletonList(getUserByName(
                    user.getFirstName(),
                    user.getSecondName(),
                    user.getLastName()));
            if (usersByFNSNandP.isEmpty()) {
                addUser(user);
                addAddress(user);
            } else {
                updateUser(user);
                updateAddress(user);
            }
        }
    }

    @Override
    public void addUser(User user) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO USERS_DATA.USERS (first_name,second_name,last_name,birthday,gender,inn) VALUES (?,?,?,?,?,?); SELECT LAST_INSERT_ID();");
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSecondName());
            statement.setString(3, user.getLastName());
            statement.setDate(4, (Date) user.getDateOfBirth());
            statement.setString(5, user.getGender());
            statement.setString(6, user.getiNN());
        } catch (SQLException | IOException e) {
            e.getMessage();
        }
    }

    @Override
    public void addAddress(User user) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO USERS_DATA.ADDRESS (postcode,country,area,city,street,house,flat) VALUES (?,?,?,?,?,?,?); SELECT LAST_INSERT_ID();");
            statement.setInt(1, user.getZipcode());
            statement.setString(2, user.getCountry());
            statement.setString(3, user.getArea());
            statement.setString(4, user.getCity());
            statement.setString(5, user.getStreet());
            statement.setInt(6, user.getHouse());
            statement.setInt(7, user.getFlat());
        } catch (SQLException | IOException e) {
            e.getMessage();
        }
    }

    @Override
    public User getUserByName(String first_name, String second_name, String last_name) {

        List<User> users = getUsersFromDB();
        if (!users.isEmpty()) {
            for (User user : users) {
                if (user.getFirstName().equals(first_name) && user.getSecondName().equals(second_name) && user.getLastName().equals(last_name)) {
                    return user;
                }
            }
        } return null;
    }

    @Override
    public boolean updateUser(User user) {
        boolean userUpdated = false;
        try (Connection connection = getConnection();
             Statement getUsers = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
             ResultSet result = getUsers.executeQuery("SELECT * FROM USERS_DATA.USERS")) {
            while (result.next()) {
                String firstName = result.getString("first_name");
                String secondName = result.getString("second_name");
                String lastName = result.getString("last_name");

                //интересно, так тоже можно?
                if (firstName.equals(user.getFirstName()) && secondName.equals(user.getSecondName()) && lastName.equals(user.getLastName())) {
                    result.updateDate("birthday", (Date) user.getDateOfBirth());
                    result.updateString("inn", user.getiNN());
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

    @Override
    public boolean updateAddress(User user) {
        boolean userUpdated = false;
        try (Connection connection = getConnection();
             Statement getUsers = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
             ResultSet result = getUsers.executeQuery(
                     "SELECT * FROM USERS_DATA.USERS JOINS USERS_DATA.ADDRESS ON users_data.address.id = users_data.users.id")) {
            while (result.next()) {
                String firstName = result.getString("first_name");
                String secondName = result.getString("second_name");
                String lastName = result.getString("last_name");
                if (firstName.equals(user.getFirstName()) && secondName.equals(user.getSecondName()) && lastName.equals(user.getLastName())) {
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
