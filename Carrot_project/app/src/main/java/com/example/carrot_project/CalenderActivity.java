package com.example.carrot_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.view.ViewGroup;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Locale;

public class CalenderActivity extends AppCompatActivity {

    private TextView tvDate; // 연/월 텍스트뷰
    private CalenderActivity.GridAdapter gridAdapter; // 그리드뷰 어댑터
    private ArrayList<String> dayList; // 일 저장할 리스트
    private GridView gridView;
    private Calendar cal;

    Button leftbtn;
    Button rightbtn;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_calender);

            tvDate = (TextView) findViewById(R.id.tv_date);
            gridView = (GridView) findViewById(R.id.gridview);

            leftbtn = (Button) findViewById(R.id.btn_previous_calendar);
            rightbtn = (Button) findViewById(R.id.btn_next_calendar);

            long now = System.currentTimeMillis();
            final Date date = new Date(now); //오늘 날짜 세팅
            //연,월,일 따로 저장
            final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
            final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
            final SimpleDateFormat curDateFormat = new SimpleDateFormat("dd", Locale.KOREA);

            //현재 날짜 텍스트뷰에
            tvDate.setText(curYearFormat.format(date) + "/" + curMonthFormat.format(date));

            dayList = new ArrayList<String>();

            cal = Calendar.getInstance();

            cal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
            int dayNum = cal.get(Calendar.DAY_OF_WEEK);
            //1일 - 요일 매칭 위해 공백
            for (int i = 1; i < dayNum; i++) {
                dayList.add("");
            }
            setCalendarDate(cal.get(Calendar.MONTH) + 1);

            gridAdapter = new GridAdapter(getApplicationContext(), dayList);
            gridView.setAdapter(gridAdapter);

            leftbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal = Calendar.getInstance();
                    tvDate.setText(curYearFormat.format(date) + "/" + curMonthFormat.format(date));
                    cal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1 - 1, 1);
                    int dayNum = cal.get(Calendar.DAY_OF_WEEK);

                    dayList = new ArrayList<String>();//refresh
                    //1일 - 요일 매칭 시키기 위해 공백 add
                    for (int i = 1; i < dayNum; i++) {
                        dayList.add("");
                    }
                    setCalendarDate(cal.get(Calendar.MONTH) + 1);
                    gridAdapter.notifyDataSetChanged();
                }
            });

            rightbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal = Calendar.getInstance();
                    tvDate.setText(curYearFormat.format(date) + "/" + curMonthFormat.format(date));
                    cal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1 + 1, 1);
                    int dayNum = cal.get(Calendar.DAY_OF_WEEK);

                    dayList = new ArrayList<String>();//refresh
                    //1일 - 요일 매칭 시키기 위해 공백 add
                    for (int i = 1; i < dayNum; i++) {
                        dayList.add("");
                    }
                    setCalendarDate(cal.get(Calendar.MONTH) + 1);
                    gridAdapter.notifyDataSetChanged();
                }
            });
        }

        //해당 월에 표시할 일 수 구하는 함수
        private void setCalendarDate(int month) {
            cal.set(Calendar.MONTH, month - 1);

            for (int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                dayList.add("" + (i + 1));
            }
        }

        //그리드뷰 어댑터
        private class GridAdapter extends BaseAdapter {
            private final List<String> list;
            private final LayoutInflater inflater;

            public GridAdapter(Context context, List<String> list) {
                this.list = list;
                this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public String getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;

                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item_calender_gridview, parent, false);
                    holder = new ViewHolder();

                    holder.tvItemGridView = (TextView) convertView.findViewById(R.id.tv_item_gridview);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.tvItemGridView.setText("" + getItem(position));

                //해당 날짜 텍스트 컬러, 배경 변경
                cal = Calendar.getInstance();

                Integer today = cal.get(Calendar.DAY_OF_MONTH);
                String sToday = String.valueOf(today);
                if (sToday.equals(getItem(position))) {
                    holder.tvItemGridView.setTextColor(getResources().getColor(R.color.black));
                }
                return convertView;
            }
        }

        private class ViewHolder {
            TextView tvItemGridView;
        }
    }
