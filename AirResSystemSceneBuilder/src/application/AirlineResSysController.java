package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AirlineResSysController implements Initializable{
	
	//scenes to go  back to other windows
	private Scene enterInfoScene;
	
	//field for storing seat selector controller
	private SeatSelectorController seatSelectorController;
	
	@FXML
	private BorderPane bProot;
	
	@FXML
	private Text welcomeText;
	
	private ImageView gifHolder;
	private BufferedImage gifBuff;
	private Image gif;
	
	//method to use injected scene from main to populate scene
	public void setEnterInfoScene(Scene scene) {
		enterInfoScene = scene;
	}
	
	public void setSeatSelectorController(SeatSelectorController s) {
		seatSelectorController = s;
	}
	
	//button call to go the enter info scene
	public void openEnterInfoScene(ActionEvent event) {
		seatSelectorController.setHomeEvent(event);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(enterInfoScene);
	}
	
	public void openTableView(ActionEvent e) {
		BoardingGroupView table = new BoardingGroupView();
		table.display();
	}
	
	public void openSearchView(ActionEvent e) {
		SearchBox.display();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Thread thread = new Thread(new loadGif());
		
		thread.start();
		
	}
	
	class loadGif implements Runnable {
		
		public void run() {
			try {
				gif = new Image(getClass().getResource("..\\Clouds.gif").toString(), 700, 400, false, true, true);
				gifHolder = new ImageView(gif);
			} catch(Exception e) {System.out.println("error");}
			
			Platform.runLater(() ->	{
				bProot.setCenter(gifHolder);
			});
		}
	}
}
