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

import java.util.LinkedList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
	int nummer; //zur Identifizierung der Zufahrt
	Kreuzung kr;
	Pane pane = new Pane();
	public TableView<Signalgeber> table = new TableView<Signalgeber>();	//Anzeige der Signalgeber je Zufahrt
	public final ObservableList<Signalgeber> Signalgeberlist = FXCollections.observableArrayList();	//Array für Table
	LinkedList<Signalgeber> signal_geber = new LinkedList<Signalgeber>(); //Speichert alle Signalgeber einer Zufahrt
    VBox v = new VBox();
	
	public Zufahrt(Kreuzung k, Pane p, VBox v) {
		this.pane=p;	//Pane in der die Table erzeugt wird
		this.nummer=k.anz_Zufahrt()+1;	
		this.kr=k;
		this.v=v;	//VBox in der die Signalgeber erzeugt werden
		k.putZufahrt(this);	//Zufahrt zur Kreuzung hinzufügen
	}
	public Signalgeber return_Signalgeber(int id) {
		return signal_geber.get(id);
	}
	public int getNummer() {
		return nummer;
	}
	//Signalgeber zur Zufahrt hinzufügen
	public void putSignalgeber (Signalgeber sg) {
		signal_geber.add(sg);
		kr.putSignalgeberInList(sg);
	}
	public int get_anzahl_signalgeber()	//in dieser Zufahrt
	{
		return signal_geber.size();
	}
	public int erzeugeSignalgeber(int kat)	//Eingabewert Kategorie des Signalgebers
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
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					//----------------------------
					TextInputDialog dialog2 = new TextInputDialog("2000");
					dialog2.setTitle("Abfrage der Sättigungsverkehrsstärke");
					dialog2.setHeaderText("Bitte geben Sie die Sättiungsungsverkehrsstärke dieses Signalgeber an! [Fzg/h]");
					dialog2.setContentText("qs=");

					try {
						Optional<String> result2 = dialog2.showAndWait();
						if (result2.isPresent()) {
							MainWindowController.getS()[kr.get_signalgeberlist().size()]=new Signalgeber(kr, this, kat, kr.get_signalgeberlist().size(),Float.parseFloat(result.get()),Float.parseFloat(result2.get()),kr.getF1(),kr.getF2());
						}
					} catch (Exception e) {
					}
				} 
			} catch (Exception e) {
			}
			updateTable();
			check=1;
		}
		else
		{
			System.out.println("Signalgeber konnte nicht hinzugefügt werden!");
			check=0;
		}	
		return check;
	}
	
	@SuppressWarnings("unchecked")
	private void updateTable() {
		table.getColumns().clear();
		table.setEditable(true);
		TableColumn<Signalgeber, String> BezeichnungCol = new TableColumn<Signalgeber, String>("Bez.");
		BezeichnungCol.setMinWidth(10);
		BezeichnungCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,String>("Bezeichnung"));
		//SumoID
		TableColumn<Signalgeber, Integer> sumoidCol = new TableColumn<Signalgeber, Integer>("SID");
		sumoidCol.setMinWidth(10);
		sumoidCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,Integer>("sumoid"));
		//Änderungen zurückschreiben
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
		//Änderungen zurückschreiben
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
	    //qS
	  	TableColumn<Signalgeber, Float> qsCol = new TableColumn<Signalgeber, Float>("qs [Fzg/h]");
	  	qsCol.setMinWidth(10);
	  	qsCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,Float>("qs"));
	  	//Änderungen zurückschreiben
	  	qsCol.setCellFactory(TextFieldTableCell.<Signalgeber, Float>forTableColumn(new FloatStringConverter()));
	    qsCol.setOnEditCommit(
			    new EventHandler<CellEditEvent<Signalgeber, Float>>() {
			        public void handle(CellEditEvent<Signalgeber, Float> s) {
			            ((Signalgeber) s.getTableView().getItems().get(
			                s.getTablePosition().getRow())
			                ).setQS(s.getNewValue());
			        }
			    }
			);
		//tfStunde
		TableColumn<Signalgeber, Float> tfStundeCol = new TableColumn<Signalgeber, Float>("tF [s/h]");
		tfStundeCol.setMinWidth(10);
		tfStundeCol.setCellValueFactory(new PropertyValueFactory<Signalgeber,Float>("tfStunde"));
		//tableErstellen
		table.setItems(Signalgeberlist);
	    table.getColumns().addAll(BezeichnungCol, sumoidCol, qCol, qsCol, tfStundeCol);
        v.getChildren().clear();
        v.getChildren().addAll(table);
	}
}