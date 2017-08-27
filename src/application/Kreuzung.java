package application;

import java.util.HashMap;
import java.util.LinkedList;

import javafx.scene.control.TextField;

class Kreuzung {
	private float f1=0.9f;
	private float f2=1.0f;

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
	LinkedList<Zufahrt> zufahrten = new LinkedList<Zufahrt>();
	HashMap<Zufahrt, Signalgeber> alleSignalgeber = new HashMap<Zufahrt, Signalgeber>();
	public void putZufahrt (Zufahrt zf) {
		zufahrten.add(zf);
		//System.out.println("Zufahrt erzeugt!");
	}
	
	public void putSignalgeber(Zufahrt z, Signalgeber sg) {
		alleSignalgeber.put(z, sg);
		//System.out.println("Signalgeber gespeichert.");
	}
	
	public HashMap<Zufahrt, Signalgeber> getAlleSignalgeber(){
		return alleSignalgeber;
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
	public int get_anz_Signalgeber() 
	{
		int anz=0;
		for (int i=0;i<=zufahrten.size()-1;i++)
		{
			anz=anz+zufahrten.get(i).get_anzahl_signalgeber();
		}
		return anz;
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
			System.out.println("4 Spuren ist das maximum!");
		}
		else
		{
			
			int rechte = (zf.signal_geber.get(zf.get_anzahl_signalgeber()-1)).getTyp();
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
}
