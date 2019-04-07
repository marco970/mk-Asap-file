package aSap;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JSeparator;

public class OpForm1 implements ActionListener, FocusListener {

	private int colCount;
	private MainTableModel model;
	
	private int rowNr;
	
	private String[] validateArr;
	private Object[] tfAll;
	
	private ErrMessage errMessage;
	
	JFrame opForm;

	public OpForm1(String nazwa, int rowNr, MainTableModel mod, ErrMessageShow errMS)  {
		
		errMessage = new ErrMessage(mod);
		String[] errMessageStr = errMessage.getErrMessage();
		errMessage.addPropertyChangeListener(errMS); //potrzebne?
				
		this.model = mod;
		this.rowNr = rowNr;
		this.colCount=model.getColumnCount();
		this.validateArr = new String[model.getColumnCount()-model.getNumberDs()];
		
		//ramka
		opForm = new JFrame();
		opForm.setTitle(nazwa);
		opForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		opForm.setVisible(true);
		opForm.setBounds(100, 100, 450, 600);
		//panel
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		opForm.setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow][][]"));//różnica
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 0,grow");
		
		//int colCount = model.getColumnCount();
		
		//definicje od rysunku-----------------------
		JLabel[] nazwaPola = new JLabel[colCount];
		
		Object[] a = new Object[colCount];
		Object[] b = new Object[colCount];
		String migLayRowNo = "";
		String[] targetNazwaPola = new String[colCount];
		String[] targetField = new String[colCount];
		String[] targetErrMessage = new String[colCount];	
		for (int i = 0; i<=colCount-1; i++)	{
			migLayRowNo = migLayRowNo+"[]";
			targetNazwaPola[i] = "cell 0 "+ i;
			targetField[i] = "cell 1 "+ i;
			targetErrMessage[i] = "cell 2 "+ i;
		}
		JLabel[] errMessage = errMS.getErrMessageLab();
		
		
		//rysujemy-----------------------------------
		panel.setLayout(new MigLayout("", "[][][]", migLayRowNo));
		for (int i = 0; i<=colCount-1-model.getNumberDs(); i++) {
			nazwaPola[i] = new JLabel(model.getColumnName(i));
			panel.add(nazwaPola[i], targetNazwaPola[i]);
			if (i==4)	{
				String[] strA5 = {"open","done","on hold"}; //do modelu
				System.out.println(i+" - "+rowNr);
				String defaultStatus = (String) model.getValueAt(rowNr, i);
				JComboBox<String> a5 = (JComboBox<String>) new JComboBox<String>(strA5);
				a5.setSelectedItem(defaultStatus);
				a[i]=a5;
				b[i]=a5;
			}
			else if (i==8)	{
				String[] strA5 = {"przetarg","z wolnej ręki","inne"};//ściągać z modelu
				String defaultTryb = (String) model.getValueAt(rowNr, i);
				JComboBox<String> a5 = new JComboBox<String>(strA5);
				a5.setSelectedItem(defaultTryb);
				a5.setName(model.getColumnName(i));
				a[i]=a5;
				b[i]=a5;
			}
			else if (i==9)	{
				//String[] strA5 = {"PLK","PLI","CPO"};
				String defaultSpolka = (String) model.getValueAt(rowNr, i);
				//JComboBox<String> a5 = new JComboBox<String>(strA5);
				JLabel a5 = new JLabel(defaultSpolka);
				//a5.setSelectedItem(defaultSpolka);
				a5.setName(model.getColumnName(i));
				a[i]=a5;
				b[i]=a5;
			}
			else if (i==5)	{
				JTextArea a5 = new JTextArea(5, 15);
				JScrollPane scrl = new JScrollPane(a5);
				a[i]=a5;
				b[i]=scrl;
				((Component) a[i]).setName(model.getColumnName(i));

				if (rowNr<model.getRowCount())	{
					if (model.getValueAt(rowNr, i)!=null) 	((JTextComponent) a[i]).setText(model.getValueAt(rowNr, i).toString());
					else										((JTextComponent) a[i]).setText("");
				}

			}

			
			else	{
				JTextField a5 = new JTextField(8);

				a[i]=a5;
				

				b[i]=a5;
				if (rowNr<model.getRowCount())	{
					if (model.getValueAt(rowNr, i)!=null) 	((JTextComponent) a[i]).setText(model.getValueAt(rowNr, i).toString());
					else										((JTextComponent) a[i]).setText("");
				}
				
				((Component) a[i]).setName(model.getColumnName(i));
				if (i<=3)	{
					( (Component) a[i]).addFocusListener(this);
				}
			}
				
			panel.add((Component) b[i], targetField[i]);
			errMessage[i] = new JLabel(errMessageStr[i]);//--------!
			panel.add(errMessage[i], targetErrMessage[i]);
			
			errMessage[i].setHorizontalAlignment(SwingConstants.LEFT);
			errMessage[i].setForeground(Color.RED);
			errMessage[i].setFont(new Font("Tahoma", Font.PLAIN, 9));	
						
		}//koniec dużego for-----------------------
		tfAll = a;
		//przycisk---------------------------------------
		JButton btnSave = new JButton("save");
		btnSave.addActionListener(this);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);
		separator.setVisible(true);
		contentPane.add(separator, "cell 0 1");
		btnSave.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(btnSave, "cell 0 2");
	
		//dalej
		
	}//koniec konstruktora

	public Object[] DsIterator(String dateString, Object[] savedRow, int liczbaWierszy, int liczbaDs, int currRow)	{
		for (int i = 0; i <= liczbaDs-1; i++)	{
			if (!"".equals(savedRow[i])&&!model.doesElExists(currRow, i))	{
				savedRow[colCount-liczbaDs+i]=dateString;
			}
			else	{
				savedRow[colCount-liczbaDs+i]=model.getValueAt(currRow, colCount-liczbaDs+i);
			}
		}
		return savedRow;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {

		int liczbaDs = model.getNumberDs();
		int liczbaWierszy = model.getRowCount();
		
		boolean test = true;
	    Date currentDate = new Date();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss");
	    String dateString = dateFormat.format(currentDate);
	    //System.out.println(colCount);
		Object[] savedRow = new Object[colCount];
		String[] rowAll = new String[tfAll.length];
		for (int i = 0; i <= colCount-1; i++)	{
			if (i<=colCount-1-liczbaDs)	{
				if (i==4 || i==8 || i==9) {
		    		rowAll[i]=(String) ( (JComboBox<String>) tfAll[i]).getSelectedItem();
		    	}
		    	else	{
		    		if (tfAll[i]==null)	{
		    			rowAll[i]=" ";
		    		}
		    		else {
		    			rowAll[i]= ((JTextComponent) tfAll[i]).getText();
		    		}
		    	}
				
				savedRow[i]=rowAll[i];
			}
			else	{
				savedRow[i]=model.getValueAt(rowNr, i);
			}
			System.out.println(savedRow[i]+" ** "+i);
		}

		//--odczyt z okienek

		//start DS
		
		savedRow = DsIterator(dateString, savedRow, liczbaWierszy, liczbaDs, rowNr);
		//EoDS
		//walidacja
		
		Validator vali = new Validator(savedRow, model);
		validateArr = (String[]) vali.getMessageArray();
		for (String el: validateArr)	{
			//System.out.println("OknoF: "+el);
		}
		errMessage.setErrMessage(validateArr);		//to poprawić, 
													//powinno być chyba w rozsądniejszym miejscu
		test = vali.getValDone();
		//System.out.println("test: "+test+" rowNr: "+rowNr+" liczba wierszy: "+ liczbaWierszy);

		
		//EoWalidacja
		if(test==true)	{
			//if(rowNr>liczbaWierszy) model.recordAdd(emtyRow());
			if(rowNr>liczbaWierszy) model.recordAdd(savedRow);
			else model.recordUpdate(savedRow, rowNr);
			try {new Zapis(model);} catch (IOException e1) {e1.printStackTrace();}
			opForm.setVisible(false); //to jakimś cudem działa dobrze
		}
	}//koniec metody actionPerformed

	public void addChangeListener(EkranGlowny ekranGlowny) { 
		//nie używana ale musi zostać
	}

	@Override
	public void focusGained(FocusEvent e) {
		//nic się nie dzieje
	}

	@Override
	public void focusLost(FocusEvent e) {
		System.out.println(((JTextComponent) e.getSource()).getText());
		
		String odFocus = ((JTextComponent) e.getSource()).getText();
		String odFocusName = e.getComponent().getName();
		

		
	}
}//koniec klasy

