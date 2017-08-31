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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Export {

	public void do_export(Kreuzung kr, Phase[] p, int anz_phasen, Verriegelungsmatrix vm, Zwischenzeiten zz) {

	  try {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("LSA");
		doc.appendChild(rootElement);

		//1. Zweig
		Element kreuzung = doc.createElement("Kreuzung");
		rootElement.appendChild(kreuzung);
		Element signalgeber = doc.createElement("Signalgeber");
		rootElement.appendChild(signalgeber);
		Element phasen = doc.createElement("Phasen");
		rootElement.appendChild(phasen);
		
		//Kreuzung:
		kreuzung.setAttribute("id", "1");
		Element f1 = doc.createElement("f1");
		f1.appendChild(doc.createTextNode(Float.toString(kr.getF1())));
		kreuzung.appendChild(f1);
		Element f2 = doc.createElement("f2");
		f1.appendChild(doc.createTextNode(Float.toString(kr.getF2())));
		kreuzung.appendChild(f2);
		Element t_gelb = doc.createElement("t_gelb");
		t_gelb.appendChild(doc.createTextNode(Float.toString(kr.getT_gelb())));
		kreuzung.appendChild(t_gelb);
		Element t_rot_gelb = doc.createElement("t_rot_gelb");
		t_rot_gelb.appendChild(doc.createTextNode(Float.toString(kr.getT_rot_gelb())));
		kreuzung.appendChild(t_rot_gelb);
		//Signalgeber
		Element[] sg = new Element[kr.get_signalgeberlist().size()];
		Element[] sg_bezeichnung = new Element[kr.get_signalgeberlist().size()];
		Element[] sumoid = new Element[kr.get_signalgeberlist().size()];
		Element[] id = new Element[kr.get_signalgeberlist().size()];
		Element[] typ = new Element[kr.get_signalgeberlist().size()];
		Element[] q = new Element[kr.get_signalgeberlist().size()];
		Element[] qs = new Element[kr.get_signalgeberlist().size()];
		Element[] tfStunde = new Element[kr.get_signalgeberlist().size()];
		Element[] tfUmlauf = new Element[kr.get_signalgeberlist().size()];
		Element[] ZufahrtID = new Element[kr.get_signalgeberlist().size()];
		Element[] tp = new Element[kr.get_signalgeberlist().size()];
		Element[] g = new Element[kr.get_signalgeberlist().size()];
		
		for (int i=0;i<kr.get_signalgeberlist().size();i++) {
			
			sg[i] = doc.createElement("Signalgeber");
			sg[i].setAttribute("id", Integer.toString(kr.get_signalgeberlist().get(i).getId()));
			signalgeber.appendChild(sg[i]);
			sg_bezeichnung[i] = doc.createElement("Bezeichnung");
			sg_bezeichnung[i].appendChild(doc.createTextNode(kr.get_signalgeberlist().get(i).getBezeichnung()));
			sg[i].appendChild(sg_bezeichnung[i]);
			ZufahrtID[i] = doc.createElement("ZufahrtID");
			ZufahrtID[i].appendChild(doc.createTextNode(Integer.toString(kr.get_signalgeberlist().get(i).getEigene_zufahrt().getNummer())));
			sg[i].appendChild(ZufahrtID[i]);
			sumoid[i] = doc.createElement("sumoid");
			sumoid[i].appendChild(doc.createTextNode(Integer.toString(kr.get_signalgeberlist().get(i).getSumoid())));
			sg[i].appendChild(sumoid[i]);
			typ[i] = doc.createElement("Typ");
			typ[i].appendChild(doc.createTextNode(Integer.toString(kr.get_signalgeberlist().get(i).getTyp())));
			sg[i].appendChild(typ[i]);
			q[i] = doc.createElement("q");
			q[i].appendChild(doc.createTextNode(Float.toString(kr.get_signalgeberlist().get(i).getQ())));
			sg[i].appendChild(q[i]);
			qs[i] = doc.createElement("qs");
			qs[i].appendChild(doc.createTextNode(Float.toString(kr.get_signalgeberlist().get(i).getQs())));
			sg[i].appendChild(qs[i]);
			tfStunde[i] = doc.createElement("tfStunde");
			tfStunde[i].appendChild(doc.createTextNode(Float.toString(kr.get_signalgeberlist().get(i).getTfStunde())));
			sg[i].appendChild(tfStunde[i]);
			tp[i] = doc.createElement("tp");
			tp[i].appendChild(doc.createTextNode(Double.toString(kr.get_signalgeberlist().get(i).getTp())));
			sg[i].appendChild(tp[i]);
			tp[i].setAttribute("g", Double.toString(kr.get_signalgeberlist().get(i).getG()));
			tfUmlauf[i] = doc.createElement("tfUmlauf");
			tfUmlauf[i].appendChild(doc.createTextNode(Float.toString(kr.get_signalgeberlist().get(i).getTfUmlauf())));
			tp[i].appendChild(tfUmlauf[i]);
			
		}

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		//StreamResult result = new StreamResult(new File("D:\\file.xml"));
		StreamResult result =  new StreamResult(System.out);
		transformer.transform(source, result);

		System.out.println("File saved!");

	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
}
