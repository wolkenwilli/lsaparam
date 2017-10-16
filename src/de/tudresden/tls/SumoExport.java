package de.tudresden.tls;

public class SumoExport {
	private int duration=0;
	private String sumostring;

	public SumoExport(int duration, String sumostring)  {
		this.duration=duration;
		this.sumostring=sumostring;
	}
	public int getDuration() {
		return duration;
	}
	public String getSumoString() {
		return sumostring;
	}
}
