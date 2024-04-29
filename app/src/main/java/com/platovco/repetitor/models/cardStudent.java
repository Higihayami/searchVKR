package com.platovco.repetitor.models;

public class cardStudent {
    private String photo;
    private String age;
    private String name;

    private String city;

    private String direction;

    public cardStudent(String photo, String age, String name, String city, String direction) {
        this.photo = photo;
        this.age = age;
        this.name = name;
        this.city = city;
        this.direction = direction;
    }

    public String getPhoto() {
        return photo;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getDirection() {
        return direction;
    }

    public String getCity() {
        return city;
    }
}
