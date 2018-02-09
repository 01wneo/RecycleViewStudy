package com.example.happyghost.widgetstudy;

import android.app.Application;

import com.example.happyghost.widgetstudy.rx.RxBus;

/**
 * @author Zhao Chenping
 * @creat 2017/7/15.
 * @description
 */

public class MyApp extends Application {

    public RxBus getRxBus() {
        return rxBus;
    }

    private RxBus rxBus;

    @Override
    public void onCreate() {
        super.onCreate();
        rxBus = RxBus.getIntanceBus();
    }
}
