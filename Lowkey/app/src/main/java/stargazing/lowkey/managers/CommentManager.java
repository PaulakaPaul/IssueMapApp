package stargazing.lowkey.managers;


import org.json.JSONObject;

import java.util.UUID;

import stargazing.lowkey.api.views.CommentsView;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.models.CommentModel;
import stargazing.lowkey.serializers.CommentSerializer;

public class CommentManager  {
    private CommentsView commentsView = new CommentsView();

    public void create(CommentModel commentModel, OnSuccessHandler onSuccessHandler) {
        CommentSerializer commentSerializer = new CommentSerializer(commentModel);
        JSONObject serializedObject = commentSerializer.getCommentSerialized();

        commentsView.create(serializedObject, onSuccessHandler);
    }

    public void update(CommentModel commentModel, OnSuccessHandler onSuccessHandler) {
        CommentSerializer commentSerializer = new CommentSerializer(commentModel);
        JSONObject serializedObject = commentSerializer.getCommentSerialized();

        commentsView.edit(serializedObject, onSuccessHandler);
    }

    public void delete(UUID id, OnSuccessHandler onSuccessHandler) {
        commentsView.delete(id, onSuccessHandler);
    }
}
