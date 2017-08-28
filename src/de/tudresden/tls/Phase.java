package de.tudresden.tls;

import java.util.LinkedList;

public class Phase {
	
	private double phasendauer;
	LinkedList<Signalgeber> sg = new LinkedList<Signalgeber>();

	public LinkedList<Signalgeber> getSignalgeber() {
		return sg;
	}

	public void setSignalgeber(LinkedList<Signalgeber> sg) {
		this.sg = sg;
	}
	public void putSignalgeber(Signalgeber s) {
		sg.add(s);
	}
	public void calc_phasendauer() {
		for (int i=0; i<sg.size();i++) {
			if (sg.get(i).getTfUmlauf()>this.phasendauer) {
				this.phasendauer=sg.get(i).getTfUmlauf();
			}
		}
			
	}

}