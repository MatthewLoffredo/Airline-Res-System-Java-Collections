package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;

import javax.swing.ToolTipManager;

import application.PassengerClasses.BusinessClass;
import application.PassengerClasses.EconomyClass;
import application.PassengerClasses.FirstClass;
import application.PassengerClasses.Passenger;
import application.ResTableView.Item;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ResTableView {
	TableView<Item> table;
	String searchName;
	
    public ResTableView(String searchName) {
    	//create new table view
        table = new TableView<>();
        this.searchName = searchName;
    
        table.setRowFactory(tv -> {
        	TableRow<Item> row = new TableRow<>();
        	Tooltip options = new Tooltip();
            VBox deleteBox = new VBox(2);
            Button delete = new Button("Delete");
            deleteBox.getChildren().add(delete);
            options.setGraphic(deleteBox);
        	options.setHideDelay(new Duration(600));
            
        	row.setOnMouseEntered(event -> {
        		if(!row.isEmpty()) {
        			row.setTooltip(options);
        		}
        	});
        	
            delete.setOnAction(e -> {
            	if(AlertBox.display("Warning", "Are you sure you want to delete this reservation?")) {
            		String res = row.getItem().getExactResText();
            		writeAllSeatData(res);
            		this.addItemsToView();
            	}
            });
            
            return row ;
        });
        //add columns to the table view
        table.getColumns().add(column("BoardingGroup", Item::boardingGroupProperty));
        table.getColumns().add(column("Name", Item::nameProperty));
        table.getColumns().add(column("Gender", Item::genderProperty));
        table.getColumns().add(column("Seat", Item::seatIDProperty));
        table.getColumns().add(column("Class Type", Item::classTypeProperty));
        table.getColumns().add(column("Phone", Item::phoneProperty));
        table.getColumns().add(column("Country of Origin", Item::countryOfOriginProperty));
        table.getColumns().add(column("Flight Path", Item::flightPathProperty));
        table.getColumns().add(column("DOB", Item::dOBProperty));
        table.getColumns().add(column("Veteran", Item::isVeteranProperty));
        table.getColumns().add(column("Disability", Item::isDisabledProperty));
        table.getColumns().add(column("Date Reserved", Item::dateCreatedProperty));
        
        this.addItemsToView();
        
    }
    
    public void display() {
    	Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		//window.setTitle(title);
		window.setMinWidth(250);
		Scene scene = new Scene(table, 1050, 200);
	    window.setScene(scene);
	    window.setTitle("Hover over an item for delete option");
	    window.show();
    }

    private static <S,T> TableColumn<S,T> column(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S,T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        return col ;
    }
    
    private void addItemsToView() {
    	//clears current table
    	table.getItems().clear();
    	//instantiates a list for search matches
        LinkedList<Passenger> searchMatch = new LinkedList<>();
        
    	//adds passengers list if they match the last name
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
        		
        		if(lName.toLowerCase().equals(searchName.toLowerCase()) && seatType.equals("F")) {
        			FirstClass p = new FirstClass(fName, mI, lName, gender, dOB, countryOfOrigin, 
        					phone, fromTo, seatType, Boolean.parseBoolean(isVeteran), Boolean.parseBoolean(isDisabled));
        			p.setSeatID(seat);
        			searchMatch.add(p);
        		}
        		
        		if(lName.toLowerCase().equals(searchName.toLowerCase()) && seatType.equals("B")) {
        			BusinessClass p = new BusinessClass(fName, mI, lName, gender, dOB, countryOfOrigin, 
        					phone, fromTo, seatType, Boolean.parseBoolean(isVeteran), Boolean.parseBoolean(isDisabled));
        			p.setSeatID(seat);
        			searchMatch.add(p);
        		}
        		
        		if(lName.toLowerCase().equals(searchName.toLowerCase()) && seatType.equals("E")) {
        			EconomyClass p = new EconomyClass(fName, mI, lName, gender, dOB, countryOfOrigin, 
        					phone, fromTo, seatType, Boolean.parseBoolean(isVeteran), Boolean.parseBoolean(isDisabled));
        			p.setSeatID(seat);
        			searchMatch.add(p);
        		}
        	}
        	
        	//adds passengers to table view 
        	for(Passenger p : searchMatch)
        		table.getItems().add(new Item(p));
        	
        } catch(Exception e) {e.printStackTrace();}
    }

    public static class Item {
    	//items in a table view use properties
    	private final IntegerProperty boardingGroup = new SimpleIntegerProperty();
        private final StringProperty name = new SimpleStringProperty();
        private final StringProperty gender = new SimpleStringProperty();
        private final StringProperty countryOfOrigin = new SimpleStringProperty();
        private final LongProperty phone = new SimpleLongProperty();
        private final StringProperty dOB = new SimpleStringProperty();
        private final StringProperty seatID = new SimpleStringProperty();
        private final StringProperty flightPath = new SimpleStringProperty();
        private final StringProperty classType = new SimpleStringProperty();
        private final StringProperty isDisabled = new SimpleStringProperty();
        private final StringProperty isVeteran = new SimpleStringProperty();
        private final StringProperty dateCreated = new SimpleStringProperty();
        String exactResText;

        public Item(Passenger p) {
        	
        	setBoardingGroup(p.getBoardingGroup());
            setName(p.getFirstName() + " " + p.getMiddleI() + " " + p.getLastName());
            setGender(p.getGender());
            setSeatID(p.getSeatID());
            setDOB(p.getBirthDate().substring(0,2) + "-" + p.getBirthDate().substring(2,4) + "-" + p.getBirthDate().substring(4, p.getBirthDate().length()));
            setPhone(Long.parseLong(p.getPhone()));
            setCountryOfOrigin(p.getCountryOrigin());
            setFlightPath(p.getFromTo().replaceAll(",", "-->"));
            setIsVeteran(String.valueOf(p.isVeteran()));
            setIsDisabled(String.valueOf(p.isDisabled()));
            setDateCreated(new SimpleDateFormat("MM-dd-yyyy HH:mm").format(p.getDateCreated()));
            if(p.getSeatType().equals("F"))
            	setClassType("First");
            else if(p.getSeatType().equals("B"))
            	setClassType("Business");
            else if(p.getSeatType().equals("E"))
            	setClassType("Economy");
            
            
            exactResText = p.getSeatID() + " " + p.getFirstName() + " " + p.getMiddleI() + " " + p.getLastName() + " " +
            				p.getGender() + " " + p.getBirthDate() + " " + p.getCountryOrigin() + " " + p.getPhone() +
            				" " + p.getFromTo() + " " + p.getSeatType() + " " + p.isDisabled() + " " + p.isVeteran();
            
        }
        
        //getters and setters for properties
        public StringProperty nameProperty() {
            return name ;
        }

		public final String getName() {
            return nameProperty().get();
        }

        public final void setName(String name) {
            nameProperty().set(name);
        }

		public final StringProperty genderProperty() {
			return this.gender;
		}

		public final String getGender() {
			return this.genderProperty().get();
		}
		

		public final void setGender(final String gender) {
			this.genderProperty().set(gender);
		}

		public final StringProperty countryOfOriginProperty() {
			return this.countryOfOrigin;
		}
		

		public final String getCountryOfOrigin() {
			return this.countryOfOriginProperty().get();
		}
		

		public final void setCountryOfOrigin(final String countryOfOrigin) {
			this.countryOfOriginProperty().set(countryOfOrigin);
		}
		

		public final LongProperty phoneProperty() {
			return this.phone;
		}
		

		public final long getPhone() {
			return this.phoneProperty().get();
		}
		

		public final void setPhone(final long phone) {
			this.phoneProperty().set(phone);
		}
		

		public final StringProperty dOBProperty() {
			return this.dOB;
		}
		

		public final String getDOB() {
			return this.dOBProperty().get();
		}
		

		public final void setDOB(final String dOB) {
			this.dOBProperty().set(dOB);
		}
		

		public final StringProperty seatIDProperty() {
			return this.seatID;
		}
		

		public final String getSeatID() {
			return this.seatIDProperty().get();
		}
		

		public final void setSeatID(final String seatID) {
			this.seatIDProperty().set(seatID);
		}
		

		public final StringProperty flightPathProperty() {
			return this.flightPath;
		}
		

		public final String getFlightPath() {
			return this.flightPathProperty().get();
		}
		

		public final void setFlightPath(final String flightPath) {
			this.flightPathProperty().set(flightPath);
		}
		

		public final StringProperty classTypeProperty() {
			return this.classType;
		}
		

		public final String getClassType() {
			return this.classTypeProperty().get();
		}
		

		public final void setClassType(final String classType) {
			this.classTypeProperty().set(classType);
		}
		

		public final StringProperty isDisabledProperty() {
			return this.isDisabled;
		}
		

		public final String isIsDisabled() {
			return this.isDisabledProperty().get();
		}
		

		public final void setIsDisabled(final String isDisabled) {
			this.isDisabledProperty().set(isDisabled);
		}
		

		public final StringProperty isVeteranProperty() {
			return this.isVeteran;
		}
		

		public final String getIsVeteran() {
			return this.isVeteranProperty().get();
		}
		

		public final void setIsVeteran(final String isVeteran) {
			this.isVeteranProperty().set(isVeteran);
		}

		public final IntegerProperty boardingGroupProperty() {
			return this.boardingGroup;
		}
		

		public final int getBoardingGroup() {
			return this.boardingGroupProperty().get();
		}
		

		public final void setBoardingGroup(final int boardingGroup) {
			this.boardingGroupProperty().set(boardingGroup);
		}
		
		public String getExactResText() {
			return exactResText;
		}

		public void setExactResText(String exactResText) {
			this.exactResText = exactResText;
		}

		public final StringProperty dateCreatedProperty() {
			return this.dateCreated;
		}
		

		public final String getDateCreated() {
			return this.dateCreatedProperty().get();
		}
		

		public final void setDateCreated(final String dateCreated) {
			this.dateCreatedProperty().set(dateCreated);
		}
		
		
    }
    
    public void writeAllSeatData(String res) {
		String oldData = "";
		String newData = "";
		String id = "";
		
		//sets old string and new string values
		try {
			Scanner resToDelete = new Scanner(res);
			id = "" + resToDelete.next();
			resToDelete.close();
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
			newData = "" + input3.next() + " " + input3.next() +" false";
			input3.close();
		} catch(Exception e) {
			
		}
		
		//finds and replaces text in file
		String origFilePath = "SeatList.txt";
        String origFile = "";
        
        BufferedReader reader = null;
        BufferedWriter writer = null;
        
		try {
			
			reader = new BufferedReader(new FileReader(origFilePath));
			
			String currentReadingLine = reader.readLine();
			
			while (currentReadingLine != null) {
	                origFile += currentReadingLine + System.lineSeparator();
	                currentReadingLine = reader.readLine();
	        }
			
			String moddedFile = origFile.replaceAll(oldData, newData);
			
			writer = new BufferedWriter(new FileWriter(origFilePath));
			
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
		deleteResData(res);
		SuccessBox box = new SuccessBox();
		if(box.display("", "Deletion Successful!")) {
			
		}
	}
    
    public void deleteResData(String res) {
		//rewrites text file without the reservation
		String origFilePath = "ReservationList.txt";
        String moddedFile = "";
        
        BufferedReader reader = null;
        BufferedWriter writer = null;
        
		try {
			
			reader = new BufferedReader(new FileReader(origFilePath));
			
			String currentReadingLine = reader.readLine();
			//goes through reservation file, if the line being deleted matches the next line, omit it from the new written file
			while (currentReadingLine != null) {
				
				if(!currentReadingLine.equals(res)) {
	                moddedFile += currentReadingLine + System.lineSeparator();
				}
				
	            currentReadingLine = reader.readLine();
	        }
			
			writer = new BufferedWriter(new FileWriter(origFilePath));
			
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
            	System.out.println("Storage File is missing");
            }
		}
	}

}