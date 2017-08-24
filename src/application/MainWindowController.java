package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.FloatStringConverter;


public class MainWindowController implements Initializable {
	public Kreuzung kr = new Kreuzung();
	
	
	public Zufahrt z1;
	public Zufahrt z2;
	public Zufahrt z3;
	public Zufahrt z4;
	public static Spur[] s = new Spur[16];
	public Verriegelungsmatrix vm = new Verriegelungsmatrix();
	public Zwischenzeiten zz;
	public static MenuItem[] menuitem = new MenuItem[7];
	private LinkedList<String> kats;
	StackPane spane;
	StackPane spane2;
	
	

	//Views
	@FXML TableView<Option> table_options1;
	@FXML TableView<Option> table_options2;	
	@FXML Label label_info;
	//----
	@FXML private TextField qs;
	@FXML private TextField f1;
	@FXML private TextField f2;
	@FXML private TextField qsn;
	@FXML private Pane gui_zufahrt1;
	@FXML private Pane gui_zufahrt2;
	@FXML private Pane gui_zufahrt3;
	@FXML private Pane gui_zufahrt4;
	@FXML private VBox gui_vbox_z1;
	@FXML private VBox gui_vbox_z2;
	@FXML private VBox gui_vbox_z3;
	@FXML private VBox gui_vbox_z4;
	@FXML private ContextMenu gui_contextmenu;
	//----
	@FXML private Pane vm_pane;
	@FXML private Pane zz_pane;
	
	public Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	public static void main(String[] args) {
	
		

	}

	@FXML
	public void do_menu_init(){
				
	//	label_info.setText("Bitte erzeugen Sie nun Spuren in den Zufahrten!");
		
	table_options1.getColumns().clear();
	ObservableList<Option> data1 = FXCollections.observableArrayList(
            new Option("Angleichsfaktor f1", 0.90f),
            new Option("Angleichsfaktor f2", 0.85f),
            new Option("Dauer Signalbild Gelb [s]", 3f),
            new Option("Dauer Sigalbild Rot-Gelb [s]", 2f),
            new Option("Räumgeschwindigkeit KFZ gerade [m/s]", 10f),
            new Option("Räumgeschwindigkeit KFZ abb. [m/s]", 7f),
            new Option("Räumgeschwindigkeit Radfahrer [m/s]", 4f),
            new Option("Einfahrgeschwindigkeit KFZ gerade [m/s]", 11.1f),
            new Option("Einfahrgeschwindigkeit KFZ abb. [m/s]", 11.1f),
            new Option("Einfahrgeschwindigkeit Radfahrer [m/s]", 5f),
            new Option("Überfahrzeit KFZ gerade [s]", 3f),
            new Option("Überfahrzeit KFZ abb. [s]", 2f),
            new Option("Überfahrzeit Rad [s]", 1f)
    );
	table_options1.setEditable(true);
	TableColumn<Option, String> NameCol = new TableColumn("Name");
    NameCol.setCellValueFactory(new PropertyValueFactory<Option,String>("name"));
    NameCol.setCellFactory(TextFieldTableCell.<Option>forTableColumn());
    NameCol.setOnEditCommit(
		    new EventHandler<CellEditEvent<Option, String>>() {
		        public void handle(CellEditEvent<Option, String> t) {
		            ((Option) t.getTableView().getItems().get(
		                t.getTablePosition().getRow())
		                ).setName(t.getNewValue());
		        }
		    }
		);
	TableColumn<Option, Float> WertCol = new TableColumn("Wert");
    WertCol.setCellValueFactory(new PropertyValueFactory<Option,Float>("wert"));
    WertCol.setCellFactory(TextFieldTableCell.<Option, Float>forTableColumn(new FloatStringConverter()));
    WertCol.setOnEditCommit(
		    new EventHandler<CellEditEvent<Option, Float>>() {
		        public void handle(CellEditEvent<Option, Float> t) {
		            ((Option) t.getTableView().getItems().get(
		                t.getTablePosition().getRow())
		                ).setWert(t.getNewValue());
		        }
		    }
		);
    table_options1.getColumns().addAll(NameCol, WertCol);
    table_options1.setItems(data1);

    
    //----------------------------------
    table_options2.getColumns().clear();
	ObservableList<Option> data2 = FXCollections.observableArrayList(
            
    );
	TableColumn<Option, String> Name2Col = new TableColumn("Name");
    Name2Col.setCellValueFactory(new PropertyValueFactory<Option,String>("name"));
	TableColumn<Option, Float> Wert2Col = new TableColumn("Wert");
    Wert2Col.setCellValueFactory(new PropertyValueFactory<Option,Float>("wert"));
    table_options2.getColumns().addAll(Name2Col, Wert2Col);
    table_options2.setItems(data2);
	

	}
	
	@FXML
	public void do_menu_probe(){
		this.spane = new StackPane(vm);
		this.vm_pane.getChildren().add(this.spane);
		AnchorPane.setTopAnchor(this.spane, 0.0);
		AnchorPane.setLeftAnchor(this.spane, 0.0);
		AnchorPane.setRightAnchor(this.spane, 0.0);
		AnchorPane.setBottomAnchor(this.spane, 0.0);
		System.out.println(kr.getAlleSpuren());
		vm.create_matrix(kr);
		zz=new Zwischenzeiten(vm.getVr_array());
		this.spane2 = new StackPane(zz);
		this.zz_pane.getChildren().add(this.spane2);
		AnchorPane.setTopAnchor(this.spane2, 0.0);
		AnchorPane.setLeftAnchor(this.spane2, 0.0);
		AnchorPane.setRightAnchor(this.spane2, 0.0);
		AnchorPane.setBottomAnchor(this.spane2, 0.0);
		zz.pruef_zz(kr, vm);
			
	}
	
	@FXML
	public void erzeugeZufahrten(){
		z1 = new Zufahrt(kr,gui_zufahrt1, gui_vbox_z1);
		z2 = new Zufahrt(kr,gui_zufahrt2, gui_vbox_z2);
		z3 = new Zufahrt(kr, gui_zufahrt3, gui_vbox_z3);
		z4 = new Zufahrt(kr,gui_zufahrt4, gui_vbox_z4);
		kats = new LinkedList<String>();
		kats.add("Gerade + Rechts");		//0
		kats.add("Gerade");					//1
		kats.add("Rechts");					//2
		kats.add("Links");					//3
		kats.add("Links + Rechts");			//4
		kats.add("Links + Gerade");			//5
		kats.add("Links + Gerade+ Rechts");	//6
		
	}
	
	@FXML
	public void erzeuge3Zufahrten(){
		z1 = new Zufahrt(kr,gui_zufahrt1, gui_vbox_z1);
		z2 = new Zufahrt(kr,gui_zufahrt2, gui_vbox_z2);
		z3 = new Zufahrt(kr, gui_zufahrt3, gui_vbox_z3);
		kats = new LinkedList<String>();
		kats.add("Gerade + Rechts");		//0
		kats.add("Gerade");					//1
		kats.add("Rechts");					//2
		kats.add("Links");					//3
		kats.add("Links + Rechts");			//4
		kats.add("Links + Gerade");			//5
		kats.add("Links + Gerade+ Rechts");	//6
		
	}
	
	@FXML
	public void handlecalc() {
		qsn.setText(String.valueOf(kr.calc_qsn(qs, f1, f2))+" Kfz/h");
	}
	
	
	public void contextMenu(Pane p, double x, double y) 
	{
		Zufahrt zf = kr.get_zufahrt(p);
		gui_contextmenu.getItems().clear();
		
		for (int i=0;i<kats.size();i++)
		{
			if (kr.checkspur(zf, i)==1)
			{
				menuitem[i] = new MenuItem(kats.get(i));
				menuitem[i].setOnAction(new EventHandler<ActionEvent>() 
				{
					int j;
					public void handle(ActionEvent e) 
					{
						
						System.out.println("Debug: "+kats.get(j)+" Spur wird erzeugt!");
						zf.erzeugeSpur(j);				        						
					}

					private EventHandler<ActionEvent> init(int i)
					{
				        j = i;
				        return this;
					}
				}
				.init(i));
				gui_contextmenu.getItems().add(menuitem[i]);
			}
			else
			{
				System.out.println("Debug: Spur nicht möglich!");
			}
		}
		gui_contextmenu.show(p, x, y);
	
	}

	
	public void initialize(URL location, ResourceBundle resources) {
		gui_zufahrt1.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() 
		{public void handle(MouseEvent e){contextMenu(gui_zufahrt1,e.getScreenX(), e.getScreenY());}});
		
		gui_zufahrt2.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() 
		{public void handle(MouseEvent e){contextMenu(gui_zufahrt2,e.getScreenX(), e.getScreenY());}});
		
		gui_zufahrt3.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() 
		{public void handle(MouseEvent e){contextMenu(gui_zufahrt3,e.getScreenX(), e.getScreenY());}});
		
		gui_zufahrt4.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() 
		{public void handle(MouseEvent e){contextMenu(gui_zufahrt4,e.getScreenX(), e.getScreenY());}});
	}
	
	@FXML
	public void do_menu_beenden()
	{
		System.exit(0);		
	}
}
