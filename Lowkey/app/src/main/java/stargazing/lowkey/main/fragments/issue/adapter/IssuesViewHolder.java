package stargazing.lowkey.main.fragments.issue.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import stargazing.lowkey.R;
import stargazing.lowkey.models.IssueModel;

public class IssuesViewHolder extends RecyclerView.ViewHolder {


    //ImageView image;
    TextView title;
    TextView description;
    CircleImageView userImage;
    Double latitude;
    Double longitude;
    UUID createdBy;

    public IssuesViewHolder(View itemView) {
        super(itemView);

        if(itemView!=null) {
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.title);

        }
    }

    public void bindUI(IssueModel issueModel){
        title.setText("OK");
        description.setText("OK");
    }
/*
    public void bind(final IssuesViewHolder item, final IssuesAdapter.OnItemClickListenerNews listener) {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item,v);
            }
        });
    }
    public void bindDelete(final IssuesViewHolder item, final IssuesAdapter.OnDeleteItem listener){
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteItem(item,view);
            }
        });
    }
    public void bindViewProfile(final IssuesViewHolder item, final IssuesAdapter.onViewProfile listener){
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                listener.viewProfile(item,view);
            }
        });
    }
*/
}
