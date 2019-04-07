package aSap;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PopupMenuBean {
	
	private String[] popupStr;
	
	private PropertyChangeSupport propertyChange = new PropertyChangeSupport(this);
	
	public PopupMenuBean(String[] popupString)	{
		popupStr = popupString;
	}

	public String[] getPopupStr() {
		return popupStr;		
	}

	public void setPopupStr(String[] popupString) {
		String[] oldStr = popupStr;
		popupStr = popupString;
		String[] newStr = popupStr;
		propertyChange.firePropertyChange("popupStr", oldStr, newStr);
	}
	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
	}

	public synchronized void removePropertyChangeListener(PropertyChangeListener l) {
		propertyChange.removePropertyChangeListener(l);
	}

}
