package stargazing.lowkey.main.fragments.issue.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import stargazing.lowkey.R;
import stargazing.lowkey.api.views.IssuesView;
import stargazing.lowkey.models.IssueGetModel;

public class IssuesViewHolder extends RecyclerView.ViewHolder {


    //ImageView image;
    TextView title;
    TextView description;
    TextView gre;
    TextView red;
    TextView timelocation;
    CircleImageView userImage;
    Double latitude;
    TextView up;
    TextView down;
    Double longitude;
    TextView createdBy;

    public IssuesViewHolder(View itemView) {
        super(itemView);

        if(itemView!=null) {
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            timelocation = itemView.findViewById(R.id.timelocation);
            createdBy = itemView.findViewById(R.id.username);
            gre = itemView.findViewById(R.id.up);
            red = itemView.findViewById(R.id.down);

        }
    }

    public void bindUI(IssueGetModel issueModel){
       try {
           title.setText(issueModel.getTitle());
           description.setText(issueModel.getDescription());
           latitude = issueModel.getLatitude();
           longitude = issueModel.getLongitude();
           createdBy.setText(issueModel.getCreator());
           String s = localTime(issueModel.getCreatedAt()) + "at " + latitude + ", " + longitude;
           timelocation.setText(s);
           s = issueModel.getUpVotes() + " Approved";
           gre.setText(s);
           s = issueModel.getDownVotes() + " Declined";
           red.setText(s);
       }catch (NullPointerException npe){

       }

    }

    public void bindLikes(final IssuesViewHolder issueGetModel, final IssuesAdapter.OnGreenClick listener){
        gre.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(issueGetModel,v);
            }
        });
    }
    public void bindDislikes(final IssuesViewHolder issueGetModel,final IssuesAdapter.OnRedClick listener){
        red.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(issueGetModel,v);
            }
        });
    }


    public void bind(final IssuesViewHolder item, final IssuesAdapter.OnItemClickListenerNews listener) {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item,v);
            }
        });
    }/*
    public void bindDelete(final IssuesViewHolder item, final IssuesAdapter.OnDeleteItem listener){
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteItem(item,view);
            }
        });
    }*/
    private String localTime(Long time) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        SimpleDateFormat sf = new SimpleDateFormat("dd MMMMM - HH:mm");
        sf.setTimeZone(tz);
        Date date = new Date(time);
        PrettyTime t = new PrettyTime(date);
        return t.format(new Date());
    }

}
