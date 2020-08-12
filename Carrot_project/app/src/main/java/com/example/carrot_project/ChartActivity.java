package com.example.carrot_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class ChartActivity extends AppCompatActivity {

    private BarChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY,sample_s,sample_m;
    String mStudyTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent chartIntent = getIntent();
        BarChart chart = findViewById(R.id.Barchart);
        TextView sample_s = findViewById(R.id.sample_s);
        TextView sample_m = findViewById(R.id.sample_m);

        mStudyTime = chartIntent.getStringExtra("time");
        Toast.makeText(getApplicationContext(), mStudyTime, Toast.LENGTH_LONG).show(); //시간을 받아오는지 확인



        StringTokenizer tokens = new StringTokenizer(mStudyTime);

        String sMinute = tokens.nextToken(":");
        String sSecond = tokens.nextToken(":");


        int mMinute = Integer.valueOf(sMinute);
        int mSecond = Integer.valueOf(sSecond);


        sample_m.setText(mMinute+"분");
        sample_s.setText(mSecond+"초");




        /*차트 버전 낮춘게 잘 작동하는지 확인을 위해 데려온 친구
            ToDo--> 그래프 x축 y축 커스텀, 시간을 날짜별로 어캐 DB에 저장할건지..
            시발 왜이래
   */
        ArrayList NoOfEmp = new ArrayList();
        NoOfEmp.add(new BarEntry(mMinute, 0));
        NoOfEmp.add(new BarEntry(mMinute+10, 1));
        NoOfEmp.add(new BarEntry(mMinute +20, 2));
        NoOfEmp.add(new BarEntry(mMinute + 25, 3));
        ArrayList day = new ArrayList();
        day.add("8/6"); day.add("8/7");
        day.add("8/8"); day.add("8/9");
        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
        chart.animateY(5000); BarData data = new BarData(day, bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);





    }


}

