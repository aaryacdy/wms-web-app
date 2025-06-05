package com.kathford.wms.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {
    public static void sendMail(
            String text, String subject,
            String username, String toEmail
    ) {

        System.out.println(" Sender Email " + username);
        // Gmail account credentials
//        final String password = "M2iGJk2pqQnU0nNr9GSEHPRRYGjkU77b/7tz3twFxRc=";
        final String password = "bdtwiffbtczsphwk";
        //final String password = "M2iGJk2pqQnU0nNr9GSEHPRRYGjkU77b/7tz3twFxRc="; //for 7276mail

        // Gmail SMTP server details
        String host = "smtp.gmail.com";
        int port = 587;

        // Set the properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Create a Session object
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                try {
//                    return new PasswordAuthentication(username, Helper.decryptPassword(password));
                    return new PasswordAuthentication(username, password);
//                    return new PasswordAuthentication(username, "nfuhqzerzvwhnrnu");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(username));

            // Set To: header field
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            // Set Subject: header field
            message.setSubject(subject);

            // Set the actual message
            message.setText(text);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

