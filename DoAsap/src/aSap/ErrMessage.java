package aSap;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ErrMessage {		//to jest bean
	
	private String[] errMessage; //cechą jest string
	//boolean valDone;
	
	private PropertyChangeSupport propertyChange = new PropertyChangeSupport(this);
	
	public ErrMessage(MainTableModel model)	{
		//this.valDone = true;
		int colValNo = model.getColumnCount()-model.getNumberDs();
		this.errMessage = new String[colValNo];
		for (int i=0; i<=colValNo-1; i++)	{
			errMessage[i]="";
		}
	}
	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
	    propertyChange.addPropertyChangeListener(listener);
	}

	public synchronized void removePropertyChangeListener(PropertyChangeListener l) {
	    propertyChange.removePropertyChangeListener(l);
	}
	
	public String[] getErrMessage()	{
		return errMessage;
	}
	
	
	public void setErrMessage(String[] message)	{
		String[] oldErrMessage = errMessage;
		String[] newErrMessage = message;
		errMessage = message;	
		for (int i=0; i<=message.length-1; i++)	{
			//System.out.println(i+" errM: "+newErrMessage[i]);
		}
		
		propertyChange.firePropertyChange("errMessage", oldErrMessage, newErrMessage);
	}
	/*
	public void setValDone(boolean vD)	{
		boolean oldValDone = valDone;
		boolean newValDone = vD;
		valDone = vD;
		propertyChange.firePropertyChange("valDone", oldValDone, newValDone);
	}
	*/

}
