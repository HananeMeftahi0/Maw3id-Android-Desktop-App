package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

public class SignUP extends AppCompatActivity {
    //Declaration EditTexts
    EditText editTextUserName;
    EditText editTextEmail;
    EditText editTextPassword;

    //Declaration TextInputLayout
    TextInputLayout textInputLayoutUserName;
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    //Declaration Button
    Button buttonRegister;

    //Declaration SqliteHelper
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        sqliteHelper = new SqliteHelper(this);
        initTextViewLogin();
        initViews();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() && validUsername() && validEmail()) {
                    String UserName = editTextUserName.getText().toString().trim();
                    String Password = editTextPassword.getText().toString().trim();

                    //Check in the database is there any user associated with  this email
                    if (!sqliteHelper.isUsernameExists(UserName)) {

                        //Email does not exist now add new user to database
                        sqliteHelper.addUser(new User(null, UserName, Password),"no");
                        Toast.makeText(getApplicationContext(),"تم إنشاء المستخدم بنجاح! الرجاء تسجيل الدخول" ,Toast.LENGTH_LONG).show();
                    }else {

                        //Email exists with email input provided so show error user already exist
                        Toast.makeText(getApplicationContext(),"المستخدم موجود بالفعل بنفس اسم االمستخدم" ,Toast.LENGTH_LONG).show();

                    }


                }
            }
        });
    }

    //this method used to set Login TextView click event
    private void initTextViewLogin() {
        TextView textViewLogin = (TextView) findViewById(R.id.loginText);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextUserName = (EditText) findViewById(R.id.username);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutUserName = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
        buttonRegister = (Button) findViewById(R.id.buttonSignUp);

    }

    //This method is used to validate input given by user
    public boolean validUsername(){
        boolean valid = false;

        //Get values from EditText fields
        String UserName = editTextUserName.getText().toString();
        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            textInputLayoutUserName.setError("الرجاء إدخال اسم مستخدم صالح!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                textInputLayoutUserName.setError(null);
            } else {
                valid = false;
                textInputLayoutUserName.setError("اسم المستخدم قصير!");
            }
        }
        return valid;
    }
    public boolean validEmail(){
        String Email = editTextEmail.getText().toString();
        boolean valid = false;

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            textInputLayoutEmail.setError("الرجاء إدخال بريد إلكتروني صحيح!");
        } else {
            valid = true;
            textInputLayoutEmail.setError(null);
        }
        return valid;
    }
    public boolean validate() {
        boolean valid = false;


        String Password = editTextPassword.getText().toString();

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setError("الرجاء إدخال كلمة مرور صالحة!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                textInputLayoutPassword.setError(null);
            } else {
                valid = false;
                textInputLayoutPassword.setError("كلمة المرور قصيرة!");
            }
        }


        return valid;
    }
}