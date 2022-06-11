package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collection;
import java.util.List;

public class Login extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    TextInputLayout textInputLayoutUsername;
    TextInputLayout textInputLayoutPassword;
    Button buttonLogin;
    SqliteHelper sqliteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
            sqliteHelper = new SqliteHelper(this);
            initCreateAccountTextView();
            initViews();
            List<String> list=sqliteHelper.areYouConnected();
        if(!isEmpty(list)){
            User currentUser = sqliteHelper.Authenticate(new User(null, list.get(0), list.get(1)));
            Toast.makeText(getApplicationContext(),"تم تسجيل الدخول بنجاح!",Toast.LENGTH_LONG).show();

            //User Logged in Successfully Launch You home screen activity
            Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
            intent.putExtra("currentUserNameKey", String.valueOf(currentUser.getId()));
            startActivity(intent);
            finish();


        }
              buttonLogin.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  String Username = editTextUsername.getText().toString().trim();
                  String Password = editTextPassword.getText().toString().trim();
                  if (validate(Password )&& validUsername(Username)) {


                      User currentUser = sqliteHelper.Authenticate(new User(null,  Username, Password));

                      if (currentUser != null) {
                          String user=currentUser.getId();
                          sqliteHelper.isConnected("yes",user,Username,Password);

                          Toast.makeText(getApplicationContext(),"تم تسجيل الدخول بنجاح!",Toast.LENGTH_LONG).show();

                          //User Logged in Successfully Launch You home screen activity
                        Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                        intent.putExtra("currentUserNameKey", String.valueOf(currentUser.getId()));
                        startActivity(intent);
                        finish();
                      } else {

                          Toast.makeText(getApplicationContext(),"فشل في تسجيل الدخول ، يرجى المحاولة مرة أخرى",Toast.LENGTH_LONG).show();

                      }


                  }

              }
          });
        }

        //this method used to set Create account TextView text and click event( maltipal colors
        // for TextView yet not supported in Xml so i have done it programmatically)
        private void initCreateAccountTextView() {
            TextView textViewCreateAccount = (TextView) findViewById(R.id.signUpText);
            textViewCreateAccount.setText(fromHtml("<font color='#FF018786'>ليس لدي حساب بعد </font><font color='#0c0099'>سجل هنا</font>"));
            textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), SignUP.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        //this method is used to connect XML views to its Objects
        private void initViews() {
            editTextUsername = (EditText) findViewById(R.id.username);
            editTextPassword = (EditText) findViewById(R.id.password);
            textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
            textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
            buttonLogin = (Button) findViewById(R.id.buttonLogin);

        }

        //This method is for handling fromHtml method deprecation
        @SuppressWarnings("deprecation")
        public static CharSequence fromHtml(String html) {
            Spanned result;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
            } else {
                result = Html.fromHtml(html);
            }
            return result;
        }

        //This method is used to validate input given by user
        public boolean validate(String Password) {
            boolean valid = false;

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
        public boolean validUsername(String Username){
            boolean valid = false;
            if (Username.isEmpty()) {
                valid = false;
                textInputLayoutUsername.setError("الرجاء إدخال اسم االمستخدم صحيح!");
            } else {
                valid = true;
                textInputLayoutUsername.setError(null);
            }
            return valid;
        }
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }


    }