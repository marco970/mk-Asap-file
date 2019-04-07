package aSap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Zapis {
	
	MainTableModel model;
	
	
	public Zapis (MainTableModel model) throws IOException	{
		this.model = model;
		
		String[] row = new String[model.getRowCount()];
		
		for (int j=0; j<=model.getRowCount()-1; j++)	{
			
			String singleRow ="";
			
			for(int i = 0; i<= model.getColumnCount()-1; i++)	{
				if (model.getValueAt(j, i)==null)	{
					singleRow=singleRow.concat(";");
				}
				else	{
					singleRow=singleRow.concat(model.getValueAt(j, i).toString()+";");
				}
			}
			row[j]=singleRow;
		}
		writeFile(model.getPath(), row);
		
	}
	
	public void writeFile(String filePath, String[] textLines)
		    throws IOException {
		  
		  FileWriter fileWriter = new FileWriter(filePath);
		  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		  
		  try {
		    for (String line : textLines) {
		      bufferedWriter.write(line);
		      bufferedWriter.newLine();
		    }
		  } finally {
		    bufferedWriter.close();
		  }
		}

}
