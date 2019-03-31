package com.fintech.internship.api;

import com.fintech.internship.data.User;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

public class UserTransformer implements JsonDeserialiser <User>{

    @Override
    public User deserialise(JsonElement json, Type typeOfSrc, JsonDeserializationContext context) {

        JsonObject jsonObject = json.getAsJsonObject();
        User user = new User();
        user.setGender(jsonObject.get("gender").getAsString());
        user.setFirstName(jsonObject.get("name").get("first").getAsString();

//        user.setFirstName(

        return user;
    }
}
