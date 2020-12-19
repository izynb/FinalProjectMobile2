package com.example.finalprojectmobile2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;

public class register extends AppCompatActivity {

    EditText email;
    EditText name;
    EditText pass;
    TextView login;
    Button btn;
    DatabaseReference myDb;
    FirebaseAuth auth;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email=findViewById(R.id.etEmail);
        name=findViewById(R.id.etName);
        pass=findViewById(R.id.etPass);
        btn=findViewById(R.id.subBtn);
        login=findViewById(R.id.tvLogin);

        myDb= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        pDialog=new ProgressDialog(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, com.example.finalprojectmobile2.login.class);
                startActivity(intent);
            }
                });

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                String txtName = name.getText().toString();
                String txtEmail = email.getText().toString();
                String txtPass = pass.getText().toString();

                if (TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPass)) {

                    Toast.makeText(register.this, "pleas fill all field!", Toast.LENGTH_SHORT).show();
                } else if (txtPass.length() < 6) {

                    Toast.makeText(register.this, "password length is short", Toast.LENGTH_SHORT).show();
                } else register(txtEmail, txtPass, txtName);
            }
        });

    }

    public void register(final String email, final String name, final String pass)
    {
        auth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {


                HashMap<String, Object> data = new HashMap<>();
                data.put("name", name);
                data.put("email", email);
                data.put("password", pass);

                myDb.child("user").child(auth.getCurrentUser().getUid()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            pDialog.dismiss();
                            Toast.makeText(register.this, "Registre is successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(register.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }

}