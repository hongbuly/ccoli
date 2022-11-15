package com.example.school01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

        if(auth.getCurrentUser() != null){
            Toast.makeText(this, "이미 로그인되어있습니다.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), ProductActivity.class));
        }

        EditText edit_id = findViewById(R.id.edit_id);
        EditText edit_password = findViewById(R.id.edit_password);
        Button login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(v -> {
            String id = edit_id.getText().toString().trim();
            String pw = edit_password.getText().toString().trim();

            userLogin(id, pw);
        });

        Button main_btn = findViewById(R.id.main_btn);
        main_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            startActivity(intent);
        });

        Button register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void userLogin(String id, String pw) {
        if(TextUtils.isEmpty(id) || TextUtils.isEmpty(pw)) {
            Toast.makeText(this, "아이디나 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.setMessage("로그인 중...");
        dialog.show();

        auth.signInWithEmailAndPassword(id, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}