package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgetPasswordActivity extends AppCompatActivity {
    TextView forgetpassword_textview , forgetpassword_retrievedpassword;
    EditText forgetpassword_answereditext , forgetpassword_emailedittext ;
    Button btn_forgetpassword_searchforpassword;
    EcommerceDataBase dbobj;
    Cursor c;

    String username , password , email , question , answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetpassword_textview = (TextView)(findViewById(R.id.forgetpassword_textview));
        forgetpassword_retrievedpassword = (TextView)(findViewById(R.id.forgetpassword_retrievedpassword));
        forgetpassword_answereditext = findViewById(R.id.forgetpassword_answereditext);
        forgetpassword_emailedittext = findViewById(R.id.forgetpassword_emailedittext);
        btn_forgetpassword_searchforpassword = (Button)(findViewById(R.id.btn_forgettpassword_searchforpassword));

        dbobj = new EcommerceDataBase(getApplicationContext());

        Bundle b = getIntent().getExtras();
        username = b.getString("username");

        c = dbobj.ForgetPassword(username);
        password = c.getString(1);
        email = c.getString(2);
        question = c.getString(3);
        answer = c.getString(4);


        forgetpassword_textview.setText(question);

        btn_forgetpassword_searchforpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String outpass = "Your Password is " + password , error_editextisempty = "Please fill empty fields!!!", error_in_fields = "Email or security answer may be incorrect";
                if( forgetpassword_answereditext.getText().toString().equals("") || forgetpassword_emailedittext.getText().toString().equals("") ){
                    new CountDownTimer(3000, 2000) {

                        public void onTick(long millisUntilFinished) {
                            forgetpassword_retrievedpassword.setText(error_editextisempty);
                            forgetpassword_retrievedpassword.setTextColor(getResources().getColor(R.color.black));
                        }

                        public void onFinish() {
                            forgetpassword_retrievedpassword.setText("");
                        }
                    }.start();


                }
                else{
                    if( answer.equals(forgetpassword_answereditext.getText().toString()) && email.equals(forgetpassword_emailedittext.getText().toString()) ) {
                        forgetpassword_retrievedpassword.setTextColor(getResources().getColor(R.color.black));
                        forgetpassword_retrievedpassword.setText(outpass);
                    }
                    else {
                        new CountDownTimer(2000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                forgetpassword_retrievedpassword.setText(error_in_fields);
                                forgetpassword_retrievedpassword.setTextColor(getResources().getColor(R.color.black));
                            }

                            public void onFinish() {
                                forgetpassword_retrievedpassword.setText("");
                            }
                        }.start();
                    }
                }


            }
        });
    }
}