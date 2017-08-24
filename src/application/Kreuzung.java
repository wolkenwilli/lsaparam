package application;

import java.util.LinkedList;

import javafx.scene.control.TextField;

class Kreuzung {
	private float calc_qs;
	private float calc_f1;
	private float calc_f2;
	private float calc_qsn;

	LinkedList<Zufahrt> zufahrten = new LinkedList<Zufahrt>();
	public void putZufahrt (Zufahrt zf) {
		
		zufahrten.add(zf);
		System.out.println("Zufahrt erzeugt!");
	}
	
	public int anz_Zufahrt () {
		return (zufahrten.size());
	}
	public float calc_qsn (TextField gui_qs, TextField gui_f1, TextField gui_f2) {
		float calc_qs=Float.parseFloat(gui_qs.getText());
		float calc_f1=Float.parseFloat(gui_f1.getText());
		float calc_f2=Float.parseFloat(gui_f2.getText());
		this.calc_qsn=calc_qs*calc_f1*calc_f2;
		return this.calc_qsn;

		
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
	public int get_anz_spuren() 
	{
		int anz=0;
		for (int i=0;i<=zufahrten.size()-1;i++)
		{
			anz=anz+zufahrten.get(i).get_anzahl_spuren();
		}
		return anz;
	}
	int checkspur(Zufahrt zf, int k)
	{
		int check = 0;
		int neu = k;
		if (zf.get_anzahl_spuren()==0)
		{
			check=1;
			//System.out.println("Erste Spur in dieser Zufahrt, keine weitere Prüfung notwendig.");
		}
		else if (zf.get_anzahl_spuren()==4)
		{
			check=0;
			System.out.println("4 Spuren ist das maximum!");
		}
		else
		{
			
			int rechte = (zf.spuren.get(zf.get_anzahl_spuren()-1)).getTyp();
			//System.out.println("Neue Spur: "+neu+" Rechte Spur: "+rechte);
			if (rechte==2)
			{
				check=1;
				//System.out.println("Rechte Spur hat Kategorie 2, alles ok!");	
			}
			else if ((neu==3)&&(rechte>=3))
			{
				check=1;
				//System.out.println("Neue Spur hat Kategorie 3 und rechte davon >=3");
			}
			else if (((neu==1)||(neu==3)||(neu==5))&&(rechte<=1))
			{
				check=1;
				//System.out.println("Neue Spur hat Kategorie 1 3 oder 5 und die Rechte <=1 also alles io!");
			}
			else
			{
				check=0;
				//System.out.println("Check failed! Spur mit Kategorie "+neu+" kann nicht eingefügt werden.");
			}
		}
		return check;
	}
	public float getCalc_qsn() {
		return calc_qsn;
	}


	
}
