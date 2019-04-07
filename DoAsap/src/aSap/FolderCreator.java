package aSap;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

public class FolderCreator {
	
	private String aktywne = "\\00_Postępowania_Aktywne\\";
	private String zamknięte = "\\01_Postępowania_Zamknięte\\";
	private String defaultPath;
	
	public FolderCreator()	{
		defaultPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
	}
	
	public String getAktywne() {
		return aktywne;
	}

	public String getZamkniete() {
		return zamknięte;
	}
	public String getDefaultPath() {
		return defaultPath;
	}
	
	public void createFolder(String folderName)	{
		File a = new File(defaultPath+aktywne+folderName);
		a.getAbsoluteFile().mkdirs();
		try {
			Desktop.getDesktop().open(a);
		} catch (IOException e) {
			System.out.println("nie udało się utworzyć folderu");
			e.printStackTrace();
		}
	}

	public void moveFolder(String folderName, boolean tam)	{
		
		//System.out.println(defaultPath);
		
		File destinationFolder;
	    File sourceFolder;
		if (tam) {
			sourceFolder = new File(defaultPath+aktywne+folderName);
			destinationFolder = new File(defaultPath+zamknięte+folderName);
		}
		else	{
			sourceFolder = new File(defaultPath+zamknięte+folderName);
			destinationFolder = new File(defaultPath+aktywne+folderName);
		}
		//System.out.println(sourceFolder);
		//System.out.println(destinationFolder);

	    if (!destinationFolder.exists())	{
	    	destinationFolder.mkdirs();
	    }
	    // Check weather source exists and it is folder.
	    if (sourceFolder.exists() && sourceFolder.isDirectory())	{
	        // Get list of the files and iterate over them
	        File[] listOfFiles = sourceFolder.listFiles();

	        if (listOfFiles != null)	{
	            for (File child : listOfFiles )	{
	                // Move files to destination folder
	                child.renameTo(new File(destinationFolder + "\\" + child.getName()));
	            }
	            // Add if you want to delete the source folder 
	            sourceFolder.delete();
	        }
	    }
	    else	{
	        System.out.println(sourceFolder + "  Folder nie istnieje");
	    }
	    try {
			Desktop.getDesktop().open(destinationFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
