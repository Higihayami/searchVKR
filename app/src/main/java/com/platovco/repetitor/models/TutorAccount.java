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
    private String education;
    private String direction;
    private String experience;
    private String uuid;

    public TutorAccount(String photoUrl, String name, String high, String direction, String experience, String uuid) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.education = high;
        this.direction = direction;
        this.experience = experience;
        this.uuid = uuid;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", uuid);
        result.put("name", name);
        result.put("education", education);
        result.put("direction", direction);
        result.put("experience", experience);
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String carNumber) {
        this.experience = carNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
