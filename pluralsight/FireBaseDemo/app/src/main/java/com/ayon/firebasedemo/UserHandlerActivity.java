package com.ayon.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserHandlerActivity extends AppCompatActivity {

    // Widget references
    private EditText signupEmail;

    private EditText signupPassword;
    private EditText signupPasswordAgain;

    private Button signupButton;
    private Button signinButtonStart;

    private String email;
    private String password;
    private String passwordAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up all the references to widgets
        initializeWidgets();

        // Handle Sign Up
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getEmailAndPassword();

                if( checkForEmptyInput() ) {
                    Toast.makeText(UserHandlerActivity.this, "Email or passwords cannot be empty",Toast.LENGTH_SHORT).show();
                } else {
                    if ( checkForInvalidPassword() ) {
                        Toast.makeText(UserHandlerActivity.this, "Password must be at least 6 characters long and equal",Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle sign up
                        registerNewUser();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            }
        });

    }

    private void registerNewUser() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() ) {
                    Toast.makeText(UserHandlerActivity.this, "User created!",Toast.LENGTH_SHORT).show();
                }   else {
                    Toast.makeText(UserHandlerActivity.this, "Error Creating User!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkForInvalidPassword() {
        return password.length() <= 6 || !(password.equals(passwordAgain));
    }

    private boolean checkForEmptyInput() {
        return isEmpty(email) || isEmpty(password) || isEmpty(passwordAgain);
    }

    private boolean isEmpty(String text) {
        return text.equals("");
    }

    private void getEmailAndPassword() {
        email = signupEmail.getText().toString();
        password = signupPassword.getText().toString();
        passwordAgain = signupPasswordAgain.getText().toString();
    }

    private void initializeWidgets() {
        signupEmail = (EditText) findViewById(R.id.signup_email);
        signupPassword = (EditText) findViewById(R.id.signup_password);
        signupPasswordAgain = (EditText) findViewById(R.id.signup_password_again);

        signupButton = (Button) findViewById(R.id.signup_btn);
        signinButtonStart = (Button) findViewById(R.id.signin_btn_start);
    }

}
