package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;

public class Main extends Application{

	public void start(Stage stage) throws Exception{
		Parent root =
				FXMLLoader.load(getClass().getResource("SMTPApplication.fxml"));

		Scene scene = new Scene(root);
		stage.setTitle("SMTP Application");		
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.setScene(scene);


		Image icon = new Image("gui/imgs/icon.png");
		stage.getIcons().add(icon);
		stage.show();
	}
	
	public static void main(String[]args){
		launch(args);
	}

}