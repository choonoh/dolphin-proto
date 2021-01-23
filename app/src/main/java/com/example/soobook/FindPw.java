package com.example.soobook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FindPw extends AppCompatActivity{
    private FirebaseAuth firebaseAuth; //계정인증에 필요한 변수
    private EditText targetEmail; //이메일 입력 EditText
    private Button sendButton; //클릭시, 해당 이메일로 패스워드 재설정 페이지링크를 보냄


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);
        sendButton = (Button) findViewById(R.id.send_btn);
        sendButton.setOnClickListener(v -> {
            Email();
        });

    }

    public void Email(){
        firebaseAuth = FirebaseAuth.getInstance();
        targetEmail = (EditText) findViewById(R.id.et_email);
        firebaseAuth.sendPasswordResetEmail(targetEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() { //해당 이메일 주소로 비밀번호 재설정
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(FindPw.this,"이멜로 비번 재설정 링크를 보내씀미다",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FindPw.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }




}