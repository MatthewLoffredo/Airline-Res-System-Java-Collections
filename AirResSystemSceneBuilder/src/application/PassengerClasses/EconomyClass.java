package application.PassengerClasses;

public class EconomyClass extends Passenger {
	
	private String seatType = "E";
	
	public EconomyClass() {
		super();
	}
	
	public EconomyClass(String fn, String mi, String ln, String g, String b, String cO, String ph, String fT, String sT, boolean vet, boolean dis) {
		super(fn, mi, ln, g, b, cO, ph, fT, sT, vet, dis);
		super.seatType = seatType;
		setBoardingGroup();
	}
	public String toString() {
		return super.toString();
	}
}
