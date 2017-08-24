package application;

import java.util.HashMap;

import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Zwischenzeiten extends SpreadsheetView {

	GridBase grid;
	private Zwischenzeitbeziehungen[][] vr_matrix;
	
	
	public Zwischenzeiten(Zwischenzeitbeziehungen[][] zzb) {
		vr_matrix=zzb;
		
	}
	
	
	public void pruef_zz(Kreuzung kr, Verriegelungsmatrix vm){
		HashMap <Zufahrt, Spur> hm = kr.getAlleSpuren();
		int s=hm.size();
		int rowCount = s;
        int columnCount = s+1;
        
        
    
        grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        
        for(int i=0;i<vr_matrix.length;i++) 
		{
			final ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
			for(int j=0;j<vr_matrix.length;j++){
				SpreadsheetCell cell;
		        if ((vr_matrix[i][j].getVerriegelung()==9)||(vr_matrix[i][j].getVerriegelung()==0)) {
		        	cell = SpreadsheetCellType.STRING.createCell(j, 1, 1, 0, "X");
		        	cell.setEditable(false);	
		        }
		        else {
		        	cell = SpreadsheetCellType.STRING.createCell(j, 1, 1, 0, "Wert");
		        	cell.setEditable(true);
		        }
		 		Row.add(cell);
			}
			rows.add(Row);
		}
        
        
        
        
        
	    grid.setRows(rows);
	    setGrid(grid);
        getFixedRows().add(0);
        getColumns().get(0).setFixed(true);
        //getColumns().get(1).setPrefWidth(250);
    	
	}


}
