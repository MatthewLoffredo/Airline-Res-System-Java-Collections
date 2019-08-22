package CustomRect;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import java.util.*;

public class SeatPane extends AnchorPane {
	
	protected String seatID;
	protected boolean isFilled;
	protected String classType;
	
	public SeatPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SeatPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
	
	@FXML
	public void initialize() {
		
	}

	public String getSeatID() {
		return seatID;
	}

	public void setSeatID(String seatID) {
		this.seatID = seatID;
	}

	public boolean isFilled() {
		return isFilled;
	}

	public void setFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}
	
	
}
