package application;

import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Zwischenzeiten extends SpreadsheetView {

	private int[][] zz_array;
	private int[][] vr_matrix;
	
	public Zwischenzeiten(int[][] v) {
		vr_matrix=v;
		
	}

	public int[][] getZz_array() {
		return zz_array;
	}
	public void pruef_zz(int s){
		String zz;
		int rowCount = s;
        int columnCount = s+1;
        
        GridBase grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
		for(int i=0;i<vr_matrix.length;i++) 
		{
			final ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
			System.out.println("Nächste Zufahrt!");
			for(int j=0;j<vr_matrix.length;j++){
				System.out.println("Nächste Spur:"+vr_matrix[i][j]);
				System.out.println("i:"+i+" j:"+j);
				SpreadsheetCell cell;
		        if ((vr_matrix[i][j]==9)||(vr_matrix[i][j]==0)) {
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
