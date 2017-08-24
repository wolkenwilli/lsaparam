package application;

import java.util.HashMap;

import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

class Verriegelungsmatrix extends SpreadsheetView {
	private int[][] array_ungerade = new int[7][7];
	private int[][] array_gerade = new int[7][7];
	private int[][] vr_array;

	

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
	public int[][] getVr_array() {
		return vr_array;
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
		
		HashMap <Zufahrt, Spur> hm = kr.getAlleSpuren();
		int s=hm.size();
		int rowCount = s;
        int columnCount = s+1;
        int i=0; int j=0;
        vr_array = new int[s][s];
        System.out.println(s);
         
        
        GridBase grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();

        for (Zufahrt z1 : hm.keySet()) {
        	final ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
        	i=0;
        	for (Zufahrt z2 : hm.keySet()) {
        		int pruef=0;
        		pruef=pruef_verriegelung(z1.getNummer(), z2.getNummer(), hm.get(z1).getTyp(), hm.get(z2).getTyp());
				SpreadsheetCell cell = SpreadsheetCellType.INTEGER.createCell(i, 0, 0, 0, pruef); 		                
				vr_array[i][j]=pruef;
				vr_array[j][i]=pruef;
				System.out.println("i:"+i+" j:"+j+" Prüf:"+pruef);
				System.out.println(vr_array);
				cell.setEditable(true);
				Row.add(cell);
				i++;
        	}
        	rows.add(Row);
        	j++;
        }
        grid.setRows(rows);
        setGrid(grid);

        getFixedRows().add(0);
        //getColumns().get(0).setFixed(true);
        //getColumns().get(1).setPrefWidth(250);

	}
}

