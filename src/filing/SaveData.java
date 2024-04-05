package filing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import startGame.Game;

public class SaveData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String savePath = System.getProperty("user.dir") + File.separator + "Game.ser";
	
	public SaveData(Game object) throws Exception{
		try {
			File f = new File(savePath);
			f.setWritable(true);
			
			FileOutputStream fos = new FileOutputStream(savePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			
			oos.close();
			fos.close();
			
			f.setReadOnly();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
