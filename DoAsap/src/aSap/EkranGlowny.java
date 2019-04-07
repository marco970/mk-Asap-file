package aSap;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.TableRowSorter;

	
public class EkranGlowny implements ActionListener {
	
	private JFrame eg;
	private int width, height;
	private String tytul;
	private JMenuBar menuBar;
	private MainTableModel data;
	private JPopupMenu popupMenu;
	private JTable lista;
	JMenu raport=null;  
	//private ErrMessage errMessage;

	//do menu - string pierwszy nazwa menu, kolejne - nazwy MenuItemów
	String[] start = {"Start", "Nowe postępowanie", "Raport miesięczny", "Exit"};
	String[] sort = {"Sort","Nieaktywne", "Aktywne","Zawieszone", "Zakończone", "Wszystkie"};
	String[] toDo = {"ToDo", "Lista", "Notatki"};
	String[] notatki = {"Notatki","Nowa notatka","Edytuj"};
	String[] popupStr = {"modyfikacja", "zmień daty", "zakończ postępowanie", "zawieś postepowanie"};
	
	static String[] nazwaMies = { "styczeń", "luty", "marzec", "kwiecień",
            "maj", "czerwiec", "lipiec", "sierpień",
            "wrzesień", "październik", "listopad", "grudzień"
          };
	
	//sortowanie filtrowanie
	TableRowSorter<MainTableModel> sorter;
	RowFilter<Object, Object> filter;
	
	public EkranGlowny()	{

		SwingUtilities.invokeLater(new Runnable() {
		      @Override
		      public void run() {
		        createGui(tytul);
		      }
		    });
	}
	
	public void createGui(String tytul)	{
		MainTableModel dane = new MainTableModel();
		data = dane;
		eg = new JFrame("ASap - Lista Postępowań");
		width = dane.getColumnCount()*100;
		height=	dane.getRowCount()*12+200;	
		eg.setSize(width, height);
		
		lista = new JTable(dane);
		//sortowanie i filtrowanie
		//lista.setAutoCreateRowSorter(true);			//sortowanie najprościej
		sorter = new TableRowSorter<MainTableModel>(dane);
		//compare(dane.getValueAt(12, 11), dane.getValueAt(5, 11));
		sorter.setComparator(0, new CompareZZ());
		sorter.setComparator(2, new CompareZZ());
		sorter.setComparator(3, new CompareZZ());
		sorter.setComparator(10, new Compare());
		sorter.setComparator(11, new Compare());
		sorter.setComparator(12, new Compare());
		sorter.setComparator(13, new Compare());
		lista.setRowSorter(sorter);

	    filter = new RowFilter<Object, Object>() {
		      public boolean include(Entry<?, ?> entry) {
		        String status = (String) entry.getValue(4);
		        return !("".equals(status) || status == null);
		      }
		    };

		sorter.setRowFilter(filter);
		lista.setRowSorter(sorter);
		Dimension dim = new Dimension(width, height);
		lista.setPreferredSize(dim);
		JScrollPane scroll = new JScrollPane(lista);

		
		eg.add(scroll);
		
		menuBar = new JMenuBar();
		
		/**
		 * To poniżej to jest do podmenu, żeby wygenerować raport. 
		 * Moje metody doMassAddMenu niestety nie przewidują podmenu i to podmenu zostało zrobione chujowo
		 * metoda poowinna być bardziej uniwersalna, ta jest zrobiona pod konkretną pozycję
		 * To powinno być poprawione w kolejnych wersjach.
		 */
		
		doMassAddMenu(menuBar, start);
		doMassAddMenu(menuBar, sort);
		//doMassAddMenu(menuBar, toDo);
		//doMassAddMenu(menuBar, notatki);

		eg.setJMenuBar(menuBar); // f - oznacza obiekt typu JFrame
		
		PopupMenuBean pmb = new PopupMenuBean(popupStr);
		
		TableMouseListener tbml = new TableMouseListener(lista, data, pmb);
		PopupContent pc = new PopupContent(lista, data, eg);
		
		pmb.addPropertyChangeListener(pc);
		
		lista.addMouseListener(tbml);

		lista.setComponentPopupMenu(pc); //tu wrzucamy dynamiczny obiekt

		eg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		eg.setVisible(true);

	}
	public void setPopupContent(String[] s)	{		//do wywalenia
		popupStr = s;
	}
	public void doMassAddMenu(JPopupMenu popup, String...args)	{ //metoda z Popup do wywalenia z tej klasy
		//JMenu menu = new JMenu(args[0]);
		//popup.add(menu);
		for (int i =0; i<=args.length-1; i++)	{
			JMenuItem menuItem = mi(args[i]);
			
			popup.add(menuItem);
			//menuItem.addActionListener(this);
		}
	}
	public void doMassAddMenu(JMenuBar mb, String...args)	{
		JMenu menu = new JMenu(args[0]);
		mb.add(menu);
		for (int i =1; i<=args.length-1; i++)	{

				JMenuItem menuItem = mi(args[i]);
				menu.add(menuItem);
	
		}
	}
	public JMenuItem mi(String str)	{
		//Color col = colors.get(str.substring(1));
		JMenuItem mi = new JMenuItem(str);
		mi.addActionListener(this);	
		mi.setActionCommand(str);
		return mi;
	}
	public JMenuItem mi(String str, String acc, int mnem)	{
		JMenuItem mi = new JMenuItem(str);
		mi.addActionListener(this);	
		mi.setAccelerator(KeyStroke.getKeyStroke(acc));
		mi.setMnemonic(mnem);
		mi.setActionCommand(str);
		return mi;
	}
	public void doMassAddMenu(JMenu nazwa, JMenuItem...args)	{
		//JMenuItem Sep = null;
		for (JMenuItem el: args)	{
			if (el==null)	{
				nazwa.addSeparator();
			}
			else	{
				nazwa.add(el);
			}
		}
	}
	public MainTableModel getModel()	{	//nie jestem pewien, czy metoda jest potrzebna
		return data;
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		String u = e.getActionCommand();
		
		//System.out.println(u);

		if (u.equals(start[3]))	{
			System.exit(0);
		}
		//ErrMessageShow errMS = new ErrMessageShow(data); 		//do wywalenia?
		if (u.equals(start[1]))	{
			new NewForm(data.getRowCount()+1, data);
		}
		if (u.equals(start[2])){
			//System.out.println(u+"--"+Arrays.asList(nazwaMies).indexOf(u)+"--"+q.toString());
			new RaportForm(data);
			
		}
		if (u.equals(sort[1]))	{
			//System.out.println("sort teraz "+u);
		    filter = new RowFilter<Object, Object>() {
			      public boolean include(Entry<?, ?> entry) {
			        String status = (String) entry.getValue(4);
			        //System.out.println("include()= " +status+ ("".equals(status) || status == null));
			        return !("aktywne".equals(status));
			        //return true;
			      }
			    };
				sorter.setRowFilter(filter);
				lista.setRowSorter(sorter);

		}
		if (u.equals(sort[2]))	{
			//System.out.println("sort teraz "+u);
		    filter = new RowFilter<Object, Object>() {
			      public boolean include(Entry<?, ?> entry) {
			        String status = (String) entry.getValue(4);
			        //System.out.println("include()= " +status+ ("".equals(status) || status == null));
			        return ("aktywne".equals(status));
			        //return true;
			      }
			    };
				sorter.setRowFilter(filter);
				lista.setRowSorter(sorter);

		}
		if (u.equals(sort[3]))	{
			//System.out.println("sort teraz "+u);
		    filter = new RowFilter<Object, Object>() {
			      public boolean include(Entry<?, ?> entry) {
			        String status = (String) entry.getValue(4);
			        //System.out.println("include()= " +status+ ("".equals(status) || status == null));
			        return ("zawieszone".equals(status));
			        //return true;
			      }
			    };
				sorter.setRowFilter(filter);
				lista.setRowSorter(sorter);

		}
		if (u.equals(sort[4]))	{
			//System.out.println("sort teraz "+u);
		    filter = new RowFilter<Object, Object>() {
			      public boolean include(Entry<?, ?> entry) {
			        String status = (String) entry.getValue(4);
			        //System.out.println("include()= " +status+ ("".equals(status) || status == null));
			        return ("zakonczone".equals(status));
			        //return true;
			      }
			    };
				sorter.setRowFilter(filter);
				lista.setRowSorter(sorter);

		}
		if (u.equals(sort[5]))	{
			//System.out.println("sort teraz "+u);
		    filter = new RowFilter<Object, Object>() {
			      public boolean include(Entry<?, ?> entry) {
			        String status = (String) entry.getValue(4);
			        //System.out.println("include()= " +status+ ("".equals(status) || status == null));
			        return (true);
			        //return true;
			      }
			    };
				sorter.setRowFilter(filter);
				lista.setRowSorter(sorter);

		}
		/*
		if (u.equals("modyfikacja"))	{	//popup do wywalenia - będzie w PopupContent
			int selectedRow = lista.getSelectedRow();
			int realSelectedRow = lista.convertRowIndexToModel(selectedRow);

			new OpForm2("Edycja postępowania", realSelectedRow, data);
			
			//System.out.println(" to ma być " + popupStr[0] + lista.getSelectedRow() );
		}
		//okFo.addChangeListener(this); //czy to w ogóle jest potrzebne?
		if (u.equals("zakończ postępowanie"))	{
			//spr czy jest WP?
			int selectedRow = lista.getSelectedRow();
			int realSelectedRow = lista.convertRowIndexToModel(selectedRow);
			if (data.getValueAt(realSelectedRow, 2)==null || "".equals(data.getValueAt(realSelectedRow, 2)))	{
				JOptionPane.showMessageDialog(eg, "Nie można zakończyć tego postępowania");
			}
			else {
				//System.out.println("kończę");
				data.cellUpdate("done", realSelectedRow, 4);
				try {
					new Zapis(data);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
				
			//System.out.println(" to ma być " + popupStr[1] + lista.getSelectedRow() );
		}
		if (u.equals("zmień daty"))	{
			System.out.println("daty");
			new DataChangeForm(data, lista.getSelectedRow());
		}
		if (u.equals("zawieś postepowanie"))	{
			int selectedRow = lista.getSelectedRow();
			int realSelectedRow = lista.convertRowIndexToModel(selectedRow);
		}
		if (u.equals("odwieś postępowanie"))	{
			//System.out.println(" to ma być " + popupStr[1] + lista.getSelectedRow() );
		}
		*/
		
	}



}
