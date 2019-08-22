package application.PassengerClasses;
import java.util.*;
import java.sql.Timestamp;

public abstract class Passenger implements Comparable<Passenger> {
	//all member variables
	protected String firstName;
	protected String middleI;
	protected String lastName;
	protected int age;
	protected String gender;
	protected String birthDate;
	protected String countryOrigin;
	protected String phone;
	protected String fromTo;
	protected String seatType;
	protected boolean isVeteran;
	protected boolean isDisabled;
	protected Timestamp dateCreated;
	protected String seatID;
	
	protected int boardingGroup;
	
	public Passenger() {
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		dateCreated = ts;
	}
	
	//constructor for new passenger data
	public Passenger(String fn, String mi, String ln, String g, String b, String cO, String ph, String fT, String sT, boolean vet, boolean dis) {
		firstName = fn;
		middleI = mi;
		lastName = ln;
		gender = g;
		birthDate = b;
		countryOrigin = cO;
		phone = ph;
		fromTo = fT;
		isVeteran = vet;
		isDisabled = dis;
		
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		dateCreated = ts;
		
		countryOrigin = countryOrigin.replace(' ', '-');
		fromTo = fromTo.replace(' ', '-');
	}
	
	//compareTo implementation
	public int compareTo(Passenger other) {
		if(boardingGroup > other.boardingGroup)
			return 1;
		else if(boardingGroup < other.boardingGroup)
			return -1;
		return 0;
	}
	
	//overrides toString
	public String toString() {
		return firstName + " " + middleI + " " + lastName + " " + gender + " " + birthDate + " "
				+ countryOrigin + " " + phone + " " + fromTo + " " + seatType + " " + isDisabled + " " + isVeteran;
	}
	
	//getters and setters
	public int getBoardingGroup() {
		return boardingGroup;
	}

	public void setBoardingGroup() {
		if(isDisabled == true) 
			boardingGroup = 1;
		else if(isVeteran == true) 
			boardingGroup = 2;
		else if(seatType.equals("F")) 
			boardingGroup = 3;
		else if(seatType.equals("B")) 
			boardingGroup = 4;
		else if(seatType.equals("E")) 
			boardingGroup = 5;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public String getMiddleI() {
		return middleI;
	}

	public void setMiddleI(String name) {
		this.middleI = name;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String name) {
		this.lastName = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getSeatType() {
		return seatType;
	}

	public String getCountryOrigin() {
		return countryOrigin;
	}

	public void setCountryOrigin(String countryOrigin) {
		this.countryOrigin = countryOrigin;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFromTo() {
		return fromTo;
	}

	public void setFromTo(String fromTo) {
		this.fromTo = fromTo;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}
	
	public boolean isVeteran() {
		return isVeteran;
	}

	public void setVeteran(boolean isVeteran) {
		this.isVeteran = isVeteran;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getSeatID() {
		return seatID;
	}

	public void setSeatID(String seatID) {
		this.seatID = seatID;
	}
}
