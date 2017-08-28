package de.tudresden.vlp;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Option {

	private final SimpleIntegerProperty id;
	private final SimpleStringProperty name;
	private final SimpleFloatProperty wert;
	
	Option(Integer id, String string, float f) {
		this.id = new SimpleIntegerProperty(id);
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

	public Integer getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}
}
