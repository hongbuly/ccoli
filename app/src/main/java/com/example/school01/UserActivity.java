package com.example.school01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TextView id = findViewById(R.id.id);
        TextView name = findViewById(R.id.name);
        TextView number = findViewById(R.id.number);
        TextView address = findViewById(R.id.address);

        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        FirebaseUser user = auth.getCurrentUser();
                        String email = user.getEmail();
                        for (DataSnapshot item : snapshot.getChildren()) {
                            UserModel.UserDataModel userData = item.getValue(UserModel.UserDataModel.class);
                            if (userData == null)
                                break;
                            if (email.equals(userData.email)){
                                id.setText(userData.email);
                                name.setText(userData.name);
                                number.setText(userData.number);
                                address.setText(userData.address);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}