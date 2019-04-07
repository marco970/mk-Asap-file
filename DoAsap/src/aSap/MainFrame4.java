package aSap;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.FlowLayout;

public class MainFrame4 extends JFrame implements ActionListener {

	/**
	 *  Pola
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	//menu
	private JMenuBar menuBar;
	private String[] start = {"Start", "Nowy", "Exit"};
	private String[] sort = {"Sort","Nieaktywne", "Aktywne","ToDo","Status","Tryb"};
	private String[] toDo = {"ToDo", "Lista", "Notatki"};
	private String[] notatki = {"Notatki","Nowa notatka","Edytuj"};
	private String[] popupStr = {"modyfikacja", "zakończ postępowanie", "zawieś postepowanie"};
	private JPopupMenu popupMenu;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new MainFrame4();
	}

	/**
	 * konstruktor 
	 */
	public MainFrame4() {
		//do wywalenia dała klasa
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createGui("hello");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Metoda create Gui
	 */
	public void createGui(String tytul) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Do aSAP2");
		setBounds(70, 70, 1200, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
				
		//panel z listą
		MainTableModel dane = new MainTableModel();
		JTable table = new JTable(dane);
		table.setComponentPopupMenu(popupMenu);
		//table.addMouseListener(new TableMouseListener(table));
		JScrollPane scrollPane = new JScrollPane(table);

		
		//panel boczny
		boolean continousLayout = true; 
		Konsola konsola = new Konsola("Bardzo ładny tytuł");
		FlowLayout flowLayout = (FlowLayout) konsola.getLayout();
		flowLayout.setAlignOnBaseline(true);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                continousLayout, konsola, scrollPane);
		//splitPane.setRightComponent(new Konsola("Bardzo ładny tytuł"));
		

		//splitPane.setLeftComponent(scrollPane);

		
		//splitPane.setRightComponent(menuBar);
		
		//JMenuBar menuBar_1 = new JMenuBar();
		
		
		contentPane.add(splitPane, "cell 0 0,grow");
		//menu główne i popup
		menuBar = new JMenuBar();

		doMassAddMenu(menuBar, start);
		doMassAddMenu(menuBar, sort);
		doMassAddMenu(menuBar, toDo);
		doMassAddMenu(menuBar, notatki);
		setJMenuBar(menuBar);
		
	}
		
		//metody menu
		public void doMassAddMenu(JPopupMenu popup, String...args)	{
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

		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		
		
}//koniec klasy


