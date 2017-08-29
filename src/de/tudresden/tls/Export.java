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
		Element zufahrten = doc.createElement("Zufahrten");
		rootElement.appendChild(zufahrten);
		Element signalgeber = doc.createElement("Signalgeber");
		rootElement.appendChild(signalgeber);
		Element phasen = doc.createElement("Phasen");
		rootElement.appendChild(phasen);
		Element festzeitsteuerung = doc.createElement("Festzeitsteuerung");
		rootElement.appendChild(festzeitsteuerung);
		
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


		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
//		StreamResult result = new StreamResult(new File("C:\\file.xml"));
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
