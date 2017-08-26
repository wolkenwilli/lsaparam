package application;


public class Zwischenzeitbeziehungen {
	
	private Signalgeber einfahrend;
	private Signalgeber ausfahrend;
	
	public Signalgeber getEinfahrend() {
		return einfahrend;
	}
	public void setEinfahrend(Signalgeber einfahrend) {
		this.einfahrend = einfahrend;
	}
	public Signalgeber getAusfahrend() {
		return ausfahrend;
	}
	public void setAusfahrend(Signalgeber ausfahrend) {
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
