package stargazing.lowkey.main.fragments.issue;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jaeger.library.StatusBarUtil;

import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import stargazing.lowkey.LowkeyApplication;
import stargazing.lowkey.R;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.main.fragments.issue.adapter.CommentAdapter;
import stargazing.lowkey.managers.CommentManager;
import stargazing.lowkey.models.CommentGetModel;
import stargazing.lowkey.models.CommentModel;
import stargazing.lowkey.models.IssueGetModel;

public class ComentsActivity extends AppCompatActivity {
    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";

    ConstraintLayout contentRoot;
    RecyclerView rvComments;
    Button button;
    EditText inputTxt;
    LinearLayout llAddComment;
    CardView questionInfo;
    CircleImageView imagepic;
    List<CommentGetModel> commentArrayList;
    CommentManager commentManager = new CommentManager();
    private CommentAdapter commentsAdapter;
    private int drawingStartLocation;
    int index;
    IssueGetModel issue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coments);
        contentRoot = findViewById(R.id.contentRoot);
        rvComments = findViewById(R.id.rvComments);
        llAddComment = findViewById(R.id.llAddComment);
        button = findViewById(R.id.sendComment);

        StatusBarUtil.setTransparent(this);
        index = getIntent().getIntExtra("index", 0);
        inputTxt = findViewById(R.id.chat_input_msg);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(linearLayoutManager);
        try {
            issue = LowkeyApplication.staticIssues.get(index);
            commentArrayList = LowkeyApplication.staticIssues.get(index).getComments();
        }catch (ArrayIndexOutOfBoundsException aiobe){
            Log.e("No comments","nu is bossu");
            commentArrayList = new ArrayList<>();

        }
        drawingStartLocation = getIntent().getIntExtra(ARG_DRAWING_START_LOCATION, 0);
        if (savedInstanceState == null) {
            contentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation();
                    return true;
                }
            });
        }

        populateWithData();
        setupComments();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = inputTxt.getText().toString().trim();
                CommentModel comment = createComment(commentText);

                if (comment != null) {
                    sendNewsFeedRequestWithNewComment(comment);
                    commentArrayList.add(createGetComment(comment));
                    adaptViewWithNewComment(comment);
                }

            }
        });

    }

    private void adaptViewWithNewComment(CommentModel comment) {
        int newMsgPosition = commentArrayList.size() - 1;
        commentsAdapter.notifyItemInserted(newMsgPosition);
        rvComments.scrollToPosition(newMsgPosition);
        inputTxt.setText("");
    }

    private CommentModel createComment(String commentText) {
        if (TextUtils.isEmpty(commentText))
            return null;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new CommentModel(UUID.randomUUID(), issue.getId(),commentText,LowkeyApplication.currentUserManager.getUserModel().getId());
        //return new CommentGetModel();
    }

    private CommentGetModel createGetComment(CommentModel commentModel){
        return new CommentGetModel(commentModel.getId(),commentModel.getContent(),LowkeyApplication.currentUserManager.getUserModel().getFullName(),new Timestamp((System.currentTimeMillis())).getTime(),new Timestamp((System.currentTimeMillis())).getTime());
    }

    private void sendNewsFeedRequestWithNewComment(CommentModel comment) {
        commentManager.create(comment, new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {
                if(response.equals(RequestWrapper.FAIL_JSON_RESPONSE_VALUE))
                    Log.e("Comment ADD","failed");

            }
        });

    }


    private void populateWithData() {

        try {
            commentsAdapter = new CommentAdapter(commentArrayList,this);
            rvComments.setAdapter(commentsAdapter);
            int newMsgPosition = LowkeyApplication.staticIssues.get(index).getComments().size() - 1;
            rvComments.scrollToPosition(newMsgPosition);
        } catch (NullPointerException e) {
            Log.e("Error", "parcelable object failed");
        }
    }

    private void startIntroAnimation() {
        contentRoot.setScaleY(0.1f);
        contentRoot.setPivotY(drawingStartLocation);
        llAddComment.setTranslationY(100);

        contentRoot.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animateContent();
                    }
                })
                .start();
    }

    private void animateContent() {
        llAddComment.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(200)
                .start();
    }

    private void setupComments() {
        rvComments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    commentsAdapter.setAnimationsLocked(true);
                }
            }
        });
    }
/*
    @Override
    public void onBackPressed() {
        Intent retrieveData = new Intent();
        MyParcelable object = new MyParcelable();
        object.setArrList(commentsSentList);
        retrieveData.putExtra("NewComments", object);

        retrieveData.putExtra("ItemID", getIntent().getLongExtra("timestampID", 0));
        setResult(Activity.RESULT_OK, retrieveData);

        contentRoot.animate()
                .translationY(Resources.getSystem().getDisplayMetrics().heightPixels)
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        overridePendingTransition(0, 0);

                    }
                })
                .start();

        super.onBackPressed();
        this.finish();
    }
    */

}


