package com.example.school01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth auth;
    boolean isAgree = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();

        EditText edit_id = findViewById(R.id.edit_id);
        EditText edit_password = findViewById(R.id.edit_password);
        TextView password_view = findViewById(R.id.password_view);
        edit_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String passwd = password_view.getText().toString();
                if (checkedPassword(passwd)) {
                    password_view.setVisibility(View.VISIBLE);
                } else {
                    password_view.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passwd = password_view.getText().toString();
                if (checkedPassword(passwd)) {
                    password_view.setVisibility(View.VISIBLE);
                } else {
                    password_view.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String passwd = password_view.getText().toString();
                if (checkedPassword(passwd)) {
                    password_view.setVisibility(View.VISIBLE);
                } else {
                    password_view.setVisibility(View.INVISIBLE);
                }
            }
        });

        EditText edit_number = findViewById(R.id.edit_number);
        EditText edit_address = findViewById(R.id.edit_address);
        EditText edit_name = findViewById(R.id.edit_name);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.disagree_btn) {
                isAgree = false;
            } else {
                isAgree = true;
            }
        });
        Button register_btn = findViewById(R.id.register_btn);

        register_btn.setOnClickListener(v -> {
            if(!isAgree) {
                Toast.makeText(RegisterActivity.this, "개인정보 이용 동의 해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                String email = edit_id.getText().toString();
                String passwd = edit_password.getText().toString();
                String name = edit_name.getText().toString();
                String number = edit_number.getText().toString();
                String address = edit_address.getText().toString();

                if (email.equals("")) {
                    Toast.makeText(RegisterActivity.this, "아이디를 적어주세요.", Toast.LENGTH_SHORT).show();
                } else if(passwd.equals("")) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 적어주세요.", Toast.LENGTH_SHORT).show();
                } else if(name.equals("")){
                    Toast.makeText(RegisterActivity.this, "이름을 적어주세요.", Toast.LENGTH_SHORT).show();
                } else if(number.equals("")){
                    Toast.makeText(RegisterActivity.this, "전화번호를 적어주세요.", Toast.LENGTH_SHORT).show();
                } else if(address.equals("")){
                    Toast.makeText(RegisterActivity.this, "주소를 적어주세요.", Toast.LENGTH_SHORT).show();
                } else if (checkedPassword(passwd)) {
                    Toast.makeText(RegisterActivity.this, "비밀번호 체계를 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signOut();
                    auth.createUserWithEmailAndPassword(email, passwd)
                            .addOnCompleteListener(this, task -> {
                                if(task.isSuccessful()) {
                                    try {
                                        UserModel.UserDataModel userModel = new UserModel.UserDataModel();
                                        userModel.email = email;
                                        userModel.address = address;
                                        userModel.name = name;
                                        userModel.number = number;
                                        FirebaseDatabase
                                                .getInstance()
                                                .getReference()
                                                .child("users")
                                                .push()
                                                .setValue(userModel)
                                                .addOnSuccessListener(unused -> {
                                                    Toast.makeText(RegisterActivity.this, "계정이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                                });
                                    } catch(Exception e) {
                                        Log.e("firebase", "database 에러 발생!");
                                    }
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "이미 등록된 계정이거나, 서버 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    boolean checkedPassword(String passwd) {
        boolean passwd_isDigit = false;
        boolean passwd_isSpecial = false;
        for (int i = 0; i < passwd.length(); i++) {
            char[] passwd_ch = passwd.toCharArray();
            if (Character.isDigit(passwd_ch[i])) {
                passwd_isDigit = true;
                break;
            }
        }

        String pattern = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$";
        if(!Pattern.matches(pattern, passwd)) {
            passwd_isSpecial = true;
        }

        return !passwd_isDigit || !passwd_isSpecial || passwd.length() < 8;
    }
}