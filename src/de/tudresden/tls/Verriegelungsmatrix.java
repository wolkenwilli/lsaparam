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
        zzb = new Zwischenzeitbeziehungen[s*s];
        anz_zzb=s*s;
        
        GridBase grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        ObservableList<String> rowsHeaders = FXCollections.observableArrayList();
        ObservableList<String> columnsHeaders = FXCollections.observableArrayList();
        
        for (int i=0;i<ll.size();i++) {
        	final ObservableList<SpreadsheetCell> data = FXCollections.observableArrayList();
			rowsHeaders.add(ll.get(i).getBezeichnung());
			columnsHeaders.add(ll.get(i).getBezeichnung());
        	for (int j=0;j<ll.size();j++) {
        		int pruef=0;
        		pruef=pruef_verriegelung(ll.get(i).getEigene_zufahrt().getNummer(), ll.get(j).getEigene_zufahrt().getNummer(), ll.get(i).getTyp(), ll.get(j).getTyp());
				SpreadsheetCell cell = SpreadsheetCellType.INTEGER.createCell(i, j, 1, 1, pruef); 		 
				zzb[x] = new Zwischenzeitbeziehungen();
				zzb[x].setVerriegelung(pruef);
				zzb[x].setEinfahrend(ll.get(i));
				zzb[x].setAusfahrend(ll.get(j));
				vr_array[i][j]=zzb[x];
				cell.setEditable(true);
				data.add(cell);
				x++;
        	}
        	rows.add(data);
        	System.out.println("Erzeugte Cols: "+data.size()+" geplante Cols: "+columnCount);
        	System.out.println("Erzeugte Rows: "+rows.size()+" geplante RowCount: "+rowCount);
        }
        
        
        grid.setRows(rows);
        setGrid(grid);
        grid.getRowHeaders().addAll(rowsHeaders);
	    grid.getColumnHeaders().addAll(columnsHeaders);

        getFixedRows().add(0);
        
        //getColumns().get(0).setFixed(true);
        //getColumns().get(1).setPrefWidth(250);
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

