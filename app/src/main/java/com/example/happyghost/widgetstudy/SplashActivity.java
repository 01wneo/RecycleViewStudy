package com.example.happyghost.widgetstudy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.happyghost.widgetstudy.widget.ProgressCustomView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Zhao Chenping
 * @creat 2017/7/12.
 * @description
 */

public class SplashActivity extends AppCompatActivity{

    private ProgressCustomView downTime;
    private boolean isStart =false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
    }

    private void initView() {
        downTime = (ProgressCustomView) findViewById(R.id.downtime);
    }

    private void initData() {
        downTime.startAnimation();
        downTime.setPCviewOnClickListener(new ProgressCustomView.PCviewOnClickListener() {
            @Override
            public void onClick() {
                startHomeActivity();
            }
        });
        if(!isStart){
            downTime(5).subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    if (isStart){
                        d.dispose();
                    }
                }

                @Override
                public void onNext(Integer value) {

                    downTime.setmText("倒计时:"+value);
                    downTime.setmTextColor(Color.parseColor("#000000"));
                }

                @Override
                public void onError(Throwable e) {
                    startHomeActivity();
                }

                @Override
                public void onComplete() {
                    startHomeActivity();
                }
            });
        }
    }

    private void startHomeActivity() {
        if(!isStart){
            isStart=true;
            Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public Observable<Integer> downTime(final int time){
        return Observable.interval(0,1, TimeUnit.SECONDS)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return time-aLong.intValue();
                    }
                })
                .take(time+1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
