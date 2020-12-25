package com.example.finalprojectmobile2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class note_add extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    EditText edtext;
    EditText edsubject;
    ImageView imgsave;
    TextView txtdate;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FloatingActionButton btnAdd;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String keyId;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        edsubject=findViewById(R.id.subject);
        edtext=findViewById(R.id.Text);
        imgsave=findViewById(R.id.imgsave);
        txtdate=findViewById(R.id.date);

        user = FirebaseAuth.getInstance().getCurrentUser();
        fStore = FirebaseFirestore.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd    HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        txtdate.setText(currentDateandTime);


        progressDialog = new ProgressDialog(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.all){
                    Intent intent=new Intent(note_add.this,note.class);
                    startActivity(intent);
                }
                else if (item.getItemId()==R.id.add){
                    Intent intent=new Intent(note_add.this,note_add.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        imgsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txsubject=edsubject.getText().toString();
                String txmaintext=edtext.getText().toString();
                String txdate=txtdate.getText().toString();

                progressDialog.setMessage("Saving...");


                if (TextUtils.isEmpty(txsubject) || TextUtils.isEmpty(txmaintext)){
                    Toast.makeText(note_add.this,"please fill subject & text",Toast.LENGTH_LONG).show();
                }
                else
                {
                    progressDialog.show();
                    modelnote note = new modelnote();
                    note.setId(keyId);
                    note.setTitle(edsubject.getText().toString().trim());
                    note.setDesc(edtext.getText().toString().trim());
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss", Locale.getDefault());
                    String formattedDate = df.format(c);

                    note.setDate(formattedDate);
                    save(note);
                }
            }
        });
    }
    private void save(modelnote note) {
        DocumentReference docref = fStore.collection("Notes").document(user.getUid()).collection("myNotes").document();
        docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(note_add.this, "Note Added.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(note_add.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}