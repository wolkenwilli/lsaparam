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

class Kreuzung {
	private float f1=1.2f;
	private float f2=1.0f;
	private int t_gelb=5;
	private int t_rot_gelb=5;

	LinkedList<Zufahrt> zufahrten = new LinkedList<Zufahrt>();
	LinkedList<Signalgeber> signalgeberlist = new LinkedList<Signalgeber>();
	LinkedList<Option> optionenlist = new LinkedList<Option>();
	
	public void putOption(Option o) {
		optionenlist.add(o);
		updateOption();
	}
	private void updateOption() {
		if (optionenlist.size()==4) {
			f1=optionenlist.get(0).getWert();
			f2=optionenlist.get(1).getWert();
			t_gelb=(int)Math.round(optionenlist.get(2).getWert());
			t_rot_gelb=(int)Math.round(optionenlist.get(3).getWert());
		}
	}
	public void putZufahrt (Zufahrt zf) {
		zufahrten.add(zf);
	}
	public void putSignalgeberInList(Signalgeber sg) {
		signalgeberlist.add(sg);
	}
	public LinkedList<Signalgeber> get_signalgeberlist() {
		return signalgeberlist;
	}
	public int anz_Zufahrt () {
		return (zufahrten.size());
	}
	public Zufahrt return_zufahrt(int i)
	{
		return zufahrten.get(i);
	}
	public Zufahrt get_zufahrt(Object o)
	{	
		Zufahrt z = null;
		for (int i=0;i<=zufahrten.size()-1;i++)
		{
			if (zufahrten.get(i).pane==o)
			{
				z=zufahrten.get(i);
			}
		}
		return z;
	}
	public float getF1() {
		return f1;
	}
	public void setF1(float f1) {
		this.f1 = f1;
	}
	public float getF2() {
		return f2;
	}
	public void setF2(float f2) {
		this.f2 = f2;
	}
	int checksignalgeber(Zufahrt zf, int k)
	{
		int check = 0;
		int neu = k;
		if (zf.get_anzahl_signalgeber()==0)
		{
			check=1;
			//System.out.println("Erster Signalgeber in dieser Zufahrt, keine weitere Prüfung notwendig.");
		}
		else if (zf.get_anzahl_signalgeber()==4)
		{
			check=0;
			System.out.println("4 Signalgeber ist das maximum!");
		}
		else
		{
			int rechte = (zf.signal_geber.get(zf.get_anzahl_signalgeber()-1)).getTyp();
			//System.out.println("Neuer Signalgeber: "+neu+" davon rechter Signalgeber: "+rechte);
			if (rechte==1)
			{
				check=1;
				//System.out.println("Rechter Signalgeber hat Kategorie 1, alles ok!");	
			}
			else if ((neu==2)&&(rechte==2))
			{
				check=1;
				//System.out.println("Neuer Signalgeber hat Kategorie 2 und der Rechte davon 2");
			}
			else if (((neu==0)||(neu==2))&&(rechte==0))
			{
				check=1;
				//System.out.println("Neuer Signalgeber hat Kategorie 0 oder 2 und der Rechte 0 also alles io!");
			}
			else
			{
				check=0;
				//System.out.println("Check failed! Spur mit Kategorie "+neu+" kann nicht eingefügt werden.");
			}
		}
		return check;
	}

	public int getT_gelb() {
		return t_gelb;
	}

	public void setT_gelb(int t_gelb) {
		this.t_gelb = t_gelb;
	}

	public int getT_rot_gelb() {
		return t_rot_gelb;
	}

	public void setT_rot_gelb(int t_rot_gelb) {
		this.t_rot_gelb = t_rot_gelb;
	}
	
}
