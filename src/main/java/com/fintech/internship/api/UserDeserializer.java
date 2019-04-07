package com.fintech.internship.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fintech.internship.data.User;
import com.fintech.internship.randoms.RandomINN;
import com.fintech.internship.randoms.RandomNumberGenerator;
import com.fintech.internship.randoms.RandomizedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class UserDeserializer extends StdDeserializer<User> {
    public UserDeserializer() {
        this(null);
    }

    protected UserDeserializer(Class<?> vc) {
        super(vc);
    }

    SimpleDateFormat jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private final RandomizedReader rReader = new RandomizedReader();
    private final RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        User user = new User();
        user.setFirstName(rootNode.get("name").get("first").asText());
        user.setLastName(rootNode.get("name").get("last").asText());
        user.setAge(rootNode.get("dob").get("age").asInt());
        user.setiNN(new RandomINN().getRandomINN());
        user.setZipcode(randomNumberGenerator.getRandomNumber());
        user.setCountry(getRandomFromFile("Countries.txt"));
        user.setArea(rootNode.get("location").get("state").asText());
        user.setCity(rootNode.get("location").get("city").asText());
        user.setStreet(rootNode.get("location").get("street").asText());
        user.setHouse(randomNumberGenerator.getRandomNumber(1, 199));
        user.setFlat(randomNumberGenerator.getRandomNumber(1, 999));

        String genderFromApi = rootNode.get("gender").asText();

        if (genderFromApi.equals("male")) {
            user.setSecondName(getRandomFromFile("SecNamesMale.txt"));
            user.setGender("М");
        } else {
            user.setSecondName(getRandomFromFile("SecNamesFem.txt"));
            user.setGender("Ж");
        }

        try {
            user.setDateOfBirth(jsonDateFormat.parse(rootNode.get("dob").get("date").asText()));
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
        return user;
    }

    private String getRandomFromFile(String fileName) {
        return Optional.of(rReader.generate(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(fileName)))).orElse("");
    }
}
