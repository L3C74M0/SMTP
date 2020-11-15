package model;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Client {
	
	private File attachedfile;
	
	public void SendMail(String sender, String password, String subject, String messageContent, String addressee) throws IOException {
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
						
			//Se pone el Tema del correo
			mimeMessage.setSubject(subject);
			
			//Se crea un mimeBodyPart que almacenará en mensaje de texto plano
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setText(messageContent);
			
			//Agrego las partes
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			//Se crea un mimeBodyPart que almacenará un archivo
			if(attachedfile!=null) {	
				MimeBodyPart mimeBodyPartAdjunt = new MimeBodyPart();
				mimeBodyPartAdjunt.attachFile(attachedfile);				
				multipart.addBodyPart(mimeBodyPartAdjunt);
			}
			
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

	public void setAttachedfile(File attachedfile) {
		this.attachedfile = attachedfile;
	}	
}