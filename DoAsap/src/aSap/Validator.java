package aSap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Validator {
	
	private String[] messageArr;
	private Object[] validatedRow;
	MainTableModel model;
	int rColCount;
	boolean[] whatVal;
	boolean valDone;

	//public Validator() {}
	
	public Validator(Object[] row, MainTableModel model)	{
		this.model=model;
		this.valDone = true;
		rColCount = model.getColumnCount()-model.getNumberDs()-1;
		messageArr = new String[rColCount+1];
		this.validatedRow = row;
		whatVal = new boolean[model.getColumnsNoDs().length];
		ValidatioModel valModel = new  ValidatioModel(); 
		Class<? extends ValidatioModel> cl = valModel.getClass();
		Field[] fields = cl.getDeclaredFields();
		String[] test = {""} ;

		// Tworzymy wektor do decyzji, które pola walidować
		for (int j=0; j<=rColCount-1; j++)	{
			whatVal[j] = false;
			for(int i=0; i<=fields.length-1; i++)	{
				if (fields[i].getName().equals(model.getColumnsNoDs()[j])) whatVal[j] = true;				
			}		
		}
		//----------
		for (int i=0; i<=rColCount-1; i++)	{
			if (whatVal[i])	{
				try {
					//System.out.println(valModel.getField(model.getColumnName(i)));
					test = (String[]) valModel.getField(model.getColumnName(i)).get(valModel);
					for(int l=0; l<=test.length-1;l++)	{
						//System.out.println(test[l]+" "+ l);
						whichVal(test[l], i);
						//System.out.println(messageArr[i]);
					}
				} catch (SecurityException | IllegalArgumentException | ReflectiveOperationException e) {
					e.printStackTrace();
				}
			}
		}
		for(String el: messageArr)	{
			//System.out.println("Val: "+ el);
		}
	}	//koniec konstruktora
	//metody walidacyjne
	public String notNull(Object field)	{	
		if("".equals(field)||field==null) {
			valDone = false;
			return "pole nie może być puste";
		}
		else return "";
	}
	public String toShort(Object field, int l)	{ 	//dodać dopuszczenie 0
		int dl = field.toString().length();			//dodać definicję długości w zależności od pola
		if (dl<l && dl>0)	{
			valDone = false;
			return "zbyt mało znaków";
		}	
		return "";
	}
	public String toTest(Object field, String abr)	{ //dodać dopuszczenie 0
		int check = abr.length();
		String twoLett = "";
		if(field.toString().length()>=check ) twoLett = field.toString().substring(0, 2);

		
		//System.out.println(twoLett);
		if (!abr.equals(twoLett) && !"".equals(twoLett))	{
			valDone = false;
			return "nieprawidłowy format";
		}	
		return "";
	}
	
	
	
	//EoMetodyWalidacyjne
	public Object[] getMessageArray()	{	
		return messageArr;
	}
	public boolean getValDone()	{					//upewnić się, że w każdej metodzie walidacyjnej 
													//dodane jest valDone = false;
		//System.out.println("z Val, z metody: "+valDone);
		return valDone;
	}
	public void whichVal(String name, int i)	{	//metoda waliduje stosując odpowiednią funkcję
		//messageArr[i]="";
		String abr = model.getColumnName(i);
		switch (name)	{							//metoda musi być uzupełniona po dodaniu kolejnych 
		case "notNull":								//funkcji do walidacji
			if (!"".equals(notNull(validatedRow[i]))) messageArr[i]=notNull(validatedRow[i]);
			break;
		case "toShort":
			if (!"".equals(toShort(validatedRow[i], 5)))	{
				if (!"pole nie może być puste".equals(messageArr[i]))	{
					messageArr[i]=toShort(validatedRow[i],5);
				}
			}

			break;
		case "toTest":
			if (!"".equals(toTest(validatedRow[i], abr)))	{
				if(!"pole nie może być puste".equals(messageArr[i]))	{
					if(!"zbyt mało znaków".equals(messageArr[i]))	{
						messageArr[i]=toTest(validatedRow[i],abr);
					}
				}
			}
			//if ((!"pole nie może być puste".equals(messageArr[i])||!"zbyt mało znaków".equals(messageArr[i])&&!"".equals(toTest(validatedRow[i])) )) messageArr[i]=toTest(validatedRow[i]);
			break;
			
		}
	}
}
