package stargazing.lowkey.models;

import java.util.UUID;

public class CommentGetModel {
    private UUID Id;
    private String Content;
    private String Creator;
    private long CreatedAt;
    private long EditedAt;

    public CommentGetModel(UUID id, String content, String creator, long createdAt, long editedAt) {
        Id = id;
        Content = content;
        Creator = creator;
        CreatedAt = createdAt;
        EditedAt = editedAt;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public long getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(long createdAt) {
        CreatedAt = createdAt;
    }

    public long getEditedAt() {
        return EditedAt;
    }

    public void setEditedAt(long editedAt) {
        EditedAt = editedAt;
    }
}
