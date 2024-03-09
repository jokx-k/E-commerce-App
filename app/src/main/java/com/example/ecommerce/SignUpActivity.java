package com.example.ecommerce;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SignUpActivity extends AppCompatActivity {
    EditText ed_name;
    EditText ed_email;
    EditText ed_birthdate;
    EditText ed_username;
    EditText ed_password;
    EditText ed_question;
    EditText ed_answer;
    DatePickerDialog.OnDateSetListener mDateListner;

    TextView tv;

    EcommerceDataBase dbobj;

    Customer c;
    Button btn_confirm;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ed_name = findViewById(R.id.signup_name);
        ed_birthdate = findViewById(R.id.signup_birthdate);
        ed_username = findViewById(R.id.signup_username);
        ed_password = findViewById(R.id.signup_password);
        ed_email=findViewById(R.id.signup_email);
        ed_question = findViewById(R.id.signup_recoveryquestion);
        ed_answer = findViewById(R.id.signup_recoveryanswer);

        tv = (TextView)(findViewById(R.id.signup_wait_asceond_and_successfully_));




        dbobj = new EcommerceDataBase(getApplicationContext());

        btn_confirm = (Button)(findViewById(R.id.btn_confirm_data_in_sign_up));

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = new Customer(ed_name.getText().toString() , ed_username.getText().toString() , ed_password.getText().toString() , ed_birthdate.getText().toString() , ed_email.getText().toString() ,  ed_question.getText().toString() , ed_answer.getText().toString() );

                dbobj.SignUp(c);
                new CountDownTimer(4000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        tv.setText("Wait a Second...............");
                        tv.setTextColor(getResources().getColor(R.color.black));
                    }

                    public void onFinish() {
                        tv.setText("Successfully Signed Up");
                        tv.setTextColor(getResources().getColor(R.color.black));
                        Intent i =new Intent(getApplicationContext(),LogInActivity.class);
                        startActivity(i);

                    }

                }.start();

            }
        });

        ed_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*final Calendar mycalender = Calendar.getInstance();
                final int myear = mycalender.get(Calendar.YEAR) , mmonth = mycalender.get(Calendar.MONTH) , mday = mycalender.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        ed_birthdate.setText(dayOfMonth + "-" + month + "-" + year);
                    }
                },myear,mmonth,mday);
                datePickerDialog.setTitle("Confirm your birth date");
                datePickerDialog.show();*/

                Calendar cal =Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =new DatePickerDialog(SignUpActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateListner,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                mDateListner =new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month+=1;
                        ed_birthdate.setText(day + "-" + month + "-" + year);
                    }
                };

            }
        });

    }
}