package stargazing.lowkey.main.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import stargazing.lowkey.R;

public class LoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;

    public LoadingViewHolder(View view) {
        super(view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
    }
}
