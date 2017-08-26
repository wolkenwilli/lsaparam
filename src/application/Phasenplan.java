package application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class Phasenplan {
	private Signalgeber s;
	private int q;
	private int qs;
	private int g;
	private int minfz;
	public TableView<Phasenplan> table = new TableView<Phasenplan>();
	
	public Phasenplan(Signalgeber s, int q, int qs) {
		this.s=s;
		this.q=q;
		this.qs=qs;
	}
	public void calc_minfz() {
		
		
		
	}
	public void create_table_fz() {
	
	}
	
		
		
}


