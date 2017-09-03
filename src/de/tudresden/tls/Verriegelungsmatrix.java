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
		
		//--- Array ungerade ---	-->Signalgeber liegen sich gegenüber
		array_ungerade[0][0]=0;
		array_ungerade[1][1]=0;
		array_ungerade[2][2]=0;
		array_ungerade[0][1]=0;
		array_ungerade[1][0]=0;
		
		//--- Array gerade --- 		-->Signalgeber sind im rechten Winkel zueinander
		
		array_gerade[1][1]=0;
		array_gerade[1][0]=0;
		array_gerade[1][2]=0;
		array_gerade[2][1]=0;
	}
	int pruef_verriegelung(int id_sg1, int id_sg2, int kat_sg1, int kat_sg2) {
		int verriegelung=1;	//Signalgeber müssen gegeneinander verriegelt werden
		if (id_sg1==id_sg2)
		{
			verriegelung=9;	//Wenn gleicher Signalgeber, dann Verriegelung 9
		}
		else if ((id_sg1+id_sg2)%2>0)
		{
			if (array_gerade[kat_sg1][kat_sg2]==3)
			{
				if (array_gerade[kat_sg2][kat_sg1]==3)
				{
					verriegelung=1;
				}
				else
				{
					verriegelung=array_gerade[kat_sg2][kat_sg1];
				}
			}
			else
			{
				verriegelung=array_gerade[kat_sg1][kat_sg2];
			}
		}
		else if ((id_sg1+id_sg2)%2==0)
		{
			if (array_ungerade[kat_sg1][kat_sg2]==3)
			{
				if (array_ungerade[kat_sg2][kat_sg1]==3)
				{
					verriegelung=1;
				}
				else
				{
					verriegelung=array_ungerade[kat_sg2][kat_sg1];
				}
			}
			else
			{
				verriegelung=array_ungerade[kat_sg1][kat_sg2];
			}
		}
		
		return verriegelung;
	}
	public void create_matrix(Kreuzung kr){
		LinkedList <Signalgeber> ll = kr.get_signalgeberlist();
		int s=ll.size();
		int rowCount = s;
        int columnCount = s;
        anz_zzb=s*s;
        vr_array = new Zwischenzeitbeziehungen[s][s];
        int x=0;	//Laufvariable für Zwischenzeitbeziehungen
        //Nur wenn noch keine Zwischenzeitbeziehungen existieren die Objekte anlegen
        if ((zzb == null)||(zzb.length!=(s*s)))  {
        	zzb = new Zwischenzeitbeziehungen[s*s];
        	System.out.println("Neu");
        }
        grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        ObservableList<String> rowsHeaders = FXCollections.observableArrayList();
        ObservableList<String> columnsHeaders = FXCollections.observableArrayList();
        //Äußere Schleife über alle Signalgeber
        for (int i=0;i<s;i++) {
        	final ObservableList<SpreadsheetCell> data = FXCollections.observableArrayList();
			rowsHeaders.add(ll.get(i).getBezeichnung());
			columnsHeaders.add(ll.get(i).getBezeichnung());
			//Innere Schleife über alle Signalgeber
        	for (int j=0;j<s;j++) {
        		int pruef=0;
        		SpreadsheetCell cell;
        		//wenn das Objekt der Zwischenzeitbeziehungen noch nicht existiert, dann anlegen und Verriegelung prüfen sowie Einträge in zzb tätigen
        		if (zzb[x]==null) {
        			pruef=pruef_verriegelung(ll.get(i).getEigene_zufahrt().getNummer(), ll.get(j).getEigene_zufahrt().getNummer(), ll.get(i).getTyp(), ll.get(j).getTyp());
        			cell = SpreadsheetCellType.INTEGER.createCell(i, j, 1, 1, pruef); 		 
					zzb[x] = new Zwischenzeitbeziehungen();
					zzb[x].setVerriegelung(pruef);
					zzb[x].setEinfahrend(ll.get(i));
					zzb[x].setAusfahrend(ll.get(j));
				}
        		//sonst nur die Verriegelung aus der Zwischenzeitbeziehung auslesen
        		else {
        			cell = SpreadsheetCellType.INTEGER.createCell(i, j, 1, 1, zzb[x].getVerriegelung()); 		 
				}
        		//alles in ein Array zur Verriegelung schreiben, was dann in den Zwischenzeiten ausgelesen werden kann
				vr_array[i][j]=zzb[x];
				cell.setEditable(true);
				data.add(cell);
				x++;
        	}
        	rows.add(data);
        }
        grid.setRows(rows);
        setGrid(grid);
        grid.getRowHeaders().addAll(rowsHeaders);
	    grid.getColumnHeaders().addAll(columnsHeaders);

	    //Alle Spalten auf Breite 50 setzen
        for (int i=0;i<getColumns().size();i++) {
        getColumns().get(i).setPrefWidth(50);
        }
	}
	public void SaveChanges(Kreuzung kr) {
		int x=0;
		//Schleife über alle Zeilen
		for (int i=0;i<grid.getRowCount();i++) {
			//Schleife über alle Zellen der Zeile
			for (int j=0;j<grid.getColumnCount();j++) {
				zzb[x].setVerriegelung(Integer.parseInt(grid.getRows().get(i).get(j).getText()));
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

