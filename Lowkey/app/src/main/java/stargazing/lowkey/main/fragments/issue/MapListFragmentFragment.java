package stargazing.lowkey.main.fragments.issue;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import stargazing.lowkey.R;
import stargazing.lowkey.main.fragments.issue.adapter.IssuesAdapter;
import stargazing.lowkey.models.IssueModel;

public class MapListFragmentFragment extends Fragment {

    private static final String KEY_POSITION = "position";
    //If there's need for a callback from the Parrent
    RecyclerView rvContacts;

    static MapListFragmentFragment newInstance(int position) {
        MapListFragmentFragment frag = new MapListFragmentFragment();
        Bundle args = new Bundle();
//f
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return (frag);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_fragment_listmap, container, false);
        int position = getArguments().getInt(KEY_POSITION, -1);
        //mainCallback.DoSomething();

        rvContacts = (RecyclerView) result.findViewById(R.id.issuerv);
        // Initialize contacts
        // Create adapter passing in the sample user data
        ArrayList<IssueModel> list = new ArrayList<IssueModel>();
        list.add(new IssueModel());
        list.add(new IssueModel());
        list.add(new IssueModel());
        list.add(new IssueModel());
        list.add(new IssueModel());
        list.add(new IssueModel());
        list.add(new IssueModel());

        IssuesAdapter adapter = new IssuesAdapter(list, getContext().getApplicationContext(), rvContacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));


        return (result);
    }

    private void initAdapter() {
        // Initialize contacts
        // Create adapter passing in the sample user data
        final ArrayList<IssueModel> list = new ArrayList<IssueModel>();
        list.add(new IssueModel());
        list.add(new IssueModel());
        list.add(new IssueModel());
        list.add(new IssueModel());
        list.add(new IssueModel());
        list.add(new IssueModel());
        list.add(new IssueModel());

        final IssuesAdapter adapter = new IssuesAdapter(list, getContext().getApplicationContext(), rvContacts);
        // Attach the adapter to the recyclerview to populate items
        adapter.setOnLoadMoreListener(new IssuesAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                final IssueModel m = null;
                list.add(m);
                adapter.notifyItemInserted(list.size() - 1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.removeItem(list.indexOf(m));
                    }
                }, 2000);

            }});
            rvContacts.setAdapter(adapter);
            // Set layout manager to position the items
        rvContacts.setLayoutManager(new

            LinearLayoutManager(getContext().

            getApplicationContext()));

        }
    }
