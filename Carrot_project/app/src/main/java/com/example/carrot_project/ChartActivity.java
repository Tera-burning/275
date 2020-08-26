package com.example.carrot_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


public class ChartActivity extends AppCompatActivity {

    private BarChart chart;
    String mStudyTime;
    String userID;
    String mDay;
    int DataCount = 0;
    ArrayList day = new ArrayList();
    ArrayList time = new ArrayList();
    BarDataSet bardataset;
    BarData data;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        Intent chartIntent = getIntent();
        chart = findViewById(R.id.Barchart);


        mStudyTime = chartIntent.getStringExtra("time");
        userID = chartIntent.getStringExtra("ID");
        mDay = chartIntent.getStringExtra("day");
        Toast.makeText(getApplicationContext(), "측정 시간: " + mStudyTime, Toast.LENGTH_LONG).show(); //시간을 받아오는지 확인





        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("study")
                .document(userID)
                .collection("studytime")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (DataCount < 6){

                                    day.add(document.getId());
                                    Map<String, Object> temp = null;
                                    temp = document.getData();
                                    String temp1 = null;
                                    temp1 = (String) temp.get("studytime");
                                    int temp2 = Integer.valueOf(temp1);
                                    time.add(new BarEntry(temp2, DataCount));
                                    DataCount++;
                                    bardataset = new BarDataSet(time, " ");
                                    bardataset.setColor(Color.WHITE);

                                    data = new BarData(day, bardataset);
                                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                                    bardataset.setValueTextColor(Color.WHITE);
                                    chart.setDescription("6일까지의 공부량");
                                    chart.setDescriptionColor(Color.WHITE);
                                    YAxis y = chart.getAxisLeft();
                                    y.setTextColor(Color.WHITE);
                                    XAxis x = chart.getXAxis();
                                    x.setTextColor(Color.WHITE);
                                    YAxis Ry = chart.getAxisRight();
                                    Ry.setDrawLabels(false);
                                    Ry.setDrawAxisLine(false);
                                    Ry.setDrawGridLines(false);
                                    chart.setData(data);
                                    chart.animateY(7000);
                                 }
                            }
                        } else {
                            Log.d("Error ", "오류가 나버려써 ", task.getException());
                        }
                    }
                });



    }




}