package application;

import java.io.File;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;

import application.PassengerClasses.BusinessClass;
import application.PassengerClasses.EconomyClass;
import application.PassengerClasses.FirstClass;
import application.PassengerClasses.Passenger;
import application.BoardingGroupView.Item;
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
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BoardingGroupView {
	TableView<Item> table;
	
    public BoardingGroupView() {
    	//create new table view, can double click on items
        table = new TableView<>();
        table.setRowFactory(tv -> {
            TableRow<Item> row = new TableRow<>();
            /*
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Item rowData = row.getItem();
                    rowData.getName();
                    BoardingPassView.display("", "Your boarding group numer: ");
                }
            });
            */
            return row ;
        });
        //add columns to the table view
        table.getColumns().add(column("BoardingGroup", Item::boardingGroupProperty));
        table.getColumns().add(column("Name", Item::nameProperty));
        table.getColumns().add(column("Gender", Item::genderProperty));
        table.getColumns().add(column("Seat", Item::seatIDProperty));
        table.getColumns().add(column("Class Type", Item::classTypeProperty));
        table.getColumns().add(column("Veteran", Item::isVeteranProperty));
        table.getColumns().add(column("Disability", Item::isDisabledProperty));
        
        //instantiates a priority queue for boarding group order
        PriorityQueue<Passenger> boardingQueue = new PriorityQueue<>();
        
        //adds passengers to queue from reservation list
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
        		String isVeteran = input.next();
        		String isDisabled = input.next();
        		input.nextLine();
        		
        		if(seatType.equals("F")) {
        			FirstClass p = new FirstClass(fName, mI, lName, gender, dOB, countryOfOrigin, 
        					phone, fromTo, seatType, Boolean.valueOf(isVeteran), Boolean.valueOf(isDisabled));
        			p.setSeatID(seat);
        			boardingQueue.offer(p);
        		}
        		if(seatType.equals("B")) {
        			BusinessClass p = new BusinessClass(fName, mI, lName, gender, dOB, countryOfOrigin, 
        					phone, fromTo, seatType, Boolean.valueOf(isVeteran), Boolean.valueOf(isDisabled));
        			p.setSeatID(seat);
        			boardingQueue.offer(p);
        		}
        		if(seatType.equals("E")) {
        			EconomyClass p = new EconomyClass(fName, mI, lName, gender, dOB, countryOfOrigin, 
        					phone, fromTo, seatType, Boolean.valueOf(isVeteran), Boolean.valueOf(isDisabled));
        			p.setSeatID(seat);
        			boardingQueue.offer(p);
        		}
        	}
        	
        	//adds passengers to table view according to their priority
        	while(!boardingQueue.isEmpty()) {
        		table.getItems().add(new Item(boardingQueue.poll()));
        	}
        } catch(Exception e) {e.printStackTrace();}
    }
    
    public void display() {
    	Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		//window.setTitle(title);
		window.setMinWidth(250);
		Scene scene = new Scene(table);
	    window.setScene(scene);
	    window.show();
    }

    private static <S,T> TableColumn<S,T> column(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S,T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        return col ;
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

        public Item(Passenger p) {
        	
        	setBoardingGroup(p.getBoardingGroup());
            setName(p.getFirstName() + " " + p.getMiddleI() + " " + p.getLastName());
            setGender(p.getGender());
            setSeatID(p.getSeatID());
            setDOB(p.getBirthDate());
            setIsVeteran(String.valueOf(p.isVeteran()));
            setIsDisabled(String.valueOf(p.isDisabled()));
            if(p.getSeatType().equals("F"))
            	setClassType("First");
            else if(p.getSeatType().equals("B"))
            	setClassType("Business");
            else if(p.getSeatType().equals("E"))
            	setClassType("Economy");
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
		
		
		
    }

}