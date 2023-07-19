package com.example.realtimedatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_activity extends AppCompatActivity {
    EditText editphn,editpass;
    Button btn;
    TextView textView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://realtimedataase-ed1f1-default-rtdb.firebaseio.com/").child("User");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editphn = findViewById(R.id.editphone);
        editpass = findViewById(R.id.editpassword);
        textView = findViewById(R.id.textclick);
        btn = findViewById(R.id.btnlogin);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ragistar_activity.class);
                startActivity(intent);
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = editphn.getText().toString();
                String pass = editpass.getText().toString();
                if (phone.isEmpty() || pass.isEmpty()){
                    Toast.makeText(Login_activity.this, "Enter Mobile Number and password", Toast.LENGTH_SHORT).show();
                }
               else {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(phone)){
                                String getpass = snapshot.child(phone).child("passedit").getValue(String.class);
                                if (getpass.equals(pass)){
                                    Toast.makeText(Login_activity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                     intent.putExtra("phone",phone);
                                     startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(Login_activity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}