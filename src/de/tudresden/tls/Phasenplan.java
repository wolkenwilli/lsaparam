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
import javafx.scene.control.TableView;

public class Phasenplan extends SpreadsheetView {
	public TableView<Signalgeber> table = new TableView<Signalgeber>();
	public final ObservableList<Signalgeber> Phasenplanlist = FXCollections.observableArrayList();
	
	public void create_fz_table(Kreuzung kr)  {
		LinkedList<Signalgeber> ll = kr.get_signalgeberlist();
		int s=ll.size();
		int rowCount = s;
        int columnCount = s+1;
        
        GridBase grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        ObservableList<String> rowsHeaders = FXCollections.observableArrayList();
        ObservableList<String> columnsHeaders = FXCollections.observableArrayList("q [Fzg/h]", "qs [Fzg/h]", "g", "Tp [s]", "tf [s]");
        
        for (int i=0;i<ll.size();i++) {
        	final ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
			rowsHeaders.add(ll.get(i).getBezeichnung());
       		SpreadsheetCell cell1 = SpreadsheetCellType.DOUBLE.createCell(i, 0, 0, 0, (double) ll.get(i).getQ() );
       		SpreadsheetCell cell2 = SpreadsheetCellType.DOUBLE.createCell(i, 0, 0, 0, (double) ll.get(i).getQs());
       		SpreadsheetCell cell3 = SpreadsheetCellType.STRING.createCell(i, 0, 0, 0, Double.toString(Math.round(ll.get(i).getG()*100)/100.0));
       		SpreadsheetCell cell4 = SpreadsheetCellType.DOUBLE.createCell(i, 0, 0, 0, Math.round(ll.get(i).getTp()*100)/100.0);
       		SpreadsheetCell cell5 = SpreadsheetCellType.DOUBLE.createCell(i, 0, 0, 0, (double) ll.get(i).getTfUmlauf());
			Row.add(cell1);
			Row.add(cell2);
			Row.add(cell3);
			Row.add(cell4);
			Row.add(cell5);
        	rows.add(Row);
        }
        grid.setRows(rows);
        setGrid(grid);
        grid.getRowHeaders().addAll(rowsHeaders);
	    grid.getColumnHeaders().addAll(columnsHeaders);
	}		
}