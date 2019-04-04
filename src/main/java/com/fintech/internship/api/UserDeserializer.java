package com.fintech.internship.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fintech.internship.data.User;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserDeserializer extends StdDeserializer<User> {
    public UserDeserializer() { this(null); }
    protected UserDeserializer(Class<?> vc) { super(vc); }

    SimpleDateFormat jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        User user = new User();
        user.setFirstName(rootNode.get("name").get("first").asText());
        user.setSecondName("");
        user.setLastName(rootNode.get("name").get("last").asText());
        user.setAge(rootNode.get("dob").get("age").asInt());
        user.setGender(rootNode.get("genger").asText());
        user.setiNN("");
        user.setZipcode(0);
        user.setCountry("");
        user.setArea(rootNode.get("location").get("state").asText());
        user.setCity(rootNode.get("location").get("city").asText());
        user.setStreet(rootNode.get("location").get("street").asText());
        user.setHouse(0);
        user.setFlat(0);
        try {
            user.setDateOfBirth(jsonDateFormat.parse(rootNode.get("dob").get("date").asText()));
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
        return user;
    }
}
