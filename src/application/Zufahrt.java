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
	public TableView<Signalgeber> table = new TableView<Signalgeber>();
	public final ObservableList<Signalgeber> Signalgeberlist = FXCollections.observableArrayList();
	LinkedList<Signalgeber> signal_geber = new LinkedList<Signalgeber>();
    VBox v = new VBox();
	
	
	public Zufahrt(Kreuzung k, Pane p, VBox v) {
		this.pane=p;
		this.nummer=k.anz_Zufahrt()+1;
		this.kr=k;
		this.v=v;
		k.putZufahrt(this);
	}
	public Signalgeber return_Signalgeber(int id) {
		return signal_geber.get(id);
	}
	

	public int getNummer() {
		return nummer;
	}

	public void putSignalgeber (Signalgeber sg) {
		signal_geber.add(sg);
	}
	public int get_anzahl_signalgeber()
	{
		return signal_geber.size();
	}
	public int erzeugeSignalgeber(int kat)
	{
		int check=0;
		if (kr.checksignalgeber(this, kat)==1)
		{
			//----------------------------
			TextInputDialog dialog = new TextInputDialog("600");
			dialog.setTitle("Abfrage der Verkehrsst�rke");
			dialog.setHeaderText("Bitte geben Sie die Verkehrsst�rke dieses Signalgeber an! [Fzg/h]");
			dialog.setContentText("q=");

			try {
				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					MainWindowController.s[kr.get_anz_Signalgeber()]=new Signalgeber(kr, this, kat, kr.get_anz_Signalgeber(),Float.parseFloat(result.get()));
				} 
			} catch (Exception e) {
				// TODO: handle exception
			}
			updateTable();
			//System.out.println("Signalgeber mit Kategorie "+kat+" wurde erfolgreich hinzugef�gt.");
			check=1;
		}
		else
		{
			System.out.println("Signalgeber konnte nicht hinzugef�gt werden!");
			check=0;
		}	
		return check;
	}
	
	private void updateTable() {
		table.getColumns().clear();
		table.setEditable(true);
		//ID
		TableColumn<Signalgeber, String> BezeichnungCol = new TableColumn<Signalgeber, String>("Bez.");
		BezeichnungCol.setMinWidth(10);
		BezeichnungCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,String>("Bezeichnung"));
		//Bild Typ
//		TableColumn<Signalgeber, ImageView> imageCol = new TableColumn<Signalgeber, ImageView>("Typ2");
//		imageCol.setMinWidth(10);
//		imageCol.setMaxWidth(50);
//		imageCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,ImageView>("view"));
		//SumoID
		TableColumn<Signalgeber, Integer> sumoidCol = new TableColumn<Signalgeber, Integer>("SumoID");
		sumoidCol.setMinWidth(10);
		sumoidCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,Integer>("sumoid"));
	    sumoidCol.setCellFactory(TextFieldTableCell.<Signalgeber, Integer>forTableColumn(new IntegerStringConverter()));
	    sumoidCol.setOnEditCommit(
			    new EventHandler<CellEditEvent<Signalgeber, Integer>>() {
			        public void handle(CellEditEvent<Signalgeber, Integer> t) {
			            ((Signalgeber) t.getTableView().getItems().get(
			                t.getTablePosition().getRow())
			                ).setSumoid(t.getNewValue());
			        }
			    }
			);
		//q
		TableColumn<Signalgeber, Float> qCol = new TableColumn<Signalgeber, Float>("q [Fzg/h]");
		qCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,Float>("q"));
		qCol.setMinWidth(10);
		qCol.setCellFactory(TextFieldTableCell.<Signalgeber, Float>forTableColumn(new FloatStringConverter()));
	    qCol.setOnEditCommit(
			    new EventHandler<CellEditEvent<Signalgeber, Float>>() {
			        public void handle(CellEditEvent<Signalgeber, Float> t) {
			            ((Signalgeber) t.getTableView().getItems().get(
			                t.getTablePosition().getRow())
			                ).setQ(t.getNewValue());
			        }
			    }
			);
	    
		//tfStunde
		TableColumn<Signalgeber, Float> tfStundeCol = new TableColumn<Signalgeber, Float>("tF [h]");
		tfStundeCol.setMinWidth(10);
		tfStundeCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,Float>("tfStunde"));
	    //qS
		TableColumn<Signalgeber, Float> qsCol = new TableColumn<Signalgeber, Float>("qs [Fzg/h]");
		qsCol.setMinWidth(10);
		qsCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,Float>("qs"));
	    
		
		table.setItems(Signalgeberlist);
	    //table.getColumns().addAll(idCol, imageCol, sumoidCol, qCol, erftfCol); 	//mit Bild
	    table.getColumns().addAll(BezeichnungCol, sumoidCol, qCol, qsCol, tfStundeCol);
        v.setSpacing(5);
        v.setPadding(new Insets(10, 0, 0, 10));
        v.getChildren().clear();
        v.getChildren().addAll(table);
        
//        table.addEventHandler(KeyboardDownRightHandler.onKeyReleased, arg1);
        
        
	}

	public void calc_erftf() {
		
		for (int i=0;i<signal_geber.size();i++)
		{
			signal_geber.get(i).calc_erftf(kr.getCalc_qsn());
		}
		updateTable();
	}
}