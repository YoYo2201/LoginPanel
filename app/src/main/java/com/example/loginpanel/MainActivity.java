package com.example.loginpanel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

public class MainActivity extends AppCompatActivity {
    public static String res = "";
    public static String string = "";
    EditText b;
    int flag = 0;
    public void loginbtn(View view)
    {
        res = "";
        EditText a = (EditText) findViewById(R.id.emailid);
        EditText b = (EditText) findViewById(R.id.password);
        String id = a.getText().toString();
        String pass = b.getText().toString();
        Login login = new Login(this, id, pass);
        login.execute();
    }
    public void registerbtn(View view)
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    public void showhidebtn(View view)
    {
        EditText b = (EditText) findViewById(R.id.password);
        if(flag == 0)
        {
            ((ImageView)(view)).setImageResource(R.drawable.show_pass_foreground);
            flag= 1;
            b.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else
        {
            ((ImageView)(view)).setImageResource(R.drawable.hide_pass_foreground);
            flag = 0;
            b.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

class Login extends AsyncTask<String, Void, String> {
    private Context context;
    private String id;
    private String pass;
    AlertDialog dialog;
    public Login(Context context, String id, String pass)
    {
        this.context = context;
        this.id = id;
        this.pass = pass;
    }

    @Override
    protected void onPreExecute()
    {
        dialog = new AlertDialog.Builder(this.context).create();
        dialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String s)
    {
        if(s.equals("0"))
        {
            dialog.setMessage("Email does not exist!!! Click on Register to create a new account...");
            dialog.show();
        }
        else if(!s.equals(this.pass))
        {
            dialog.setMessage("Wrong Password!!!");
            dialog.show();
        }
        else if(s.equals(this.pass))
        {
            context.startActivity(new Intent(context, HotelSelect.class));
        }
    }

    @Override
    protected String doInBackground(String... voids)
    {
        String link = "http://172.16.129.41/login.php";
        System.out.println(link);
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(this.id, "UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
            String l = "";
            while((l = reader.readLine()) != null)
                MainActivity.res += l;
            reader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            MainActivity.res = e.getMessage();
        } catch (IOException e) {
            MainActivity.res = e.getMessage();
        }
        if(MainActivity.res.equals(this.pass))
            MainActivity.string = "Exe";
        return MainActivity.res;
    }
}