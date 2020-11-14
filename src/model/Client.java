package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Client {
	protected static String user;
	protected static String password;
	protected static String subject;
	protected static String messageContent;
	protected static String addressee;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Por favor digite la dirección de su correo electronico: \n\t");
			user = br.readLine();
		
		System.out.println("\nPor favor digite la contraseña de su correo electronico: \n\t");
			password = br.readLine();
		
		System.out.println("\nPor favor el tema del correo a enviar: \n\t");
			subject = br.readLine();
		
		System.out.println("\nPor favor digite el correo electronico del destinatario: \n\t");
			addressee = br.readLine();
		
		System.out.println("\nPor favor digite el mensaje a enviar: \n\t");
			messageContent = br.readLine();
			
		SendMail();
	}

	protected static void SendMail() throws IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		//props.put("mail.smtp.host", "smtp.gmail.com"); //Si el remitente es hotmail, se debe reemplazar gmail por live
		//props.put("mail.smtp.port", "587"); //Si el remitente es hotmail, se debe cambiar el puerto por 25
		
		//hotmail
		props.put("mail.smtp.host", "smtp.live.com"); 
		props.put("mail.smtp.port", "25");
		
		

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
			message.setText(messageContent);

			Transport.send(message);
			System.out.println("Su mensaje de correo electronico ha sido enviado con exito.");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}