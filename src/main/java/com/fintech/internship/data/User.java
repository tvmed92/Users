package com.fintech.internship.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fintech.internship.api.UserDeserializer;

import java.util.Date;
import java.util.List;

@JsonDeserialize(using = UserDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String firstName;
    private String secondName;
    private String lastName;
    private Integer age;
    private String gender;
    private Date dateOfBirth;
    private String iNN;
    private Integer zipcode;
    private String country;
    private String area;
    private String city;
    private String street;
    private Integer house;
    private Integer flat;


    private List<String> nameNode;

    @JsonSetter("name")
    public List<String> getNameNode() {return nameNode;}

    public void setNameNode(List<String> nameNode) { this.nameNode = nameNode; }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getiNN() {
        return iNN;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public String getCountry() {
        return country;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() { return street; }

    public Integer getHouse() {
        return house;
    }

    public Integer getFlat() {
        return flat;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setiNN(String iNN) {
        this.iNN = iNN;
    }

    public void setZipcode(Integer zipcode) { this.zipcode = zipcode; }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouse(Integer house) {
        this.house = house;
    }

    public void setFlat(Integer flat) {
        this.flat = flat;
    }



    public User() {
    }

    public User(String firstName, String secondName, String lastName,
                Integer age, String gender, Date dateOfBirth, String iNN,
                Integer zipcode, String country, String area,
                String city, String street, Integer house, Integer flat) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.iNN = iNN;
        this.zipcode = zipcode;
        this.country = country;
        this.area = area;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }
}
