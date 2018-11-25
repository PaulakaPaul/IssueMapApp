package stargazing.lowkey.main.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;

import org.json.JSONObject;

import stargazing.lowkey.LowkeyApplication;
import stargazing.lowkey.R;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.main.fragments.issue.IssuesFragment;
import stargazing.lowkey.managers.UserManager;

public class MainActivity extends AppCompatActivity implements
        ProfileFragment.OnFragmentInteractionListener,
        IssuesFragment.OnFragmentInteractionListener,
        StatisticFragment.OnFragmentInteractionListener {

    final Fragment profileFragment = new ProfileFragment();
    final Fragment issuesFragment = new IssuesFragment();
    final Fragment statisticFragment = new StatisticFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = profileFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fm.beginTransaction().hide(active).show(profileFragment).commit();
                    active = profileFragment;
                    return true;
                case R.id.navigation_dashboard:
                    fm.beginTransaction().hide(active).show(issuesFragment).commit();
                    active = issuesFragment;
                    return true;
                case R.id.navigation_notifications:
                    fm.beginTransaction().hide(active).show(statisticFragment).commit();
                    active = statisticFragment;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTransparent(this);
        fm.beginTransaction().add(R.id.main_container, issuesFragment, "2").hide(issuesFragment).commit();
        fm.beginTransaction().add(R.id.main_container, statisticFragment, "3").hide(statisticFragment).commit();
        fm.beginTransaction().add(R.id.main_container, profileFragment, "1").commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
