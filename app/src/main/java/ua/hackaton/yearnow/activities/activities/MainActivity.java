package ua.hackaton.yearnow.activities.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Vocalizer;
import ru.yandex.speechkit.VocalizerListener;
import ua.hackaton.yearnow.R;
import ua.hackaton.yearnow.activities.adapters.ViewPagerAdapter;
import ua.hackaton.yearnow.activities.widgets.YearWidgetProvider;


public class MainActivity extends ActionBarActivity {


    private ru.yandex.speechkit.Vocalizer vocalizer;
    private TextView time_now, year_now;
    private Button button_TTS;
    private Handler mHandler = new Handler();
    public static ViewPager mDetailPager;

    public static String[] sEventsExample = {
            "example 1", "example 2", "example 3", "example 4", "example 5", "example 6 ", "example 7", "example 8",
            "example 9", "example 10", "example 11", "example 12", "example 13", "example 14", "example 15", "example 16",
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

        button_TTS = (Button) findViewById(R.id.button_widget_TTS);

        mHandler.postDelayed(runnable, 0);

        //Hardcoded widget updating
        

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

    private void openYearsJson() {

    }

    private void updateWidget(String time, String year, String title, String description) {
        if (time != null && year != null) {

            ComponentName thisWidget = new ComponentName(getApplicationContext(),
                    YearWidgetProvider.class);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                    .getApplicationContext());

            RemoteViews views = new RemoteViews(
                    thisWidget.getPackageName(),
                    R.layout.widgetlayout_background);

            views.setTextViewText(R.id.textView_year, time);
            views.setTextViewText(R.id.textView_storySummary, year);
            views.setTextViewText(R.id.textView_title, title);
            views.setTextViewText(R.id.textView_description, description);

            appWidgetManager.updateAppWidget(
                    new ComponentName(this.getPackageName(),
                            YearWidgetProvider.class.getName()),
                    views);
        }

    }


    // Слушатель события нажатия кнопки
    public void onTtsButtonClick(View view) {
        // Метод должен быть вызван перед началом использования любой функциональности SpeechKit
        SpeechKit.getInstance().configure(getBaseContext(), "3de0cf79-b08e-4356-ba85-ce9ad6d91bce");

        startTTS("Это проверочная речь. Есть ли у меня интонация? Ахаха!");
    }

    // Принимает на вход текст, который нужно озвучить.
    private void startTTS(String text) {

        if (vocalizer != null) {
            Toast.makeText(getApplicationContext(), "Already running", Toast.LENGTH_SHORT).show();
            return;
        }

        if (text != null) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            vocalizer = ru.yandex.speechkit.Vocalizer.createVocalizer("ru-RU", text, prefs.getBoolean("auto_play", true));

            vocalizer.setListener(new TestListener());

            vocalizer.start();
        }
    }

    private void cancelTTS() {
        if (vocalizer != null) {
            vocalizer.cancel();
            vocalizer = null;
        }
    }

    private boolean isTTSActive() {
        return vocalizer != null;
    }

    // Listener, который должен быть имплементирован.
    private class TestListener implements VocalizerListener {

        public void onSynthesisBegin(Vocalizer vocalizer) {
        }

        public void onSynthesisDone(Vocalizer vocalizer, Synthesis synthesis) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }

        public void onPlayingBegin(Vocalizer vocalizer) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }

        public void onPlayingDone(Vocalizer vocalizer) {
            MainActivity.this.vocalizer = null;
        }

        @Override
        public void onVocalizerError(Vocalizer vocalizer, ru.yandex.speechkit.Error error) {
            MainActivity.this.vocalizer = null;
        }
    }

}
