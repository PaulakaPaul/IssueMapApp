package stargazing.lowkey.main.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import stargazing.lowkey.R;

public class MapListFragmentFragment extends Fragment {

    private static final String KEY_POSITION = "position";
    //If there's need for a callback from the Parrent

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

        RecyclerView rvContacts = (RecyclerView) result.findViewById(R.id.issuerv);
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

        IssuesAdapter adapter = new IssuesAdapter(list,getContext().getApplicationContext(),rvContacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));


        return (result);
    }


}
