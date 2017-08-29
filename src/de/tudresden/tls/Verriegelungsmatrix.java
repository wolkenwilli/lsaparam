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

class Verriegelungsmatrix extends SpreadsheetView {
	private int[][] array_ungerade = new int[3][3];
	private int[][] array_gerade = new int[3][3];	
	private Zwischenzeitbeziehungen[] zzb;
	private Zwischenzeitbeziehungen[][] vr_array;
	private int anz_zzb;
	GridBase grid;

	public Verriegelungsmatrix()
	{
		//Initialisierung der Verriegelungsmatrix
		for (int i=0;i<3;i++) {
			for (int j=0;j<3;j++) {
				array_gerade[i][j]=3;
			}
		}
		for (int i=0;i<3;i++) {
			for (int j=0;j<3;j++) {
				array_ungerade[i][j]=3;
			}
		}
		
		//--- Array gerade ---
		//nicht verriegelt
		array_ungerade[0][0]=0;
		array_ungerade[1][1]=0;
		array_ungerade[2][2]=0;
		array_ungerade[0][1]=0;
		array_ungerade[1][0]=0;
		
		//--- Array Ungerade ---
		
		array_gerade[1][1]=0;
		array_gerade[1][2]=0;
		array_gerade[2][1]=0;
	}
	int pruef_verriegelung(int id_spur1, int id_spur2, int kat_spur1, int kat_spur2) {
		int verriegelung=2;
		if (id_spur1==id_spur2)
		{
			verriegelung=9;
		}
		else if ((id_spur1+id_spur2)%2>0)
		{
			if (array_gerade[kat_spur1][kat_spur2]==3)
			{
				if (array_gerade[kat_spur2][kat_spur1]==3)
				{
					verriegelung=2;
				}
				else
				{
					verriegelung=array_gerade[kat_spur2][kat_spur1];
				}
			}
			else
			{
				verriegelung=array_gerade[kat_spur1][kat_spur2];
			}
		}
		else if ((id_spur1+id_spur2)%2==0)
		{
			if (array_ungerade[kat_spur1][kat_spur2]==3)
			{
				if (array_ungerade[kat_spur2][kat_spur1]==3)
				{
					verriegelung=2;
				}
				else
				{
					verriegelung=array_ungerade[kat_spur2][kat_spur1];
				}
			}
			else
			{
				verriegelung=array_ungerade[kat_spur1][kat_spur2];
			}
		}
		
		return verriegelung;
	}
	public int[][] getArray_gerade() {
		return array_gerade;
	}
	public int[][] getArray_ungerade() {
		return array_ungerade;
	}
	
	public void create_matrix(Kreuzung kr){
		LinkedList <Signalgeber> ll = kr.get_signalgeberlist();
		int s=ll.size();
		int rowCount = s;
        int columnCount = s;
        vr_array = new Zwischenzeitbeziehungen[s][s];
        int x=0;
        if (zzb == null) {
        	zzb = new Zwischenzeitbeziehungen[s*s];
        }
        anz_zzb=s*s;
        
        grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        ObservableList<String> rowsHeaders = FXCollections.observableArrayList();
        ObservableList<String> columnsHeaders = FXCollections.observableArrayList();
        
        for (int i=0;i<ll.size();i++) {
        	final ObservableList<SpreadsheetCell> data = FXCollections.observableArrayList();
			rowsHeaders.add(ll.get(i).getBezeichnung());
			columnsHeaders.add(ll.get(i).getBezeichnung());
        	for (int j=0;j<ll.size();j++) {
        		int pruef=0;
        		SpreadsheetCell cell;
        		if (zzb[x]==null) {
        			pruef=pruef_verriegelung(ll.get(i).getEigene_zufahrt().getNummer(), ll.get(j).getEigene_zufahrt().getNummer(), ll.get(i).getTyp(), ll.get(j).getTyp());
        			cell = SpreadsheetCellType.INTEGER.createCell(i, j, 1, 1, pruef); 		 
					zzb[x] = new Zwischenzeitbeziehungen();
					zzb[x].setVerriegelung(pruef);
					zzb[x].setEinfahrend(ll.get(i));
					zzb[x].setAusfahrend(ll.get(j));
				}
        		else {
        			cell = SpreadsheetCellType.INTEGER.createCell(i, j, 1, 1, zzb[x].getVerriegelung()); 		 
				}
				vr_array[i][j]=zzb[x];
				cell.setEditable(true);
				data.add(cell);
				x++;
        	}
        	rows.add(data);
        	// DEBUG: System.out.println("Erzeugte Cols: "+data.size()+" geplante Cols: "+columnCount);
        	// DEBUG: System.out.println("Erzeugte Rows: "+rows.size()+" geplante RowCount: "+rowCount);
        }
        
        
        grid.setRows(rows);
        setGrid(grid);
        grid.getRowHeaders().addAll(rowsHeaders);
	    grid.getColumnHeaders().addAll(columnsHeaders);

        getFixedRows().add(0);
        
        for (int i=0;i<getColumns().size();i++) {
        getColumns().get(i).setPrefWidth(50);
        }
	}
	public void SaveChanges(Kreuzung kr) {
		int x=0;
		LinkedList <Signalgeber> ll = kr.get_signalgeberlist();
		for (int i=0;i<grid.getRowCount();i++) {
			for (int j=0;j<grid.getColumnCount();j++) {
				
				String s_calc=Integer.toString(pruef_verriegelung(ll.get(i).getEigene_zufahrt().getNummer(), ll.get(j).getEigene_zufahrt().getNummer(), ll.get(i).getTyp(), ll.get(j).getTyp()),0);
				String s_grid=grid.getRows().get(i).get(j).getText();
				if (s_calc.equals(s_grid)) {
					zzb[x].setVerriegelung(Integer.parseInt(grid.getRows().get(i).get(j).getText()));
				}
				else {
					zzb[x].setVerriegelung(Integer.parseInt(grid.getRows().get(i).get(j).getText()));
				}
				x++;
			}
		}
		create_matrix(kr);
	}
	public Zwischenzeitbeziehungen[] getZzb() {
		return zzb;
	}
	public Zwischenzeitbeziehungen[][] getVr_array() {
		return vr_array;
	}
	public int getAnz_zzb() {
		return anz_zzb;
	}
}

