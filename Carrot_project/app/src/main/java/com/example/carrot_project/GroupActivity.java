package com.example.carrot_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class GroupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button addIDbtn = findViewById(R.id.addIDbutton);
    EditText memberID;
    EditText memberID2;
    EditText memberID3;
    EditText groupName;
    private FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        memberID = (EditText)findViewById(R.id.memberID);
        memberID2 = (EditText)findViewById(R.id.memberID2);
        memberID3 = (EditText)findViewById(R.id.memberID3);
        groupName = (EditText)findViewById(R.id.groupname);

        addIDbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = memberID.getText().toString().trim();
                String email2 = memberID2.getText().toString().trim();
                String email3 = memberID3.getText().toString().trim(); // xml에서 입력한 멤버 email들

                db.collection("User").whereEqualTo("Username", email1).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                //email1과 일치하는 유저 email이 존재하면
                                if (task.isSuccessful()) {
                                    String email11 = memberID.getText().toString().trim();
                                    DocumentReference Groupinfo = db.collection("Group").document("Groupinfo");
                                    Groupinfo.update("Groupmem", FieldValue.arrayUnion(email11));
                                    if(count == 1)
                                        count = 0;
                                }
                                else{
                                    Toast.makeText(getApplication(), "1번 이메일 오류 !", Toast.LENGTH_LONG).show();
                                    count = 1;
                                }
                            }
                        });

                db.collection("User").whereEqualTo("Username", email2).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                //email1과 일치하는 유저 emai2가 존재하면
                                if (task.isSuccessful()) {
                                    String email22 = memberID.getText().toString().trim();
                                    DocumentReference Groupinfo = db.collection("Group").document("Groupinfo");
                                    Groupinfo.update("Groupmem", FieldValue.arrayUnion(email22));
                                    if(count == 2)
                                        count = 0;
                                }
                                else{
                                    Toast.makeText(getApplication(), "2번 이메일 오류 !.", Toast.LENGTH_LONG).show();
                                    count = 2;
                                }
                            }
                        });

                db.collection("User").whereEqualTo("Username", email3).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                //email1과 일치하는 유저 emai3이 존재하면
                                if (task.isSuccessful()) {
                                    String email33 = memberID.getText().toString().trim();
                                    DocumentReference Groupinfo = db.collection("Group").document("Groupinfo");
                                    Groupinfo.update("Groupmem", FieldValue.arrayUnion(email33));
                                    if(count == 3)
                                        count = 0;
                                }
                                else{
                                    Toast.makeText(getApplication(), "3번 이메일 오류 !", Toast.LENGTH_LONG).show();
                                    count = 3;
                                }
                            }
                        });

                if(count == 0){
                    //그룹 이름 파베에 넣고 현재 사용자 이메일도 파베에 넣고 select group activity로 넘어가기
//                    Intent intent = new Intent(getBaseContext(), Select_group_Activity.class);
//                    startActivity(intent);
                }
                else{
                    // 해당 액티비티 종료하지 말기
                }
            }
        });

    }
}
