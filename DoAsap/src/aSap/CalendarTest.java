package aSap;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarTest {
	
	

	  static Calendar kalend = new GregorianCalendar();

	  static String[] nazwaDnia = {  "nd", "pon", "wt",
	                                 "śr", "czw", "pt", "sb" };

	  static String[] nazwaMies = { "styczeń", "luty", "marzec", "kwiecień",
	                           "maj", "czerwiec", "lipiec", "sierpień",
	                           "wrzesień", "październik", "listopad", "grudzień"
	                         };
	  static int[] ldni = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	  int rok;
	  int mies;

	  public CalendarTest(int rok, int month) {
	     this.rok = rok;
	     this.mies = month;
	     //System.out.println("CT: mies " +mies+" "+nazwaMies[mies]+ " y: "+ rok+ " l_dni: "+getDayNo(month));
	     //kalend.set(rok, mies);
	     /*
	     for (int i = 1; i<=ldni[mies]; i++)	{
	    	 kalend.set(rok, mies, i);
	    	 System.out.println("dziś jest: "+i+ " "+nazwaMies[mies]+ " roku "+rok+" "+nazwaDnia[kalend.get(Calendar.DAY_OF_WEEK)-1]);
	     }
	     */
	  }
	  public int getDayNo(int month)	{
		  int a = ldni[mies];
		  if (month == 1 && rokPrzest()) a = 29;
		  return a;
	  }
	  public boolean rokPrzest()	{
		  //System.out.println("CT_rokPrzest: "+rok);
		  boolean a = false;
		  if ((rok - 2016)%4 == 0) a = true;
		  return a;
	  }
	  
	  public String getDayName(int dayNo) {
		  String CalendarOutput ="";
		  kalend.set(rok, mies, dayNo);
		  CalendarOutput = nazwaDnia[kalend.get(Calendar.DAY_OF_WEEK)-1];
		  
		  return CalendarOutput;
	  }
	  public String getDate(int dayNo)	{
		  String date = "";
		  date = dayNo+"/"+(mies+1)+"/"+rok;
		  return date;
	  }
}
