package application;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

public class Option {

	private final SimpleStringProperty name;
	private final SimpleFloatProperty wert;
	
	Option(String string, float f) {
		this.name= new SimpleStringProperty(string);
		this.wert= new SimpleFloatProperty(f);
	}

	public String getName() {
		return name.get();
	}
	
	public void setName(String n) {
		this.name.set(n);
	}

	public Float getWert() {
		return wert.get();
	}
	
	public void setWert(Float w) {
		this.wert.set(w);
	}
	
}
