package stargazing.lowkey.models;

import java.util.UUID;

public class RegisterModel {
    private UUID Id;
    private String FullName;
    private String Email;
    private double Latitude;
    private double Longitude;
    private int Radius;
    private int Age;
    private int Gender;
    private String Password;
    private String ProfilePicture;

    public RegisterModel(String fullName, String email, double latitude, double longitude,
                         int radius, int age, int gender, String password, String profilePicture) {
        this(fullName, email, latitude, longitude, radius, age, gender, password);
        this.ProfilePicture = profilePicture;
    }

    public RegisterModel(String fullName, String email, double latitude, double longitude,
                         int radius, int age, int gender, String password) {
        this.Id = UUID.randomUUID();
        this.FullName = fullName;
        this.Email = email;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.Radius = radius;
        this.Age = age;
        this.Gender = gender;
        this.Password = password;
    }

    public UUID getId() {
        return Id;
    }

    public String getFullName() {
        return FullName;
    }

    public String getEmail() {
        return Email;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public int getRadius() {
        return Radius;
    }

    public int getAge() {
        return Age;
    }

    public int getGender() {
        return Gender;
    }

    public String getPassword() {
        return Password;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }
}
