package com.example.achint.ecommerce.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.achint.ecommerce.Controller.MainController;
import com.example.achint.ecommerce.Interface.UsersInterface;
import com.example.achint.ecommerce.Model.Users;
import com.example.achint.ecommerce.R;
import com.example.achint.ecommerce.Sessions.AlertDialogManager;
import com.example.achint.ecommerce.Sessions.SessionManagement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button login;
    Button register;
    EditText etEmail;
    EditText etPassword;
    UsersInterface usersInterface;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManagement(getApplicationContext());

        etEmail = findViewById(R.id.et_lemail);
        etPassword = findViewById(R.id.et_lpassword);
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        login = findViewById(R.id.bt_login);
        register = findViewById(R.id.bt_register);

        usersInterface = MainController.getInstance().getClientForLogin().create(UsersInterface.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etEmail.getText().toString().trim().length() > 0 && etPassword.getText().toString().trim().length() > 0) {

                    Users users = new Users();
                    users.setEmail(etEmail.getText().toString());
                    users.setPassword(etPassword.getText().toString());
                    etEmail.setText("");
                    etPassword.setText("");
                    loginUser(users);
                }
                else{
                    alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please Enter Email and Password", false);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);

            }

        });
    }

    private void loginUser(Users users){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        Call<Users> call = usersInterface.validateUser(users);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (200 == response.code()) {
                    Users userInResponse = response.body();
                    session.createLoginSession(userInResponse.getFirstname(), userInResponse.getEmail(), userInResponse.getId());

                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                    Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(i);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                progressDialog.dismiss();
                alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username/Password is incorrect", false);
                Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
            }
        });
    }


}
