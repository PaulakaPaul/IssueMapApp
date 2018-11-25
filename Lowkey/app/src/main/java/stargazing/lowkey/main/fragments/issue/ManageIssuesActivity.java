package stargazing.lowkey.main.fragments.issue;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONArray;

import java.util.ArrayList;

import stargazing.lowkey.LowkeyApplication;
import stargazing.lowkey.R;
import stargazing.lowkey.api.wrapper.OnSuccessListHandler;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.main.fragments.issue.adapter.IssuesAdapter;
import stargazing.lowkey.main.fragments.issue.adapter.IssuesViewHolder;
import stargazing.lowkey.managers.IssueManager;
import stargazing.lowkey.models.IssueGetModel;
import stargazing.lowkey.models.IssueModel;

public class ManageIssuesActivity extends AppCompatActivity {

    RecyclerView rvContacts;
    FloatingActionButton floatingActionButton;
    IssueManager issueManager = new IssueManager();
    ArrayList<IssueGetModel> list = new ArrayList<IssueGetModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_issues);
        //mainCallback.DoSomething();
        rvContacts = (RecyclerView) findViewById(R.id.issuerv);
        putIssues();
    }

    private void initAdapter() {
        // Initialize contacts
        // Create adapter passing in the sample user data

        final IssuesAdapter adapter = new IssuesAdapter(list);
        adapter.setListener(new IssuesAdapter.OnItemClickListenerNews() {
            @Override
            public void onItemClick(IssuesViewHolder item, View v) {
                Intent intent = new Intent(getApplicationContext(), ComentsActivity.class);
                startActivity(intent);
            }
        });
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new

                LinearLayoutManager(getApplicationContext().

                getApplicationContext()));

    }

    private void putIssues() {

        issueManager.getAll(new OnSuccessListHandler() {
            @Override
            public void handle(JSONArray response) {
                if (!response.equals(RequestWrapper.FAIL_JSON_LIST_RESPONSE_VALUE)) {
                    list = LowkeyApplication.currentUserManager.getUserModel().getIssues();


                    LowkeyApplication.staticIssues = list;
                    final IssuesAdapter adapter = new IssuesAdapter(list);
                    adapter.setListener(new IssuesAdapter.OnItemClickListenerNews() {
                        @Override
                        public void onItemClick(IssuesViewHolder item, View v) {
                            boolean found = false;
                            Intent intent = new Intent(getApplicationContext(), ComentsActivity.class);
                            int position = item.getAdapterPosition();
                            IssueGetModel m = adapter.getMsg(position);
                            for (IssueGetModel i : list) {
                                if (i.getId().equals(m.getId())){
                                    intent.putExtra("index", list.indexOf(i));
                                    found = true ;
                                }}
                            if(!found)
                                intent.putExtra("index", -1);
                            overridePendingTransition(0, 0);
                            startActivity(intent);
                            overridePendingTransition(0, 0);


                        }
                    });
                    rvContacts.setAdapter(adapter);
                    rvContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }
        });
    }


}
