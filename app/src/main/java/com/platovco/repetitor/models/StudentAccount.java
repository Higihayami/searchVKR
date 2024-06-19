package com.platovco.repetitor.models;

import java.util.Map;

public class StudentAccount {
    private String photo;
    private String name;
    private String surname;
    private String city;
    private String birthday;
    private String direction;
    private long budget;
    private String reason;
    private long id;

    // No-argument constructor required for Firebase
    public StudentAccount() {
    }

    public StudentAccount(Map<?, ?> map) {
        this.photo = String.valueOf(map.get("photo"));
        this.name = String.valueOf(map.get("name"));
        this.surname = String.valueOf(map.get("surname"));
        this.city = String.valueOf(map.get("city"));
        this.birthday = String.valueOf(map.get("birthday"));
        this.direction = String.valueOf(map.get("direction"));
        this.budget =  map.get("budget") != null ? ((Number) map.get("budget")).longValue() : 0;
        this.reason = String.valueOf(map.get("reason"));
        this.id = map.get("$id") != null ? ((Number) map.get("$id")).longValue() : 0;
    }

    public StudentAccount(String photoUrl, String name, String surname, String city, String birthday, String direction, long budget, String reason, long id) {
        this.photo = photoUrl;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.birthday = birthday;
        this.direction = direction;
        this.budget = budget;
        this.reason = reason;
        this.id = id;
    }

    // Getters and setters for all fields
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
