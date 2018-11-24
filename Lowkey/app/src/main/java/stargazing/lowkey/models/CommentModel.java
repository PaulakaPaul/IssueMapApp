package stargazing.lowkey.models;

import java.util.UUID;

public class CommentModel {
    private UUID Id;
    private UUID IssueId;
    private String Content;
    private UUID CreatedBy;

    public CommentModel(UUID issueId, String content, UUID createdBy) {
        Id = UUID.randomUUID();
        IssueId = issueId;
        Content = content;
        CreatedBy = createdBy;
    }

    public CommentModel(UUID id, UUID issueId, String content, UUID createdBy) {
        Id = id;
        IssueId = issueId;
        Content = content;
        CreatedBy = createdBy;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public UUID getIssueId() {
        return IssueId;
    }

    public void setIssueId(UUID issueId) {
        IssueId = issueId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public UUID getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(UUID createdBy) {
        CreatedBy = createdBy;
    }
}
