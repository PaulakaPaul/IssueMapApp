package stargazing.lowkey.managers;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import stargazing.lowkey.api.views.CommentsView;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.models.CommentModel;
import stargazing.lowkey.serializers.CommentSerializer;

public class CommentManager {
    private static final String ID_RESPONSE_KEY = "Id";
    private static final String SUCCESS_RESPONSE_KEY = "Success";

    private CommentsView commentsView = new CommentsView();

    public void create(CommentModel commentModel, final OnSuccessHandler onSuccessHandler) {
        CommentSerializer commentSerializer = new CommentSerializer(commentModel);
        JSONObject serializedObject = commentSerializer.getCommentSerialized();

        OnSuccessHandler validationHandler = getValidationHandler(onSuccessHandler);
        commentsView.create(serializedObject, validationHandler);
    }

    public void update(CommentModel commentModel, OnSuccessHandler onSuccessHandler) {
        CommentSerializer commentSerializer = new CommentSerializer(commentModel);
        JSONObject serializedObject = commentSerializer.getCommentSerialized();

        OnSuccessHandler validationHandler = getValidationHandler(onSuccessHandler);
        commentsView.edit(serializedObject, validationHandler);
    }

    public void delete(UUID id, final OnSuccessHandler onSuccessHandler) {
        OnSuccessHandler validationHandler = new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {
                if (onSuccessHandler != null)
                    try {
                        response.get(SUCCESS_RESPONSE_KEY);
                        onSuccessHandler.handle(response);
                    } catch (JSONException e) {
                        onSuccessHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
                    }
            }
        };

        commentsView.delete(id, validationHandler);
    }

    private OnSuccessHandler getValidationHandler(final OnSuccessHandler onSuccessHandler) {
        return new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {

                if (onSuccessHandler != null)
                    try {
                        response.get(ID_RESPONSE_KEY);
                        onSuccessHandler.handle(response);
                    } catch (JSONException e) {
                        onSuccessHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
                    }
            }
        };
    }
}
