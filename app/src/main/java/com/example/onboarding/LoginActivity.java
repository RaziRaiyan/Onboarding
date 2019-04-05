package com.example.onboarding;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LOGIN_ACTIVITY";

    private ProgressBar mProgressLogin;
    private EditText mTextEmail;
    private EditText mTextPassword;
    private Button mButtonLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_login);

        mProgressLogin = findViewById(R.id.progressBar_login);
        mTextEmail = findViewById(R.id.et_email);
        mTextPassword = findViewById(R.id.et_password);

        mButtonLogin = findViewById(R.id.btn_login);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        FirebaseApp.initializeApp(getApplicationContext());
        mAuth =  FirebaseAuth.getInstance();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(task.isSuccessful()){
                    String token = task.getResult().getToken();
                    Toast.makeText(LoginActivity.this,"Token = "+token,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this,
                            "Error Message = "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createUser(){

        final String email = mTextEmail.getText().toString().trim();
        final String password = mTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            mTextEmail.setError("Email Required");
            mTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            mTextPassword.setError("Password required");
            mTextPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            mTextPassword.setError("Password must be atleast 6 digits");
            mTextPassword.requestFocus();
            return;
        }

        mProgressLogin.setVisibility(View.VISIBLE);
        if(mAuth!=null){
            Task<AuthResult> taskAuthResult = mAuth.createUserWithEmailAndPassword(email,password);
            taskAuthResult.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        getInsideTheApp();

                    }else {
                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            userLogin(email,password);
                        }else {
                            mProgressLogin.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this,
                                    task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

    }
    private void userLogin(String email,String password){
        Task<AuthResult> taskAuthResult = mAuth.signInWithEmailAndPassword(email,password);
        taskAuthResult.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    getInsideTheApp();
                }else {
                    mProgressLogin.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void getInsideTheApp(){
        mProgressLogin.setVisibility(View.INVISIBLE);
        Intent intentInside = new Intent(this,InsideActivity.class);
        intentInside.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentInside);
    }


}














