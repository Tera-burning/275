package com.example.carrot_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonSignin;
    TextView textviewSignin;
    TextView textviewMessage;
    TextView textviewFindPassword;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();

            startActivity(new Intent(getApplicationContext(), Select_group_Activity.class));
        }

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        textviewSignin = (TextView)findViewById(R.id.textViewSignin);
        textviewMessage = (TextView)findViewById(R.id.textviewMessage);
        textviewFindPassword = (TextView)findViewById(R.id.textViewFindpassword);
        buttonSignin = (Button)findViewById(R.id.buttonSignup);
        progressDialog = new ProgressDialog(this);

        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        final Intent intent = new Intent(this, SignupActivity.class);

        textviewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(intent);
            }
        });

        final Intent intent2 = new Intent(this, FindActivity.class);
                textviewFindPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        startActivity(intent2);
                    }
                });
    }

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "email을 입력해 주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("로그인 중입니다. 기다려...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), Select_group_Activity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "로그인 실패~ 79줄", Toast.LENGTH_LONG).show();
                    textviewMessage.setText("로그인 실패 유현\n -password 틀림");
                }
            }
        });
    }
}