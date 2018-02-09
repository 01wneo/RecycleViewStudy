package com.example.happyghost.widgetstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happyghost.widgetstudy.ipl.OnSwipListener;
import com.example.happyghost.widgetstudy.recycleviewhelper.CardItemTouchHelperCallBack;
import com.example.happyghost.widgetstudy.recycleviewhelper.CardLayoutManager;
import com.example.happyghost.widgetstudy.recycleviewhelper.RecycleItemDecorator;
import com.example.happyghost.widgetstudy.recycleviewhelper.RecycleViewAdapter;
import com.example.happyghost.widgetstudy.recycleviewhelper.RecycleViewHolder;
import com.example.happyghost.widgetstudy.rx.RxBus;
import com.example.happyghost.widgetstudy.rx.RxBusMessage;
import com.example.happyghost.widgetstudy.test.RxTestActivity;
import com.example.happyghost.widgetstudy.widget.ProgressCustomView;
import com.example.happyghost.widgetstudy.widget.SectorDiagram;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HomeActivity extends AppCompatActivity {
//    private SectorDiagram mSd;
//    private Button button;
//    private ProgressCustomView mPcView;

    private RecyclerView mRvList;
    private ItemTouchHelper itemTouchHelper;
    private TextView mTvRebus;
    private RxBus rxBus;
    private TextView rx2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initData();
    }

    private void initData() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.img_avatar_01);
        list.add(R.mipmap.img_avatar_02);
        list.add(R.mipmap.img_avatar_03);
        list.add(R.mipmap.img_avatar_04);
        list.add(R.mipmap.img_avatar_05);
        list.add(R.mipmap.img_avatar_06);
        list.add(R.mipmap.img_avatar_07);
        RecycleViewAdapter adapter = new RecycleViewAdapter<Integer>(HomeActivity.this,list);
        mRvList.setAdapter(adapter);
        mRvList.setItemAnimator(new DefaultItemAnimator());
        CardItemTouchHelperCallBack callBack = new CardItemTouchHelperCallBack(adapter,list);
        callBack.setOnSwipListener(new OnSwipListener() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                RecycleViewHolder myHolder = (RecycleViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
                if (direction == CardLayoutManager.SWIPING_LEFT) {
                    myHolder.dislikeImageView.setAlpha(Math.abs(ratio));
                } else if (direction == CardLayoutManager.SWIPING_RIGHT) {
                    myHolder.likeImageView.setAlpha(Math.abs(ratio));
                } else {
                    myHolder.dislikeImageView.setAlpha(0f);
                    myHolder.likeImageView.setAlpha(0f);
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int direction) {
                RecycleViewHolder myHolder = (RecycleViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1f);
                myHolder.dislikeImageView.setAlpha(0f);
                myHolder.likeImageView.setAlpha(0f);
                Toast.makeText(HomeActivity.this, direction == CardLayoutManager.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipClear() {
                Toast.makeText(HomeActivity.this, "data clear", Toast.LENGTH_SHORT).show();
                mRvList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        mRvList.getAdapter().notifyDataSetChanged();
                    }
                }, 3000L);
            }
        });
        itemTouchHelper = new ItemTouchHelper(callBack);
        mRvList.setLayoutManager(new CardLayoutManager(mRvList, itemTouchHelper));
//        mRvList.addItemDecoration(new RecycleItemDecorator(HomeActivity.this));
        itemTouchHelper.attachToRecyclerView(mRvList);

//        mSd.setLoadButtonOnClickListener(new SectorDiagram.LoadButonOnClickListener() {
//            @Override
//            public void onClick() {
//                Toast.makeText(getApplicationContext(),"bei dian ji dao le",Toast.LENGTH_SHORT).show();
//            }
//        });
//        mPcView = (ProgressCustomView) findViewById(R.id.sector_diagram);
//        mPcView.setPCviewOnClickListener(new ProgressCustomView.PCviewOnClickListener() {
//            @Override
//            public void onClick() {
//                Toast.makeText(getApplicationContext(),"bei dian ji dao le",Toast.LENGTH_SHORT).show();
//                mPcView.startAnimation();
//            }
//        });
//        mPcView.setmText("haha");


    }

    private void initView() {
        mRvList = (RecyclerView) findViewById(R.id.rv_timemmachine);
        mTvRebus = (TextView) findViewById(R.id.tv_Rxbus);
        rx2 = (TextView) findViewById(R.id.tv_Rxbus2);
        Button mBtRxBus = (Button) findViewById(R.id.bt_rxbus);
//        mSd = (SectorDiagram) findViewById(R.id.sector_diagram);
//        button = (Button) findViewById(R.id.bt_animation);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSd.setDiagram(270);
//            }
//        });
        mBtRxBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RxTestActivity.class);
                startActivity(intent);
            }
        });
        initRxBus();
    }

    private void initRxBus() {
        rxBus = RxBus.getIntanceBus();
        registerRxBus(RxBusMessage.class, new Consumer<RxBusMessage>() {
            @Override
            public void accept(@NonNull RxBusMessage rxBusMessage) throws Exception {

                if(TextUtils.equals(rxBusMessage.getMessage(),"1")){
                    mTvRebus.setText(rxBusMessage.getMessage());
                }else if(TextUtils.equals(rxBusMessage.getMessage(),"2")){
                    rx2.setText(rxBusMessage.getMessage());
                }
            }
        });
    }


    public <T> void registerRxBus(Class<T> eventType, Consumer<T> action) {
        Disposable disposable = rxBus.doSubscribe(eventType, action, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e("NewsMainPresenter", throwable.toString());
            }
        });
        rxBus.addSubscription(this,disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterRxBus();
    }

    public void unregisterRxBus() {
        rxBus.unSubscribe(this);
    }
}
