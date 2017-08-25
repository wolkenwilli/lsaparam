package application;

import java.util.LinkedList;

public class Phase {
	
	private int phasendauer;
	
	
	
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
	public void calc_phasendauer() {
		for (int i=0; i<spuren.size();i++) {
			if (spuren.get(i).getDauer()>this.phasendauer) {
				this.phasendauer=spuren.get(i).getDauer();
			}
		}
			
	}

}
