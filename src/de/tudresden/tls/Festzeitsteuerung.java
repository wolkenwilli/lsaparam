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
		for (int i=0; i<anz_phasen;i++) {
			Phase aphase=p[i];
			Phase nphase;
			int phase_zwischen=-100;
			if (anz_phasen-i>1) {
				nphase=p[i+1];	
			}
			else {
				nphase=p[0];
			}
			int sg_max_zwischen=-998;
			int gzv = 0;
			for (int m=0;m<2;m++) {
				for (int j=0;j<aphase.getSignalgeber().size();j++) {
					gzv=0;
					ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
					rowsHeaders_fs.add(p[i].getSignalgeber().get(j).getBezeichnung());
					// Maximale Zwischenzeit berechnen
						for (int k=0; k<nphase.getSignalgeber().size();k++) {
							int zwischen=zz.get_zwischenzeit(aphase.getSignalgeber().get(j), nphase.getSignalgeber().get(k), kr, vm);
							if (sg_max_zwischen<zwischen) {
								sg_max_zwischen=zwischen;	
							}
						}
						if (phase_zwischen<sg_max_zwischen) {
							phase_zwischen=sg_max_zwischen;
						}
						gzv=phase_zwischen-sg_max_zwischen;
					if (m==1) {
						if (index > 0) {
							for (int z=0;z<index;z++) {
							cell[z]=SpreadsheetCellType.STRING.createCell(j, z, 1, 1, "");
							}
						}
						cell[index]=SpreadsheetCellType.INTEGER.createCell(j, index, 1, 1, (int) p[i].getSignalgeber().get(j).getTfUmlauf()+gzv);		//Grünzeit
						cell[index+1]=SpreadsheetCellType.INTEGER.createCell(j, index+1, 1, 1, (int) kr.getT_gelb());		//Gelbzeit
						cell[index+2]=SpreadsheetCellType.INTEGER.createCell(j, index+2, 1, 1, (int) sg_max_zwischen);		//Rotzeit	TODO Rotzeit auslesen
						cell[index+3]= SpreadsheetCellType.INTEGER.createCell(j, index+3, 1, 1, (int) kr.getT_rot_gelb());		//Rotzeit	TODO Rotzeit auslesen
						if ((anz_phasen*4)-index>=4) {
							for (int z=index+4;z<(anz_phasen*4);z++) {
							cell[z]=SpreadsheetCellType.STRING.createCell(j, z, 1, 1, "");
							}
						}
						for (int l=0; l<(anz_phasen*4);l++) {
							Row.add(cell[l]);	
						}
						rows_fs.add(Row);

					}
					sg_max_zwischen=-998;
				}
			}
			index=index+4;
			columnsHeaders_fs.add("g");
			columnsHeaders_fs.add("ge");
			columnsHeaders_fs.add("r");
			columnsHeaders_fs.add("rg");
		}
		grid_fs.setRows(rows_fs);
	    setGrid(grid_fs);
	    grid_fs.getRowHeaders().addAll(rowsHeaders_fs);
		grid_fs.getColumnHeaders().addAll(columnsHeaders_fs);
        getFixedRows().add(0);
        for (int i=0;i<getColumns().size();i++) {
            getColumns().get(i).setPrefWidth(50);
            }
		
		
	}
}
