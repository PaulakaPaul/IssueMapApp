package stargazing.lowkey.main.fragments.issue.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import stargazing.lowkey.R;
import stargazing.lowkey.models.IssueGetModel;
import stargazing.lowkey.models.IssueModel;

public class IssuesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private ArrayList<IssueGetModel> mIssues;
    public OnItemClickListenerNews listener;
    public OnDeleteItem del;

    public IssuesAdapter(ArrayList<IssueGetModel> mIssues) {
        this.mIssues = mIssues;
    }

    public interface OnItemClickListenerNews {
        void onItemClick(IssuesViewHolder item, View v);
    }

    public interface OnDeleteItem {
        void deleteItem(IssuesViewHolder item, View v);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new IssuesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.issue_item, parent, false));

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

            final IssueGetModel issueDto = this.mIssues.get(position);
            final IssuesViewHolder holderToBind = (IssuesViewHolder) holder;
            holderToBind.bindUI(issueDto);
            holderToBind.bind(holderToBind,listener);
            holderToBind.bindDelete(holderToBind,del);

    }

    @Override
    public int getItemCount() {
        return mIssues != null ? mIssues.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mIssues.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    public void clear() {
        mIssues.clear();
        notifyDataSetChanged();
    }


    public void setListener(OnItemClickListenerNews listener) {
        this.listener = listener;
    }

    public void setDeleteListener(OnDeleteItem listener) {
        this.del = listener;
    }

    public void removeItem(int position) {
        if(position != -1 && position < mIssues.size()) {
            mIssues.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mIssues.size());
        }
    }

    public IssueGetModel getMsg(int position) {
        return mIssues.get(position);
    }

    public void addItem(IssueGetModel msg) {
        mIssues.add(msg);
        notifyItemInserted(mIssues.size());
    }

    public int getPosition(IssueModel msg) {
        return mIssues.indexOf(msg);
    }

}
