package ua.hackaton.yearnow.activities.adapters;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import static android.view.ViewGroup.*;

/**
 * Created by andriy on 04.04.15.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Activity mActivity;
    private String[] mEventsArray;

    public ViewPagerAdapter(Activity activity,String[] events){
        mActivity = activity;
        mEventsArray = events;
    }

    @Override
    public int getCount() {
        return mEventsArray.length;
    }

    public Object instantiateItem (View collection, int position){
        TextView currentEvent = new TextView(mActivity);
        currentEvent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        currentEvent.setText(mEventsArray[position]);
        ((ViewPager) collection).addView(currentEvent,0);
        return currentEvent;
    }

    @Override
    public void destroyItem(View view, int i, Object object){
        ((ViewPager) view).removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view == ((View) object);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
