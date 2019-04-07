package aSap;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

public class OpForm2 implements ActionListener, FocusListener {

	private int colCount;
	private MainTableModel model;
	
	private int rowNr;
	
	//private String[] validateArr;
	private Component[] tfAll;
	
	//private ErrMessage errMessage;
	
	private JFrame opForm;
	private ArrayList<Component> listaComp = new ArrayList<Component>();		//out - jednak nie potrzeba, stara konstrukcja działa ok
	
	///
	JComboBox<String> statusPole;
	JComboBox<String> trybPole;
	
	JLabel errPZLab; 
	JLabel errWPLab; 
	JLabel errDKLab; 
	
	
	JPanel panel;
	
	
	private JButton btnSave;
	private JButton btnClose;
	private JButton btnNext;

	public OpForm2(String nazwa, int rowNo, MainTableModel mod)  {
		
		
		/*errMessage = new ErrMessage(mod); //out
		String[] errMessageStr = errMessage.getErrMessage();	//out
		errMessage.addPropertyChangeListener(errMS); //potrzebne?//out
		*/
				
		this.model = mod;
		this.rowNr = rowNo;
		this.colCount=model.getColumnCount();
		//this.validateArr = new String[model.getColumnCount()-model.getNumberDs()]; //out
		
		//ramka
		//-----------to do wyjebki 
		opForm = new JFrame();
		opForm.setTitle(nazwa);
		opForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		opForm.setVisible(true);
		opForm.setBounds(100, 100, 460, 600);
		//panel
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		opForm.setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow][][]"));//różnica
		//-------------dotąd
		panel = new JPanel();	//tu metoda klasy dziedziczonej
		contentPane.add(panel, "cell 0 0,grow");

		//definicje od rysunku-----------------------
		JLabel[] nazwaPola = new JLabel[colCount];
		
		Component[] a = new Component[colCount];
		Component[] b = new Component[colCount];
		String migLayRowNo = "";
		String[] targetNazwaPola = new String[colCount];
		String[] targetField = new String[colCount];
		//String[] targetErrMessage = new String[colCount];	//out
		for (int i = 0; i<=colCount-1; i++)	{
			migLayRowNo = migLayRowNo+"[]";
			targetNazwaPola[i] = "cell 0 "+ i;
			targetField[i] = "cell 1 "+ i;
			//targetErrMessage[i] = "cell 2 "+ i; //out
		}
		//JLabel[] errMessage = errMS.getErrMessageLab();	//out
		
		
		//rysujemy-----------------------------------
		panel.setLayout(new MigLayout("", "[][][]", "[][][]"));
		panel.add(new JLabel("Edycja danych postępowania"), "dock north");
		
		errPZLab = new JLabel();
		errPZLab.setHorizontalAlignment(SwingConstants.LEFT);
		errPZLab.setForeground(Color.RED);
		errPZLab.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panel.add(errPZLab, "cell 2 1");
		
		errWPLab = new JLabel();
		errWPLab.setHorizontalAlignment(SwingConstants.LEFT);
		errWPLab.setForeground(Color.RED);
		errWPLab.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panel.add(errWPLab, "cell 2 2");
		
		errDKLab = new JLabel();
		errDKLab.setHorizontalAlignment(SwingConstants.LEFT);
		errDKLab.setForeground(Color.RED);
		errDKLab.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panel.add(errPZLab, "cell 2 3");
		
		
		for (int i = 0; i<=colCount-1-model.getNumberDs(); i++) {
			nazwaPola[i] = new JLabel(model.getColumnName(i));
			panel.add(nazwaPola[i], targetNazwaPola[i]);
			if (i==4)	{
				String[] strA5 = {"aktywne","zakonczone","zawieszone"}; //do modelu
				String defaultStatus = (String) model.getValueAt(rowNr, i);
				statusPole = (JComboBox<String>) new JComboBox<String>(strA5);
				statusPole.setSelectedItem(defaultStatus);
				a[i]=statusPole;
				b[i]=statusPole;
				listaComp.add(statusPole);
			}
			else if (i==8)	{
				String[] strA5 = {"przetarg","z wolnej ręki","inne"};//ściągać z modelu
				String defaultTryb = (String) model.getValueAt(rowNr, i);
				trybPole = new JComboBox<String>(strA5);
				trybPole.setSelectedItem(defaultTryb);
				trybPole.setName(model.getColumnName(i));
				a[i]=trybPole;
				b[i]=trybPole;
				listaComp.add(trybPole);
			}
			else if (i==9)	{
				String defaultSpolka = (String) model.getValueAt(rowNr, i);
				JLabel a5 = new JLabel(defaultSpolka);
				a5.setName(model.getColumnName(i));
				a[i]=a5;
				b[i]=a5;
				listaComp.add(a5);
			}
			else if (i==0)	{
				String defaultZZ = (String) model.getValueAt(rowNr, i);
				JLabel a5 = new JLabel(defaultZZ);
				a5.setName(model.getColumnName(i));
				a[i]=a5;
				b[i]=a5;
				listaComp.add(a5);
			}
			else if (i>0 && i<=3)	{
	
				if (model.doesElExists(rowNr, i))	{
					
					JLabel a5 = new JLabel((String) model.getValueAt(rowNr, i));
					a5.setName(model.getColumnName(i));

					a[i]=a5;
					listaComp.add(a5);
					b[i]=a5;
				}
				else	{
					JTextField a5 = new JTextField(13);
					if (i>1)	{
						if (!model.doesElExists(rowNr, i-1))	a5.setEnabled(false);
						
					}
					a[i]=a5;
					listaComp.add(a5);
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
				//System.out.println("asd "+i +"  -> "+b[i].toString());
			}
			else if (i==5)	{
				JTextArea a5 = new JTextArea(5, 15);
				a5.setLineWrap(true);
				a5.setWrapStyleWord(true);
				
				JScrollPane scrl = new JScrollPane(a5);
				a[i]=a5;
				b[i]=scrl;
				listaComp.add(a5);
				((Component) a[i]).setName(model.getColumnName(i));

				if (rowNr<model.getRowCount())	{
					if (model.getValueAt(rowNr, i)!=null) 	((JTextComponent) a[i]).setText(model.getValueAt(rowNr, i).toString());
					else										((JTextComponent) a[i]).setText("");
				}

			}
			else	{
				JTextField a5 = new JTextField(13);

				a[i]=a5;
				listaComp.add(a5);

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
			if (i==1)	{
				panel.add(errPZLab, "cell 2 1");
			}
			else if (i==2)	{
				panel.add(errWPLab, "cell 2 2");
			}
			else if (i==3)	{
				panel.add(errDKLab, "cell 2 3");
			}
			
		}//koniec dużego for-----------------------
		tfAll = a;
		//przycisk---------------------------------------
		btnSave = new JButton("Zapisz");
		btnClose = new JButton("Anuluj");
		btnNext = new JButton("Dalej");
		btnSave.addActionListener(this);
		btnClose.addActionListener(this);

		btnSave.setHorizontalAlignment(SwingConstants.LEFT);
		
		contentPane.add(btnClose, "cell 0 2");
		contentPane.add(btnNext, "cell 0 2");
		contentPane.add(btnSave, "cell 0 2");
	
		//dalej
		//nowe etykiety błędów-----------
	
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
		//System.out.println(e.getActionCommand());
		if (e.getActionCommand().equals("Anuluj"))	{
			opForm.dispose(); 
		}

		//--odczyt z okienek
		
		if (e.getActionCommand().equals("Zapisz")) {
			int liczbaDs = model.getNumberDs();
			int liczbaWierszy = model.getRowCount();
			
			Date currentDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			String dateString = dateFormat.format(currentDate);
			Object[] savedRow = new Object[colCount];
			String[] rowAll = new String[tfAll.length];
			for (int i = 0; i <= colCount - 1; i++) {
				if (i <= colCount - 1 - liczbaDs) {
					if (i == 4 || i == 8) {
						rowAll[i] = (String) ((JComboBox<String>) tfAll[i]).getSelectedItem();
					} else if (i == 0 || i == 9) {
						rowAll[i] = ((JLabel) tfAll[i]).getText();
					} else if (i > 0 && i <= 3) {
						//System.out.println("nr: "+i+"; "+ model.doesElExists(rowNr, i)+" -- "+ model.getValueAt(rowNr, i));
						if (model.doesElExists(rowNr, i))
							rowAll[i] = ((JLabel) tfAll[i]).getText();
						else
							rowAll[i] = ((JTextComponent) tfAll[i]).getText();
					} else {
						if (tfAll[i] == null) {
							rowAll[i] = " ";
						} else {
							rowAll[i] = ((JTextComponent) tfAll[i]).getText().replaceAll("\n", " ").replaceAll("\t", " ").replaceAll(";", ",");
						}
					}
					savedRow[i] = rowAll[i];
				} else {
					savedRow[i] = model.getValueAt(rowNr, i);
				}
				//System.out.println(i+" ---- "+savedRow[i]+" "+model.getValueAt(rowNr, i) );

			}
			savedRow = DsIterator(dateString, savedRow, liczbaWierszy, liczbaDs, rowNr);
			//EoDS
			//Zmiana folderu
			FolderCreator folder = new FolderCreator();
			String numerZZ = model.getValueAt(rowNr, 0).toString().substring(6);
			String myPath = "";
			//System.out.println();
			//System.out.println("Zmiana Statusu z: " + model.getValueAt(rowNr, 4) + " na: " + savedRow[4]);
			if (savedRow[4].equals("zakonczone") && !model.getValueAt(rowNr, 4).equals("zakonczone")) {
				//System.out.println("Zmieniamy lokalizację folderu z " + folder.getAktywne());
				myPath = folder.getDefaultPath() + folder.getAktywne();
				findMoveFolder(numerZZ, myPath, true);
			} 
			else if (savedRow[4].equals("aktywne") && model.getValueAt(rowNr, 4).equals("zakonczone")) {
				//System.out.println("Zmieniamy lokalizację folderu z " + folder.getZamkniete());
				myPath = folder.getDefaultPath() + folder.getZamkniete();
				findMoveFolder(numerZZ, myPath, false);
			}
			else {
				myPath = folder.getDefaultPath();
			}

			model.recordUpdate(savedRow, rowNr);
			try {
				new Zapis(model);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			opForm.dispose(); //to jakimś cudem działa dobrze
		}
		//System.out.println(e.getActionCommand());
	}//koniec metody actionPerformed
	public void findMoveFolder(String numerZZ, String myPath, boolean tam)	{
		if (myPath.length()>0)	{
			//System.out.println(myPath + " -- " + myPath.length());
			//int l = myPath.length();
			//String[] fragmentZZ = new String[myPath.length()];
			File[] directories = new File(myPath).listFiles(File::isDirectory);
			//System.out.println(" --> " + directories.length);
			if (directories.length > 0) {
				for (int i = 0; i <= directories.length - 1; i++) {
					//System.out.println(directories[i].toString().substring(myPath.length(), +myPath.length() + 7)+" ---> "+numerZZ);
					if (directories[i].toString().substring(myPath.length(), +myPath.length() + 7).equals(numerZZ))	{
						new FolderCreator().moveFolder(directories[i].toString().substring(myPath.length()), tam);
					}
				}
			}
		}
		
	}
	public void elReplace(Component added, Component removed, JPanel p, String migTarget)	{	//czy aby na pewno potrzebujemy tej metody?
		p.remove(removed);
		p.add(added,migTarget);
	}
	public String getPrecedValue(int i)	{
		String a = "";
		if (i==1 || i==2 || i==3) a=((JTextComponent) tfAll[i]).getText();	
		return a;
	}

	@Override
	public void focusGained(FocusEvent e) {
		btnNext.setEnabled(true);
		btnSave.setEnabled(false);	
	}

	@Override
	public void focusLost(FocusEvent e) {	/*
											/-------> kod tej metody do poprawy. Zmiana konstruktorów SingleFieldValidator + setter na 2 pierwsze parametry
											 * a moze wystarczy jedynie setter a konstruktor zostawić....
											*/

		SingleFieldValidator valCheck = new SingleFieldValidator();

		if (((JTextComponent) e.getSource()).getName().equals("PZ")) {
			SingleFieldValidator zzVal = new SingleFieldValidator("PZ", ((JTextComponent) e.getSource()).getText(), model, rowNr, this);
			//System.out.println(zzVal.getErrMessage()+" 1");
			errPZLab.setText(zzVal.getErrMessage());
			//errPZLab.setEnabled(true);
			//System.out.println(errPZLab.getText()+" 2");
			valCheck = zzVal;
		}
		else if (((JTextComponent) e.getSource()).getName().equals("WP")) {
			SingleFieldValidator zzVal = new SingleFieldValidator("WP", ((JTextComponent) e.getSource()).getText(), model, rowNr, this);
			//System.out.println(zzVal.getErrMessage()+" 1");
			errWPLab.setText(zzVal.getErrMessage());
			//errWPLab.setEnabled(true);
			//System.out.println(errWPLab.getText()+" 2");
			valCheck = zzVal;
		}
		else if (((JTextComponent) e.getSource()).getName().equals("DK")) 
		{
			SingleFieldValidator zzVal = new SingleFieldValidator("DK", ((JTextComponent) e.getSource()).getText(), model, rowNr, this);
			//System.out.println(zzVal.getErrMessage()+" 1");
			errDKLab.setText(zzVal.getErrMessage());
			//errDKLab.setEnabled(true);
			//System.out.println(errDKLab.getText()+" 2");
			valCheck = zzVal;
		}
		if(!valCheck.getValidationResult())	{
			btnSave.setEnabled(false);
			btnNext.setEnabled(true);
		}
		else {
			/*
			 * tu trzebaby sprawdzić, czy zmieniły się dane we wszysktich okienkach - na razie mi się nie chce
			if (!((JTextComponent) e.getSource()).getText().equals(""))	{
				btnSave.setEnabled(true);
			}
			*/
			btnSave.setEnabled(true);
			btnNext.setEnabled(false);
		}
		SwingUtilities.updateComponentTreeUI(opForm);
		opForm.invalidate();
		opForm.validate();
		opForm.repaint();
		
	}

}//koniec klasy

