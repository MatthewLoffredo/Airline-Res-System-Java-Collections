package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import CustomRect.SeatPane;
import application.PassengerClasses.Passenger;

public class SeatSelectorController implements Initializable {
	
	
	@FXML
	private SeatPane R1A1, R1B2, R1C3, R1D4, R2A5, R2B6, R2C7, R2D8, R3A9, R3B10, R3C11, R3D12, R4A13, R4B14, R4C15, R4D16, 
		R4E17, R4F18, R5A19, R5B20, R5C21, R5D22, R5E23, R5F24, R6A25, R6B26, R6C27, R6D28, R6E29, R6F30, R7A31, R7B32, R7C33, 
		R7D34, R7E35, R7F36, R8A37, R8B38, R8C39, R8D40, R8E41, R8F42, R9A43, R9B44, R9C45, R9D46, R9E47, R9F48, R10A49, R10B50, R10C51, R10D52, R10E53, R10F54,
		R11A55, R11B56, R11C57, R11D58, R11E59, R11F60, R12A61, R12B62, R12C63, R12D64, R12E65, R12F66, R13A67, R13B68, R13C69, R13D70, R13E71, R13F72, R14A73, 
		R14B74, R14C75, R14D76, R14E77, R14F78, R15A79, R15B80, R15C81, R15D82, R15E83, R15F84, R16A85, R16B86, R16C87, R16D88, R16E89, R16F90, R17A91, R17B92, 
		R17C93, R17D94, R17E95, R17F96, R18A97, R18B98, R18C99, R18D100, R18E101, R18F102, R19A103, R19B104, R19C105, R19D106, R19E107, R19F108, R20A109, R20B110, 
		R20C111, R20D112, R20E113, R20F114, R21A115, R21B116, R21C117, R21D118, R21E119, R21F120, R22A121, R22B122, R22C123, R22D124, R22E125, R22F126, R23A127, 
		R23B128, R23C129, R23D130, R23E131, R23F132, R24A133, R24B134, R24C135, R24D136, R24E137, R24F138; 
	
	@FXML
	private Button back, reserveBtn;
	
	@FXML
	private AnchorPane root;
	
	@FXML
	private TextArea textForSelected;
	
	private LinkedList<SeatPane> seatList;
	private HashMap<SeatPane, Boolean> selectedSeat;
	private SeatPane tempSeat;
	private Passenger currentPassengerInfo;
	private boolean anySelected;
	private boolean wrongSeat;
	
	private Scene enterInfoScene;
	private Scene currentScene;
	private Scene homeScene;
	private ActionEvent homeEvent = new ActionEvent();
	
	public void setHomeScene(Scene scene) {
		homeScene = scene;
	}
	
	public void openHomeScene() {
		Stage window = (Stage) ((Node)homeEvent.getSource()).getScene().getWindow();
		window.setScene(homeScene);
	}
	
	public void setEnterInfoScene(Scene scene) {
		enterInfoScene = scene;
	}
	
	public void setHomeEvent(ActionEvent event) {
		homeEvent = event;
	}
	
	public void openEnterInfoScene(ActionEvent event) {
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(enterInfoScene);
	}
	
	@FXML
	private void action(ActionEvent event) throws IOException {
		currentScene = root.getScene();
	}
	
	public Scene getThisScene() {
		return currentScene;
	}
	
	public void setCurrentPassengerInfo(Passenger p) {
		currentPassengerInfo = p;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//instantiates variables
		seatList = new LinkedList<>();
		selectedSeat = new HashMap<>();
		anySelected = false;
		wrongSeat = false;
		textForSelected.setWrapText(true);
		
		//add seats to list
		addToList();
		
		//fills seats
		fillSeats();
		
		//highlights seat when clicked; loop to set mouse click for all seats
		for(int i = 0; i < seatList.size(); i++) {
			final Integer innerI = Integer.valueOf(i);
			
			seatList.get(innerI).setOnMouseClicked(e -> {
				anySelected = true;
				wrongSeat = false;
				//checks if seat is already selected
				if(selectedSeat.get(seatList.get(innerI)) == false) {
					//finds which seat is already selected
					ListIterator it = seatList.listIterator();
					int count = 0;
					while(it.hasNext()) {
						tempSeat = (SeatPane) it.next();
						if(selectedSeat.get(tempSeat) == true) {
							break;
						}
						if(count < 137)
							count++;
					}
		
					//selects new seat
					Node nodeOut2 = seatList.get(innerI).getChildren().get(0);
			        if(nodeOut2 instanceof Rectangle){
			        	((Rectangle)nodeOut2).setStrokeWidth(3);;
			            ((Rectangle)nodeOut2).setStroke(Color.RED);
			            selectedSeat.put(seatList.get(innerI), true);
			            //checks if user tries to select a seat that doesn't match the class they selected
			            if(Character.isLetter(seatList.get(innerI).getSeatID().charAt(2))) {
			            	if(currentPassengerInfo.getSeatType().equals("F") && Character.getNumericValue(seatList.get(innerI).getSeatID().charAt(1)) > 3) {
			            		textForSelected.setText("Please select a First Class seat");
			            		wrongSeat = true;
			            	}
			            	if(currentPassengerInfo.getSeatType().equals("B") && Character.getNumericValue(seatList.get(innerI).getSeatID().charAt(1)) <= 3) {
			            		textForSelected.setText("Please select a Business Class seat");
			            		wrongSeat = true;
			            	}
			            	if(currentPassengerInfo.getSeatType().equals("E")) {
			            		textForSelected.setText("Please select an Economy Class seat");
			            		wrongSeat = true;
			            	}
			            }
						else {
							if(Character.isDigit(seatList.get(innerI).getSeatID().charAt(2)) && currentPassengerInfo.getSeatType().equals("F")) {
									textForSelected.setText("Please select a First Class seat");
									wrongSeat = true;
							}
							if(currentPassengerInfo.getSeatType().equals("B") && Character.getNumericValue(seatList.get(innerI).getSeatID().charAt(1) + seatList.get(innerI).getSeatID().charAt(2)) >= 11) {
			            		textForSelected.setText("Please select a Business Class seat");
			            		wrongSeat = true;
			            	}
							if(currentPassengerInfo.getSeatType().equals("E") && Character.getNumericValue(seatList.get(innerI).getSeatID().charAt(1) + seatList.get(innerI).getSeatID().charAt(2)) < 11) {
			            		textForSelected.setText("Please select an Economy Class seat");
			            		wrongSeat = true;
			            	}
			        	}
			            
			            //if the user is in the right section:
			            if(wrongSeat == false) {
							textForSelected.setText("Seat ID: " + seatList.get(innerI).getSeatID());
							if(seatList.get(innerI).isFilled())
								textForSelected.appendText("\nAlready Reserved");
			            }   
			        }
			        //deselects old seat
					Node nodeOut1 = seatList.get(count).getChildren().get(0);
			        if(nodeOut1 instanceof Rectangle){
			        	((Rectangle)nodeOut1).setStrokeWidth(1);;
			            ((Rectangle)nodeOut1).setStroke(Color.GREEN);
			            selectedSeat.put(seatList.get(count), false);
			        }
				}
			});
			
		}

	}
	
	public void addToList() {
		seatList.add(R1A1);
		R1A1.setSeatID("R1A1");
		
		seatList.add(R1B2);
		R1B2.setSeatID("R1B2");
		
		seatList.add(R1C3);
		R1C3.setSeatID("R1C3");
		
		seatList.add(R1D4);
		R1D4.setSeatID("R1D4");
		
		seatList.add(R2A5);
		R2A5.setSeatID("R2A5");
		
		seatList.add(R2B6);
		R2B6.setSeatID("R2B6");
		
		seatList.add(R2C7);
		R2C7.setSeatID("R2C7");
		
		seatList.add(R2D8);
		R2D8.setSeatID("R2D8");
		
		seatList.add(R3A9);
		R3A9.setSeatID("R3A9");
		
		seatList.add(R3B10);
		R3B10.setSeatID("R3B10");
		
		seatList.add(R3C11);
		R3C11.setSeatID("R3C11");
		
		seatList.add(R3D12);
		R3D12.setSeatID("R3D12");
		
		seatList.add(R4A13);
		R4A13.setSeatID("R4A13");
		seatList.add(R4B14);
		R4B14.setSeatID("R4B14");
		seatList.add(R4C15);
		R4C15.setSeatID("R4C15");
		seatList.add(R4D16);
		R4D16.setSeatID("R4D16");
		seatList.add(R4E17);
		R4E17.setSeatID("R4E17");
		seatList.add(R4F18);
		R4F18.setSeatID("R4F18");
		seatList.add(R5A19);
		R5A19.setSeatID("R5A19");
		seatList.add(R5B20);
		R5B20.setSeatID("R5B20");
		seatList.add(R5C21);
		R5C21.setSeatID("R5C21");
		seatList.add(R5D22);
		R5D22.setSeatID("R5D22");
		seatList.add(R5E23);
		R5E23.setSeatID("R5E23");
		seatList.add(R5F24);
		R5F24.setSeatID("R5F24");
		seatList.add(R6A25);
		R6A25.setSeatID("R6A25");
		seatList.add(R6B26);
		R6B26.setSeatID("R6B26");
		seatList.add(R6C27);
		R6C27.setSeatID("R6C27");
		seatList.add(R6D28);
		R6D28.setSeatID("R6D28");
		seatList.add(R6E29);
		R6E29.setSeatID("R6E29");
		seatList.add(R6F30);
		R6F30.setSeatID("R6F30");
		seatList.add(R7A31);
		R7A31.setSeatID("R7A31");
		seatList.add(R7B32);
		R7B32.setSeatID("R7B32");
		seatList.add(R7C33);
		R7C33.setSeatID("R7C33");
		seatList.add(R7D34);
		R7D34.setSeatID("R7D34");
		seatList.add(R7E35);
		R7E35.setSeatID("R7E35");
		seatList.add(R7F36);
		R7F36.setSeatID("R7F36");
		seatList.add(R8A37);
		R8A37.setSeatID("R8A37");
		seatList.add(R8B38);
		R8B38.setSeatID("R8B38");
		seatList.add(R8C39);
		R8C39.setSeatID("R8C39");
		seatList.add(R8D40);
		R8D40.setSeatID("R8D40");
		seatList.add(R8E41);
		R8E41.setSeatID("R8E41");
		seatList.add(R8F42);
		R8F42.setSeatID("R8F42");
		seatList.add(R9A43);
		R9A43.setSeatID("R9A43");
		seatList.add(R9B44);
		R9B44.setSeatID("R9B44");
		seatList.add(R9C45);
		R9C45.setSeatID("R9C45");
		seatList.add(R9D46);
		R9D46.setSeatID("R9D46");
		seatList.add(R9E47);
		R9E47.setSeatID("R9E47");
		seatList.add(R9F48);
		R9F48.setSeatID("R9F48");
		seatList.add(R10A49);
		R10A49.setSeatID("R10A49");
		seatList.add(R10B50);
		R10B50.setSeatID("R10B50");
		seatList.add(R10C51);
		R10C51.setSeatID("R10C51");
		seatList.add(R10D52);
		R10D52.setSeatID("R10D52");
		seatList.add(R10E53);
		R10E53.setSeatID("R10E53");
		seatList.add(R10F54);
		R10F54.setSeatID("R10F54");
		
		seatList.add(R11A55);
		R11A55.setSeatID("R11A55");
		seatList.add(R11B56);
		R11B56.setSeatID("R11B56");
		seatList.add(R11C57);
		R11C57.setSeatID("R11C57");
		seatList.add(R11D58);
		R11D58.setSeatID("R11D58");
		seatList.add(R11E59);
		R11E59.setSeatID("R11E59");
		seatList.add(R11F60);
		R11F60.setSeatID("R11F60");
		seatList.add(R12A61);
		R12A61.setSeatID("R12A61");
		seatList.add(R12B62);
		R12B62.setSeatID("R12B62");
		seatList.add(R12C63);
		R12C63.setSeatID("R12C63");
		seatList.add(R12D64);
		R12D64.setSeatID("R12D64");
		seatList.add(R12E65);
		R12E65.setSeatID("R12E65");
		seatList.add(R12F66);
		R12F66.setSeatID("R12F66");
		seatList.add(R13A67);
		R13A67.setSeatID("R13A67");
		seatList.add(R13B68);
		R13B68.setSeatID("R13B68");
		seatList.add(R13C69);
		R13C69.setSeatID("R13C69");
		seatList.add(R13D70);
		R13D70.setSeatID("R13D70");
		seatList.add(R13E71);
		R13E71.setSeatID("R13E71");
		seatList.add(R13F72);
		R13F72.setSeatID("R13F72");
		seatList.add(R14A73);
		R14A73.setSeatID("R14A73");
		seatList.add(R14B74);
		R14B74.setSeatID("R14B74");
		seatList.add(R14C75);
		R14C75.setSeatID("R14C75");
		seatList.add(R14D76);
		R14D76.setSeatID("R14D76");
		seatList.add(R14E77);
		R14E77.setSeatID("R14E77");
		seatList.add(R14F78);
		R14F78.setSeatID("R14F78");
		seatList.add(R15A79);
		R15A79.setSeatID("R15A79");
		seatList.add(R15B80);
		R15B80.setSeatID("R15B80");
		seatList.add(R15C81);
		R15C81.setSeatID("R15C81");
		seatList.add(R15D82);
		R15D82.setSeatID("R15D82");
		seatList.add(R15E83);
		R15E83.setSeatID("R15E83");
		seatList.add(R15F84);
		R15F84.setSeatID("R15F84");
		seatList.add(R16A85);
		R16A85.setSeatID("R16A85");
		seatList.add(R16B86);
		R16B86.setSeatID("R16B86");
		seatList.add(R16C87);
		R16C87.setSeatID("R16C87");
		seatList.add(R16D88);
		R16D88.setSeatID("R16D88");
		seatList.add(R16E89);
		R16E89.setSeatID("R16E89");
		seatList.add(R16F90);
		R16F90.setSeatID("R16F90");
		seatList.add(R17A91);
		R17A91.setSeatID("R17A91");
		seatList.add(R17B92);
		R17B92.setSeatID("R17B92");
		seatList.add(R17C93);
		R17C93.setSeatID("R17C93");
		seatList.add(R17D94);
		R17D94.setSeatID("R17D94");
		seatList.add(R17E95);
		R17E95.setSeatID("R17E95");
		seatList.add(R17F96);
		R17F96.setSeatID("R17F96");
		seatList.add(R18A97);
		R18A97.setSeatID("R18A97");
		seatList.add(R18B98);
		R18B98.setSeatID("R18B98");
		seatList.add(R18C99);
		R18C99.setSeatID("R18C99");
		seatList.add(R18D100);
		R18D100.setSeatID("R18D100");
		seatList.add(R18E101);
		R18E101.setSeatID("R18E101");
		seatList.add(R18F102);
		R18F102.setSeatID("R18F102");
		seatList.add(R19A103);
		R19A103.setSeatID("R19A103");
		seatList.add(R19B104);
		R19B104.setSeatID("R19B104");
		seatList.add(R19C105);
		R19C105.setSeatID("R19C105");
		seatList.add(R19D106);
		R19D106.setSeatID("R19D106");
		seatList.add(R19E107);
		R19E107.setSeatID("R19E107");
		seatList.add(R19F108);
		R19F108.setSeatID("R19F108");
		seatList.add(R20A109);
		R20A109.setSeatID("R20A109");
		seatList.add(R20B110);
		R20B110.setSeatID("R20B110");
		seatList.add(R20C111);
		R20C111.setSeatID("R20C111");
		seatList.add(R20D112);
		R20D112.setSeatID("R20D112");
		seatList.add(R20E113);
		R20E113.setSeatID("R20E113");
		seatList.add(R20F114);
		R20F114.setSeatID("R20F114");
		seatList.add(R21A115);
		R21A115.setSeatID("R21A115");
		seatList.add(R21B116);
		R21B116.setSeatID("R21B116");
		seatList.add(R21C117);
		R21C117.setSeatID("R21C117");
		seatList.add(R21D118);
		R21D118.setSeatID("R21D118");
		seatList.add(R21E119);
		R21E119.setSeatID("R21E119");
		seatList.add(R21F120);
		R21F120.setSeatID("R21F120");
		seatList.add(R22A121);
		R22A121.setSeatID("R22A121");
		seatList.add(R22B122);
		R22B122.setSeatID("R22B122");
		seatList.add(R22C123);
		R22C123.setSeatID("R22C123");
		seatList.add(R22D124);
		R22D124.setSeatID("R22D124");
		seatList.add(R22E125);
		R22E125.setSeatID("R22E125");
		seatList.add(R22F126);
		R22F126.setSeatID("R22F126");
		seatList.add(R23A127);
		R23A127.setSeatID("R23A127");
		seatList.add(R23B128);
		R23B128.setSeatID("R23B128");
		seatList.add(R23C129);
		R23C129.setSeatID("R23C129");
		seatList.add(R23D130);
		R23D130.setSeatID("R23D130");
		seatList.add(R23E131);
		R23E131.setSeatID("R23E131");
		seatList.add(R23F132);
		R23F132.setSeatID("R23F132");
		seatList.add(R24A133);
		R24A133.setSeatID("R24A133");
		seatList.add(R24B134);
		R24B134.setSeatID("R24B134");
		seatList.add(R24C135);
		R24C135.setSeatID("R24C135");
		seatList.add(R24D136);
		R24D136.setSeatID("R24D136");
		seatList.add(R24E137);
		R24E137.setSeatID("R24E137");
		seatList.add(R24F138);
		R24F138.setSeatID("R24F138");
		
		for(int i = 0; i < seatList.size(); i++) {
			selectedSeat.put(seatList.get(i), false);
			setFilled(seatList.get(i));
		}
	}
	
	public SeatPane getSelected() {
		ListIterator it = seatList.listIterator();
		int count = 0;
		while(it.hasNext()) {
			tempSeat = (SeatPane) it.next();
			if(selectedSeat.get(tempSeat) == true) {
				break;
			}
			count++;
		}
		
		return seatList.get(count);
	}
	
	public void setFilled(SeatPane s) {
		try {
			Scanner input = new Scanner(new File("SeatList.txt"));
			//gets seat id
			String id = "";
			if(Character.isLetter(s.getSeatID().charAt(2)))
				id = "" + s.getSeatID().charAt(1) + s.getSeatID().charAt(2);
			else id = "" + s.getSeatID().charAt(1) + s.getSeatID().charAt(2) + s.getSeatID().charAt(3);
			
			//finds seat in file and checks if its filled
			while(input.hasNext()) {
				if(id.equals(input.next())) {
					break;
				}
				input.next();
				input.next();
			}
			input.next();
			
			if(input.next().equals("true"))
				s.setFilled(true);
			else s.setFilled(false);
			input.close();
		} catch (Exception e) {
			System.out.println("Seatlist file not found!");
		}
	}
	
	public void fillSeats() {
		//refills seats in text file
		for(int i = 0; i < seatList.size(); i++) {
			setFilled(seatList.get(i));
		}
		//fills seats in gui if they are filled
		for(int i = 0; i < seatList.size(); i++) {
			if(seatList.get(i).isFilled()) {
				//fills seat
				Node seat = seatList.get(i).getChildren().get(0);
		        if(seat instanceof Rectangle){
		            ((Rectangle)seat).setFill(Color.RED);
		        }
			}
		}
	}
	
	public void writeSeatData(ActionEvent ev) {
		homeEvent = ev;
		if(!anySelected) {
			SuccessBox box = new SuccessBox();
			box.display("", "Please select a seat");
			return;
		}
		if(wrongSeat) {
			SuccessBox box = new SuccessBox();
			box.display("", "Cannot reserve a seat that does not match your flight accomodation preferences");
			return;
		}
		if(getSelected().isFilled()) {
			SuccessBox box = new SuccessBox();
			box.display("", "Seat is already reserved");
			return;
		}
		
		if(!AlertBox.display("Confirm", "Are you sure you want to reserve this seat?"))
			return;
		
		String oldData = "";
		String newData = "";
		String id = "";
		
		//sets old string and new string values
		try {
			if(Character.isLetter(getSelected().getSeatID().charAt(2)))
				id = "" + getSelected().getSeatID().charAt(1) + getSelected().getSeatID().charAt(2);
			else id = "" + getSelected().getSeatID().charAt(1) + getSelected().getSeatID().charAt(2) + getSelected().getSeatID().charAt(3);
			int count = 0;
			Scanner input = new Scanner(new File("SeatList.txt"));
			while(input.hasNext()) {
				if(id.equals(input.next())) {
					//lineNum = count;
					break;
				}
				input.next();
				input.next();
				count++;
			}
			input.close();
			
			Scanner input2 = new Scanner(new File("SeatList.txt"));
			for(int i = 0; i <= count; i++) {
				oldData = input2.nextLine();
			}
			input2.close();
			
			Scanner input3 = new Scanner(oldData);
			newData = "" + input3.next() + " " + input3.next() +" true";
			input3.close();
			
		} catch(Exception e) {
			System.out.println("Seatlist file not found!");
		}
		
		//finds and replaces text in file
		String seatFilePath = "SeatList.txt";
        String origFile = "";
        
        BufferedReader reader = null;
        BufferedWriter writer = null;
        
		try {
			
			reader = new BufferedReader(new FileReader(seatFilePath));
			
			String currentReadingLine = reader.readLine();
			
			while (currentReadingLine != null) {
	                origFile += currentReadingLine + System.lineSeparator();
	                currentReadingLine = reader.readLine();
	        }
			
			String moddedFile = origFile.replaceAll(oldData, newData);
			
			writer = new BufferedWriter(new FileWriter(seatFilePath));
			
			writer.write(moddedFile);
		}
		catch(IOException e) {
			System.out.println("Storage File is missing");
		}
		finally {
			try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                
            }
		}
		reserveSeat(id);
		fillSeats();
		SuccessBox box = new SuccessBox();
		if(box.display("", "Reservation Successful!")) {
			Main.restart();
		}
	}
	
	public void reserveSeat(String seat) {
		try {
			PrintWriter output = new PrintWriter(new FileWriter("ReservationList.txt", true));
			output.println(seat + " " + currentPassengerInfo.toString());
			output.close();
			
			
		}
		catch(IOException e) {
			System.out.println("Storage File is missing");
			
		}
	}
	
}
