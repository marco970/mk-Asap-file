package aSap;

/**
 * 
 * @author marcin.kuciak
 * 
 * To jest klasa do
 *
 */

public class DataCreator {
	
	//String nazwa;
	int dniMiesNo;
	String[] a;
	Integer[] b;
	
	
	public DataCreator(int dniMiesNo) {
		
		
		
		//this.nazwa = nazwa;
		this.dniMiesNo = dniMiesNo;
		a = new String[dniMiesNo+1];
		b = new Integer[dniMiesNo+1];
		for (int i = 1; i <= dniMiesNo; i++)	{
			a[i] = "";
			b[i] = 0;
		}
		//showAll();
	
	}
	public void addDane(int adr, String content)	{
		a[adr] = (a[adr] +" "+content).trim();
		b[adr] = b[adr] + 1;
	}
	public void showAll()	{
		for (int i = 1; i<=dniMiesNo; i++)	{
			System.out.println(i+" "+b[i]+" "+a[i]);
		}
	}
	public String[] getAll()	{
		return a;		
	}
	public Integer[] getHours()	{
		return b;
	}
	
	
	
	

}
