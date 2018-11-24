package stargazing.lowkey.main.fragments.issue.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
import stargazing.lowkey.models.IssueModel;

public class IssuesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private ArrayList<IssueModel> mMessages;
    private OnLoadMoreListener onLoadMoreListener;
    public OnItemClickListenerNews listener;
    public OnDeleteItem del;
    private Context mcontext;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;

    public IssuesAdapter(ArrayList<IssueModel> mMessages, Context context, RecyclerView recyclerView) {
        this.mMessages = mMessages;
        this.mcontext = context;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            }
        });

    }

    public interface OnItemClickListenerNews {
        void onItemClick(IssuesViewHolder item, View v);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnDeleteItem {
        void deleteItem(IssuesViewHolder item, View v);
    }

    public interface onViewProfile {
        void viewProfile(IssuesViewHolder item,View v);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mcontext).inflate(R.layout.issue_item, parent, false);
            return new IssuesViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mcontext).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof IssuesViewHolder) {
            final IssueModel issueDto = this.mMessages.get(position);
            final IssuesViewHolder holderToBind = (IssuesViewHolder) holder;
            holderToBind.bindUI(issueDto);

        } else if (holder instanceof LoadingViewHolder) {

        }

    }

    @Override
    public int getItemCount() {
        return mMessages != null ? mMessages.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoaded() {
        isLoading = false;
    }



    public void clear() {
        mMessages.clear();
        notifyDataSetChanged();
    }


    public void setListener(OnItemClickListenerNews listener) {
        this.listener = listener;
    }

    public void setDeleteListener(OnDeleteItem listener) {
        this.del = listener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    private String localTime(Long time) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        SimpleDateFormat sf = new SimpleDateFormat("dd MMMMM - HH:mm");
        sf.setTimeZone(tz);
        Date date = new Date(time);
        //PrettyTime t = new PrettyTime(date);
        //return t.format(new Date());
        return  null;
    }

    public void removeItem(int position) {
        if(position != -1 && position < mMessages.size()) {
            mMessages.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mMessages.size());
        }
    }

    public IssueModel getMsg(int position) {
        if(position < 0)
            return new IssueModel();

        return mMessages.get(position);
    }

    public void addItem(IssueModel msg) {
        mMessages.add(msg);
        notifyItemInserted(mMessages.size());
    }

    public int getPosition(IssueModel msg) {
        return mMessages.indexOf(msg);
    }

}
