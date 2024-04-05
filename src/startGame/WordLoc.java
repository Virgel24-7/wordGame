package startGame;

import java.io.Serializable;

public class WordLoc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int[][] lLocs;
	public String text;
	
	public WordLoc(String text){
		this.text = text;
		lLocs = new int[text.length()+1][2];
	}
	
	
}
