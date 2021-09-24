package com.example.loginpanel;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

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
import java.util.concurrent.TimeUnit;


public class CheckMail extends AsyncTask <String, Void, String> {
    AlertDialog dialog;
    Context context;
    public CheckMail(Context context)
    {
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Login Status");
    }
    @Override
    protected void onPostExecute(String s) {
        if(s.equals("1")) {
            s = "Email Already Registered!!!";
            Register.string = "1";
            dialog.setMessage(s);
            dialog.show();
        }
        else
            Register.string = "0";
    }
    @Override
    protected String doInBackground(String... voids) {
        String res = "";
        String ce = voids[0];
        String link = "http://172.16.129.41/checkmail.php";
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(ce, "UTF-8");
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
