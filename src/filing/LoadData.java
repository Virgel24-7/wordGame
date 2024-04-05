package filing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import startGame.Game;

public class LoadData implements Serializable{
	Game g;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LoadData(File f){
		try
		{   
			
		    FileInputStream fis = new FileInputStream(f);
		    ObjectInputStream ois = new ObjectInputStream(fis);
		     
		    g = (Game)ois.readObject();
		    ois.close();
		    fis.close();
		}
		 
		catch(IOException ex)
		{
		    System.out.println("IOException is caught");
		}
		 
		catch(ClassNotFoundException ex)
		{
		    System.out.println("ClassNotFoundException is caught");
		}
	}
	public Game getG() {
		return g;
	}
}
