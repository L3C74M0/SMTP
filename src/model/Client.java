package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Client {
	protected static String sender;
	protected static String password;
	protected static String subject;
	protected static String messageContent;
	protected static String addressee;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Por favor digite la dirección de su correo electronico: \n\t");
			sender = br.readLine();
		
		System.out.println("\nPor favor digite la contraseña de su correo electronico: \n\t");
			password = br.readLine();
		
		System.out.println("\nPor favor digite el correo electronico del destinatario: \n\t");
			addressee = br.readLine();
			
		System.out.println("\nPor favor el tema del correo a enviar: \n\t");
			subject = br.readLine();
		
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
				return new PasswordAuthentication(sender, password);
			}
		});
		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(sender, subject));
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addressee));
			
			/*
			InternetAddress[] internetAddresses = {new InternetAddress("destinatario1@vojabes.com"),
					new InternetAddress("destinatario2@vojabes.com"),
					new InternetAddress("destinatario3@vojabes.com"),
					new InternetAddress("destinatario4@vojabes.com"),
					new InternetAddress("destinatario5@vojabes.com")};
			*/
			
			//Se pone el Tema del correo
			mimeMessage.setSubject(subject);
			
			//Se crea un mimeBodyPart que almacenará en mensaje de texto plano
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setText(messageContent);
			
			//Se crea un mimeBodyPart que almacenará un archivo
			MimeBodyPart mimeBodyPartAdjunt = new MimeBodyPart();
			mimeBodyPartAdjunt.attachFile(new File("C:/Users/R00TKIT/Downloads/Infoteorica.pdf"));
			
			//Agrego las partes
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			multipart.addBodyPart(mimeBodyPartAdjunt);
			
			//Agrego las partes a el mensaje
			mimeMessage.setContent(multipart);
			
			//Creo el trasnport que enviará el mensaje, le indico el protocolo
			Transport transport = session.getTransport("smtp");
			
			//Para la conexion le indico el correo y la contraseña
			transport.connect(sender,password);
			
			//Envio el mensaje
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			
			//Termino la conexion
			transport.close();
			System.out.println("\nSu mensaje de correo electronico ha sido enviado con exito.");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}