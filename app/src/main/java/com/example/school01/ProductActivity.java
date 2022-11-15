package com.example.school01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ProductActivity extends AppCompatActivity {
    FirebaseAuth auth;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.reView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter();
        recyclerView.setAdapter(productAdapter);

        ImageView cartView = findViewById(R.id.cartView);
        cartView.setOnClickListener(v -> {
            Toast.makeText(ProductActivity.this, "구현되지 않은 기능입니다!", Toast.LENGTH_SHORT).show();
        });

        ImageView userView = findViewById(R.id.userView);
        userView.setOnClickListener(v -> {
            auth = FirebaseAuth.getInstance();
            if(auth.getCurrentUser() != null){
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
            } else {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ProductActivity.this);
                dlg.setTitle("회원가입 하셨나요?");
                dlg.setMessage("회원가입을 원하시면 '예'를 눌러주세요.");
                dlg.setPositiveButton("예", (dialog, which) -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
                dlg.setNegativeButton("아니오", (dialog, which) -> {
                    Toast.makeText(ProductActivity.this, "로그인을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}