package application;

import java.util.LinkedList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class Zufahrt {
	int nummer;
	Kreuzung kr;
	Pane pane = new Pane();
	public TableView<Spur> table = new TableView<Spur>();
	public final ObservableList<Spur> spurlist = FXCollections.observableArrayList();
	LinkedList<Spur> spuren = new LinkedList<Spur>();
    VBox v = new VBox();
	
	
	public Zufahrt(Kreuzung k, Pane p, VBox v) {
		this.pane=p;
		this.nummer=k.anz_Zufahrt()+1;
		this.kr=k;
		this.v=v;
		k.putZufahrt(this);
	}
	public Spur return_spur(int id) {
		return spuren.get(id);
	}
	

	public int getNummer() {
		return nummer;
	}

	public void putSpur (Spur sp) {
		spuren.add(sp);
	}
	public int get_anzahl_spuren()
	{
		return spuren.size();
	}
	public int erzeugeSpur(int kat)
	{
		int check=0;
		if (kr.checkspur(this, kat)==1)
		{
			//----------------------------
			TextInputDialog dialog = new TextInputDialog("600");
			dialog.setTitle("Abfrage der Verkehrsstärke");
			dialog.setHeaderText("Bitte geben Sie die Verkehrsstärke dieser Spur an! [Fzg/h]");
			dialog.setContentText("q=");

			try {
				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					MainWindowController.s[kr.get_anz_spuren()]=new Spur(kr, this, kat, kr.get_anz_spuren(),Float.parseFloat(result.get()));
				} 
			} catch (Exception e) {
				// TODO: handle exception
			}
			updateTable();
			System.out.println("Spur mit Kategorie "+kat+" wurde erfolgreich hinzugefügt.");
			check=1;
		}
		else
		{
			System.out.println("Spur konnte nicht hinzugefügt werden!");
			check=0;
		}	
		return check;
	}
	private void updateTable() {
		table.getColumns().clear();
		table.setEditable(true);
		//ID
		TableColumn<Spur, Integer> idCol = new TableColumn<Spur, Integer>("ID");
		idCol.setMinWidth(10);
		idCol.setCellValueFactory(new PropertyValueFactory<Spur,Integer>("id"));
		//Bild Typ
//		TableColumn<Spur, ImageView> imageCol = new TableColumn<Spur, ImageView>("Typ2");
//		imageCol.setMinWidth(10);
//		imageCol.setMaxWidth(50);
//		imageCol.setCellValueFactory(new PropertyValueFactory<Spur,ImageView>("view"));
		//Typ
		TableColumn<Spur, Integer> typCol = new TableColumn<Spur, Integer>("Typ");
		typCol.setMinWidth(10);
		typCol.setCellValueFactory(new PropertyValueFactory<Spur,Integer>("typ"));
		//SumoID
		TableColumn<Spur, Integer> sumoidCol = new TableColumn<Spur, Integer>("Sumo-ID");
		sumoidCol.setMinWidth(10);
		sumoidCol.setCellValueFactory(new PropertyValueFactory<Spur,Integer>("sumoid"));
	    sumoidCol.setCellFactory(TextFieldTableCell.<Spur, Integer>forTableColumn(new IntegerStringConverter()));
	    sumoidCol.setOnEditCommit(
			    new EventHandler<CellEditEvent<Spur, Integer>>() {
			        public void handle(CellEditEvent<Spur, Integer> t) {
			            ((Spur) t.getTableView().getItems().get(
			                t.getTablePosition().getRow())
			                ).setSumoid(t.getNewValue());
			        }
			    }
			);
		//q
		TableColumn<Spur, Float> qCol = new TableColumn<Spur, Float>("q [Fzg/h]");
		qCol.setCellValueFactory(new PropertyValueFactory<Spur,Float>("q"));
		qCol.setMinWidth(10);
		qCol.setCellFactory(TextFieldTableCell.<Spur, Float>forTableColumn(new FloatStringConverter()));
	    qCol.setOnEditCommit(
			    new EventHandler<CellEditEvent<Spur, Float>>() {
			        public void handle(CellEditEvent<Spur, Float> t) {
			            ((Spur) t.getTableView().getItems().get(
			                t.getTablePosition().getRow())
			                ).setQ(t.getNewValue());
			        }
			    }
			);
	    
		//erftf
		TableColumn<Spur, Float> erftfCol = new TableColumn<Spur, Float>("tF,erf [s]");
		erftfCol.setMinWidth(10);
		erftfCol.setCellValueFactory(new PropertyValueFactory<Spur,Float>("erftf"));
	    table.setItems(spurlist);
	    //table.getColumns().addAll(idCol, imageCol, sumoidCol, qCol, erftfCol); 	//mit Bild
	    table.getColumns().addAll(idCol, typCol, sumoidCol, qCol, erftfCol);
        v.setSpacing(5);
        v.setPadding(new Insets(10, 0, 0, 10));
        v.getChildren().clear();
        v.getChildren().addAll(table);
	}

	public void calc_erftf() {
		
		for (int i=0;i<spuren.size();i++)
		{
			spuren.get(i).calc_erftf(kr.getCalc_qsn());
		}
		updateTable();
	}
}