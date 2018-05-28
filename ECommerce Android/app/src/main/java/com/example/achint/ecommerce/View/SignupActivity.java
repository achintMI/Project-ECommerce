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

public class SignupActivity extends AppCompatActivity {
    UsersInterface usersInterface;
    SessionManagement session;
    AlertDialogManager alert = new AlertDialogManager();

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
                    Intent i = new Intent(SignupActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText etFirstName = findViewById(R.id.et_firstname);
        final EditText etLastName = findViewById(R.id.et_lastname);
        final EditText etAddress = findViewById(R.id.et_address);
        final EditText etContact = findViewById(R.id.et_contact);
        final EditText etEmail = findViewById(R.id.et_email);
        final EditText etPassword = findViewById(R.id.et_password);
        final Button signup = findViewById(R.id.bt_signup);

        usersInterface = MainController.getInstance().getClientForLogin().create(UsersInterface.class);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                addUser(users);
            }
        });
    }

}
