package application;

import java.util.LinkedList;

public class Phase {
	
	private int phasendauer;
	
	
	
	LinkedList<Signalgeber> sg = new LinkedList<Signalgeber>();

	public LinkedList<Signalgeber> getSignalgeber() {
		return sg;
	}

	public void setSpuren(LinkedList<Signalgeber> sg) {
		this.sg = sg;
	}
	public void putSpuren(Signalgeber s) {
		sg.add(s);
	}
	public void calc_phasendauer() {
		for (int i=0; i<sg.size();i++) {
			if (sg.get(i).getDauer()>this.phasendauer) {
				this.phasendauer=sg.get(i).getDauer();
			}
		}
			
	}

}
