package aSap;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


import net.miginfocom.swing.MigLayout;

public class OknoForm implements ActionListener {

	/**
	 * Chyba to trzeba przepisać od nowa.
	 * 
	 */
	
	private JFrame a;
	private int colCount; //
	private JTextField[] tfAll;
	private MainTableModel model;
	private int rowNr ;
	private JPanel contentPane;
	private GroupLayout gl_contentPane;
	private JPanel panel;
	private String[] validateArr;
	private ErrMessage errMessage;
	private JButton btnSave;
	private ErrMessageShow errMS;
	
	public OknoForm(String nazwa, int rowNr, MainTableModel mod, ErrMessageShow errMS)	{
				
		this.errMS = errMS;
		errMessage = new ErrMessage(mod);
		errMessage.addPropertyChangeListener(errMS);
		this.model=mod;
		a = new JFrame(nazwa);
		this.rowNr=rowNr;
		this.colCount = model.getColumnCount();
		this.contentPane = new JPanel();
		this.gl_contentPane = new GroupLayout(contentPane);
		this.panel = new JPanel();
		this.validateArr = new String[model.getColumnCount()-model.getNumberDs()];
		for (int i=0; i<=model.getColumnCount()-model.getNumberDs()-1; i++)	{
			validateArr[i]="";
		}

		tfAll = new JTextField[colCount];
//------------rysowanie
		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		a.setVisible(true);
		a.setBounds(100, 100, 400, 160+23*(colCount-model.getNumberDs())); //te 400 sobie muszę zmieniać w zależności od tego, czy występuje errorMessage
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		a.setContentPane(contentPane);
		
		JLabel FormTitle = new JLabel(nazwa);
		FormTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JSeparator separator = new JSeparator();
		
		btnSave = new JButton("save");
		btnSave.addActionListener(this);
		btnSave.setHorizontalAlignment(SwingConstants.LEFT);

		gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(20)
							.addComponent(btnSave))
						.addComponent(FormTitle)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(FormTitle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSave)
					.addGap(20))
		);
		createFields(model.getMatrix(), rowNr, btnSave, errMessage.getErrMessage());
	}	//koniec konstruktora
	
	public void createFields(Object[][] matrix, int wierszNr, JButton btnSave, String[] errMessageStr)	{
		//string creators
		//panel.removeAll();
		String migLayRowNo = "";
		String[] targetNazwaPola = new String[colCount];
		String[] targetTextField = new String[colCount];
		String[] targetErrMessage = new String[colCount];		
		
		JLabel[] nazwaPola = new JLabel[colCount];
		
		JTextField[] textField = new JTextField[colCount];
		JLabel[] errMessage = errMS.getErrMessageLab();
		for (int i = 0; i<=colCount-1; i++)	{
			migLayRowNo = migLayRowNo+"[]";
			targetNazwaPola[i] = "cell 0 "+ i;
			targetTextField[i] = "cell 1 "+ i;
			targetErrMessage[i] = "cell 2 "+ i;
		}
		//Rysujemy 
		panel.setLayout(new MigLayout("", "[][][]", migLayRowNo));
		
		for (int i = 0; i<=colCount-1-model.getNumberDs(); i++) {
			nazwaPola[i] = new JLabel(model.getColumnName(i));
			panel.add(nazwaPola[i], targetNazwaPola[i]);
			nazwaPola[i].setHorizontalAlignment(SwingConstants.RIGHT);
			textField[i] = new JTextField();
			if (wierszNr<model.getRowCount())	{
				if (model.getValueAt(wierszNr, i)!=null) 	textField[i].setText(model.getValueAt(wierszNr, i).toString());
				else										textField[i].setText("");
			}			
			textField[i].setName(model.getColumnName(i));
			panel.add(textField[i], targetTextField[i]);
			textField[i].setColumns(10);
			errMessage[i] = new JLabel(errMessageStr[i]);//--------!
			panel.add(errMessage[i], targetErrMessage[i]);
			
			errMessage[i].setHorizontalAlignment(SwingConstants.LEFT);
			errMessage[i].setForeground(Color.RED);
			errMessage[i].setFont(new Font("Tahoma", Font.PLAIN, 9));	
		}
		tfAll = textField;
		contentPane.setLayout(gl_contentPane);
	} //koniec metody createFields
	
	/* --- to niepotrzebne
	public Object[]	emtyRow()	{
		Object[] eR = new Object[colCount];
		for (int i=0; i<=eR.length-1; i++)	{
			eR[i]="";
		}
		return eR;
	}
	*/
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
	
	@Override
	public void actionPerformed(ActionEvent e) {

		int liczbaDs = model.getNumberDs();
		int liczbaWierszy = model.getRowCount();
		boolean test = true;
	    Date currentDate = new Date();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss");
	    String dateString = dateFormat.format(currentDate);

		Object[] savedRow = new Object[colCount];

		for (int i = 0; i <= colCount-1; i++)	{
			if (i<=colCount-1-liczbaDs)	{
				savedRow[i]=tfAll[i].getText();
			}
			else	{
				savedRow[i]=model.getValueAt(rowNr, i);
			}
			System.out.println(savedRow[i]+" ** "+i);
		}
		
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
			a.setVisible(false); //to jakimś cudem działa dobrze
		}
	}//koniec metody actionPerformed



	public void addChangeListener(EkranGlowny ekranGlowny) { //do wywalenia...
		// TODO Auto-generated method stub
		
	}
}//koniec klasy
