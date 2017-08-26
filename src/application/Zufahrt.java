package application;

import java.util.LinkedList;
import java.util.Optional;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;
import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardEndHandler;

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
import javafx.scene.input.MouseEvent;
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
			dialog.setTitle("Abfrage der Verkehrsstärke");
			dialog.setHeaderText("Bitte geben Sie die Verkehrsstärke dieses Signalgeber an! [Fzg/h]");
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
			//System.out.println("Signalgeber mit Kategorie "+kat+" wurde erfolgreich hinzugefügt.");
			check=1;
		}
		else
		{
			System.out.println("Signalgeber konnte nicht hinzugefügt werden!");
			check=0;
		}	
		return check;
	}
	
	private void updateTable() {
		table.getColumns().clear();
		table.setEditable(true);
		//ID
		TableColumn<Signalgeber, Integer> idCol = new TableColumn<Signalgeber, Integer>("ID");
		idCol.setMinWidth(10);
		idCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,Integer>("id"));
		//Bild Typ
//		TableColumn<Signalgeber, ImageView> imageCol = new TableColumn<Signalgeber, ImageView>("Typ2");
//		imageCol.setMinWidth(10);
//		imageCol.setMaxWidth(50);
//		imageCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,ImageView>("view"));
		//Typ
		TableColumn<Signalgeber, Integer> typCol = new TableColumn<Signalgeber, Integer>("Typ");
		typCol.setMinWidth(10);
		typCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,Integer>("typ"));
		//SumoID
		TableColumn<Signalgeber, Integer> sumoidCol = new TableColumn<Signalgeber, Integer>("Sumo-ID");
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
	    
		//erftf
		TableColumn<Signalgeber, Float> erftfCol = new TableColumn<Signalgeber, Float>("tF,erf [s]");
		erftfCol.setMinWidth(10);
		erftfCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,Float>("erftf"));
	    table.setItems(Signalgeberlist);
	    //table.getColumns().addAll(idCol, imageCol, sumoidCol, qCol, erftfCol); 	//mit Bild
	    table.getColumns().addAll(idCol, typCol, sumoidCol, qCol, erftfCol);
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