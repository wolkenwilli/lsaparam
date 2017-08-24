package application;

import java.util.HashMap;
import java.util.Stack;

import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.GridChange;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;

class Verriegelungsmatrix extends SpreadsheetView {
	private int[][] array_ungerade = new int[7][7];
	private int[][] array_gerade = new int[7][7];	

	public Verriegelungsmatrix()
	{
		//Initialisierung der Verriegelungsmatrix
		for (int i=0;i<7;i++) {
			for (int j=0;j<7;j++) {
				array_gerade[i][j]=3;
			}
		}
		for (int i=0;i<7;i++) {
			for (int j=0;j<7;j++) {
				array_ungerade[i][j]=3;
			}
		}
		
		//--- Array gerade ---
		//nicht verriegelt
		array_ungerade[0][0]=0;
		array_ungerade[1][1]=0;
		array_ungerade[2][2]=0;
		array_ungerade[3][3]=0;
		array_ungerade[0][1]=0;
		array_ungerade[0][2]=0;
		array_ungerade[1][2]=0;
		//bedingt verträglich
		array_ungerade[0][5]=1;
		array_ungerade[1][5]=1;
		array_ungerade[2][5]=1;
		array_ungerade[3][5]=1;
		array_ungerade[4][5]=1;
		array_ungerade[5][5]=1;
		array_ungerade[6][5]=1;
		array_ungerade[0][6]=1;
		array_ungerade[1][6]=1;
		array_ungerade[2][6]=1;
		array_ungerade[5][6]=1;
		array_ungerade[6][6]=1;
		
		//--- Array Ungerade ---
		
		array_gerade[2][2]=0;
		array_gerade[2][3]=0;
		array_gerade[2][4]=0;
		array_gerade[2][5]=0;
		array_gerade[2][6]=0;
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
	
	public void create_matrix(Kreuzung kr, Zwischenzeitbeziehungen[] zzb){
		
		HashMap <Zufahrt, Spur> hm = kr.getAlleSpuren();
		int s=hm.size();
		int rowCount = s;
        int columnCount = s+1;
        int i=0; int j=0; int x=0;
        zzb = new Zwischenzeitbeziehungen[s];
        
        GridBase grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();

        for (Zufahrt z1 : hm.keySet()) {
        	final ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
        	i=0;
        	for (Zufahrt z2 : hm.keySet()) {
        		int pruef=0;
        		pruef=pruef_verriegelung(z1.getNummer(), z2.getNummer(), hm.get(z1).getTyp(), hm.get(z2).getTyp());
				SpreadsheetCell cell = SpreadsheetCellType.INTEGER.createCell(i, 0, 0, 0, pruef); 		                
				zzb[x].setVerriegelung(pruef);
		
				cell.setEditable(true);
				Row.add(cell);
				i++;
				x++;
        	}
        	rows.add(Row);
        	j++;
        }
        grid.setRows(rows);
        setGrid(grid);

        getFixedRows().add(0);
        Stack<GridChange> st = new Stack<GridChange>();
        grid.addEventHandler(GridChange.GRID_CHANGE_EVENT, new EventHandler<GridChange>() {
            
            public void handle(GridChange change) {
                    st.push(change);
                }
            });
        //getColumns().get(0).setFixed(true);
        //getColumns().get(1).setPrefWidth(250);

	}
}

