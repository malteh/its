package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// args[0] MailServer
		// args[1] From
		// args[2] To
		// args[3] Text

		String server;
		String from;
		String to;
		String text;
		
		final String port = "587";

		if (args.length == 0) {
			server = "haw-mailer.haw-hamburg.de";
			from = "donald.duck@disneyland.de";
			to = "";
			text = "TESTMAIL FROM DISNEYLAND";
		} else {
			server = args[0];
			from = args[1];
			to = args[2];
			text = args[3];
		}

		// Copied from http://www.tutorialspoint.com/java/java_sending_email.htm

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", server);
		
		// Add Currect Haw Port Number
		properties.setProperty("mail.smtp.port",port);
		

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Now set the actual message
			message.setText(text);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
