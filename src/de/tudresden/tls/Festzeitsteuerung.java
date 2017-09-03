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

import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Festzeitsteuerung extends SpreadsheetView {
	public void create_festzeitplan(Kreuzung kr, Phase[] p, int anz_phasen, Verriegelungsmatrix vm, Zwischenzeiten zz) 
	{
		int rowCount_fs = kr.get_signalgeberlist().size();
        int columnCount_fs = anz_phasen*4;
       
        GridBase grid_fs = new GridBase(rowCount_fs, columnCount_fs);
        ObservableList<ObservableList<SpreadsheetCell>> rows_fs = FXCollections.observableArrayList();
        ObservableList<String> rowsHeaders_fs = FXCollections.observableArrayList();
        ObservableList<String> columnsHeaders_fs = FXCollections.observableArrayList();
        SpreadsheetCell[] cell = new SpreadsheetCell [columnCount_fs];
		int index=0;
		//Schleife über alle Phasen
		for (int i=0; i<anz_phasen;i++) {
			//aktuelle Phase
			Phase aphase=p[i];
			Phase nphase;
			int phase_zwischen=-100;	//maximale Zwischenzeit aller Signalgeber einer Phase
			int phase_min_gruen=-100;
			if (anz_phasen-i>1) {
				nphase=p[i+1];	
			}
			//wenn höchste Phase erreicht, dann wird nächste Phase die 1. Phase
			else {
				nphase=p[0];
			}
			int sg_max_zwischen=-998;	//maximale Zwischenzeit je Signalgeber
			int gzv = 0;				//Grünzeitverlängerung, kommt zum Tragen, wenn Signalgeber einer Phase eine kürzere Zwischenzeit haben, als andere
			//Doppelter Durchlauf, da im 1. Schritt die maximale Grünzeit einer Phase bestimmt werden muss, im 2. Schritt dann als Grünzeitverlängerung gewährt und Ausgabe
			for (int m=0;m<2;m++) {
				//Schleife über alle Signalgeber der Phase
				for (int j=0;j<aphase.getSignalgeber().size();j++) {
					gzv=0;
					ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
					// Maximale Zwischenzeit des Signalgebers zu Signalgebern der nächsten Phase berechnen
						//Dazu Schleife über alle Signalgeber der nächsten Phase
						for (int k=0; k<nphase.getSignalgeber().size();k++) {
							int zwischen=zz.get_zwischenzeit(aphase.getSignalgeber().get(j), nphase.getSignalgeber().get(k), kr, vm);
							//Abgleich, ob sich die Zwischenzeit erhöht hat
							if (sg_max_zwischen<zwischen) {
								sg_max_zwischen=zwischen;	
							}
						}
						//Minimale Grünzeit der Phase ermitteln
						if (phase_min_gruen<p[i].getSignalgeber().get(j).getTfUmlauf()) {
							phase_min_gruen=(int) p[i].getSignalgeber().get(j).getTfUmlauf();
						}
						//Speichern, um die Maximale Zwischenzeit der Phase zu ermitteln!
						if (phase_zwischen<sg_max_zwischen) {
							phase_zwischen=sg_max_zwischen;
						}
						//Grünzeitverlängerung ist die Maximale Zwischenzeit der Phase minus Zwischenzeit des derzeitigen Signalgebers
						gzv=phase_zwischen-sg_max_zwischen;
					//beim 2. Durchlauf Ausgabe
					if (m==1) {
						rowsHeaders_fs.add(p[i].getSignalgeber().get(j).getBezeichnung());
						//wenn es nicht die erste Phase ist, leere Zellen erstellen
						if (index > 0) {
							for (int z=0;z<index;z++) {
							cell[z]=SpreadsheetCellType.STRING.createCell(j, z, 1, 1, "");
							}
						}
						//Zellen mit Werten erstellen
						cell[index]=SpreadsheetCellType.INTEGER.createCell(j, index, 1, 1, (int) phase_min_gruen+gzv);		//Grünzeit
						cell[index+1]=SpreadsheetCellType.INTEGER.createCell(j, index+1, 1, 1, (int) kr.getT_gelb());		//Gelbzeit
						cell[index+2]=SpreadsheetCellType.INTEGER.createCell(j, index+2, 1, 1, (int) sg_max_zwischen);		//Rotzeit	TODO Rotzeit auslesen
						cell[index+3]= SpreadsheetCellType.INTEGER.createCell(j, index+3, 1, 1, (int) kr.getT_rot_gelb());		//Rotzeit	TODO Rotzeit auslesen
						//wenn es nicht die letzte Phase ist, mit leeren Zellen füllen
						if ((anz_phasen*4)-index>=4) {
							for (int z=index+4;z<(anz_phasen*4);z++) {
							cell[z]=SpreadsheetCellType.STRING.createCell(j, z, 1, 1, "");
							}
						}
						//alle Zellen der Zeile hinzufügen
						for (int l=0; l<(anz_phasen*4);l++) {
							Row.add(cell[l]);	
						}
						rows_fs.add(Row);
					}
					//Maximale Zwischenzeit der Signalgeber zurücksetzen
					sg_max_zwischen=-998;
				}
			}
			//Index für die nächste Phase erhöhen
			index=index+4;
			//Spaltentitel setzen (g-grün, ge-gelb, r-rot, r-ge - rot-gelb)
			columnsHeaders_fs.add("g");
			columnsHeaders_fs.add("ge");
			columnsHeaders_fs.add("r");
			columnsHeaders_fs.add("r-ge");
		}
		grid_fs.setRows(rows_fs);
	    setGrid(grid_fs);
	    grid_fs.getRowHeaders().addAll(rowsHeaders_fs);
		grid_fs.getColumnHeaders().addAll(columnsHeaders_fs);
		//Breite 50 für alle Spalten
        for (int i=0;i<getColumns().size();i++) {
            getColumns().get(i).setPrefWidth(50);
        }
	}
}
