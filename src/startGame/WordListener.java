package startGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import homePage.Main;

class WordListener implements ActionListener, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String string;
	
	WordListener(String text){
		string = text;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Main.playSound("files/remove.wav");
		Game.deleteFromFound(string);
	}
}
