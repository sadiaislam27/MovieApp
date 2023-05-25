package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    TextView createnewAccount;
    EditText inputEmail,inputPassword1;
    Button btnLogin;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword1=findViewById(R.id.inputPassword1);
        btnLogin=findViewById(R.id.btnLogin);
        progressDialog=new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        createnewAccount=findViewById(R.id.createnewAccount);
        createnewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }

        });

    }
    private void performLogin() {
        String email = inputEmail.getText().toString();
        String password = inputPassword1.getText().toString();

        if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter correct email");
        } else if (password.isEmpty() || password.length() < 6) {
            inputPassword1.setError("Enter correct password");
        } else {
            progressDialog.setMessage("Please wait while Login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(HomeActivity.this, "Login is successful", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(HomeActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
    private void sendUserToNextActivity() {
        Intent intent=new Intent(HomeActivity.this,HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}