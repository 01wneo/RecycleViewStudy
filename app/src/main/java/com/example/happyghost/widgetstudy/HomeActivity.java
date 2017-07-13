package com.example.happyghost.widgetstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.happyghost.widgetstudy.widget.ProgressCustomView;
import com.example.happyghost.widgetstudy.widget.SectorDiagram;

public class HomeActivity extends AppCompatActivity {
    private SectorDiagram mSd;
    private Button button;
    private ProgressCustomView mPcView;

//    private RecyclerView mRvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initData();
    }

    private void initData() {
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            list.add(i);
//        }
//        mRvList.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
//        mRvList.addItemDecoration(new RecycleItemDecorator(HomeActivity.this));
//        RecycleViewAdapter adapter = new RecycleViewAdapter<Integer>(HomeActivity.this,list);
//        mRvList.setAdapter(adapter);
//        mSd.setLoadButtonOnClickListener(new SectorDiagram.LoadButonOnClickListener() {
//            @Override
//            public void onClick() {
//                Toast.makeText(getApplicationContext(),"bei dian ji dao le",Toast.LENGTH_SHORT).show();
//            }
//        });
        mPcView = (ProgressCustomView) findViewById(R.id.sector_diagram);
        mPcView.setPCviewOnClickListener(new ProgressCustomView.PCviewOnClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(getApplicationContext(),"bei dian ji dao le",Toast.LENGTH_SHORT).show();
                mPcView.startAnimation();
            }
        });
        mPcView.setmText("haha");


    }

    private void initView() {
//        mRvList = (RecyclerView) findViewById(R.id.rv_timemmachine);
//        mSd = (SectorDiagram) findViewById(R.id.sector_diagram);
//        button = (Button) findViewById(R.id.bt_animation);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSd.setDiagram(270);
//            }
//        });
    }
}
