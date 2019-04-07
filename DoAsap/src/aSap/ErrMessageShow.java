package aSap;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;

public class ErrMessageShow extends JLabel implements PropertyChangeListener	{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int errNo;
	JLabel[] errMessageLab;
	
	public ErrMessageShow(MainTableModel model)	{
		this.errNo = model.getColumnCount()-model.getNumberDs();
		errMessageLab = new JLabel[errNo];
		//System.out.println(model.getColumnCount()+" "+model.getNumberDs()+" "+errNo);
		
		for(int i=0; i<=errNo -1 ;i++)	{
			errMessageLab[i] = new JLabel();
		}
	}
	public JLabel[] getErrMessageLab()	{
		return errMessageLab;
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String[] errMessageStr = (String[]) evt.getNewValue();
		for(int i=0; i<=errNo -1 ;i++)	{
			errMessageLab[i].setText(errMessageStr[i]);
		}
	}

}
