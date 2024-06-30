package services;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;

public class CheckFailedMail {

	boolean flag=true;
	
  synchronized	public boolean checkFailedMail() {
		
		
		
		 // Email server configuration
        String host = "imap.gmail.com";
        String username = "gopaljawle02@gmail.com"; // replace with your email
        String password = "fkwj tsrt vpet nppj"; // replace with your email password or app-specific password

       
        // Set up properties
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.ssl.enable", "true");

        Session emailSession = Session.getDefaultInstance(properties);

        try {
        	
        	
            // Connect to the message store
            Store store = emailSession.getStore("imaps");
            store.connect(host, username, password);

            // Open the inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Get all messages from the inbox
           // Message[] messages = inbox.getMessages();
            
            
//            Calendar cal = Calendar.getInstance();
//            cal.set(2024, Calendar.JUNE, 10); // replace with your specific date (year, month, day)
//            java.util.Date specificDate = cal.getTime();
//            
            
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            java.util.Date todayDate = cal.getTime();

            

            // Create a search term for emails sent on the specific date
            SearchTerm searchTerm = new SentDateTerm(SentDateTerm.EQ, todayDate);

            // Get messages that match the search term
            Message[] messages = inbox.search(searchTerm);

            

            for (Message message : messages) {
                if (isBounceBack(message)) {
                	
                	flag=false;
                	
                    System.out.println("\n\nBounce-back email found:");
                    System.out.println("From: " + message.getFrom()[0]);
                    
                  
                    
                    // its get recipients from our mail 
//                    System.out.println("To: ");
//                    Address[] toRecipients = message.getRecipients(Message.RecipientType.TO);
//                    printAddresses(toRecipients);
//
//                    System.out.println("CC: ");
//                    Address[] ccRecipients = message.getRecipients(Message.RecipientType.CC);
//                    printAddresses(ccRecipients);
//
//                    System.out.println("BCC: ");
//                    Address[] bccRecipients = message.getRecipients(Message.RecipientType.BCC);
//                    printAddresses(bccRecipients);

                    
                    
                    
                    
                    System.out.println("Subject: " + message.getSubject());
                   
                    System.out.println("Sent Date: " + message.getSentDate());
                    
                    
                    // this give failed receiver mail address
                    String failedRecipient = getFailedRecipient(message);
                    System.out.println("Failed Recipient: " + failedRecipient);
                    
                    
                }
            }

            // Close the folder and store
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
        return flag;
	
	}
	
	
	
	 private static boolean isBounceBack(Message message) {
	        try {
	        	
	        	
	        	
	            // Check for bounce-back based on the message subject
	            String subject = message.getSubject();
	            if (subject != null && subject.contains("Delivery Status Notification (Failure)")) {
	                return true;
	            }

	            // Check for bounce-back based on message content type
	            if (message.isMimeType("multipart/report")) {
	                Multipart multipart = (Multipart) message.getContent();
	                for (int i = 0; i < multipart.getCount(); i++) {
	                    BodyPart bodyPart = multipart.getBodyPart(i);
	                    if (bodyPart.isMimeType("message/delivery-status")) {
	                        return true;
	                    }
	                }
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	    
	    
	    
	    private static void printAddresses(Address[] addresses) {
	        if (addresses != null) {
	            for (Address address : addresses) {
	                System.out.println(address.toString());
	            }
	        } else {
	            System.out.println("None");
	        }
	    }
	    
	    
	    
	    private static String getFailedRecipient(Message message) {
	        try {
	            if (message.isMimeType("multipart/report")) {
	                Multipart multipart = (Multipart) message.getContent();
	                for (int i = 0; i < multipart.getCount(); i++) {
	                    BodyPart bodyPart = multipart.getBodyPart(i);
	                    if (bodyPart.isMimeType("message/delivery-status")) {
	                        InputStream is = bodyPart.getInputStream();
	                        StringBuilder sb = new StringBuilder();
	                        int ch;
	                        while ((ch = is.read()) != -1) {
	                            sb.append((char) ch);
	                        }
	                        String[] lines = sb.toString().split("\n");
	                        for (String line : lines) {
	                            if (line.startsWith("Original-Recipient:") || line.startsWith("Final-Recipient:")) {
	                                String[] parts = line.split(";");
	                                if (parts.length > 1) {
	                                    return parts[1].trim();
	                                }
	                            }
	                        }
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "Unknown";
	    }
	    
	
	
	
	
	
}
