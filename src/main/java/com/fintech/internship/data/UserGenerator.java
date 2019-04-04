package com.fintech.internship.data;

import com.fintech.internship.randoms.RandomDateOfBirth;
import com.fintech.internship.randoms.RandomINN;
import com.fintech.internship.randoms.RandomNumberGenerator;
import com.fintech.internship.randoms.RandomizedReader;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class UserGenerator {

    private List<User> users = new ArrayList<>();
    private final RandomizedReader rReader = new RandomizedReader();
    private final RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    public List<User> fillUsers(int upperLimit) {
        int limit = ThreadLocalRandom.current().nextInt(1, upperLimit);

        for (int j = 1; j <= limit; j++) {

            String randomGender;
            String randomFirstName;
            String randomSecondName;
            String randomLastName;

            RandomDateOfBirth randomDateOfBirth = new RandomDateOfBirth();
            boolean genderFlag = new Random().nextBoolean();

            if (genderFlag) {
                randomGender = "М";
                randomFirstName = getRandomFromFile("NamesMale.txt");
                randomSecondName = getRandomFromFile("SecNamesMale.txt");
                randomLastName = getRandomFromFile("SurnamesMale.txt");
            } else {
                randomGender = "Ж";
                randomFirstName = getRandomFromFile("NamesFem.txt");
                randomSecondName = getRandomFromFile("SecNamesFem.txt");
                randomLastName = getRandomFromFile("SurnamesFem.txt");
            }

            User newUser = new User();
            newUser.setFirstName(randomFirstName);
            newUser.setSecondName(randomSecondName);
            newUser.setLastName(randomLastName);
            newUser.setAge(randomDateOfBirth.getAge());
            newUser.setGender(randomGender);
            newUser.setDateOfBirth(randomDateOfBirth.getDateTime());
            newUser.setiNN(new RandomINN().getRandomINN());
            newUser.setZipcode(randomNumberGenerator.getRandomNumber());
            newUser.setCountry(getRandomFromFile("Countries.txt"));
            newUser.setArea(getRandomFromFile("Areas.txt"));
            newUser.setCity(getRandomFromFile("Cities.txt"));
            newUser.setStreet(getRandomFromFile("Streets.txt"));
            newUser.setHouse(randomNumberGenerator.getRandomNumber(1, 199));
            newUser.setFlat(randomNumberGenerator.getRandomNumber(1, 999));
            users.add(newUser);
        }
        return users;
    }

    private String getRandomFromFile(String fileName) {
        return Optional.of(rReader.generate(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(fileName)))).orElse("");
    }
}
