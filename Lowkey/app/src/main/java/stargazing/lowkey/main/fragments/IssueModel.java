package stargazing.lowkey.main.fragments;

import java.util.List;
import java.util.UUID;

public class IssueModel {
    private UUID Id;
    private String Title;
    private String Description;
    private double Latitude;
    private double Longitude;
    private UUID CreatedBy;
    private List<String> ImagesList;

    public IssueModel(UUID id,
                      String title,
                      String description,
                      double latitude,
                      double longitude,
                      UUID createdBy,
                      List<String> imagesList){

        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setCreatedBy(createdBy);
        this.setImagesList(imagesList);

    }

    public IssueModel(){}

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public UUID getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(UUID createdBy) {
        CreatedBy = createdBy;
    }

    public List<String> getImagesList() {
        return ImagesList;
    }

    public void setImagesList(List<String> imagesList) {
        ImagesList = imagesList;
    }
}
