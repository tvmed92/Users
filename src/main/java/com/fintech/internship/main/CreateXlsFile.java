package com.fintech.internship.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.internship.api.UserClient;
import com.fintech.internship.api.UsersContainer;
import com.fintech.internship.data.User;
import com.fintech.internship.data.UserGenerator;
import com.fintech.internship.output.CreatePDF;
import com.fintech.internship.output.XLSCreator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.fintech.internship.data.ConstantsUtil.*;

public class CreateXlsFile {

    public static void main(String[] args) {
        List<User> users;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String getResponse = new UserClient().doGetRequest(RANDOM_USER_URL, USER_GENERATION_LIMIT);
            UsersContainer responseContainer = mapper.readValue(getResponse, UsersContainer.class);
            users = responseContainer.getResults();
        } catch (IOException e) {
            users = new UserGenerator().fillUsers(USER_GENERATION_LIMIT);
        }
        new XLSCreator(columns).populateAndWriteToXLS(users, "Users.xls");

        File file = new File("Users.pdf");
        new CreatePDF(users).createFile(file.getAbsolutePath());
        System.out.println("Файл создан. Путь: " + file.getAbsolutePath());
    }
}