package com.example.achint.ecommerce.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.achint.ecommerce.Controller.MainController;
import com.example.achint.ecommerce.FormValidation.Validation;
import com.example.achint.ecommerce.Interface.UsersInterface;
import com.example.achint.ecommerce.Model.Users;
import com.example.achint.ecommerce.R;
import com.example.achint.ecommerce.Sessions.AlertDialogManager;
import com.example.achint.ecommerce.Sessions.SessionManagement;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    UsersInterface usersInterface;
    SessionManagement session;
    AlertDialogManager alert = new AlertDialogManager();
    EditText etFirstName;
    EditText etLastName;
    EditText etAddress;
    EditText etContact;
    EditText etEmail;
    EditText etPassword;
    Button signup;
    EditText etConfirmPassword;

    private void addUser(Users users){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        Call<Users> call = usersInterface.createUser(users);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.code() == 200) {
                    Users userInResponse = response.body();

                    session = new SessionManagement(getApplicationContext());
                    session.createLoginSession(userInResponse.getFirstname(), userInResponse.getEmail(), userInResponse.getId());
                    Toast.makeText(SignupActivity.this,"registered successfully:)",Toast.LENGTH_LONG).show();

                    alert.showAlertDialog(SignupActivity.this, "Successful SignUp, verify your email for login.", "Welcome to Easy Buy", true);
                    progressDialog.dismiss();

                    Intent i = new Intent(SignupActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    try {
                        alert.showAlertDialog(SignupActivity.this, "Signup Failed..", response.errorBody().string(), false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(SignupActivity.this,"registration failed :(",Toast.LENGTH_LONG).show();
                alert.showAlertDialog(SignupActivity.this, "SignUp Failed", "Please try again..", false);
                progressDialog.dismiss();
            }
        });
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.isPassword(etPassword, true))
            ret = false;
        if (!Validation.isConfirmPassword(etConfirmPassword,etPassword, true))
            ret = false;
        if (!Validation.isEmailAddress(etEmail, true))
            ret = false;
        if(!etContact.getText().toString().isEmpty()) {
            if (!Validation.isPhoneNumber(etContact, false)) ret = false;
        }

        return ret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etFirstName = findViewById(R.id.et_firstname);
        etLastName = findViewById(R.id.et_lastname);
        etAddress = findViewById(R.id.et_address);
        etContact = findViewById(R.id.et_contact);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        signup = findViewById(R.id.bt_signup);
        etConfirmPassword = findViewById(R.id.et_confirm_password);

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Validation.isEmailAddress(etEmail, true);
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Validation.isPassword(etPassword, true);
            }
        });

        etConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Validation.isConfirmPassword(etConfirmPassword, etPassword, true);
            }
        });

        etContact.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!etContact.getText().toString().isEmpty()) {
                    Validation.isPhoneNumber(etContact, false);
                }
            }
        });


        usersInterface = MainController.getInstance().getClientForLogin().create(UsersInterface.class);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation ()){
                    Users users = new Users();
                    users.setFirstname(etFirstName.getText().toString());
                    users.setLastname(etLastName.getText().toString());
                    users.setAddress(etAddress.getText().toString());
                    users.setContact(etContact.getText().toString());
                    users.setEmail(etEmail.getText().toString());
                    users.setPassword(etPassword.getText().toString());
                    etFirstName.setText("");
                    etLastName.setText("");
                    etContact.setText("");
                    etAddress.setText("");
                    etEmail.setText("");
                    etPassword.setText("");
                    etConfirmPassword.setText("");
                    addUser(users);
                }

                else{
                    Toast.makeText(SignupActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}