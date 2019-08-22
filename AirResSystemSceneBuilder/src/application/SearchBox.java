package application;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import CustomRect.SeatPane;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchBox {
	
	static boolean answer;
	
	public static void display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Search for Reservation");
		window.setMinWidth(250);
		
		Label label = new Label();
		label.setText("Please enter a last name");
		TextArea searchBar = new TextArea();
		searchBar.setMaxHeight(30);
		Button searchButton = new Button("Search");
		Button backButton = new Button("Back");
		
		//checks for match in reservation list and opens a table view of those reservations
		searchButton.setOnAction(e -> {
			if(searchForMatch(searchBar)) {
				ResTableView table = new ResTableView(searchBar.getText());
				table.display();
				window.close();
			}
			else {
				SuccessBox box = new SuccessBox();
				box.display("", "No reservations found for that last name");
				return;
			}
		});
		backButton.setOnAction(e -> {
			window.close();
		});
		
		VBox layout = new VBox(10);
		HBox btnBox = new HBox(10);
		btnBox.getChildren().addAll(searchButton, backButton);
		btnBox.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(label, searchBar, btnBox);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	
	public static boolean searchForMatch(TextArea searchBar) {
		//method for finding last name that matches the query
		String searchName = searchBar.getText();
		
		try(Scanner input = new Scanner(new File("ReservationList.txt"))) {
        	while(input.hasNext() ) {
        		String seat = input.next();
        		String fName = input.next();
        		String mI = input.next();
        		String lName = input.next();
        		String gender = input.next();
        		String dOB = input.next();
        		String countryOfOrigin = input.next();
        		String phone = input.next();
        		String fromTo = input.next();
        		String seatType = input.next();
        		String isDisabled = input.next();
        		String isVeteran = input.next();
        		input.nextLine();
        		
        		if(lName.toLowerCase().equals(searchName.toLowerCase())){
        			return true;
        		}
        	}
        	return false;
        	
		} catch(Exception e) {
			System.out.println("File not found!");
		}
		
		return false;
	}
}

