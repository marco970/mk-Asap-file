package aSap;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;

public class TableMouseListener extends MouseAdapter  {
    
   private JTable table;
   private MainTableModel model;
   private PopupMenuBean pmb;
    
   public TableMouseListener(JTable table, MainTableModel model, PopupMenuBean pmb) {
       this.table = table;
       this.model = model;
       this.pmb = pmb;
   }
    
   @Override
   public void mousePressed(MouseEvent event) {
       Point point = event.getPoint();
       int currentRow = table.rowAtPoint(point);
       table.setRowSelectionInterval(currentRow, currentRow);
       
       int selectedRow = table.getSelectedRow();
       int realSelectedRow = table.convertRowIndexToModel(selectedRow);
		
       String[] popupStr1 = {"modyfikacja", "zmień daty"};
       String[] popupStr2 = {"modyfikacja", "zmień daty", "zakończ postępowanie", "zawieś postepowanie"};
       String[] popupStr3 = {"modyfikacja", "zmień daty", "odwieś postępowanie"};
       
       Object status = model.getValueAt(realSelectedRow, 4);
       
       if (status.equals("aktywne"))	{
    	   pmb.setPopupStr(popupStr2);
    	   //System.out.println("TML mousePressed - pmb change");
       }
       else if (status.equals("zawieszone")) {
    	   pmb.setPopupStr(popupStr3);
       }
       else pmb.setPopupStr(popupStr1);

       
   }

	   
}
