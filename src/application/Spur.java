package application;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Spur {
	private final SimpleIntegerProperty id;
	private final SimpleIntegerProperty typ;
	private final SimpleIntegerProperty sumoid;
	private final SimpleFloatProperty q;
	private final SimpleFloatProperty erftf;
	
	public ImageView getView() {
		return view;
	}


	public void setView(ImageView view) {
		this.view = view;
	}

	ImageView view = new ImageView();
	
	Image p0 = new Image("@../../img/0.png");
	Image p1 = new Image("@../../img/1.png");
	Image p2 = new Image("@../../img/2.png");
	Image p3 = new Image("@../../img/3.png");
	Image p4 = new Image("@../../img/4.png");
	Image p5 = new Image("@../../img/5.png");
	Image p6 = new Image("@../../img/6.png");
	
		
	public Spur (Zufahrt zf, int kat, int id, float q)
	{
		//Werteinitalisierung
		this.id = new SimpleIntegerProperty(id);
		this.typ = new SimpleIntegerProperty(kat);
		this.sumoid = new SimpleIntegerProperty(0);
		this.q=new SimpleFloatProperty(q);
		this.erftf=new SimpleFloatProperty(0.0f);
		zf.putSpur(this);	//Spur der Zufahrt hinzufügen
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
		zf.spurlist.add(this);
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
	public void calc_erftf(float qsn) {
		erftf.set(q.get()*(3600/qsn));
	}
	
	public float getErftf() {
		return erftf.get();
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
}
