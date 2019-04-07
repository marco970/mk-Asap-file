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
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class NewForm implements  ActionListener, FocusListener {
	
	private MainTableModel model;
	private JFrame newFrame;
	private JPanel contentPane;		//bo potrzebujemy tego w metodach
	private JPanel panel;			//jw
	private JTextField poleZZ;
	private JLabel poleZZlab;
	private JLabel errZZLab;
	private JLabel spolkaPole;
	private JLabel statusPole;
	private JTextArea przedmiotTa;
	private JTextField dostawcaPole;
	private JTextField nazwaPole ;
	private JComboBox trybPole;

	private ArrayList<Component> listaComp = new ArrayList<Component>(); //lista komponentów do visible
	private int rowNr;
	
	//przyciski
	private JButton btnSave = new JButton("Zapisz");
	private JButton btnCancel = new JButton("Anuluj");
	private JButton btnNext = new JButton("Dalej");
	private JButton btnBack = new JButton("Powrót");
	
	public NewForm(int rowNr, MainTableModel mod)	{
		
		this.model = mod;
		this.rowNr = rowNr;
		//ramka
		newFrame = new JFrame("Nowy ZZ");
		newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newFrame.setVisible(true);
		newFrame.setBounds(100, 100, 450, 600);
		//panel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		newFrame.setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		panel = new JPanel();
		contentPane.add(panel, "cell 0 0, grow");
		panel.setLayout(new MigLayout("", "[][][]", "[][][]"));
		//tytuł ramki
		JLabel title = new JLabel("Dodaj nowy wniosek");
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		//przyciski
		btnSave.addActionListener(this);
		btnCancel.addActionListener(this);
		//btnNext.setText("Dalej");
		btnNext.addActionListener(this);
		btnBack.addActionListener(this);
		//pole ZZ
		poleZZ = new JTextField(13);
		poleZZlab = new JLabel();
		poleZZlab.setHorizontalAlignment(SwingConstants.LEFT);
		poleZZlab.setVisible(true);
		//poleZZlab.setVisible(false);
		poleZZ.addFocusListener(this);
		JLabel labZZ = new JLabel(model.getColumnName(0));
		//komunikat o błędzie
		errZZLab = new JLabel();
		errZZLab.setHorizontalAlignment(SwingConstants.LEFT);
		errZZLab.setForeground(Color.RED);
		errZZLab.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		panel.add(title, "dock north");
		panel.add(labZZ, "cell 0 1");
		panel.add(poleZZ, "cell 1 1");
		//panel.add(poleZZlab, "cell 1 1");
		panel.add(errZZLab, "cell 2 1");
		
		//tworzenie HashMapy - o ile będzie potrzebna
		//tworzenie pozostałych elementów
		
		//dodać status 
		JLabel statusPolelab = new JLabel(model.getColumnName(4));
		listaComp.add(statusPolelab);
		statusPole = new JLabel("aktywne");
		listaComp.add(statusPole);
		panel.add(statusPolelab,"cell 0 2");
		panel.add(statusPole,"cell 1 2");
		//dodać przedmiot
		JLabel przemiotPolelab = new JLabel(model.getColumnName(5));
		listaComp.add(przemiotPolelab);
		przedmiotTa = new JTextArea(5,15);
		przedmiotTa.setLineWrap(true);
		przedmiotTa.setWrapStyleWord(true);
		JScrollPane scrl = new JScrollPane(przedmiotTa);
		listaComp.add(scrl);
		panel.add(przemiotPolelab,"cell 0 3");
		panel.add(scrl,"cell 1 3");
		//dodać dostawca
		JLabel dostawcaPolelab = new JLabel(model.getColumnName(6));
		listaComp.add(dostawcaPolelab);
		dostawcaPole = new JTextField(15);
		listaComp.add(dostawcaPole);
		panel.add(dostawcaPolelab,"cell 0 4");
		panel.add(dostawcaPole,"cell 1 4");
		//dodać nazwę 
		JLabel nazwaPolelab = new JLabel(model.getColumnName(7));
		listaComp.add(nazwaPolelab);
		nazwaPole = new JTextField(15);
		listaComp.add(nazwaPole);
		panel.add(nazwaPolelab,"cell 0 5");
		panel.add(nazwaPole,"cell 1 5");
		//tryb postępowania
		JLabel trybPolelab = new JLabel(model.getColumnName(8));
		listaComp.add(trybPolelab);
		String[] tryby = {"przetarg", "z wolnej ręki", "inne"};
		trybPole = new JComboBox<>(tryby);
		listaComp.add(trybPole);
		panel.add(trybPolelab,"cell 0 6");
		panel.add(trybPole,"cell 1 6");
		//dodać spółkę
		JLabel spolkaPolelab = new JLabel(model.getColumnName(9));
		listaComp.add(spolkaPolelab);
		spolkaPole = new JLabel();
		listaComp.add(spolkaPole);
		panel.add(spolkaPolelab,"cell 0 7");
		panel.add(spolkaPole,"cell 1 7");
		
		//przyciski
		contentPane.add(btnCancel, "cell 0 1");
		contentPane.add(btnBack, "cell 0 1");
		contentPane.add(btnNext, "cell 0 1");
		contentPane.add(btnSave, "cell 0 1");
		btnSave.setEnabled(false);
		btnBack.setVisible(false);
		makeThemVisible(false);
		
	}//koniec konstruktora
	//metody pomocnicze
	public void makeThemVisible(boolean a)	{
		for (Component el: listaComp)	el.setVisible(a);
	}
	public void elReplace(Component added, Component removed, JPanel p, String migTarget)	{
		p.remove(removed);
		p.add(added,migTarget);
	}
	
	@Override
	public void focusGained(FocusEvent eFg) {
		btnNext.setEnabled(true);
		btnNext.setText("Dalej");
		btnSave.setEnabled(false);	
	}
	//obsługa zdarzeń
	@Override
	public void focusLost(FocusEvent eFg) {
		String gotZZ = poleZZ.getText();
		int lengthZZ = gotZZ.length();
		SingleFieldValidator zzVal = new SingleFieldValidator("ZZ", gotZZ, model, rowNr);
		errZZLab.setText(zzVal.getErrMessage());
		String spolka = zzVal.getSpolka();
		///System.out.println("*** "+spolka);
		if (!zzVal.getValidationResult())	{//jeśli walidacja negatywna
			btnNext.setEnabled(false);
			poleZZ.requestFocus();
		}
		else {
			btnNext.setEnabled(false);
			poleZZlab.setText(gotZZ);
			elReplace(poleZZlab, poleZZ, panel, "cell 1 1");
			spolkaPole.setText(spolka);
			makeThemVisible(true);
			btnSave.setEnabled(true);
			btnBack.setVisible(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object command= e.getActionCommand();
		if(command.equals("Anuluj"))	{
			newFrame.setVisible(false);
		}
		if(command.equals("Powrót"))	{
			btnBack.setVisible(false);
			poleZZ.setText(poleZZ.getText());
			elReplace(poleZZ, poleZZlab, panel, "cell 1 1");
			poleZZ.requestFocus();
		}
		if(command.equals("Zapisz"))	{
			String[] savedRow = new String[model.getColumnCount()];
		    Date currentDate = new Date();
		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		    String dateString = dateFormat.format(currentDate);
		    //System.out.println(model.getColumnCount());
			for (int i=0; i<=model.getColumnCount()-1; i++)	{
				if (i == 0)	savedRow[i] = poleZZ.getText();
				else if(i==4) savedRow[i] = statusPole.getText();
				else if(i==5) savedRow[i] = przedmiotTa.getText().replaceAll("\n", " ").replaceAll("\t", " ").replaceAll(";", ",");
				else if(i==6) savedRow[i] = dostawcaPole.getText();
				else if(i==7) savedRow[i] = nazwaPole.getText();
				else if(i==8) savedRow[i] = (String) trybPole.getSelectedItem();
				else if(i==9) savedRow[i] = spolkaPole.getText();
				else if(i==10) savedRow[i] = dateString;
				else savedRow[i] = "";
			}
			model.recordAdd(savedRow);
			
			try {
				new Zapis(model);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			String a = savedRow[0].substring(6)+"_";
			String b = savedRow[7].replaceAll("\\s","_")+"_";
			SimpleDateFormat datePart = new SimpleDateFormat("yyyyMM");
			String c = datePart.format(currentDate)+"_";
			String d = "_"+savedRow[9];
			//System.out.println("---> "+a+b+c+d);
			new FolderCreator().createFolder(a+b+c+d);
			
			newFrame.dispose();
		}
	}
}
