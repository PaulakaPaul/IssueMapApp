package stargazing.lowkey.main.fragments.issue;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import java.util.ArrayList;

import stargazing.lowkey.LowkeyApplication;
import stargazing.lowkey.R;
import stargazing.lowkey.api.wrapper.OnSuccessListHandler;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.auth.register.RegisterActivity3AG;
import stargazing.lowkey.main.fragments.issue.adapter.IssuesAdapter;
import stargazing.lowkey.main.fragments.issue.adapter.IssuesViewHolder;
import stargazing.lowkey.managers.IssueManager;
import stargazing.lowkey.models.CommentGetModel;
import stargazing.lowkey.models.IssueGetModel;

public class MapListFragmentFragment extends Fragment {

    private static final String KEY_POSITION = "position";
    RecyclerView rvContacts;
    IssueManager issueManager = new IssueManager();
    ArrayList<IssueGetModel> list = new ArrayList<IssueGetModel>();

    static MapListFragmentFragment newInstance(int position) {
        MapListFragmentFragment frag = new MapListFragmentFragment();
        Bundle args = new Bundle();
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
        putIssues();

        return (result);
    }

    private void initAdapter() {
        // Initialize contacts
        // Create adapter passing in the sample user data

        final IssuesAdapter adapter = new IssuesAdapter(list);
        adapter.setListener(new IssuesAdapter.OnItemClickListenerNews() {
            @Override
            public void onItemClick(IssuesViewHolder item, View v) {
                Intent intent = new Intent(getContext(), ComentsActivity.class);
                startActivity(intent);
            }
        });
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new

                LinearLayoutManager(getContext().

                getApplicationContext()));

    }

    private void putIssues() {

        issueManager.getAll(new OnSuccessListHandler() {
            @Override
            public void handle(JSONArray response) {
                if (!response.equals(RequestWrapper.FAIL_JSON_LIST_RESPONSE_VALUE)) {
                    list = issueManager.getIssues();
                    LowkeyApplication.staticIssues = list;
                    final IssuesAdapter adapter = new IssuesAdapter(list);
                    adapter.setListener(new IssuesAdapter.OnItemClickListenerNews() {
                        @Override
                        public void onItemClick(IssuesViewHolder item, View v) {
                           boolean found = false;
                            Intent intent = new Intent(getContext(), ComentsActivity.class);
                            int position = item.getAdapterPosition();
                            IssueGetModel m = adapter.getMsg(position);
                            for (IssueGetModel i : list) {
                                if (i.getId().equals(m.getId())){
                                    intent.putExtra("index", list.indexOf(i));
                                    found = true ;
                                }}
                            if(!found)
                                intent.putExtra("index", -1);
                            getActivity().overridePendingTransition(0, 0);
                            startActivity(intent);
                            getActivity().overridePendingTransition(0, 0);


                        }
                    });
                    rvContacts.setAdapter(adapter);
                    rvContacts.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
                }
            }
        });
    }
}

