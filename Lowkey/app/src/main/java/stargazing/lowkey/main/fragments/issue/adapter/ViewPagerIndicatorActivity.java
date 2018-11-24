package stargazing.lowkey.main.fragments.issue.adapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import stargazing.lowkey.main.fragments.issue.IssuesFragment;

public class ViewPagerIndicatorActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content,
                            new IssuesFragment()).commit();
        }
    }
}
