package aSap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SingleFieldValidator {
	
	private boolean valDone = true;
	private String errMessage = "";
	private String fieldName = "";
	private MainTableModel model;
	private String vendor="";
	private String[] colData;
	private int colPosition;
	private Object[][] o;
	private String spolka="";
	private int rowNr;
	private OpForm2 opF;
	
	public SingleFieldValidator(){}
	
	public SingleFieldValidator(String fieldName, String fieldValue, MainTableModel model, int rowNo)	{//ten konstruktor do newForm
		this.fieldName = fieldName;
		this.model = model;
		this.rowNr = rowNo;
		
		int i = model.getColumnPosition(fieldName);
		this.colPosition=i;
		this.o = model.getMatrix();
		
		for (Object[] el: o)	{
			if (el[i]==null) el[i] = "";//tu na pewno zastępuje nula pustym stringiem, co jest potrzebne
			//colData[i]=(String) el[i];
			//System.out.println(el[i]);
		}
		ValidatioModel valModel = new ValidatioModel();
		String[] b = valModel.getValArray(fieldName);
		for(String el: b)	{
			//System.out.println(el+" - "+fieldValue);
			runMethod(el, fieldValue);
		}
		///System.out.println(valDone+ " *** "+errMessage);
		//doesExist(fieldValue);  //-----------UWAGA, nie wiem, do czego to było...
		//spolka
		//System.out.println("**_ "+spolka+"row "+ rowNr+ "aaa "+model.getValueAt(rowNr, 0).toString());
		
		
	}//koniec konstruktora 1
	public SingleFieldValidator(String fieldName, String fieldValue, MainTableModel model, int rowNo, OpForm2 opF) {// nie wiem, do czego ten opF? Moze zeby rozróznic konstr
		this.fieldName = fieldName;
		this.model = model;
		this.rowNr = rowNo;
		
		int i = model.getColumnPosition(fieldName);
		this.colPosition=i;
		this.o = model.getMatrix();
		
		for (Object[] el: o)	{
			if (el[i]==null) el[i] = "";//tu na pewno zastępuje nula pustym stringiem, co jest potrzebne
		}
		ValidatioModel valModel = new ValidatioModel();
		String[] b = valModel.getValArray(fieldName);
		for(String el: b)	{
			runMethod(el, fieldValue);
		}
		spolka="";
		if (fieldName.equals("ZZ"))	{
			if (fieldValue.length()>6)	spolka=fieldValue.substring(3, 6);
		}
		else spolka=model.getValueAt(rowNr,9).toString();
		this.opF = opF;
	}
	
	public boolean getValidationResult()	{
		return valDone;
	}
	public String getErrMessage()	{
		return errMessage;
	}
	//odpalanie metod
	public void runMethod(String methName, String fieldValue)	{
		Method[] methArray = this.getClass().getDeclaredMethods();
		for (Method methEl: methArray)	{
			//System.out.println("*"+methEl.getName()+" - "+methName);
			if (methEl.getName().equals(methName))	{
				//tu odpalam po prostu metodę
				try {
					methEl.invoke(this,fieldValue);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	//metody walidacyjne
	public void isPredecessor(String field)	{
		//System.out.println("poprzedniki"+model.getColumnName(2)+" - "+model.getColumnName(3)+" - "+ field +" -- " +fieldName);
		String[] errMessage = {
				"najpierw uzupełnij lub zapisz numer PZ",
				"najpierw uzupełnij lub zapisz numer WP"
		};
		if(!("".equals(field)||field==null))	{
			for (int i=2; i<=3; i++)	{
				
				if(model.getColumnName(i).equals(fieldName))	{
					//System.out.println("spr el i-1: "+model.getValueAt(rowNr, i-1));
					//System.out.println("spr el i-1: "+model.doesElExists(rowNr, i-1));
					if(model.doesElExists(rowNr, i-1))	valOrg(true,"");
					//else if (!opF.getPrecedValue(i-1).equals("")) valOrg(true,"");
					else valOrg(false,errMessage[i-2]);
				}
			}
		}
	}
	
	public void notNull(String field)	{	
			if("".equals(field)||field==null) {
				valOrg(false,"pole nie może być puste");
			}
			else valOrg(true,"");
		}
		public void toShort(String field)	{ 	//dodać dopuszczenie 0
			int dl = field.length();			//dodać definicję długości w zależności od pola
			int l = 13;
			if (dl==l || dl==0)	{
				valOrg(true,"");
			}
			else {
				valOrg(false,"nieprawidłowa długość numeru");
			}
		}
		public void doesExist(String field)	{
			
			
			for (Object[] el: o)	{
				int i = colPosition;

				if (!el[i].equals(field)||el[i].equals(""))	valOrg(true,"");
				else valOrg(false,"postępowanie o tym numerze już istnieje");
			}
		}
		public void checkFormat(String field)	{	//dorobić dla PZ
			if (field.length()>=13)	{
				String fstPart = field.substring(0, 3);
				String sndPart = field.substring(3, 6);
				String trdPart = field.substring(6,13);
				spolka="";
				if (fieldName.equals("ZZ"))	{
					if (field.length()>6)	spolka=field.substring(3, 6);
				}
				else spolka=model.getValueAt(rowNr,9).toString();

				//tu dorobić dla PZ
				if (fstPart.equals(fieldName+"/"))	valOrg(true,"");
					else valOrg(false,"nieprawidłowy format numeru_1");
				//if PZ
				if (fieldName.equals("PZ"))	{
					String pzPart = field.substring(3, 13);
					if(pzPart.matches("[0-9]{10}")) valOrg(true,"");
					else valOrg(false,"nieprawidłowy format numeru_2,5");
				}
				// else to ->
				else	{
					if (sndPart.equals("PLK") || sndPart.equals("PLI") || sndPart.equals("CPO") ) {
						valOrg(true,"");
					}
					else valOrg(false,"nieprawidłowy format numeru_2");
					if(trdPart.matches("[0-9]{7}")) valOrg(true,"");
					else valOrg(false,"nieprawidłowy format numeru_3");
					
					if (fieldName.equals("WP"))	{
						//System.out.println(sndPart +" fN-> "+fieldName );
						if (sndPart.equals(spolka))	{
							valOrg(true,"");
						}
						else {
							valOrg(false,"nieprawidłowy format numeru_4");
							//System.out.println("I am here "+ fieldName);
						}
						//System.out.println("fieldName: "+fieldName+" fstPart: "+fstPart+" sndPart: "+sndPart+" spolka "+spolka);
					}
					if (fieldName.equals("DK"))	{
						System.out.println(sndPart +" fN-> "+fieldName );
						if (sndPart.equals(spolka))	{
							valOrg(true,"");
						}
						else {
							valOrg(false,"nieprawidłowy format numeru_4");
							System.out.println("I am here "+ fieldName);
						}
						//System.out.println("fieldName: "+fieldName+" fstPart: "+fstPart+" sndPart: "+sndPart+" spolka "+spolka);
					}
				}
			}
		}
		//EoMetodyWalidacyjne	
		
		//metody pomocnicze
	public void valOrg(boolean val, String errMsg)	{
		if (valDone) valDone = val;
		if (errMessage.equals(""))	errMessage = errMsg;
	}
	public String getSpolka()	{
		return spolka;
	}

}//koniec klasy

