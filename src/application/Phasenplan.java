package application;

import java.util.HashMap;
import java.util.Stack;

import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.GridChange;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class Phasenplan {
	private int minfz;
	private int tp;
	public TableView<Phasenplan> table = new TableView<Phasenplan>();
	
	public Phasenplan(Slider slider_g, Slider slider_tp, Phase p) {
		
			}
	public void calc_minfz() {
		
		
		
	}
	public void create_table_fz() {

		//int rowCount = p.getSignalgeber().size();
        //int columnCount = p.getSignalgeber().size()+1;
        //int i=0; int j=0; int x=0;
        
        //GridBase grid = new GridBase(rowCount, columnCount);
        //ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
/*
        for (p.getSignalgeber() sg ) {
        	final ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
        	i=0;
        	for (Zufahrt z2 : hm.keySet()) {
        		int pruef=0;
        		pruef=pruef_verriegelung(z1.getNummer(), z2.getNummer(), hm.get(z1).getTyp(), hm.get(z2).getTyp());
				SpreadsheetCell cell = SpreadsheetCellType.INTEGER.createCell(i, 0, 0, 0, pruef); 		                
				zzb[x] = new Zwischenzeitbeziehungen();
				zzb[x].setVerriegelung(pruef);
				zzb[x].setEinfahrend(hm.get(z1));
				zzb[x].setAusfahrend(hm.get(z2));
				vr_array[i][j]=zzb[x];
				vr_array[j][i]=zzb[x];
		
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
       */

	}
	
		
		
}


