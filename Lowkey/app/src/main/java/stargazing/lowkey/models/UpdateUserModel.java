package stargazing.lowkey.models;

import java.util.UUID;

public class UpdateUserModel {
    private UUID Id;
    private String FullName;
    private int Age;
    private int Radius;
    public int Latitude ;
    public int Longitude;
    public int Gender;
    public String ProfilePicture;

    public UpdateUserModel(UUID id, String fullName, int age, int radius, int latitude, int longitude, int gender, String profilePicture) {
        Id = id;
        FullName = fullName;
        Age = age;
        Radius = radius;
        Latitude = latitude;
        Longitude = longitude;
        Gender = gender;
        ProfilePicture = profilePicture;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getRadius() {
        return Radius;
    }

    public void setRadius(int radius) {
        Radius = radius;
    }

    public int getLatitude() {
        return Latitude;
    }

    public void setLatitude(int latitude) {
        Latitude = latitude;
    }

    public int getLongitude() {
        return Longitude;
    }

    public void setLongitude(int longitude) {
        Longitude = longitude;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }
}
