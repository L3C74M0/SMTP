package model;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Client {
	protected String user;
	protected String password;
	protected String subject;
	protected String message;
	protected String addressee;

	public static void main(String[] args) {

	}

	public void SendMail() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addressee));
			message.setSubject(subject);
			message.setText(message+"");

			Transport.send(message);
			//JOptionPane.showMessageDialog(this, "Su mensaje ha sido enviado");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}