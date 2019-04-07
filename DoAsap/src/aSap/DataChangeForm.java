package aSap;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DataChangeForm extends JFrame implements ActionListener, FocusListener  {

	private JPanel contentPane;
	JPanel panel;
	private JButton btnSave = new JButton("Zapisz");
	private JButton btnCancel = new JButton("Anuluj");
	private JLabel lblNewLabel;

	private MainTableModel model;
	

	private String[] labNames = {"data ZZ", "data PZ", "data WP", "data DK"};
	private JLabel[] labs;
	private JTextField[] tfs;
	private JLabel[] errMessage;
	
	private int rowNr;
	
	private boolean isErr;
	
	//private Compare comp = new Compare();



	/**
	 * Create the frame.
	 */
	public DataChangeForm(MainTableModel model, int rowNr) {
		super();
		this.model = model;
		this.rowNr = rowNr;
		
		isErr = true;
		
		labs = new JLabel[labNames.length];
		tfs  = new JTextField[labNames.length];
		errMessage = new JLabel[labNames.length];
		
		
		setTitle("Zmiana dat postępowania");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow][]"));
		
		contentPane.add(btnCancel, "cell 0 1");
		contentPane.add(btnSave, "cell 0 1");
		
		btnSave.addActionListener(this);
		btnCancel.addActionListener(this);
		
		btnSave.setEnabled(isErr);
		
		panel = new JPanel();
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		
		lblNewLabel = new JLabel("<html><font size=\"4\" >Uwaga: ten formularz powinien być stosowany tylko w wyjątkowych przypadkach!</font>"
				+ "<p><font size=\"3\">Formularz nie zapewnia pełnej walidacji wprowadzanych danych.</font></p>"
				+ "<p></p>"
				+ "<p>Zmiana dat postępowania:"
				+ "</p></html>");
		panel.add(lblNewLabel, "cell 0 0 3 1");

		for (int i = 0; i<=labNames.length-1; i++)	{
			//JLabel aa = new JLabel(labNames[i]);
			//.out.println(labNames.length);
			labs[i] = new JLabel(labNames[i]);
			
			//labs[i].setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel.add(labs[i], "cell 0 "+(i+2));
			
			tfs[i] = new JTextField(10);
			tfs[i].setText((String) model.getValueAt(rowNr, 10+i));
			tfs[i].addFocusListener(this);
			panel.add(tfs[i], "cell 1 "+(i+2));
			
			errMessage[i] = new JLabel();
			errMessage[i].setHorizontalAlignment(SwingConstants.LEFT);
			errMessage[i].setForeground(Color.RED);
			errMessage[i].setFont(new Font("Tahoma", Font.PLAIN, 9));
			panel.add(errMessage[i], "cell 2 "+(i+2));
			
		}

		
	}
	//-------------moje metody
	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ^\s*(3[01]|[12][0-9]|0?[1-9])\.(1[012]|0?[1-9])\.((?:19|20)\d{2})\s*$ --- to nie robi np 30.02
	 * ^((0[1-9]|[12]\\d)\\.(0[1-9]|1[012])|30\\.(0[13-9]|1[012])|31\\.(0[13578]|1[02]))\\.(19|20)\\d\\d$
	 */
	@Override
	public void focusLost(FocusEvent efl) {
		
		isErr = true;
		for (int i = 0; i<=labNames.length-1; i++)	{
			if ((tfs[i].getText()).trim().matches("^((0[1-9]|[12]\\d)\\.(0[1-9]|1[012])|30\\.(0[13-9]|1[012])|31\\.(0[13578]|1[02]))\\.(19|20)\\d\\d$")  || tfs[i].getText().equals("")) {
				errMessage[i].setText("");
				
				if (i>0) {
					if (tfs[i].getText().trim().length()==10 || tfs[i].getText().length()==0) {
						if ((compare(tfs[i].getText().trim(), tfs[i - 1].getText().trim()) >= 0)
								|| tfs[i].getText().trim().equals("")) {

						} else {
							errMessage[i].setText("<html>Błąd! " + labNames[i] + " nie może być wcześniej niż "
									+ labNames[i - 1] + "</html>");
							isErr = false;
						}

					}
					else {
						errMessage[i].setText("nieprawidłowa data");
						isErr=false;
					}
				}
			}
			else {
				errMessage[i].setText("nieprawidłowa data");
				isErr=false;
			}
		}
		btnSave.setEnabled(isErr);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object command= e.getActionCommand();
		if(command.equals("Anuluj") || command.equals("Zakończ"))	{
			dispose();
		}
		if(command.equals("Zapisz"))	{
			for (int i = 0; i<=labNames.length-1; i++)	{
				model.cellUpdate(tfs[i].getText(), rowNr, 10+i);
			}
			try {new Zapis(model);} catch (IOException e1) {e1.printStackTrace();}
			btnSave.setVisible(false);
			btnCancel.setText("Zakończ");
		}
		
	}
	
	public int compare(Object a, Object b)    {
		int n1, n2;
		String aS = a.toString();
		if ("".equals(aS) || aS.length()<10) n1 = 0;
		else {
			if (aS.substring(0, 2).matches("[0-9]{2}") && aS.substring(3, 5).matches("[0-9]{2}") && aS.substring(6, 10).matches("[0-9]{4}") ) {
				n1 = Integer.valueOf(aS.substring(0, 2)) + 30 * Integer.valueOf(aS.substring(3, 5))
						+ 30 * 12 * Integer.valueOf(aS.substring(6, 10));
			}
			else n1 = 0;
		}
		String bS = b.toString();
		if ("".equals(bS) || bS.length()<10) n2 = 0;
		else {
			if (bS.substring(0, 2).matches("[0-9]{2}") && bS.substring(3, 5).matches("[0-9]{2}") && bS.substring(6, 10).matches("[0-9]{4}")) {
				n2 = Integer.valueOf(bS.substring(0, 2)) + 30 * Integer.valueOf(bS.substring(3, 5))
						+ 30 * 12 * Integer.valueOf(bS.substring(6, 10));
			}
			else n2 = 0; 
		}
		return n1 - n2;
	}
	
	//-------------
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {	//do wywalenia na koniec
		
		DataChangeForm frame = new DataChangeForm(new MainTableModel(), 3);
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataChangeForm frame = new DataChangeForm(new MainTableModel(), 3);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}
	/**/

}
