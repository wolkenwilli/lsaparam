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

import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Zwischenzeiten extends SpreadsheetView {

	GridBase grid;
	Zwischenzeitbeziehungen[][] vr_matrix;
	
	public void pruef_zz(Kreuzung kr, Verriegelungsmatrix vm){
		vr_matrix= vm.getVr_array();
		LinkedList <Signalgeber> ll = kr.get_signalgeberlist();
		int rowCount = ll.size();
        int columnCount = ll.size()+1;
          
        grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        ObservableList<String> rowsHeaders = FXCollections.observableArrayList();
        ObservableList<String> columnsHeaders = FXCollections.observableArrayList();
        
        //Erzeugung des Tabellenkopfs
        for (int l=0;l<ll.size();l++) {
        	rowsHeaders.add(ll.get(l).getBezeichnung());
			columnsHeaders.add(ll.get(l).getBezeichnung());
        }
        
        //Äußere Schleife über alle Verriegelungen
        for(int i=0;i<vr_matrix.length;i++) 
		{
			final ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
			//Innere Schleife über alle Verriegelungen
			for(int j=0;j<vr_matrix.length;j++){
				SpreadsheetCell cell;
				//Falls eigener Signalgeber -> keine Zwischenzeit erforderlich!
		        if ((vr_matrix[i][j].getVerriegelung()==9)||(vr_matrix[i][j].getVerriegelung()==0)) {
		        	cell = SpreadsheetCellType.STRING.createCell(i, j, 1, 1, "X");
		        	vr_matrix[i][j].setZwischenzeit(0);
		        	cell.setEditable(false);	
		        }
		        //sonst Bestimmung der Zwischenzeit
		        else {
		        	//wenn bereits eine Zwischenzeit hinterlegt ist, diese laden
		        	if (vr_matrix[i][j].getZwischenzeit()>=0) {
		        		cell = SpreadsheetCellType.STRING.createCell(i, j, 1, 1, Integer.toString(vr_matrix[i][j].getZwischenzeit()));	
		        	}
		        	//sonst Aufforderung zum Wert eingeben
		        	else {
		        		cell = SpreadsheetCellType.STRING.createCell(i, j, 1, 1, "Wert");
		        	}
		        	cell.setEditable(true);
		        }
		        //Zellen der Zeile hinzufügen
		 		Row.add(cell);
			}
			//Zeile der Matrix hinzufügen
			rows.add(Row);
		}
	    grid.setRows(rows);
   	    setGrid(grid);
        grid.getRowHeaders().addAll(rowsHeaders);
	    grid.getColumnHeaders().addAll(columnsHeaders);
        //PrefWidth für alle Spalten setzen
	    for (int i=0;i<getColumns().size();i++) {
            getColumns().get(i).setPrefWidth(50);
            }
    	
	}
	public int get_zwischenzeit(Signalgeber a, Signalgeber b, Kreuzung kr, Verriegelungsmatrix vm) {
		//System.out.println("DEBUG: Neue SG Abfrage id: "+Math.random()+" Signalgeber A: "+a.getBezeichnung()+" Signalgeber B: "+b.getBezeichnung());
		int zwischenzeit=999;	//Maximale Zwischenzeit initalisieren
		Zwischenzeitbeziehungen[] zzb=vm.getZzb();
		//Schleife über alle Zwischenzeitbedingungen um zu überprüfen, ob Einfahrender mit Ausfahrendem Signalgeber übereinstimmt
		for (int x=0; x<vm.getAnz_zzb();x++) {
			if (zzb[x].getEinfahrend().equals(a)) {
				if (zzb[x].getAusfahrend().equals(b)){
					//falls Übereinstimmung besteht, Zwischenzeit auslesen
					zwischenzeit=zzb[x].getZwischenzeit();
				}
			}
		}
		return zwischenzeit;
	}


	public void SaveChanges(Kreuzung kr) {
		//Schleife über alle Zeilen
		for (int i=0;i<grid.getRowCount();i++) {
			//Schleife über alle Zellen einer Zeile
			for (int j=0;j<grid.getColumnCount();j++) {
				String s_calc=Integer.toString(vr_matrix[i][j].getZwischenzeit(),0); //auslesen der berechneten Zwischenzeit
				String s_grid=grid.getRows().get(i).get(j).getText();	//auslesen des Textes aus der Matrix
				if (s_calc.equals(s_grid)) {
					// DEBG: System.out.println("keine Veränderung!!");	
				}
				else if ((s_calc.equals("0"))&&(s_grid.equals("X"))) {
					// DEBUG: System.out.println("eigener SG (X) keine Veränderung!!");
				}
				else {
					// DEBUG: System.out.println("Veränderung festgestellt: "+grid.getRows().get(i).get(j).getText());
					//Wenn Veränderung festgestellt wurde, diese als Zwischenzeit in die Zwischenzeitbeziehungen zurück schreiben
					try {
						vr_matrix[i][j].setZwischenzeit(Integer.parseInt(grid.getRows().get(i).get(j).getText()));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
