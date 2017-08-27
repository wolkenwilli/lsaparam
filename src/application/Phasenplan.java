package application;

import java.util.HashMap;

import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class Phasenplan extends SpreadsheetView {
	public TableView<Signalgeber> table = new TableView<Signalgeber>();
	public final ObservableList<Signalgeber> Phasenplanlist = FXCollections.observableArrayList();
	
	public Phasenplan() {		
	}
	
	public void create_fz_table(Kreuzung kr)  {
		HashMap <Zufahrt, Signalgeber> hm = kr.getAlleSignalgeber();
		int s=hm.size();
		int rowCount = s;
        int columnCount = s+1;
        int i=0;
        
        GridBase grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        ObservableList<String> rowsHeaders = FXCollections.observableArrayList();
        ObservableList<String> columnsHeaders = FXCollections.observableArrayList("q [Fzg/h]", "qs [Fzg/h]", "g", "Tp [s]", "tf [s]");
        
        for (Zufahrt z1 : hm.keySet()) {
        	final ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
        	i=0;
			rowsHeaders.add(hm.get(z1).getBezeichnung());
       		SpreadsheetCell cell1 = SpreadsheetCellType.DOUBLE.createCell(i, 0, 0, 0, (double) hm.get(z1).getQ() );
       		SpreadsheetCell cell2 = SpreadsheetCellType.DOUBLE.createCell(i, 0, 0, 0, (double) hm.get(z1).getQs());
       		SpreadsheetCell cell3 = SpreadsheetCellType.DOUBLE.createCell(i, 0, 0, 0, Math.round(hm.get(z1).getG()*100)/100.0);
       		//System.out.println(Math.round(hm.get(z1).getG()*100)/100.0);	TODO: 0, anzeigen!
       		SpreadsheetCell cell4 = SpreadsheetCellType.DOUBLE.createCell(i, 0, 0, 0, Math.round(hm.get(z1).getTp()*100)/100.0);
       		//System.out.println(Math.round(hm.get(z1).getTp()*100)/100.0);	siehe oben.
       		SpreadsheetCell cell5 = SpreadsheetCellType.DOUBLE.createCell(i, 0, 0, 0, (double) hm.get(z1).getTfUmlauf());
			Row.add(cell1);
			Row.add(cell2);
			Row.add(cell3);
			Row.add(cell4);
			Row.add(cell5);
			i++;
        	rows.add(Row);
        }
        grid.setRows(rows);
        setGrid(grid);
        grid.getRowHeaders().addAll(rowsHeaders);
	    grid.getColumnHeaders().addAll(columnsHeaders);

        getFixedRows().add(0);
	}
	public void create_festzeitplan(Phase[] p, int anz_phasen) 
	{
		
		
	}
	
		
		
}


