package aSap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

public class PopupContent extends JPopupMenu implements PropertyChangeListener, ActionListener {

	private JTable lista;
	private MainTableModel data;
	private JFrame frame;
	private String[] popupStr;
	
	public PopupContent(JTable list, MainTableModel dane, JFrame fram)	{
		super();
		lista=list;
		data = dane;
		frame = fram;
		//System.out.println("pc kontruktor - jestem");
		String[] popupStr = {"modyfikacja", "zmień daty", "zakończ postępowanie", "zawieś postepowanie"};
		this.popupStr = popupStr;
		doMassAddMenu(this, popupStr);		
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.removeAll();
		String[] a = (String[]) evt.getNewValue();
		doMassAddMenu(this, a);
		
	}
	public void doMassAddMenu(JPopupMenu popup, String...args)	{
		for (int i =0; i<=args.length-1; i++)	{
			JMenuItem menuItem = mi(args[i]);	
			popup.add(menuItem);
		}
	}
	
	public JMenuItem mi(String str)	{
		JMenuItem mi = new JMenuItem(str);
		mi.addActionListener(this);	
		mi.setActionCommand(str);
		return mi;
	}
	public String getFolder(int rowNr)	{
		String path = "";
		String numerZZ = data.getValueAt(rowNr, 0).toString().substring(6);
		FolderCreator folder = new FolderCreator();
		String myPath = folder.getDefaultPath() + folder.getAktywne();
		
		File[] directories = new File(myPath).listFiles(File::isDirectory);
		//System.out.println("path: "+myPath+" ntZZ: "+numerZZ+" dierLength: "+directories.length);
		
		if (myPath.length() > 0) {		//
			for (int i = 0; i <= directories.length - 1; i++) {
				//System.out.println(directories[i].toString().substring(myPath.length(), +myPath.length() + 7)+" ---> "+numerZZ);
				String x = "";
				if (directories[i].toString().substring(myPath.length(), +myPath.length() + 7).equals(numerZZ))	{
					path = directories[i].toString().substring(myPath.length());
				}
				//System.out.println(directories[i].toString().substring(myPath.length(), +myPath.length() + 7)+" ---> "+numerZZ+ " "+x );
			}
		}
		
		return path;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String u = e.getActionCommand();
	
		if (u.equals("modyfikacja"))	{
			int selectedRow = lista.getSelectedRow();
			int realSelectedRow = lista.convertRowIndexToModel(selectedRow);
			new OpForm2("Edycja postępowania", realSelectedRow, data);
		}
		if (u.equals("zakończ postępowanie"))	{
			int selectedRow = lista.getSelectedRow();
			int realSelectedRow = lista.convertRowIndexToModel(selectedRow);
			if (data.getValueAt(realSelectedRow, 2)==null || "".equals(data.getValueAt(realSelectedRow, 2)))	{
				JOptionPane.showMessageDialog(frame, "Nie można zakończyć tego postępowania"); //tu zrobić ostrzeżenie i tak/nie
			}
			else {
				//System.out.println("kończę");//
				data.cellUpdate("zakonczone", realSelectedRow, 4);
				//System.out.println("getFolder: "+getFolder(realSelectedRow));
				try {
					new Zapis(data);
					new FolderCreator().moveFolder(getFolder(realSelectedRow), true);
					//tu uruchamiam metodę zmiany folderu
					//aby uzyskać parametr muszę stworzyć metodę
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		
		}
		if (u.equals("zmień daty"))	{
			//System.out.println("Zmiana dat");
			//new DataChangeForm(data, lista.getSelectedRow());
			new DateChangeForm2(data, lista.convertRowIndexToModel(lista.getSelectedRow()));
		}
		if (u.equals("zawieś postepowanie"))	{
			int selectedRow = lista.getSelectedRow();
			int realSelectedRow = lista.convertRowIndexToModel(selectedRow);
			if(data.getValueAt(realSelectedRow, 4).equals("aktywne"))	{
				data.cellUpdate("zawieszone", realSelectedRow, 4);
				try {
					new Zapis(data);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (data.getValueAt(realSelectedRow, 4).equals("zakonczone")) {
				JOptionPane.showMessageDialog(frame, "Nie można zawiesić zamkniętego postępowania"); 
			}
			else if (data.getValueAt(realSelectedRow, 4).equals("zakonczone")) {
				JOptionPane.showMessageDialog(frame, "Postępowanie już jest zawieszone");
			}
		}
		if (u.equals("odwieś postępowanie"))	{
			//int selectedRow = lista.getSelectedRow();
			int realSelectedRow = lista.convertRowIndexToModel(lista.getSelectedRow());
			if (data.getValueAt(realSelectedRow, 4).equals("zakonczone")) {
				data.cellUpdate("aktywne", realSelectedRow, 4);
				try {
					new Zapis(data);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
	}

}
