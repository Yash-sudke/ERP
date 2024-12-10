package com.qa.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


public class EmailUtils {
	
	public static void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) {
        // Email configuration
        String from = "tautomation17@gmail.com"; // Replace with your email
        String password = "deah texp ijlh tonf"; // Replace with your email password

        // Set up mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Create the email body
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);

            // Attach the report file
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachmentPath);

            // Combine parts into a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully with attachment.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

}
