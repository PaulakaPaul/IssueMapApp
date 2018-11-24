package stargazing.lowkey.main.fragments.issue;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import stargazing.lowkey.main.fragments.issue.MapFragmentFragment;
import stargazing.lowkey.main.fragments.issue.MapListFragmentFragment;

public class SampleAdapter extends FragmentPagerAdapter {
    Context ctxt=null;

    public SampleAdapter(Context ctxt, FragmentManager mgr) {
        super(mgr);
        this.ctxt=ctxt;
    }

    @Override
    public int getCount() {
        return(2);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return(MapFragmentFragment.newInstance(position));
        else
            return(MapListFragmentFragment.newInstance(position));
    }

    @Override
    public String getPageTitle(int position) {
        return "";
    }
}

