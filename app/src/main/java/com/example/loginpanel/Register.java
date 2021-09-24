package com.example.loginpanel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Register extends AppCompatActivity {
    public Session session;
    int f1 = 0;
    int f2 = 0;
    public static String string = "";
    public static String r = "";
    public static String res = "";
    public static String fn = "";
    public static String ln = "";
    public static String ce = "";
    public static String cp = "";
    AlertDialog dialog;
    AlertDialog dial;
    public void nextbtn(View view)
    {
        dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Password Status");
        EditText a = (EditText) findViewById(R.id.first_name);
        EditText b = (EditText) findViewById(R.id.last_name);
        EditText c = (EditText) findViewById(R.id.create_email);
        EditText d = (EditText) findViewById(R.id.create_pass);
        EditText f = (EditText) findViewById(R.id.confirm_pass);
        fn = a.getText().toString();
        ln = b.getText().toString();
        ce = c.getText().toString();
        cp = d.getText().toString();
        String cop = f.getText().toString();
        dial = new AlertDialog.Builder(this).create();
        if(fn.equals(""))
        {
            dial.setMessage("First Name cannot be Empty!!!");
            dial.show();
        }
        else if(ln.equals(""))
        {
            dial.setMessage("Last Name cannot be Empty!!!");
            dial.show();
        }
        else if(ce.equals(""))
        {
            dial.setMessage("Email cannot be Empty!!!");
            dial.show();
        }
        else if(cp.equals(""))
        {
            dial.setMessage("Password cannot be Empty!!!");
            dial.show();
        }
        else if(cop.equals(""))
        {
            dial.setMessage("Confirm Password cannot be Empty!!!");
            dial.show();
        }
        else {
            if (cp.equals(cop)) {
                int small=0, large=0, spec=0, dig=0;
                for(int i=0;i<cp.length();i++)
                {
                    if(cp.charAt(i) >= 65 && cp.charAt(i) <= 90)
                        large++;
                    else if(cp.charAt(i) >= 97 && cp.charAt(i) <= 122)
                        small++;
                    else if(cp.charAt(i) >= 48 && cp.charAt(i) <= 57)
                        dig++;
                    else
                        spec++;
                }
                if(small > 0 && large > 0 && spec > 0 && dig > 0) {
                    CheckMail checkMail = new CheckMail(this);
                    checkMail.execute(ce);
                    if (string.equals("0")) {
                        double random_no = (Math.random() * 900000) + 100000;
                        int rand = (int) (random_no);
                        r = Integer.toString(rand);
                        SendOTP otp = new SendOTP(this, r, cop);
                        otp.execute();
                        Intent intent = new Intent(this, OTP.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    dial.setMessage("Password must contain at least 1 digit, special character, 1 small and 1 capital letter!!!");
                    dial.show();
                }
            } else {
                dialog.setMessage("Password doesn't match with Confirm Password!!!");
                dialog.show();
            }
        }
    }
    public void showhidebtn1(View view)
    {
        EditText d = (EditText) findViewById(R.id.create_pass);
        if(f1 == 0)
        {
            ((ImageView)(view)).setImageResource(R.drawable.show_pass_foreground);
            f1= 1;
            d.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else
        {
            ((ImageView)(view)).setImageResource(R.drawable.hide_pass_foreground);
            f1 = 0;
            d.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
    public void showhidebtn2(View view)
    {
        EditText f = (EditText) findViewById(R.id.confirm_pass);
        if(f2 == 0)
        {
            ((ImageView)(view)).setImageResource(R.drawable.show_pass_foreground);
            f2= 1;
            f.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else
        {
            ((ImageView)(view)).setImageResource(R.drawable.hide_pass_foreground);
            f2 = 0;
            f.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}