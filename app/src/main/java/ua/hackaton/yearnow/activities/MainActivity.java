package ua.hackaton.yearnow.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ua.hackaton.yearnow.R;
import ua.hackaton.yearnow.adapters.ViewPagerAdapter;
import ua.hackaton.yearnow.Event;
import ua.hackaton.yearnow.services.NewService;
import ua.hackaton.yearnow.services.YearService;


public class MainActivity extends ActionBarActivity {


    private TextView mTime_now, mYear_now;
    private Handler mHandler = new Handler();
    private ViewPager mDetailPager;

    private String mYearsJson;
    private ViewPagerAdapter mViewPagerAdapter;
    public static ViewPager sDetailPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sDetailPager = (ViewPager) findViewById(R.id.detail_viewpager);
        mTime_now = (TextView) findViewById(R.id.time_now_textview);
        mYear_now = (TextView) findViewById(R.id.year_now_textview);

        startService(new Intent(this, NewService.class));

        regReceivers();
//        Intent intent = new Intent(this,YearService.class);
//        startService(intent);

//        mHandler.postDelayed(runnable, 0);

    }





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

    private BroadcastReceiver updateTimeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateTimeUi(intent);
        }


    private void updateTimeUi(Intent intent) {
        String time = intent.getStringExtra("time");
        String year = intent.getStringExtra("year");

        mTime_now.setText(time);
        mYear_now.setText(year);
    }
    };

       private void regReceivers(){
           this.registerReceiver(updateTimeBroadcastReceiver,
                   new IntentFilter(YearService.BROADCAST_UPDATE_TIME));
       }


}
