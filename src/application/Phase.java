package application;

import java.util.LinkedList;

public class Phase {
	LinkedList<Spur> spuren = new LinkedList<Spur>();

	public LinkedList<Spur> getSpuren() {
		return spuren;
	}

	public void setSpuren(LinkedList<Spur> spuren) {
		this.spuren = spuren;
	}
	public void putSpuren(Spur s) {
		spuren.add(s);
	}

}
