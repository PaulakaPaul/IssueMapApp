package stargazing.lowkey.models;

import java.util.List;
import java.util.UUID;

public class IssueGetModel {
    private UUID Id;
    private String Title;
    private String Description;
    private double Latitude;
    private double Longitude;
    private int UpVotes;
    private int DownVotes;
    private long CreatedAt;
    private UUID CreatedBy;
    private String Creator;
    private List<CommentGetModel> Comments;
    private List<String> Images;

    public IssueGetModel(UUID id, String title, String description, double latitude, double longitude, int upVotes, int downVotes, long createdAt, UUID createdBy, String creator, List<CommentGetModel> comments, List<String> images) {
        Id = id;
        Title = title;
        Description = description;
        Latitude = latitude;
        Longitude = longitude;
        UpVotes = upVotes;
        DownVotes = downVotes;
        CreatedAt = createdAt;
        CreatedBy = createdBy;
        Creator = creator;
        Comments = comments;
        Images = images;
    }

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

    public int getUpVotes() {
        return UpVotes;
    }

    public void setUpVotes(int upVotes) {
        UpVotes = upVotes;
    }

    public int getDownVotes() {
        return DownVotes;
    }

    public void setDownVotes(int downVotes) {
        DownVotes = downVotes;
    }

    public long getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(long createdAt) {
        CreatedAt = createdAt;
    }

    public UUID getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(UUID createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public List<CommentGetModel> getComments() {
        return Comments;
    }

    public void setComments(List<CommentGetModel> comments) {
        Comments = comments;
    }

    public List<String> getImages() {
        return Images;
    }

    public void setImages(List<String> images) {
        Images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueGetModel that = (IssueGetModel) o;

        return this.Id.equals(that.Id);
    }

    @Override
    public int hashCode() {
        return Id.hashCode() * 2 + 5;
    }
}
