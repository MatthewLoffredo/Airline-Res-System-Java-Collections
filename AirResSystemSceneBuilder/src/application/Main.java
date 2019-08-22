package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import CustomRect.SeatPane;
import java.util.ArrayList;


public class Main extends Application {
	static Stage window;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			window = primaryStage;
			
			//getting loader and pane for first scene
			FXMLLoader homePaneLoader = new FXMLLoader(getClass().getResource("AirlineResSys.fxml"));
			Parent homePane = homePaneLoader.load();
			Scene homeScene = new Scene(homePane,800,600);
			homeScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//getting loader and pane for second scene
			FXMLLoader enterInfoPaneLoader = new FXMLLoader(getClass().getResource("EnterInfoPage.fxml"));
			Parent enterInfoPane = enterInfoPaneLoader.load();
			enterInfoPane.setCache(true);
			enterInfoPane.setCacheHint(CacheHint.SPEED);
			Scene enterInfoScene = new Scene(enterInfoPane,800,600);
			enterInfoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//getting loader and pane for third scene
			FXMLLoader seatSelectorLoader = new FXMLLoader(getClass().getResource("SeatSelector.fxml"));
			Parent seatSelectorPane = seatSelectorLoader.load();
			Scene seatSelectorScene = new Scene(seatSelectorPane);
			seatSelectorScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//setting all controllers
			AirlineResSysController homePaneController = (AirlineResSysController) homePaneLoader.getController();
			EnterInfoPageController enterInfoPageController = (EnterInfoPageController) enterInfoPaneLoader.getController();
			SeatSelectorController seatSelectorController = (SeatSelectorController) seatSelectorLoader.getController();
			
			
			//injecting scenes into the controllers so they can move back and forth between scenes
			homePaneController.setEnterInfoScene(enterInfoScene);
			homePaneController.setSeatSelectorController(seatSelectorController);
			enterInfoPageController.setSeatSelectorController(seatSelectorController);
			enterInfoPageController.setHomeScene(homeScene);
			enterInfoPageController.setSeatScene(seatSelectorScene);
			seatSelectorController.setEnterInfoScene(enterInfoScene);
			seatSelectorController.setHomeScene(homeScene);
			
			primaryStage.setTitle("SkyHigh Airlines");
			primaryStage.setScene(homeScene);
			primaryStage.setOnCloseRequest(e -> {
				e.consume();
				closeProgram();
			});
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void restart() {
		  window.close();
		  Platform.runLater( () -> new Main().start( new Stage() ) );
	}
	
	private void closeProgram() {
		if(AlertBox.display("Confirm", "Are you sure you want to exit?")) {
			window.close();
		}
	}
}
