package vn.edu.usth.coronatracker.model;

import java.util.Date;

public class User {
    private String familyName;
    private String name;
    private String phoneNumber;
    private String imageurl;
    private String id;
    private String token;
    private String idCard;
    private String gender;
    private String dateOfBirth;

    public User(String familyName, String name, String phoneNumber, String imageurl, String id, String token, String idCard, String gender, String dateOfBirth) {
        this.familyName = familyName;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.imageurl = imageurl;
        this.id = id;
        this.token = token;
        this.idCard = idCard;
        this.gender = gender;
        this.dateOfBirth=dateOfBirth;
    }

    public User() {
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
