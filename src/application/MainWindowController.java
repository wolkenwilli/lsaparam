package application;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
	public static Spur[] s = new Spur[16];		//Maximum 16 da 4x4 Spuren
	public static Phase[] p = new Phase[10];	//Maximum 10 angenommen
	int anz_phasen=0; 
	public Verriegelungsmatrix vm = new Verriegelungsmatrix();
	public Zwischenzeiten zz;
	public static MenuItem[] menuitem = new MenuItem[7];
	private LinkedList<String> kats;
	StackPane spane;
	StackPane spane2;
	@FXML Tab tab_vm;
	@FXML Tab tab_zz;
	@FXML Tab tab_ph;
	@FXML Tab tab_pp;
	@FXML Tab tab_exp;
	
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
	//----
	@FXML private TreeView<String> tree_phasen;
	@FXML private VBox vbox_phase;
	@FXML private HBox hbox_phasen;
	private ComboBox<String> comboBox;
	@FXML private Pane anchor_left;
	@FXML private Pane anchor_right;
	HashMap<String, Spur> spurbezeichnung = new HashMap<String, Spur>(); 
	
	
	public Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	public static void main(String[] args) {
	//	menu_init.getOnAction();

	}

	@FXML
	public void do_menu_init(){
		

	}
	
	@FXML
	public void do_menu_probe(){
		
			
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
		tab_vm.setDisable(false);
		tab_zz.setDisable(false);
		tab_ph.setDisable(false);
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
		tab_vm.setDisable(false);
		tab_zz.setDisable(false);
		tab_ph.setDisable(false);
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
	@FXML
	public void tab_vm_clicked() {
		this.spane = new StackPane(vm);
		this.vm_pane.getChildren().add(this.spane);
		AnchorPane.setTopAnchor(this.spane, 0.0);
		AnchorPane.setLeftAnchor(this.spane, 0.0);
		AnchorPane.setRightAnchor(this.spane, 0.0);
		AnchorPane.setBottomAnchor(this.spane, 0.0);
		System.out.println(kr.getAlleSpuren());
		vm.create_matrix(kr);
			
	}
	@FXML
	public void tab_zz_clicked() {
		zz=new Zwischenzeiten(vm.getVr_array());
		this.spane2 = new StackPane(zz);
		this.zz_pane.getChildren().add(this.spane2);
		AnchorPane.setTopAnchor(this.spane2, 0.0);
		AnchorPane.setLeftAnchor(this.spane2, 0.0);
		AnchorPane.setRightAnchor(this.spane2, 0.0);
		AnchorPane.setBottomAnchor(this.spane2, 0.0);
		zz.pruef_zz(kr, vm);
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
	public void button_phase_add(){
		anchor_left.getChildren().clear();
		p[anz_phasen]=new Phase();
		anz_phasen++;
		ObservableList<String> options = FXCollections.observableArrayList();
		for (Zufahrt z1 : kr.allespuren.keySet()) {
			String bezeichnung="Zufahrt:"+z1.getNummer()+" Spur:"+kr.allespuren.get(z1).getBezeichnung();
			options.add(bezeichnung);
			spurbezeichnung.put(bezeichnung, kr.allespuren.get(z1));
		}
		comboBox = new ComboBox<String>(options);
		comboBox.getValue();
		anchor_left.getChildren().addAll(comboBox);
		update_tree_phase();
	}
	
	private void update_tree_phase() {
		anchor_right.getChildren().clear();
		TreeItem<String> rootItem = new TreeItem<String> ("Phasen");
        rootItem.setExpanded(true);
        for (int i = 0; i < anz_phasen; i++) {
            TreeItem<String> pitem = new TreeItem<String> ("Phase" + i);
            pitem.setExpanded(true);
            for (int j=0;j<p[i].spuren.size();j++) {
            	TreeItem<String> sitem = new TreeItem<String> ("Spur" + p[i].spuren.get(j).getBezeichnung());
            	sitem.setExpanded(true);
            	pitem.getChildren().add(sitem);
            }
            rootItem.getChildren().add(pitem);
        }        
        TreeView<String> tree = new TreeView<String> (rootItem);        
        StackPane root = new StackPane();
        root.getChildren().add(tree);
        anchor_right.getChildren().addAll(root);
	}
	
	
	
	@FXML
	public void button_spur_phase_add(){
		Spur s = spurbezeichnung.get(comboBox.getValue());
		p[anz_phasen-1].putSpuren(s);
		update_tree_phase();
		tab_pp.setDisable(false);
	}
	
	
	
	@FXML
	public void tab_ge_clicked() {
		
		//	label_info.setText("Bitte erzeugen Sie nun Spuren in den Zufahrten!");
		table_options1.getColumns().clear();
		ObservableList<Option> data1 = FXCollections.observableArrayList(
			new Option("Angleichsfaktor f1", 0.90f),
			new Option("Angleichsfaktor f2", 0.85f),
			new Option("Dauer Signalbild Gelb [s]", 3f),
			new Option("Dauer Sigalbild Rot-Gelb [s]", 2f)
		/*	Vorerst nicht erforderlich, da keine Berechnung der Zwischenzeit
      		new Option("Räumgeschwindigkeit KFZ gerade [m/s]", 10f),
        	new Option("Räumgeschwindigkeit KFZ abb. [m/s]", 7f),
        	new Option("Räumgeschwindigkeit Radfahrer [m/s]", 4f),
        	new Option("Einfahrgeschwindigkeit KFZ gerade [m/s]", 11.1f),
        	new Option("Einfahrgeschwindigkeit KFZ abb. [m/s]", 11.1f),
        	new Option("Einfahrgeschwindigkeit Radfahrer [m/s]", 5f),
        	new Option("Überfahrzeit KFZ gerade [s]", 3f),
        	new Option("Überfahrzeit KFZ abb. [s]", 2f),
        	new Option("Überfahrzeit Rad [s]", 1f)
		*/            
		);
		table_options1.setEditable(true);
		TableColumn<Option, String> NameCol = new TableColumn<Option, String>("Name");
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
		TableColumn<Option, Float> WertCol = new TableColumn<Option, Float>("Wert");
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
		TableColumn<Option, String> Name2Col = new TableColumn<Option, String>("Name");
		Name2Col.setCellValueFactory(new PropertyValueFactory<Option,String>("name"));
		TableColumn<Option, Float> Wert2Col = new TableColumn<Option, Float>("Wert");
		Wert2Col.setCellValueFactory(new PropertyValueFactory<Option,Float>("wert"));
		table_options2.getColumns().addAll(Name2Col, Wert2Col);
		table_options2.setItems(data2);

		}
	
	@FXML
	public void do_menu_beenden()
	{
		System.exit(0);		
	}
}
