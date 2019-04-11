package com.fintech.internship.data.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fintech.internship.data.pojo.User;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersContainer {
    private List<User> results;

    public List<User> getResults() {
        return results;
    }
}
