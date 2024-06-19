package com.platovco.repetitor.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class TutorAccount {
    private String photoUrl;
    private String name;

    private String surname;
    private String city;
    private String birthday;
    private String education;
    private String direction;
    private String price;
    private String reason;
    private String uuid;

    public TutorAccount(String photoUrl, String name, String surname, String city, String birthday , String high, String direction, String price, String reason, String uuid) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.birthday = birthday;
        this.education = high;
        this.direction = direction;
        this.price = price;
        this.reason = reason;
        this.uuid = uuid;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", uuid);
        result.put("name", name);
        result.put("surname", surname);
        result.put("city", city);
        result.put("birthday", birthday);
        result.put("education", education);
        result.put("direction", direction);
        result.put("price", price);
        result.put("reason", reason);
        result.put("photoUrl", photoUrl);

        return result;
    }
    public TutorAccount() {}


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String carModel) {
        this.education = carModel;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String carBrand) {
        this.direction = carBrand;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
