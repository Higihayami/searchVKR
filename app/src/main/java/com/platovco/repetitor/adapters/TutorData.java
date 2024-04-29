package com.platovco.repetitor.adapters;

public class TutorData {
    private String name;
    private String city;
    private String direction;

    private String photoUrl;
    // Можно добавить дополнительные поля, например, для изображения

    public TutorData(String name, String city, String direction, String photoUrl) {
        this.name = name;
        this.city = city;
        this.direction = direction;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getDirection() {
        return direction;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}