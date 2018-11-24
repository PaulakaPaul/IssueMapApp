package stargazing.lowkey.models;

import java.util.UUID;

public class UserModel {
    private UUID Id;
    private String AspNetUserId;
    private String FullName;
    private String Latitude;
    private String Longitude;
    private int Radius;
    private int Age;
    private int Gender;
    private String ProfilePicture;

    public UserModel(UUID id, String aspNetUserId, String fullName, String latitude, String longitude, int radius, int age, int gender, String profilePicture) {
        Id = id;
        AspNetUserId = aspNetUserId;
        FullName = fullName;
        Latitude = latitude;
        Longitude = longitude;
        Radius = radius;
        Age = age;
        Gender = gender;
        ProfilePicture = profilePicture;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getAspNetUserId() {
        return AspNetUserId;
    }

    public void setAspNetUserId(String aspNetUserId) {
        AspNetUserId = aspNetUserId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public int getRadius() {
        return Radius;
    }

    public void setRadius(int radius) {
        Radius = radius;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
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

    public double getDoubleLat(){
        return Double.valueOf(this.getLatitude());
    }

    public double getDoubleLon(){
        return Double.valueOf(this.getLongitude());
    }
}
