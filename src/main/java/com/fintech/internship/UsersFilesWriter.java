package com.fintech.internship;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.internship.data.api.UserClient;
import com.fintech.internship.data.api.UsersContainer;
import com.fintech.internship.data.dbase.UserServices;
import com.fintech.internship.data.pojo.User;
import com.fintech.internship.data.pojo.UserGenerator;
import com.fintech.internship.output.PDFCreator;
import com.fintech.internship.output.XLSCreator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.fintech.internship.data.helpers.ConstantsUtil.*;

public class UsersFilesWriter {

    public static void main(String[] args) {
        List<User> users;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String getResponse = new UserClient().doGetRequest(RANDOM_USER_URL, USER_GENERATION_LIMIT);
            UsersContainer responseContainer = mapper.readValue(getResponse, UsersContainer.class);
            System.out.println("Данные пользователей будут сгенерированы из приложения randomuser.me");
            users = responseContainer.getResults();
            UserServices.addUsersToDB(users);
        } catch (IOException e) {
            System.out.println("Отсутствует подключение к интернету, данные пользователей будут получены из Базы данных");
            users = UserServices.getUsersFromDB();
            if (users.isEmpty()) {
                System.out.println("База данных пуста, данные пользователей будут сгенерированы из ресурсов");
                users = new UserGenerator().fillUsers(USER_GENERATION_LIMIT);
            }
        }
        new XLSCreator(columns).populateAndWriteToXLS(users, "Users.xls");

        File file = new File("Users.pdf");
        new PDFCreator(users).createFile(file.getAbsolutePath());
        System.out.println("Файл создан. Путь: " + file.getAbsolutePath());
    }
}