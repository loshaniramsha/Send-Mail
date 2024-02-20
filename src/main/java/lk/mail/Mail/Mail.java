package lk.mail.Mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Mail implements Runnable{
    private String msg;
    private String to;
    private String subject;
    private File file;

    public Mail(String to, String subject, String msg,  File file) {
        this.msg = msg;
        this.to = to;
        this.subject = subject;
        this.file = file;
    }

    public Mail() {
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setFile(File file) {
        this.file = file;
    }

    public void outMail() throws MessagingException, IOException {
        String from = "loshaniramsha01@gmail.com"; //sender's email address
        String host = "localhost";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);


        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("loshaniramsha01@gmail.com", "wpyu boea cyoq blah");  // have to change some settings in SMTP
            }
        });


        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(from));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mimeMessage.setSubject(this.subject);
        mimeMessage.setText(this.msg);

        if (file != null) {

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.attachFile(this.file);


            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            mimeMessage.setContent(multipart);

            Transport.send(mimeMessage);

            System.out.println("email sent");
        } else {
            Transport.send(mimeMessage);
            System.out.println("sent");
        }
    }

    @Override
    public void run() {
        if (msg != null) {
            try {
                outMail();
            } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("not sent. empty msg!");
        }
    }
}