package services;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	
	
	// Define sender email and password as class-level variables
    private static final String senderEmail = "gopaljawle02@gmail.com";
    private static final String senderPassword = "fkwj tsrt vpet nppj"; // Use an app-specific password if using Gmail with 2FA

    
    public void sendEmail(List<String> recipients,String eventName,String date,String organizer) {
    	
    	
    	// Email ID of the recipient
       // String recipient = "xxxx@gmail";

        // SMTP server information
        String host = "smtp.gmail.com";

        // Setting up mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); // TLS port
        properties.put("mail.smtp.ssl.trust", host);

        // Creating a session object to get properties
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        
        
        try {
            // Creating a MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Setting From Field: adding sender's email to from field
            message.setFrom(new InternetAddress(senderEmail));

            // Setting To Field: adding recipient's email to to field
           // message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            
            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }

            
            // Setting Subject: subject of the email
            message.setSubject("Invitation for New Event ");

            // Setting body of the email
            message.setText("Dear Student\n"
            		+ "I hope this message finds you well.\n"
            		+ "We are pleased to invite you to "+eventName+" taking place on "+date+" in Our College Seminar Hall. Orginize By "+organizer+" Department.\n "
            		+ "We believe that your participation will be extremely valuable and contribute to the success of the event. Please Register For Event As soon as possible.\n"
            		+ "We look forward to your participation.\n"
            		+ "Best regards,\n"
            		+ "Thank You.\n"
            		+ "");

            // Sending email
            Transport.send(message);
            
            System.out.println("Email successfully sent!");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }

        
    
	
	
	
	
	
}
