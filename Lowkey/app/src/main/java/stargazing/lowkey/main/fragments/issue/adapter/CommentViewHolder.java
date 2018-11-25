package stargazing.lowkey.main.fragments.issue.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import stargazing.lowkey.R;
import stargazing.lowkey.models.CommentGetModel;
import stargazing.lowkey.models.IssueGetModel;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout leftMsgLayout;
    TextView date;
    TextView name;

    TextView answers;
    String uniqueID;
    public View view;

    public CommentViewHolder(View itemView) {
        super(itemView);
        if(itemView!=null) {
            leftMsgLayout = (ConstraintLayout) itemView.findViewById(R.id.chat_left_msg_layout);
            answers = (TextView) itemView.findViewById(R.id.chat_left_msg_text_view);
            date = (TextView) itemView.findViewById(R.id.text_message_timeLeft);
            name = (TextView) itemView.findViewById(R.id.username);
        }
    }

    public void bindUI(CommentGetModel issueModel){
        try {
            name.setText(issueModel.getCreator());
            answers.setText(issueModel.getContent());
            date.setText(localTime(issueModel.getCreatedAt()));
        }catch (NullPointerException npe){

        }

    }

    private String localTime(Long time) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        SimpleDateFormat sf = new SimpleDateFormat("dd MMMMM - HH:mm");
        sf.setTimeZone(tz);
        Date date = new Date(time);
        PrettyTime t = new PrettyTime(date);
        return t.format(new Date());
    }

    public void bind(final CommentGetModel item, final CommentAdapter.OnItemClickListener listener) {
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(item,getAdapterPosition());
                return true;// returning true instead of false, works for me
            }
        });

    }



}
