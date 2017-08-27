package application;

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
	private final SimpleFloatProperty g;
	private final SimpleFloatProperty q;
	private final SimpleFloatProperty qs;
	private final SimpleFloatProperty tfStunde;
	private final SimpleIntegerProperty dauer;
	private float qsn;
	
	public ImageView getView() {
		return view;
	}


	public void setView(ImageView view) {
		this.view = view;
	}

	ImageView view = new ImageView();
	
	Image p0 = new Image("http://www.eventtechnik-schmidt.de/0.png");
	Image p1 = new Image("http://www.eventtechnik-schmidt.de/1.png");
	Image p2 = new Image("http://www.eventtechnik-schmidt.de/2.png");
	Image p3 = new Image("http://www.eventtechnik-schmidt.de/3.png");
	Image p4 = new Image("http://www.eventtechnik-schmidt.de/4.png");
	Image p5 = new Image("http://www.eventtechnik-schmidt.de/5.png");
	Image p6 = new Image("http://www.eventtechnik-schmidt.de/6.png");
	
		
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
		this.tfStunde=new SimpleFloatProperty(((q*3600)/this.qsn));
		this.dauer=new SimpleIntegerProperty(0);
		this.g=new SimpleFloatProperty(0.8f);
		zf.putSignalgeber(this);	//Spur der Zufahrt hinzufügen
		kr.putSignalgeber(zf, this);
		
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
		case 3:
			view.setImage(p3);
			break;
		case 4:
			view.setImage(p4);
			break;
		case 5:
			view.setImage(p5);
			break;
		case 6:
			view.setImage(p6);
			break;
		default:
			view.setImage(p1);
		}
		view.setFitWidth(23);
		view.setFitHeight(150);
		zf.pane.getChildren().addAll(view);
		zf.Signalgeberlist.add(this);
	}
	

	public Integer getDauer() {
		return dauer.get();
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
}
