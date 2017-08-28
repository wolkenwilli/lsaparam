package de.tudresden.tls;

import java.util.HashMap;

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
		HashMap <Zufahrt, Signalgeber> hm = kr.getAlleSignalgeber();
		int s=hm.size();
		this.anzahl_signalgeber=s;
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
	public void create_festzeitplan(Kreuzung kr, Phase[] p, int anz_phasen, Verriegelungsmatrix vm, Zwischenzeiten zz) 
	{
		int rowCount_fs = anzahl_signalgeber;
        int columnCount_fs = anz_phasen*4;
        
        
        GridBase grid_fs = new GridBase(rowCount_fs, columnCount_fs);
        ObservableList<ObservableList<SpreadsheetCell>> rows_fs = FXCollections.observableArrayList();
        ObservableList<String> rowsHeaders_fs = FXCollections.observableArrayList();
        ObservableList<String> columnsHeaders_fs = FXCollections.observableArrayList();
        SpreadsheetCell[] cell = new SpreadsheetCell [columnCount_fs];
		int index=0;
		for (int i=0; i<anz_phasen;i++) {
			Phase aphase=p[i];
			Phase nphase;
			if (anz_phasen-i>1) {
				nphase=p[i+1];	
			}
			else {
				nphase=p[0];
			}
			int max_zwischen=-998;
			int gzv = 0;
			for (int j=0;j<aphase.getSignalgeber().size();j++) {
				gzv=0;
				final ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
				rowsHeaders_fs.add(p[i].getSignalgeber().get(j).getBezeichnung());
				// Maximale Zwischenzeit berechnen
				System.out.println("___________________________________________________________________________");
				for (int k=0; k<nphase.getSignalgeber().size();k++) {
					int zwischen=zz.get_zwischenzeit(aphase.getSignalgeber().get(j), nphase.getSignalgeber().get(k), kr, vm);
					if (max_zwischen<zwischen) {
						max_zwischen=zwischen;	
					}
				}
				System.out.println(max_zwischen);
				// ----
				cell[index]=SpreadsheetCellType.INTEGER.createCell(j, 0, 0, 0, (int) p[i].getSignalgeber().get(j).getTfUmlauf()+gzv);		//Grünzeit
				cell[index+1]=SpreadsheetCellType.INTEGER.createCell(j, 0, 0, 0, (int) kr.getT_gelb());		//Gelbzeit
				cell[index+2]=SpreadsheetCellType.INTEGER.createCell(j, 0, 0, 0, (int) max_zwischen);		//Rotzeit	TODO Rotzeit auslesen
				cell[index+3]= SpreadsheetCellType.INTEGER.createCell(j, 0, 0, 0, (int) kr.getT_rot_gelb());		//Rotzeit	TODO Rotzeit auslesen
				
				for (int l=index; l<5;l++) {
					Row.add(cell[l]);	
				}
				rows_fs.add(Row);						
				
				
				
			}
			columnsHeaders_fs.add("g");
			columnsHeaders_fs.add("ge");
			columnsHeaders_fs.add("r");
			columnsHeaders_fs.add("rg");
	        grid_fs.setRows(rows_fs);
	     //   setGrid(grid_fs);
	        grid_fs.getRowHeaders().addAll(rowsHeaders_fs);
		    grid_fs.getColumnHeaders().addAll(columnsHeaders_fs);

	        //getFixedRows().add(0);
		}
		
	}
		
		
}


