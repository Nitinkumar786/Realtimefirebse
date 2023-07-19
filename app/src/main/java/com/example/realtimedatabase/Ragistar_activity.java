package com.example.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.realtimedatabase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ragistar_activity extends AppCompatActivity {
    EditText editname, phone,editemail,editpassword;
    Button btnSingup;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://realtimedataase-ed1f1-default-rtdb.firebaseio.com/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragister);
        editname = findViewById(R.id.addname);
        editemail = findViewById(R.id.addemail);
        editpassword = findViewById(R.id.addpass);
        btnSingup = findViewById(R.id.btnsingup);
        phone  = findViewById(R.id.phone);

        btnSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editname.getText().toString();
                String num = phone.getText().toString();
                String emailtext = editemail.getText().toString();
                String passedit = editpassword.getText().toString();

                if (name.isEmpty() || emailtext.isEmpty() || passedit.isEmpty()){
                    Toast.makeText(Ragistar_activity.this, "Enter the Empty Box", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if ( snapshot.hasChild(num)){
                               Toast.makeText(Ragistar_activity.this, "Already Registered .", Toast.LENGTH_SHORT).show();
                           }
                           else {
                               databaseReference.child("User").child(num).child("name").setValue(name);
                               databaseReference.child("User").child(num).child("emailtext").setValue(emailtext);
                               databaseReference.child("User").child(num).child("passedit").setValue(passedit);

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