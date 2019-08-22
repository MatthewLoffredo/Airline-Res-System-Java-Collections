package application;

import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import application.PassengerClasses.BusinessClass;
import application.PassengerClasses.EconomyClass;
import application.PassengerClasses.FirstClass;
import application.PassengerClasses.Passenger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Skin;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EnterInfoPageController implements Initializable{
	//injecting variables into FXML file
	@FXML
	private TextField fName;
	@FXML
	private TextField mI;
	@FXML
	private TextField lName;
	@FXML
	private TextField phone;
	@FXML
	private ComboBox<String> cboMonth;
	@FXML
	private ComboBox<String> cboDay;
	@FXML
	private ComboBox<String> cboYear;
	@FXML
	private ComboBox<String> cboCountry;
	@FXML
	private ComboBox<String> cboCountryFrom;
	@FXML
	private ComboBox<String> cboCountryTo;
	@FXML
	private RadioButton male;
	@FXML
	private RadioButton female;
	@FXML
	private RadioButton fClass;
	@FXML
	private RadioButton bClass;
	@FXML
	private RadioButton eClass;
	@FXML
	private RadioButton yes1;
	@FXML
	private RadioButton no1;
	@FXML
	private RadioButton yes2;
	@FXML
	private RadioButton no2;
	@FXML
	ToggleGroup tGroup1 = new ToggleGroup();
	@FXML
	ToggleGroup tGroup2 = new ToggleGroup();
	@FXML
	ToggleGroup tGroup3 = new ToggleGroup();
	@FXML
	ToggleGroup tGroup4 = new ToggleGroup();
	@FXML
	private ScrollPane scrPane;
	
	//variable for showing console output in GUI
	@FXML
	private TextArea cStream;
	private PrintStream pS;
	
	private Scene homeScene;
	private Scene seatSelectorScene;
	private SeatSelectorController seatSelectorController;
	private Passenger currentPassengerInfo;
	
	//setting home screen scene
	public void setHomeScene(Scene scene) {
		homeScene = scene;
	}
	//setting seat selector scene
	public void setSeatScene(Scene scene) {
		seatSelectorScene = scene;
	}
	//setting seatSelectorController
	public void setSeatSelectorController(SeatSelectorController s) {
		seatSelectorController = s;
	}
	
	//pushing back goes back to home
	public void openHomeScene(ActionEvent event) {
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(homeScene);
	}
	//pushing next passes info to seat selector and goes to seat selector
	public void openSeatScene(ActionEvent event) {
		try {
			addRes();
		} catch (Exception e) {}
		
		if(currentPassengerInfo != null) {
			seatSelectorController.setCurrentPassengerInfo(currentPassengerInfo);
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setScene(seatSelectorScene);
		}
	}
	
	//pushing save saves the reservation to a text file
	public void saveRes(ActionEvent e) {
		addRes();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//wraps text of textbox
		cStream.setWrapText(true);
		
		//filling birth-date combo boxes
		ObservableList<String> monthArr = FXCollections.observableArrayList();
		ObservableList<String> dayArr = FXCollections.observableArrayList();
		ObservableList<String> yearArr = FXCollections.observableArrayList();
		for(int month = 1; month < 13; month++) {
			String tmp = (month < 10 ? "0" : "") + month;
			monthArr.add(tmp);
		}
		for(int day = 1; day < 31; day++) {
			String tmp = (day < 10 ? "0" : "") + day;
			dayArr.add(tmp);
		}
		for(int year = 1930; year <= Calendar.getInstance().get(Calendar.YEAR); year++) {
			String tmp = String.valueOf(year);
			yearArr.add(tmp);
		}
		cboMonth.getItems().addAll(monthArr);
		cboDay.getItems().addAll(dayArr);
		cboYear.getItems().addAll(yearArr);
		
		//fill country combo boxes
		ObservableList<String> cities = FXCollections.observableArrayList();
        String[] locales = Locale.getISOCountries();
        for (String countrylist : locales) {
            Locale obj = new Locale("", countrylist);
            String[] city = { obj.getDisplayCountry() };
            for (int x = 0; x < city.length; x++) {
                cities.add(obj.getDisplayCountry());
            }
        }
        cboCountry.getItems().addAll(cities);
        cboCountryFrom.getItems().addAll(cities);
        cboCountryTo.getItems().addAll(cities);
	}
	
	public void addRes() {
		//validate, get info from fields and instantiates new passenger
		RadioButton checkClass = (RadioButton)tGroup2.getSelectedToggle();
		if(checkAllFields() && isChar(fName.getText()) && isChar(mI.getText()) && isChar(lName.getText()) && isLong(phone.getText())) {
			
			//new First Class Passenger
			if(checkClass.getText().equals("First Class")) {
				newFirstClassP();
				return;
			}
			//new Business Class Passenger
			if(checkClass.getText().equals("Business")) {
				newBusinessClassP();
				return;
			}
			//new Economy Class Passenger
			if(checkClass.getText().equals("Economy")) {
				newEconomyClassP();
				return;
			}
		}
		//cStream.appendText("Reservation Failure.");
	}
	
	//next 3 methods create new passenger objects and writes to file
	public void newFirstClassP() {
		RadioButton check1 = (RadioButton)tGroup1.getSelectedToggle();
		RadioButton check2 = (RadioButton)tGroup2.getSelectedToggle();
		RadioButton check3 = (RadioButton)tGroup3.getSelectedToggle();
		RadioButton check4 = (RadioButton)tGroup4.getSelectedToggle();
		try {
			Passenger p = new FirstClass(fName.getText(), mI.getText(), lName.getText(), check1.getText(), 
					cboMonth.getValue() + cboDay.getValue()+ cboYear.getValue(), cboCountry.getValue(), phone.getText(), 
					cboCountryFrom.getValue() + "," + cboCountryTo.getValue(), check2.getText(), 
					isYes(check3.getText()), isYes(check4.getText()));
		
			currentPassengerInfo = p;
		}
		catch(Exception e) {
			cStream.appendText("One or more fields is invalid");
		}
		
		//writePData(p);
	}
	
	public void newBusinessClassP() {
		RadioButton check1 = (RadioButton)tGroup1.getSelectedToggle();
		RadioButton check2 = (RadioButton)tGroup2.getSelectedToggle();
		RadioButton check3 = (RadioButton)tGroup3.getSelectedToggle();
		RadioButton check4 = (RadioButton)tGroup4.getSelectedToggle();
		try {
			Passenger p = new BusinessClass(fName.getText(), mI.getText(), lName.getText(), check1.getText(), 
					cboMonth.getValue() + cboDay.getValue()+ cboYear.getValue(), cboCountry.getValue(), phone.getText(), 
					cboCountryFrom.getValue() + "," + cboCountryTo.getValue(), check2.getText(), 
					isYes(check3.getText()), isYes(check4.getText()));

			currentPassengerInfo = p;
		}
		catch(Exception e) {
			cStream.appendText("One or more fields is invalid");
		}
		
		//writePData(p);
	}
	
	public void newEconomyClassP() {
		RadioButton check1 = (RadioButton)tGroup1.getSelectedToggle();
		RadioButton check2 = (RadioButton)tGroup2.getSelectedToggle();
		RadioButton check3 = (RadioButton)tGroup3.getSelectedToggle();
		RadioButton check4 = (RadioButton)tGroup4.getSelectedToggle();
		try {
			Passenger p = new EconomyClass(fName.getText(), mI.getText(), lName.getText(), check1.getText(), 
					cboMonth.getValue() + cboDay.getValue()+ cboYear.getValue(), cboCountry.getValue(), phone.getText(), 
					cboCountryFrom.getValue() + "," + cboCountryTo.getValue(), check2.getText(), 
					isYes(check3.getText()), isYes(check4.getText()));
	        
			currentPassengerInfo = p;
		}
		catch(Exception e) {
			cStream.appendText("One or more fields is invalid");
		}
		
		//writePData(p);
	}
	
	//method to store data in a text file
	public void writePData(Passenger p) {
		try {
			PrintWriter output = new PrintWriter(new FileWriter("Storage.txt", true));
			output.println(p.toString());
			output.close();
			//System.out.println("Reservation Successful!");
			cStream.appendText("Reservation Successful!");
		}
		catch(IOException e) {
			//System.out.println("Storage File is missing");
			cStream.appendText("Storage File is missing");
		}
	}
	
	//validation methods
	//checks if text is a long
	private boolean isLong(String text) {
		try {
			long i = Long.parseLong(text);
			return true;
		}
		catch (NumberFormatException e) {
			//System.out.println(text + " is not a phone number");
			cStream.appendText("\nPlease enter a valid phone number");
			return false;
		}
	}
	
	//checks if string is made up of only letters
	private boolean isChar(String text) {
			for(int i = 0; i < text.length(); i++) {
				if(Character.isDigit(text.charAt(i)) == true) {
					//System.out.println("Names do not have numbers");
					cStream.appendText("\nPlease enter a name without numbers");
					return false;
				}		
			}
			return true;
	}
	
	//checks if radio box is yes or no
	private boolean isYes(String text) {
		if(text.equals("Yes"))
			return true;
		return false;
	}
	
	//checks if combo boxes are filled
	private boolean checkAllFields() {
		
		if(cboDay.getValue() != null && cboMonth.getValue() != null && cboYear.getValue() != null && cboCountry.getValue() != null &&
			cboCountryTo.getValue() != null && cboCountryFrom.getValue() != null && tGroup1.getSelectedToggle() != null && 
			tGroup2.getSelectedToggle() != null && tGroup3.getSelectedToggle() != null && tGroup4.getSelectedToggle() != null) {
			return true;
		}
		else {
			cStream.appendText("\nAll fields are required");
			return false;
		}
		
	}
	
}
