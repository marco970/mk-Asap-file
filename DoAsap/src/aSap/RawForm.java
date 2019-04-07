package aSap;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComponent;

public class RawForm {
	private JFrame frame;

	/**
	 * Create the framevccnnn
	 */
	public RawForm(String title, String mainLab) {
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 450, 600);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[][grow][]"));
		frame.setVisible(true);
		
		
	}
	public void addToContPane(JComponent c, String migCoords)	{
		frame.getContentPane().add(c, migCoords);
	}
	
	public void closeThisFrame()	{
		frame.dispose();
	}


}
