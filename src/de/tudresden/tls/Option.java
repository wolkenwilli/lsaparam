/* 
#    Copyright 2017 Willi Schmidt
# 
#
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.

#################################################################################################### 
*/
package de.tudresden.tls;

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
