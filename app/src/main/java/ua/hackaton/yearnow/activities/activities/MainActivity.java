package ua.hackaton.yearnow.activities.activities;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import android.os.Handler;

import ua.hackaton.yearnow.R;
import ua.hackaton.yearnow.activities.adapters.ViewPagerAdapter;


public class MainActivity extends ActionBarActivity {


    private TextView time_now, year_now;
    private Handler mHandler = new Handler();
    public static ViewPager mDetailPager;
    public static String[] sEventsExample = {
            "example 1","example 2","example 3","example 4","example 5","example 6 ","example 7","example 8",
            "example 9","example 10","example 11","example 12","example 13","example 14","example 15","example 16",
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(this, sEventsExample);
        mDetailPager = (ViewPager) findViewById(R.id.detail_viewpager);
        mDetailPager.setAdapter(mViewPagerAdapter);
        mDetailPager.setCurrentItem(0);

        time_now = (TextView) findViewById(R.id.time_now_textview);
        year_now = (TextView) findViewById(R.id.year_now_textview);

        mHandler.postDelayed(runnable, 0);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long time = System.currentTimeMillis();
            SimpleDateFormat mSdf = new SimpleDateFormat("HH:mm:ss");
            String timeStr = mSdf.format(time);
            time_now.setText(timeStr);

            mSdf = new SimpleDateFormat("HHmm" + " " + "рік");
            String yearStr = mSdf.format(time);
            year_now.setText(yearStr);

            mHandler.postDelayed(this, 1000);
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openYearsJson(){

    }
}
