package gui;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

public class SMTPApplicationController {

    @FXML
    private TextField senderAddress;

    @FXML
    private PasswordField senderPassword;

    @FXML
    private TextField receiverAddress;

    @FXML
    private TextField subject;

    @FXML
    private TextField message;
    
    @FXML
    private FileChooser fileChooser;
    
    private Client client;

    
    @FXML
    private void initialize() {
    	client = new Client();
    	fileChooser = new FileChooser();
    }
    
    @FXML
    private void attachFile(ActionEvent event) {    	    	    	
    	try{
    		File file = fileChooser.showOpenDialog(new Stage());
    		client.setAttachedfile(file);
    	}
    	catch(NullPointerException npe) {
    		npe.printStackTrace();
    	}
    }

    @FXML
    private void sendEmail(ActionEvent event) {    	    	    	
    	String sender = senderAddress.getText();
    	String receiver = receiverAddress.getText();
    	String password = senderPassword.getText();
    	String subjec = subject.getText();
    	String messageContent = message.getText();
    	
    	try {    		
			client.SendMail(sender, password, subjec, messageContent, receiver);
		} catch (IOException e) {			
			e.printStackTrace();
		}    	
    }
}
