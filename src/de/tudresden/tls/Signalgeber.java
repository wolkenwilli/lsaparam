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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Signalgeber {
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty bezeichnung;
	private final SimpleIntegerProperty typ;
	private final SimpleIntegerProperty sumoid;
	private final SimpleFloatProperty q;
	private final SimpleFloatProperty qs;
	private final SimpleFloatProperty tfStunde;
	private final SimpleFloatProperty tfUmlauf;
	private float qsn;
	private double g;
	private double tp;
	private Zufahrt eigene_zufahrt;
	private ImageView view = new ImageView();
	Image p0 = new Image(Main.class.getResourceAsStream("0.png"));
	Image p1 = new Image(Main.class.getResourceAsStream("1.png"));
	Image p2 = new Image(Main.class.getResourceAsStream("2.png"));
		
	public Signalgeber (Kreuzung kr, Zufahrt zf, int kat, int id, float q, float qs, float f1, float f2)
	{
		//Werteinitalisierung
		this.id = new SimpleIntegerProperty(id);
		this.bezeichnung=new SimpleStringProperty("K"+(id+1));
		this.typ = new SimpleIntegerProperty(kat);
		this.sumoid = new SimpleIntegerProperty(0);
		this.q=new SimpleFloatProperty(q);
		this.qs=new SimpleFloatProperty(qs);
		this.qsn=qs*f1*f2;
		this.tfUmlauf=new SimpleFloatProperty(0.0f);
		this.tfStunde=new SimpleFloatProperty(((q*3600)/this.qsn));
		this.eigene_zufahrt=zf;
		zf.putSignalgeber(this);	//Spur der Zufahrt hinzufügen
				
		switch (typ.get()) {
		case 0:
			view.setImage(p0);
			break;
		case 1:
			view.setImage(p1);
			break;
		case 2:
			view.setImage(p2);
			break;
		default:
			view.setImage(p0);
		}
		view.setFitWidth(25);
		view.setFitHeight(75);
		zf.pane.getChildren().addAll(view);
		zf.Signalgeberlist.add(this);
	}
	public Zufahrt getEigene_zufahrt() {
		return eigene_zufahrt;
	}
	public float getTfUmlauf() {
		return tfUmlauf.get();
	}
	public void calc_TfUmlauf(double g, double tp) {
		setG(g);
		setTp(tp);
		double tf=0.0d;
		tf=((q.get()*tp)/(qs.get()*g));
		tfUmlauf.set((int) Math.ceil(tf));
	}
	public String getBezeichnung() {
		return bezeichnung.get();
	}
	public int getTyp() {
		return typ.get();
	}
	public void set_typ (Integer t) {
		this.typ.set(t);
	}

	public void setQ(float q) {
		this.q.set(q);
	}
	public float getQ() {
		return q.get();
	}
	public void setQS(float qs) {
		this.qs.set(qs);
	}
	public Float getTfStunde() {
		return tfStunde.get();
	}
	public int getId() {
		return id.get();
	}
	public int getSumoid() {
		return sumoid.get();
	}
	public void setSumoid(int sid) {
		sumoid.set(sid);
	}
	public Float getQs() {
		return qs.get();
	}
	public void setG(double g) {
		this.g=g;
	}
	public void setTp(double tp) {
		this.tp=tp;
	}
	public double getG() {
		return g;
	}
	public double getTp() {
		return tp;
	}
	
}
