package com.fintech.internship.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fintech.internship.data.User;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersContainer {
    private List<User> results;

    public List<User> getResults() { return results;}

    public void setResults(List<User> results) { this.results = results; }
}
