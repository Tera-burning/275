package com.example.carrot_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Select_group_Activity extends AppCompatActivity {
    private static final String TAG = "Select_group_Activity";

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private TextView textviewDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group_);

        textViewUserEmail = (TextView)findViewById(R.id.textviewUserEmail);
        buttonLogout = (Button)findViewById(R.id.buttonLogout);
        textviewDelete = (TextView)findViewById(R.id.textviewDelete);

        firebaseAuth = FirebaseAuth.getInstance();
        final Intent intent = new Intent(this, LoginActivity.class);
        final Intent intent2 = new Intent(this, SignupActivity.class);
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(intent);
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail.setText(user.getEmail());

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(intent);
            }
        });

        textviewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Select_group_Activity.this);
                alert_confirm.setMessage("정말 계정을 삭제하겠습니까?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Select_group_Activity.this, "계정이 삭제되었습니다.", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(intent2);
                            }
                        });
                    }
                });
                alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Select_group_Activity.this, "취소", Toast.LENGTH_LONG).show();
                    }
                });
                alert_confirm.show();
            }
        });
        Button button1 = (Button)findViewById(R.id.btn_addgroup);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
                startActivity(intent);
            }
        });
    }
}
