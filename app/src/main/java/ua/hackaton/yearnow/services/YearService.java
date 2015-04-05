package ua.hackaton.yearnow.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.view.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Random;

import ua.hackaton.yearnow.Event;
import ua.hackaton.yearnow.R;
import ua.hackaton.yearnow.activities.MainActivity;
import ua.hackaton.yearnow.adapters.ViewPagerAdapter;

import static ua.hackaton.yearnow.activities.MainActivity.*;

/**
 * Created by andriy on 05.04.15.
 */
public class YearService extends Service {

    public static final String BROADCAST_UPDATE_TIME = "ua.hackaton.yearnow.services.UPDATE_TIME";
    public static final String BROADCAST_UPDATE_EVENT = "ua.hackaton.yearnow.services.UPDATE_EVENT";

    private Handler mHandler = new Handler();
    private String mYearsJson;
    private Intent mUpdateTimeIntent, mUpdateEventIntent;
    private String mTimeStr, mYearStr;
    private int mResAmountEvents;
    private Event[] mEvent;
    private ViewPagerAdapter mViewPagerAdapter;



    @Override
    public void onCreate(){
        mUpdateTimeIntent = new Intent(BROADCAST_UPDATE_TIME);
        mUpdateEventIntent = new Intent(BROADCAST_UPDATE_EVENT);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent,int flags,int startId) {

        mHandler.postDelayed(runnable, 0);
        return START_NOT_STICKY;
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long time = System.currentTimeMillis();
            SimpleDateFormat mSdf = new SimpleDateFormat("HH:mm:ss");
            mTimeStr = mSdf.format(time);

            mSdf = new SimpleDateFormat("HHmm" + " " + "рік");
            mYearStr = mSdf.format(time);

            getEvent();

            sendUpdateTimeBroadcastReceiver();
            sendUpdateEventBroadcastReceiver();

            mHandler.postDelayed(this, 1000);
        }
    };

    public void getEvent(){
        AssetManager assetManager = getAssets();
        try {
            String filename = "years(ru).txt";
            InputStream is = assetManager.open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            mYearsJson = new String(buffer);
        } catch (IOException e){
            e.printStackTrace();
        }
        try {
            mViewPagerAdapter = new ViewPagerAdapter(getApplicationContext(), getYearDataFromJson(mYearsJson));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private Event[] getYearDataFromJson(String yearsJson)
            throws JSONException {
//        String currentYearStr = year_now.toString();
        String currentYearStr = "2015";
        final String YN_AMOUNT = "amount";
        final String YN_EVENTS = "events";
        final String YN_TITLE = "title";
        final String YN_DESC = "description";

        JSONObject years = new JSONObject(yearsJson);
        JSONObject currentYearObject = years.getJSONObject(currentYearStr);
        JSONArray currentYearArray = currentYearObject.getJSONArray(YN_EVENTS);

        mResAmountEvents = currentYearObject.getInt(YN_AMOUNT);

        mEvent = new Event[mResAmountEvents];

        for (int i=0; i<mResAmountEvents;i++){
            String title;
            String description;
            JSONObject currentEvent = currentYearArray.getJSONObject(i);
            title = currentEvent.getString(YN_TITLE);
            description = currentEvent.getString(YN_DESC);
            mEvent[i] = new Event(title, description);
        }

        return mEvent;
    }

    public void sendUpdateTimeBroadcastReceiver(){
        mUpdateTimeIntent.putExtra("time",mTimeStr);
        mUpdateTimeIntent.putExtra("year",mYearStr);
        sendBroadcast(mUpdateTimeIntent);
    }

    public void sendUpdateEventBroadcastReceiver(){
        Random random = new Random();
        int randomEvent = random.nextInt(mResAmountEvents);

        sDetailPager.setAdapter(mViewPagerAdapter);
        sDetailPager.setCurrentItem(randomEvent);

        mUpdateEventIntent.putExtra("title",mEvent[randomEvent].mTitle);
        mUpdateEventIntent.putExtra("title",mEvent[randomEvent].mDesc);
        sendBroadcast(mUpdateEventIntent);
    }



}
