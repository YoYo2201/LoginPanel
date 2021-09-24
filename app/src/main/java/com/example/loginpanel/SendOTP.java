package com.example.loginpanel;

import android.content.Context;
import android.os.AsyncTask;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendOTP extends AsyncTask<Void, Void, Void> {
    private Context context;
    private Session session;
    private String r;
    private String cop;
    private String subject = "OTP for HTB Registration";
    public SendOTP(Context context, String r, String cop)
    {
        this.context = context;
        this.r = "Hello " + Register.fn +  " " + Register.ln + "!" + " Enter this OTP to complete your registration for HTB : "+ r;
        this.cop = cop;
    }
    @Override
    protected Void doInBackground(Void... params) {
        Properties p = new Properties();
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.socketFactory.port", "465");
        p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.port", "465");
        session = Session.getDefaultInstance(p, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
            }
        });
        try {
            MimeMessage mm = new MimeMessage(session);
            mm.setFrom(new InternetAddress(Config.EMAIL));
            mm.setSubject(subject);
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(Register.ce));
            mm.setText(this.r);
            Transport.send(mm);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
