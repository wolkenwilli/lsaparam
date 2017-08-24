package application;

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
	
	public void create_matrix(int s, Kreuzung kr){
		
		 int rowCount = s;
         int columnCount = s+1;
         vr_array = new int[s][s];
        
         GridBase grid = new GridBase(rowCount, columnCount);
         ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();

        for (int i=0;i<kr.anz_Zufahrt();i++)
 		{
        	for (int j=0;j<kr.return_zufahrt(i).get_anzahl_spuren();j++)
 			{
        		System.out.println("Nächste Zufahrt!");
        		final ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
 				for (int k=0;k<kr.anz_Zufahrt();k++)
 				{
 					for (int l=0;l<kr.return_zufahrt(k).get_anzahl_spuren();l++)
 					{
 						int pruef=0;
 						int i_id=kr.return_zufahrt(i).getNummer(); int j_kat=kr.return_zufahrt(i).return_spur(j).getTyp();
 						int k_id=kr.return_zufahrt(k).getNummer(); int l_kat=kr.return_zufahrt(k).return_spur(l).getTyp();
 						pruef=pruef_verriegelung(i_id, k_id, j_kat, l_kat);
 						//DEBUG: System.out.println("Prüfung: Ausgangszufahrt:"+i+"(Nr:"+i_id+") mit Spur:"+j+"(Kat:"+j_kat+") in Verriegelung mit Zufahrt:"+k+"(Nr:"+k_id+") und Spur:"+l+"(Kat:"+l_kat+") ergab das Ergebnis:"+pruef);
 						SpreadsheetCell cell = SpreadsheetCellType.INTEGER.createCell(i_id, 0, 0, 0, pruef); 		                
 						vr_array[i_id-1][k_id-1]=pruef;
 						vr_array[k_id-1][i_id-1]=pruef;
 						cell.setEditable(true);
 						Row.add(cell);

 					}
 				}
 				rows.add(Row);
 			}
 		}
   
         grid.setRows(rows);
         setGrid(grid);

         getFixedRows().add(0);
         getColumns().get(0).setFixed(true);
         //getColumns().get(1).setPrefWidth(250);
      
	}
}

