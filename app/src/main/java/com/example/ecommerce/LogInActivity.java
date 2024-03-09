package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {
    SharedPreferences shareref;
    Button btn_signup;
    TextView forgetpasswordtv;
    EditText ed_username;
    EditText ed_password;
    Button btn_login;
    EcommerceDataBase dbobj;
    TextView tv_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        shareref = getSharedPreferences("Remember Me" , Context.MODE_PRIVATE);

        btn_signup = (Button)(findViewById(R.id.btn_signup_in_loginactivity));
        forgetpasswordtv = (TextView)(findViewById(R.id.login_forgetpassword_textview));


        ed_username = findViewById(R.id.login_username);
        ed_password = findViewById(R.id.login_password);
        btn_login = (Button)(findViewById(R.id.btn_login));
        dbobj = new EcommerceDataBase(getApplicationContext());


        tv_error = (TextView)(findViewById(R.id.incorrect_username_or_password));

        //SharedPreference Code
        ed_username.setText(shareref.getString("Username",""));
        ed_password.setText(shareref.getString("Password",""));


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean Exist_Customer_or_Not = dbobj.Login(ed_username.getText().toString() , ed_password.getText().toString());
                if(Exist_Customer_or_Not == false){
                    new CountDownTimer(2000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            tv_error.setText(R.string.error_in_sign_in);
                            tv_error.setTextColor(getResources().getColor(R.color.black));
                            tv_error.setVisibility(View.VISIBLE);
                        }

                        public void onFinish() {
                            tv_error.setVisibility(View.INVISIBLE);
                        }
                    }.start();

                }
                else {
                    new CountDownTimer(2000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            tv_error.setText("Successfully Logged In");
                            tv_error.setTextColor(getResources().getColor(R.color.black));
                            tv_error.setVisibility(View.VISIBLE);
                        }

                        public void onFinish() {
                            tv_error.setVisibility(View.INVISIBLE);
                            SharedPreferences.Editor myedit = shareref.edit();
                            myedit.putString("Username" , ed_username.getText().toString());
                            myedit.putString("Password" , ed_password.getText().toString());
                            myedit.commit();
                            Intent i = new Intent(getApplicationContext() , CategoriesActivity.class);
                            int custid = dbobj.GetCustomerrId(ed_username.getText().toString());
                            Cart.CustID = custid;
                            i.putExtra("custid" , custid);
                            startActivity(i);
                            // then go to the e-commerce categories activity
                        }
                    }.start();


                }
            }
        });

        forgetpasswordtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ForgetPasswordActivity.class);
                Bundle b = new Bundle();
                b.putString("username" , ed_username.getText().toString());
                i.putExtras(b);
                startActivity(i);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });
    }
}