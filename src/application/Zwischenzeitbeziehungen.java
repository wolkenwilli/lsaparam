package application;


public class Zwischenzeitbeziehungen {
	
	private Spur einfahrend;
	private Spur ausfahrend;
	
	public Spur getEinfahrend() {
		return einfahrend;
	}
	public void setEinfahrend(Spur einfahrend) {
		this.einfahrend = einfahrend;
	}
	public Spur getAusfahrend() {
		return ausfahrend;
	}
	public void setAusfahrend(Spur ausfahrend) {
		this.ausfahrend = ausfahrend;
	}
	private int verriegelung;
	private int zwischenzeit;
	
	
	
	
	public int getVerriegelung() {
		return verriegelung;
	}
	public void setVerriegelung(int verriegelung) {
		this.verriegelung = verriegelung;
	}
	public int getZwischenzeit() {
		return zwischenzeit;
	}
	public void setZwischenzeit(int zwischenzeit) {
		this.zwischenzeit = zwischenzeit;
	}
	
}
