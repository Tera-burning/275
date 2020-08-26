package com.example.carrot_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

import checklist.ChecklistAdapter;
import checklist.ListViewItem;

public class PerchecklistActivity extends AppCompatActivity {

    FirebaseFirestore db;
    private DocumentReference docRef;
    private static final String TAG = "DocSnippets";

    Button Add;
    EditText todo;
    Calendar cal;
    String y, m, d;

    String ido;

    ListView listview;
    ChecklistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perchecklist);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        db = FirebaseFirestore.getInstance();
        //날짜에 맞는 체크리스트 존재시 출력 추가하기
        adapter = new ChecklistAdapter();

        listview = (ListView) findViewById(R.id.perlistview);
        listview.setAdapter(adapter);

        TextView tv = findViewById(R.id.date);
        cal = Calendar.getInstance();
        tv.setText(cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH) + 1) + "월 " + cal.get(Calendar.DATE) + "일");
        y = String.valueOf(cal.get(Calendar.YEAR));
        m = String.valueOf(cal.get(Calendar.MONTH) + 1);
        d = String.valueOf(cal.get(Calendar.DATE));

        db.collection("schedule")//나중에 id도 체크
                .whereEqualTo("date", y + m + d)//오늘 날짜 체크리스트 기본으로 보여주기
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ido = document.getString("text");
                                adapter.addItem(ido);
                                adapter.notifyDataSetChanged();
                                listview.invalidateViews();

                                Log.d(TAG, document.getId() + "=>" + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        todo = (EditText) findViewById(R.id.editdo);
        todo.setTextColor(Color.WHITE);

        Add = (Button) findViewById(R.id.add);
        Add.setOnClickListener(new View.OnClickListener() {//추가하기 버튼 클릭하면
            @Override
            public void onClick(View view) {
                if (todo.getText().toString().length() == 0) {//공백이면
                    Toast.makeText(getApplicationContext(), "할 일을 적어주세요", Toast.LENGTH_SHORT).show();
                } else {//공백아님
                    String str = todo.getText().toString();
                    adapter.addItem(str);
                    adapter.notifyDataSetChanged();//listview 갱신
                    todo.setText("");

                    ListViewItem fbdoc = new ListViewItem(str, y + m + d, "asdf");
                    db.collection("schedule").document().set(fbdoc);//db에 저장
                }

            }
        });
    }
    //날짜선택하면 달력
    DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    //Date Picker에서 선택한 날짜를 TextView에 설정
                    TextView tv = findViewById(R.id.date);
                    y = String.valueOf(yy);
                    m = String.valueOf(mm + 1);
                    d = String.valueOf(dd);
                    tv.setText(String.format("%d년 %d월 %d일", yy, mm + 1, dd));
                    Log.d("내가 선택한 날짜는?", y + m + d);

                    adapter.clear();//기존날짜의 리스트는 지우기
                    adapter.notifyDataSetChanged();//갱신

                    db.collection("schedule")//나중에 id도 체크
                            .whereEqualTo("date", y + m + d)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {//다른 날짜의 아이템은 지워주기
                                            ido = document.getString("text");
                                            adapter.addItem(ido);
                                            adapter.notifyDataSetChanged();

                                            Log.d(TAG, document.getId() + "=>" + document.getData());
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
            };
    //달력 나중에 달력 디자인 바꿈, 오늘 이전의 날짜들은 선택 안되게 하기
    public void mOnClick_DatePick(View view) {
        //캘린더 오늘 날짜 보이도록 설정.
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, mDateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
        Toast.makeText(PerchecklistActivity.this, "날짜클릭", Toast.LENGTH_LONG).show();
    }
}
