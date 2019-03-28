package com.fintech.internship.data;

import com.fintech.internship.randoms.RandomDateOfBirth;
import com.fintech.internship.randoms.RandomINN;
import com.fintech.internship.randoms.RandomNumberGenerator;
import com.fintech.internship.randoms.RandomizedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class UserGenerator {

    private List<User> users = new ArrayList<>();
    private RandomizedReader rReader = new RandomizedReader();

    public List<User> fillUsers(int upperLimit) throws FileNotFoundException {
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
                randomFirstName = rReader.generate(new FileReader(
                        "src/main/resources/NamesMale.txt"));
                randomSecondName = rReader.generate(new FileReader(
                        "src/main/resources/SecNamesMale.txt"));
                randomLastName = rReader.generate(new FileReader(
                        "src/main/resources/SurnamesMale.txt"));
            } else {
                randomGender = "Ж";
                randomFirstName = rReader.generate(new FileReader(
                        "src/main/resources/NamesFem.txt"));
                randomSecondName = rReader.generate(new FileReader(
                        "src/main/resources/SecNamesFem.txt"));
                randomLastName = rReader.generate(new FileReader(
                        "src/main/resources/SurnamesFem.txt"));
            }

            try {
                User newUser = new User();
                newUser.setFirstName(randomFirstName);
                newUser.setSecondName(randomSecondName);
                newUser.setLastName(randomLastName);
                newUser.setAge(randomDateOfBirth.getAge());
                newUser.setGender(randomGender);
                newUser.setDateOfBirth(randomDateOfBirth.getDateTime());
                newUser.setiNN(new RandomINN().getRandomINN());
                newUser.setZipcode(new RandomNumberGenerator().getRandomNumber());
                newUser.setCountry(new RandomizedReader().generate(new FileReader(
                        "src/main/resources/Countries.txt")));
                newUser.setArea(new RandomizedReader().generate(new FileReader(
                        "src/main/resources/Areas.txt")));
                newUser.setCity(new RandomizedReader().generate(new FileReader(
                        "src/main/resources/Cities.txt")));
                newUser.setStreet(new RandomizedReader().generate(new FileReader(
                        "src/main/resources/Streets.txt")));
                newUser.setHouse(new RandomNumberGenerator().getRandomNumber(1, 199));
                newUser.setFlat(new RandomNumberGenerator().getRandomNumber(1, 999));
                users.add(newUser);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return users;
    }
}
