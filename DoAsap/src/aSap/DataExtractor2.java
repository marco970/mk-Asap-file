package aSap;

import java.util.ArrayList;
import java.util.Arrays;

public class DataExtractor2 {
	ArrayList<String> ZZ; //0, 10
	ArrayList<String> PZ; //1, 11
	ArrayList<String> DK; //3, 13
	String[] ZZrow; //0, 10
	String[] PZrow; //1, 11
	String[] DKrow; //3, 13
	ArrayList<Integer> ZZday;
	ArrayList<Integer> PZday;
	ArrayList<Integer> DKday;
	
	String monthStr;
	String yearStr;
	MainTableModel mod;
	int rowCount;
	
	//CalendarTest ct; 
	//int dniMies;
	Integer[] ZZHours;
	Integer[] PZHours;
	Integer[] DKHours;
	
	String cPLK, cPLI, cCPO;
	
	public DataExtractor2(MainTableModel model, int month, int year, int dniMies, String u, String w, String v)	{
		
		cPLK = u;
		cPLI = w;
		cCPO = v;
		
		ZZ = new ArrayList<String>();
		PZ = new ArrayList<String>();
		DK = new ArrayList<String>();
		
		ZZday = new ArrayList<Integer>();
		PZday = new ArrayList<Integer>();
		DKday = new ArrayList<Integer>();
		
		if ((month+"").length()==1)	{
			monthStr = "0"+month;
		}
		else	{
			monthStr = month+"";
		}
		
		
		//System.out.println("DE2 monthStr: "+monthStr);
		
		yearStr = year+"";
		mod = model;
		rowCount = model.getRowCount();
		extractData(0);
		extractData(1);
		extractData(3);
				
		//this.dniMies =  dniMies;
		//System.out.println("DE2 dniMies: "+dniMies+" y: "+year);
		
		DataCreator ZZdc = new DataCreator(dniMies);
		DataCreator PZdc = new DataCreator(dniMies);
		DataCreator DKdc = new DataCreator(dniMies);
		
		createData(ZZdc, ZZ, ZZday);
		createData(PZdc, PZ, PZday);
		createData(DKdc, DK, DKday);

		ZZrow = ZZdc.getAll();
		ZZHours = ZZdc.getHours();
		
		PZrow = PZdc.getAll();
		PZHours = PZdc.getHours();

		DKrow = DKdc.getAll();
		DKHours = DKdc.getHours();

	}
	
	public String[] getExRow(String name)	{
		if (name.equals("DK")) return DKrow;	
		else if(name.equals("PZ")) return PZrow;
		else return ZZrow;
		
	}

	public Integer[] getExHours(String name)	{
		if (name.equals("DK")) return DKHours;	
		else if(name.equals("PZ")) return PZHours;
		else return ZZHours;
		
	}
	
	public void createData(DataCreator exDc, ArrayList<String> idEx, ArrayList<Integer> dayEx)	{
		for (int i = 0; i<=dayEx.size()-1; i++)	{
			exDc.addDane(dayEx.get(i), idEx.get(i));
		}

	}

	public void extractData(int position)	{
		for (int i=0; i<=rowCount-1; i++)	{ 
			String a = (String) mod.getValueAt(i, position+10); //to jest data 
			String b = (String) mod.getValueAt(i, 9);
			String cond1 = cPLK;
			String cond2 = cPLI;
			String cond3 = cCPO;
			
			if (!(("").equals(a)||a==null))	{ //czy data istnieje, jak nie, to skok do następnego wiersza
				//System.out.println(a.substring(3, 5)+"  "+a.substring(6, 10));
				if (a.length()>=10)	{
					//System.out.println("DE2 "+a.substring(3, 5)+"  "+a.substring(6, 10));
					if (a.substring(3, 5).equals(monthStr)&&a.substring(6, 10).equals(yearStr)) { //A) czy data spelnia warunek miesiąca i roku
						//System.out.println("DE2 spełnia: "+a.substring(3, 5)+"  "+a.substring(6, 10));
						if(!(cond1.equals(b)||cond2.equals(b)||cond3.equals(b)))	{//B0						//czy nazwa firmy - tu trzeba się zapytać
							if (position == 0)	{
								ZZ.add((String) mod.getValueAt(i, position));
								ZZday.add(Integer.parseInt(a.substring(0, 2)));
							}
							else if (position == 1) {
								PZ.add((String) mod.getValueAt(i, position));
								PZday.add(Integer.parseInt(a.substring(0, 2)));
							}
							else if (position == 3) {
								DK.add((String) mod.getValueAt(i, position));
								DKday.add(Integer.parseInt(a.substring(0, 2)));
							}
							//System.out.println((String) mod.getValueAt(i, position)+"*"+a+" "+i+" "+b);	
						}//koniec B)
						
					}// koniec A)
				}
			}					
		}
	}



}
