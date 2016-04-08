package me.baron.weatherstyle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.InvalidClassException;

import me.baron.androidlibrary.activity.BaseActivity;
import me.baron.weatherstyle.R;
import me.baron.weatherstyle.preferences.Preferences;
import me.baron.weatherstyle.preferences.WeatherSettings;
import me.baron.weatherstyle.utils.CommonUtil;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
public class WelcomeActivity extends BaseActivity {

    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate start");
        setContentView(R.layout.activity_splash);

        Observable.just(initAppData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> gotoMainPage());

        Log.d(TAG, "onCreate end");
    }

    private void gotoMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 初始化应用数据
     */
    private String initAppData() {
        Preferences.loadDefaults();
        //TODO 测试，待删除
        try {
            Preferences.savePreference(WeatherSettings.SETTINGS_CURRENT_CITY_ID, "101020100");
        } catch (InvalidClassException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "importCityData start");
        CommonUtil.importCityData();
        Log.d(TAG, "importCityData end");
        return null;
    }
}