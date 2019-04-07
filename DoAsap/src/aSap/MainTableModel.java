package aSap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MainTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private static String[] nazwyKolumn = {"ZZ", "PZ", "WP", "DK", "Status", 
			"Przemiot Zakupu", "Dostawca", "Nazwa", "Tryb postępowania", "Spółka", 
			"dsZZ", "dsPZ", "dsWP", "dsDK" };
	/*
	 * Walidacja
	 */
	//private boolean[] notNull = 
	//private String[] ZZ = {"notNull"};

	//private String current = "F:/aSapData/Current3.txt";
	//private String current = "C:/Users/Lappo/git/AsapJava/aSapData/Current3.txt";
	private String current = "Current4.txt";
	//private String current = "C:/Users/marcin.kuciak/Documents/workIT_projects/doAsap/aSapData/Current3.txt";
	
	private Object[][] dane = null;
	
	public MainTableModel() 	{
		try {
			this.dane=readFile(current);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//----------metody--

	public String getPath()	{
		return current;
	}
	public Object[][] getMatrix()	{
		return dane;
	}
	public Object[] getRowData(int i)	{
		return dane[i];
		
	}

	public String[] getColumns()	{
		return nazwyKolumn ;
	}
	public String[] getColumnsNoDs()	{
		String[] colNoDs = new String[nazwyKolumn.length-getNumberDs()];
		for (int i=0; i<=nazwyKolumn.length-getNumberDs()-1; i++)	{
			colNoDs[i] = nazwyKolumn[i];
		}
		return nazwyKolumn ;
	}
	public String getColumnName(int i)	{
		return nazwyKolumn[i];	
	}
	public boolean doesElExists(int i, int j)	{
		boolean a = true;
		if (i <= getRowCount())	{
			if ((null==getValueAt(i,j))||"".equals(getValueAt(i,j))) a = false;
			else a = true;
		}
		else a = false;
		return a;
	}
	public int getColumnPosition(String ColName)	{
		int j=100;
		for (int i=0; i<=nazwyKolumn.length-1; i++)	{
			if(nazwyKolumn[i].equals(ColName))	{
				j=i;
			}

		}
		return j;
		
	}
	
	@Override
	public int getColumnCount() {
		return nazwyKolumn.length;
	}

	@Override
	public int getRowCount() {
		return dane.length;
	}

	@Override	//===================
	public Object getValueAt(int arg0, int arg1) {
		if (arg0<=getRowCount() && arg1<=getColumnCount()) return dane[arg0][arg1];
		else return "";
	}
	
	public Object[][] readFile(String filePath) throws IOException {
		  FileReader fileReader = new FileReader(filePath);
		  BufferedReader bufferedReader = new BufferedReader(fileReader);
		  
		  String textLine = bufferedReader.readLine();
		  ArrayList<String> linia = new ArrayList<String>();	  
		  do {
		    linia.add(textLine);
		    textLine = bufferedReader.readLine();	    
		  } while(textLine != null);

		  bufferedReader.close();
		  int i =0;
		  Object[][] sdane = new String[linia.size()][nazwyKolumn.length];
		  for (String el: linia)	{
			  	String[] row=el.split(";");
			  	for (int j=0; j<=row.length-1; j++)	{	  		
			  		sdane[i][j]=row[j];
			  		if (sdane[i][j]==null) sdane[i][j]="";
			  	}
			  i++;
		  }
		  int n=1;
		  List<Object[]> wiersz = new ArrayList<Object[]>();
		  wiersz.add(sdane[n]);
		  return sdane;
		}
	public int getNumberDs() {
		int a = 0;
		for (Object el: nazwyKolumn)	{
			if ("ds".equals(((String) el).substring(0, 2))) {
				a++; 
			}
		}
		return a;
	}
	public void recordAdd(Object[] savedRow) {	//--zapis do DB
		int n = getRowCount()+1;
		//System.out.println("recordAdd, n: "+n + " ilość wierszy :"+getRowCount());
		Object[][] daneUpd = new Object[n][nazwyKolumn.length];
		for (int i = 0; i<= n-1; i++)	{
			if (i<=n-2) daneUpd[i]=dane[i];
			else 		daneUpd[i]=savedRow;
		}
		dane=daneUpd;
		fireTableRowsInserted(n-1, n-1);
		fireTableDataChanged();
	}
	public void recordUpdate(Object[] savedRow, int rowNr) { //--zapis do DB
		ArrayList<Object[]> rowList = new ArrayList<Object[]>();
		for (int i = 0; i<=getRowCount()-1; i++)	{
			rowList.add(dane[i]);
		}
		//System.out.println("recordUpdated rowNr: "+rowNr + " ilość wierszy :"+getRowCount());
		rowList.set(rowNr, savedRow);
		Object[][] daneUpd = new Object[rowList.size()][nazwyKolumn.length];
		int j = 0;
		for (Object[] el: rowList)	{
			daneUpd[j]=el;
			j++;
		}
		dane=daneUpd;
		fireTableRowsUpdated(rowNr, rowNr);
		fireTableDataChanged();	
		
	}
	public void cellUpdate(Object value, int rowNr, int kolNr)	{ //--zapis do DB
		dane[rowNr][kolNr] = value;
		fireTableCellUpdated(rowNr, kolNr);
	}
}
