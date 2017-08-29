package de.tudresden.tls;

import java.util.HashMap;
import java.util.LinkedList;

import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Zwischenzeiten extends SpreadsheetView {

	GridBase grid;	
	
	public Zwischenzeiten() {
		
	}
	
	
	public void pruef_zz(Kreuzung kr, Verriegelungsmatrix vm){
		Zwischenzeitbeziehungen[][] vr_matrix= vm.getVr_array();
		LinkedList <Signalgeber> ll = kr.get_signalgeberlist();
		int s=ll.size();
		int rowCount = s;
        int columnCount = s+1;
          
        grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        ObservableList<String> rowsHeaders = FXCollections.observableArrayList();
        ObservableList<String> columnsHeaders = FXCollections.observableArrayList();
        
        for (int l=0;l<ll.size();l++) {
        	rowsHeaders.add(ll.get(l).getBezeichnung());
			columnsHeaders.add(ll.get(l).getBezeichnung());
        }
        
        
        for(int i=0;i<vr_matrix.length;i++) 
		{
			final ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
			for(int j=0;j<vr_matrix.length;j++){
				SpreadsheetCell cell;
		        if ((vr_matrix[i][j].getVerriegelung()==9)||(vr_matrix[i][j].getVerriegelung()==0)) {
		        	cell = SpreadsheetCellType.STRING.createCell(j, 1, 1, 0, "X");
		        	vr_matrix[i][j].setZwischenzeit(0);			//Beispielwert
		        	cell.setEditable(false);	
		        }
		        else {
		        	cell = SpreadsheetCellType.STRING.createCell(j, 1, 1, 0, "10");	//Beispielwert
		        	vr_matrix[i][j].setZwischenzeit(10);			//Beispielwert
		        	cell.setEditable(true);
		        }
		        	// DEBUG: System.out.println("U Signalgeber A: "+vr_matrix[i][j].getEinfahrend()+" Signalgeber B: "+vr_matrix[i][j].getAusfahrend()+" ZZ: "+vr_matrix[i][j].getZwischenzeit());
		 		Row.add(cell);
		 		System.out.println("i: "+i+" j: "+j+" objekt: "+vr_matrix[i][j].getEinfahrend());
				rowsHeaders.add(vr_matrix[i][j].getEinfahrend().getBezeichnung());
				columnsHeaders.add(vr_matrix[i][j].getEinfahrend().getBezeichnung());
			}
			rows.add(Row);
		}
	    grid.setRows(rows);
   	    setGrid(grid);
        grid.getRowHeaders().addAll(rowsHeaders);
	    grid.getColumnHeaders().addAll(columnsHeaders);
        getFixedRows().add(0);
        getColumns().get(0).setFixed(true);
    	
	}
	public int get_zwischenzeit(Signalgeber a, Signalgeber b, Kreuzung kr, Verriegelungsmatrix vm) {
		System.out.println("Neue SG Abfrage id: "+Math.random()+" Signalgeber A: "+a.getBezeichnung()+" Signalgeber B: "+b.getBezeichnung());
		int zwischenzeit=999;
		Zwischenzeitbeziehungen[] zzb=vm.getZzb();
		for (int x=0; x<vm.getAnz_zzb();x++) {
			if (zzb[x].getEinfahrend().equals(a)) {
				if (zzb[x].getAusfahrend().equals(b)){
					zwischenzeit=zzb[x].getZwischenzeit();
					System.out.println("Zwischenzeit: "+zwischenzeit);
				}
			}
			if (zzb[x].getEinfahrend().equals(b)) {
				if (zzb[x].getAusfahrend().equals(a)){
					zwischenzeit=zzb[x].getZwischenzeit();
					System.out.println("Zwischenzeit: "+zwischenzeit);
				}
			}
			
		}
		return zwischenzeit;
	}


}
