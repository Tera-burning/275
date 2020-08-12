package com.example.carrot_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {

    TextView mTimer_Output;
    Button mStart_btn;
    Button mStop_btn;

    final static int INIT = 0;
    final static int RUN = 1;
    final static int PAUSE = 2;

    int mStatus = INIT;
    long mBaseTime;
    long mPauseTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTimer_Output = (TextView) findViewById(R.id.timer_output);
        mStart_btn = (Button) findViewById(R.id.start_btn);
        mStop_btn = (Button) findViewById(R.id.stop_btn);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public void OnClick(View v) {

        switch(v.getId()){
            case R.id.start_btn:
                switch(mStatus){

                    case INIT:
                        mBaseTime = SystemClock.elapsedRealtime();
                        System.out.println(mBaseTime);
                        mTimer.sendEmptyMessage(0);
                        mStart_btn.setText("정지");
                        mStatus = RUN;
                        break;
                    case RUN:
                        mTimer.removeMessages(0);
                        mPauseTime = SystemClock.elapsedRealtime();
                        mStart_btn.setText("시작");
                        mStatus =PAUSE;
                        break;
                    case PAUSE:
                        long now = SystemClock.elapsedRealtime();
                        mTimer.sendEmptyMessage(0);
                        mBaseTime += (now- mPauseTime);
                        mStart_btn.setText("정지");
                        mStatus = RUN;
                        break;

                }
                break;
            case R.id.stop_btn:

                TextView wordId = (TextView) mTimer_Output.findViewById(R.id.timer_output);
                String sId = wordId.getText().toString();
                Intent intent =  new Intent(getApplicationContext(), ChartActivity.class);
                String mOutputTime = getTimeOut();
                intent.putExtra("time",sId);
                startActivity(intent);
                finish();

                break;


        }


    }

    Handler mTimer = new Handler(){
        public void handleMessage(Message msg){
            mTimer_Output.setText(getTimeOut());
            mTimer.sendEmptyMessage(0);
        }
    };

    String getTimeOut(){
        long now = SystemClock.elapsedRealtime();
        long nTime = now - mBaseTime;
        String OutTime = String.format("%02d:%02d:%02d",nTime/1000 / 60, (nTime/1000)%60,(nTime%1000)/10);
        return OutTime;

    }


}


