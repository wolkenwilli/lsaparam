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

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Festzeitsteuerung extends SpreadsheetView {
	GridBase grid_fs;
	ObservableList<String> rowsHeaders_fs;
	public void create_festzeitplan(Kreuzung kr, Phase[] p, int anz_phasen, Verriegelungsmatrix vm, Zwischenzeiten zz, double tp) 
	{
		int rowCount_fs = kr.get_signalgeberlist().size();
        int columnCount_fs = (((int)tp)+3);
       
        grid_fs = new GridBase(rowCount_fs, columnCount_fs);
        ObservableList<ObservableList<SpreadsheetCell>> rows_fs = FXCollections.observableArrayList();
        rowsHeaders_fs = FXCollections.observableArrayList();
        ObservableList<String> columnsHeaders_fs = FXCollections.observableArrayList();
        SpreadsheetCell[] cell = new SpreadsheetCell [columnCount_fs];
        for (int i=1;i<=tp;i++) {
        	columnsHeaders_fs.add(String.valueOf(i));	
        }
        columnsHeaders_fs.add("TFA");
        columnsHeaders_fs.add("TFE");
        columnsHeaders_fs.add("TFD");
                
        int anz_sg=0;
		int maxzeit=(int)tp;
		int phase_beginn=0;
		//Schleife über alle Phasen
		for (int i=0; i<anz_phasen;i++) {
			//aktuelle Phase
			Phase aphase=p[i];
			Phase nphase;
			int phase_zwischen=-100;	//maximale Zwischenzeit aller Signalgeber einer Phase
			int phase_min_gruen=-100;
			int zeit = 0;
			if (anz_phasen-i>1) {
				nphase=p[i+1];	
			}
			//wenn höchste Phase erreicht, dann wird nächste Phase die 1. Phase
			else {
				nphase=p[0];
			}
			int sg_max_zwischen=-998;	//maximale Zwischenzeit je Signalgeber
			int gzv = 0;				//Grünzeitverlängerung, kommt zum Tragen, wenn Signalgeber einer Phase eine kürzere Zwischenzeit haben, als andere
			//Doppelter Durchlauf, da im 1. Schritt die maximale Grünzeit einer Phase bestimmt werden muss, im 2. Schritt dann als Grünzeitverlängerung gewährt und Ausgabe
			for (int m=0;m<2;m++) {
				//Schleife über alle Signalgeber der Phase
				for (int j=0;j<aphase.getSignalgeber().size();j++) {
					gzv=0;
					ObservableList<SpreadsheetCell> Row = FXCollections.observableArrayList();
					// Maximale Zwischenzeit des Signalgebers zu Signalgebern der nächsten Phase berechnen
						//Dazu Schleife über alle Signalgeber der nächsten Phase
						for (int k=0; k<nphase.getSignalgeber().size();k++) {
							int zwischen=zz.get_zwischenzeit(aphase.getSignalgeber().get(j), nphase.getSignalgeber().get(k), kr, vm);
							//Abgleich, ob sich die Zwischenzeit erhöht hat
							if (sg_max_zwischen<zwischen) {
								sg_max_zwischen=zwischen;	
							}
						}
						//Minimale Grünzeit der Phase ermitteln
						if (phase_min_gruen<p[i].getSignalgeber().get(j).getTfUmlauf()) {
							phase_min_gruen=(int) p[i].getSignalgeber().get(j).getTfUmlauf();
						}
						//Speichern, um die Maximale Zwischenzeit der Phase zu ermitteln!
						if (phase_zwischen<sg_max_zwischen) {
							phase_zwischen=sg_max_zwischen;
						}
						//Grünzeitverlängerung ist die Maximale Zwischenzeit der Phase minus Zwischenzeit des derzeitigen Signalgebers
						gzv=phase_zwischen-sg_max_zwischen;
					//beim 2. Durchlauf Ausgabe
					if (m==1) {
						rowsHeaders_fs.add(aphase.getSignalgeber().get(j).getBezeichnung());
						zeit=0;
						//wenn es nicht die erste Phase ist, leere Zellen erstellen
						if (phase_beginn > 0 ) {
							for (int z=0;z<phase_beginn;z++) {
							cell[(z)]=SpreadsheetCellType.STRING.createCell(anz_sg, (z), 1, 1, "r");
							}
						}
						//Zellen mit Werten erstellen
						for (int z=zeit;z<(zeit+kr.getT_rot_gelb());z++) {
							cell[(phase_beginn+z)]=SpreadsheetCellType.STRING.createCell(anz_sg, (phase_beginn+z), 1, 1, "u");		//Rot-Gelb-Zeit
							//System.out.println("Cell: "+(phase_beginn+z)+ " x: "+anz_sg+" y: "+(phase_beginn+z)+" Wert: u");
						}
						zeit=zeit+kr.getT_rot_gelb();
						cell[(maxzeit)]=SpreadsheetCellType.INTEGER.createCell(anz_sg, (maxzeit), 1, 1, (phase_beginn+zeit+1));
						for (int z=zeit;z<(zeit+phase_min_gruen+gzv);z++) {
							cell[(phase_beginn+z)]=SpreadsheetCellType.STRING.createCell(anz_sg, (phase_beginn+z), 1, 1, "G");		//Grünzeit
						}
						zeit=(zeit+phase_min_gruen+gzv);
						cell[(maxzeit+1)]=SpreadsheetCellType.INTEGER.createCell(anz_sg, (maxzeit+1), 1, 1, (phase_beginn+zeit));
						for (int z=zeit;z<(zeit+kr.getT_gelb());z++) {
							cell[(phase_beginn+z)]=SpreadsheetCellType.STRING.createCell(anz_sg, (phase_beginn+z), 1, 1, "y");		//Gelbzeit
						}
						zeit=(zeit+kr.getT_gelb());
						try {
							for (int z=zeit;z<(zeit+sg_max_zwischen);z++) {
								cell[(phase_beginn+z)]=SpreadsheetCellType.STRING.createCell(anz_sg, (phase_beginn+z), 1, 1, "r");		//Rotzeit
							}
						} catch (Exception e) {
							System.out.println("Phasenplanzeit überschreitet Umlaufzeit Tp!");
							e.printStackTrace();
						}
						zeit=(zeit+sg_max_zwischen);		
						//wenn es nicht die letzte Phase ist, mit leeren Zellen füllen
						if ((zeit+phase_beginn) < maxzeit) {
							for (int z=(phase_beginn+zeit);z<maxzeit;z++) {
							cell[(z)]=SpreadsheetCellType.STRING.createCell(anz_sg, (z), 1, 1, "r");
							}
						}
						cell[(maxzeit+2)]=SpreadsheetCellType.INTEGER.createCell(anz_sg, (maxzeit+2), 1, 1, phase_min_gruen+gzv);
						//alle Zellen der Zeile hinzufügen
						for (int l=0; l<(maxzeit+3);l++) {
							cell[l].setEditable(true);
							Row.add(cell[l]);	
						}
						rows_fs.add(Row);
						anz_sg++;
					}
					//Maximale Zwischenzeit der Signalgeber zurücksetzen
					sg_max_zwischen=-998;
				}
			}
			//zeit für die nächste Phase erhö
			phase_beginn=zeit;
		}
		grid_fs.setRows(rows_fs);
	    setGrid(grid_fs);
	    grid_fs.getRowHeaders().addAll(rowsHeaders_fs);
		grid_fs.getColumnHeaders().addAll(columnsHeaders_fs);
		//Breite 50 für alle Spalten
        for (int i=0;i<(getColumns().size()-3);i++) {
            getColumns().get(i).setPrefWidth(25);
        }
        for (int i=(getColumns().size()-3);i<(getColumns().size());i++) {
            getColumns().get(i).setPrefWidth(30);
        }
	}
	public void create_export2sumo(HashMap<String, Signalgeber> signalgeberbezeichnung, SumoExport[] se, double ul) {
		String[] sumostring = new String[(int)ul];
		int asumoid=0;

		//Schleife über alle Spalten
		for (int i=0;i<grid_fs.getRowCount();i++) {
			Signalgeber a = signalgeberbezeichnung.get(rowsHeaders_fs.get(i));
			for (int j=0;j<(grid_fs.getColumnCount()-3);j++) {
				for (int k=0;k<a.getSumoid();k++)
				{
					if (sumostring[j]==null) {
						sumostring[j]=grid_fs.getRows().sorted().get(i).get(j).getText();
					}
					else {
						sumostring[j]=sumostring[j].concat(grid_fs.getRows().get(i).get(j).getText());
					}
				}
			}
		}
		int count_sumo=0;
		int dur=0;
		String vstring="";
		String astring="";
		for (int i=1;i<sumostring.length;i++) {
			vstring=sumostring[i-1];
			astring=sumostring[i];
			if (vstring.equals(astring)) {
				dur++;
			}
			else {
				dur++;
				se[count_sumo]=new SumoExport(dur,vstring);
				count_sumo++;
				dur=0;
			}
		}
		dur++;
		se[count_sumo]=new SumoExport(dur,vstring);
		count_sumo++;
		dur=0;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("SUMO");
			doc.appendChild(rootElement);
			Element[] ephase = new Element[count_sumo];
			//Schleife über alle SumoPhasen
			for (int i=0; i<count_sumo;i++) {
				ephase[i] = doc.createElement("phase");
				ephase[i].setAttribute("duration", Integer.toString(se[i].getDuration()));
				ephase[i].setAttribute("state", se[i].getSumoString());
				rootElement.appendChild(ephase[i]);
			}
				
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("D:\\file.xml"));
			//StreamResult result =  new StreamResult(System.out);
			transformer.transform(source, result);
			System.out.println("File saved!");

			  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			  } catch (TransformerException tfe) {
				tfe.printStackTrace();
			  }
			
	}
}
