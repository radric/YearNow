package ua.hackaton.yearnow.adapters;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;


import ua.hackaton.yearnow.R;
import ua.hackaton.yearnow.Event;
import ua.hackaton.yearnow.widgets.YearWidgetProvider;

/**
 * Created by andriy on 04.04.15.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private static Context context;
    private Event[] mEvent;
    private LayoutInflater mInflater;
    public static ViewPagerAdapter sViewPagerAdapter;

    public ViewPagerAdapter(Context context,Event[] events){
        ViewPagerAdapter.context = context;
        mEvent = events;
        mInflater = (LayoutInflater) ViewPagerAdapter.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return mEvent.length;
    }

    public Object instantiateItem (View collection, int position){
        View view = mInflater.inflate(R.layout.list_item_events,null);
        ((ViewPager)collection).addView(view,0);
        TextView eventTitle = (TextView) view.findViewById(R.id.event_title);
        TextView eventDesc = (TextView) view.findViewById(R.id.event_desc);
        eventTitle.setText(mEvent[position].mTitle);
        eventDesc.setText(mEvent[position].mDesc);

        //Hardcoded widget updating
        updateWidget(eventTitle.getText().toString(),eventDesc.getText().toString());
        return view;
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

    public static void getPagerAdapter(Activity activity, Event[] event){
        sViewPagerAdapter = new ViewPagerAdapter(activity, event);
    }

    private void updateWidget(String heading, String summary) {
        if(heading != null && summary != null) {

            ComponentName thisWidget = new ComponentName(context,
                    YearWidgetProvider.class);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews views = new RemoteViews(
                    thisWidget.getPackageName(),
                    R.layout.widgetlayout_background);

            views.setTextViewText(R.id.textView_storyHeading, heading);
            views.setTextViewText(R.id.textView_storySummary, summary);

            appWidgetManager.updateAppWidget(
                    new ComponentName(context.getPackageName(),
                            YearWidgetProvider.class.getName()),
                    views);
        }

    }
}
