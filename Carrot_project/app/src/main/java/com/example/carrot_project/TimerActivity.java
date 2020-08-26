package com.example.carrot_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

public class TimerActivity extends AppCompatActivity {

    TextView mTimer_Output;
    Button mStart_btn;
    Button mStop_btn;
    private FirebaseAuth firebaseAuth;

    final static int INIT = 0;
    final static int RUN = 1;
    final static int PAUSE = 2;
    long now = System.currentTimeMillis();
    int mStatus = INIT;
    long mBaseTime;
    long mPauseTime;
    String sId;
    int mStudyTime = 0;
    int dataCount = 0;

    final static int NONE = 0;
    final static int SEARCH = 1;
    int dbExist = SEARCH;



    String mDay;

    String userID;
    StudytimeData mtimedata = null;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTimer_Output = (TextView) findViewById(R.id.timer_output);
        mStart_btn = (Button) findViewById(R.id.start_btn);
        mStop_btn = (Button) findViewById(R.id.stop_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        userID = user.getEmail();

        Date date = new Date(now);
        SimpleDateFormat mFormat = new SimpleDateFormat("MMdd");
        mDay = mFormat.format(date);





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
                sId = wordId.getText().toString();
                Intent intent =  new Intent(getApplicationContext(), ChartActivity.class);
                StringTokenizer tokens =  new StringTokenizer(sId);
                String mMinute = tokens.nextToken(":");
                mStudyTime = Integer.valueOf(mMinute);


                intent.putExtra("time",sId);
                intent.putExtra("ID",userID);
                intent.putExtra("day",mStudyTime);
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("study")
                        .document(userID)
                        .collection("studytime")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for(QueryDocumentSnapshot count : task.getResult())
                                    {
                                        dataCount++;
                                    }
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String dbDay = document.getId();
                                        if(dataCount == 1) {
                                            dbExist = NONE;
                                        }
                                        dataCount--;
                                        if(dbDay.equals(mDay))
                                        {
                                            Log.d("data 존재 " + document.getId(), "더해서 덮어씁니당");
                                            Map<String, Object> temp = null;
                                            temp = document.getData();
                                            String temp1;
                                            temp1 = (String) temp.get("studytime");
                                            int temp2 = Integer.valueOf(temp1);

                                            int mSum = temp2 + mStudyTime;



                                            mtimedata = new StudytimeData(mDay,Integer.toString(mSum));
                                            FirebaseFirestore db2 = FirebaseFirestore.getInstance();
                                            DocumentReference sample = db2
                                                    .collection("study").document(userID)
                                                    .collection("studytime").document(mDay);
                                            sample.set(mtimedata);
                                            dbExist = SEARCH;
                                        }
                                        else {
                                            if(dbExist == NONE) {

                                                Log.d("none data" + dataCount, "데이터가 없어용");

                                                mtimedata = new StudytimeData(mDay, Integer.toString(mStudyTime));
                                                FirebaseFirestore db2 = FirebaseFirestore.getInstance();
                                                DocumentReference sample = db2
                                                        .collection("study").document(userID)
                                                        .collection("studytime").document(mDay);
                                                sample.set(mtimedata);
                                                dbExist = SEARCH;
                                                dataCount = 0;
                                            }
                                        }


                                    }

                                } else {
                                    Log.d("Error ", "오류가 나써요", task.getException());
                                }
                            }
                        });



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


