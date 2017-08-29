package de.tudresden.tls;

import java.util.HashMap;
import java.util.LinkedList;

import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class Phasenplan extends SpreadsheetView {
	public TableView<Signalgeber> table = new TableView<Signalgeber>();
	public final ObservableList<Signalgeber> Phasenplanlist = FXCollections.observableArrayList();
	private int anzahl_signalgeber;
	
	public Phasenplan() {
	}
	
	public void create_fz_table(Kreuzung kr)  {
		LinkedList<Signalgeber> ll = kr.get_signalgeberlist();
		int s=ll.size();
		this.anzahl_signalgeber=s;
		int rowCount = s;
        int columnCount = s+1;
        
        GridBase grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        ObservableList<String> rowsHeaders = FXCollections.observableArrayList();
        ObservableList<String> columnsHeaders = FXCollections.observableArrayList("q [Fzg/h]", "qs [Fzg/h]", "g", "Tp [s]", "tf [s]");
        
        for (int i=0;i<ll.size();i++) {
        	final ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();

			rowsHeaders.add(ll.get(i).getBezeichnung());
       		SpreadsheetCell cell1 = SpreadsheetCellType.DOUBLE.createCell(i, 0, 0, 0, (double) ll.get(i).getQ() );
       		SpreadsheetCell cell2 = SpreadsheetCellType.DOUBLE.createCell(i, 0, 0, 0, (double) ll.get(i).getQs());
       		SpreadsheetCell cell3 = SpreadsheetCellType.STRING.createCell(i, 0, 0, 0, Double.toString(Math.round(ll.get(i).getG()*100)/100.0));
       		SpreadsheetCell cell4 = SpreadsheetCellType.DOUBLE.createCell(i, 0, 0, 0, Math.round(ll.get(i).getTp()*100)/100.0);
       		SpreadsheetCell cell5 = SpreadsheetCellType.DOUBLE.createCell(i, 0, 0, 0, (double) ll.get(i).getTfUmlauf());
			Row.add(cell1);
			Row.add(cell2);
			Row.add(cell3);
			Row.add(cell4);
			Row.add(cell5);
        	rows.add(Row);
        }
        grid.setRows(rows);
        setGrid(grid);
        grid.getRowHeaders().addAll(rowsHeaders);
	    grid.getColumnHeaders().addAll(columnsHeaders);

        getFixedRows().add(0);
	}
	
		
		
}


