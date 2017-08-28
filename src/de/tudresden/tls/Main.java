package de.tudresden.tls;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		mainWindow();
		
	}
	
	
	public void mainWindow()
	{
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("lsa.fxml"));
			AnchorPane pane = loader.load();
			
			primaryStage.setMinHeight(1000.00);
			primaryStage.setMinWidth(1000.00);
			
			MainWindowController mainWindowController = loader.getController();
			mainWindowController.setMain(this);
			
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
