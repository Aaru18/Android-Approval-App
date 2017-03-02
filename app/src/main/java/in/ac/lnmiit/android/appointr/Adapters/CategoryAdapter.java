package in.ac.lnmiit.android.appointr.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.ac.lnmiit.android.appointr.Home.ConfirmedFragment;
import in.ac.lnmiit.android.appointr.R;
import in.ac.lnmiit.android.appointr.Home.UnconfirmedFragment;

public class CategoryAdapter  extends FragmentPagerAdapter{
    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm){
        super(fm);
        mContext = context;
    }
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ConfirmedFragment();
        } else  {
            return new UnconfirmedFragment();
        }
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.confirmed);
        } else  {
            return mContext.getString(R.string.unconfirmed);
        }
    }
}
