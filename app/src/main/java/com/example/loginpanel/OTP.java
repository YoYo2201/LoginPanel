package com.example.loginpanel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public class OTP extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);
    }
    public void registerbtn(View view)
    {
        EditText o = (EditText) findViewById(R.id.otp);
        String otp = o.getText().toString();
        if(otp.equals(Register.r)) {
            FinalRegister finalRegister = new FinalRegister(this);
            finalRegister.execute();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            AlertDialog dial;
            dial = new AlertDialog.Builder(this).create();
            dial.setTitle("OTP Status");
            dial.setMessage("Wrong OTP Entered!!!");
            dial.show();
        }
    }
}
class FinalRegister extends AsyncTask<String, Void, String> {
    private Context context;
    AlertDialog dialog;
    public FinalRegister(Context context)
    {
        this.context = context;
    }
    @Override
    protected void onPreExecute()
    {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Registration Status");
    }
    @Override
    protected void onPostExecute(String s)
    {
        if(s.equals("1"))
        {
            dialog.setMessage("Registration Successful!!!");
            dialog.show();
        }
    }
    @Override
    protected String doInBackground(String... voids) {
        String res = "";
        String link = "http://172.16.129.41/register.php";
        System.out.println(link);
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode(Register.fn, "UTF-8") + "&&" + URLEncoder.encode("last_name", "UTF-8") + "=" + URLEncoder.encode(Register.ln, "UTF-8") + "&&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(Register.ce, "UTF-8") + "&&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(Register.cp, "UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
            String l = "";
            while((l = reader.readLine()) != null)
                res += l;
            reader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return res;
        } catch (MalformedURLException e) {
            res = e.getMessage();
        } catch (IOException e) {
            res = e.getMessage();
        }
        return res;
    }
}
/*class OTPCheck extends AsyncTask<String, Void, String> {
    private String otp;
    private Context context;
    AlertDialog dial;
    public OTPCheck(Context context, String otp)
    {
        this.context = context;
        this.otp = otp;
    }
    @Override
    protected void onPreExecute()
    {
        dial = new android.app.AlertDialog.Builder(context).create();
        dial.setTitle("Login Status");
    }
    @Override
    protected void onPostExecute(String s)
    {
        if(s == "0")
        {
            dial.setMessage("Wrong OTP entered!!!");
            dial.show();
        }
    }
    @Override
    protected String doInBackground(String... voids) {
        if(this.otp.equals(Register.r))
            Register.res = "1";
        else
            Register.res = "0";
        return Register.res;
    }
}*/