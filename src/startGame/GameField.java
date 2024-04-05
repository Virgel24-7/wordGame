package startGame;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.JPanel;

public abstract class GameField implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int gridSize;
public static int nOfWords;
	protected static int oNofWords;
	protected int loNofWords;
	protected int lNOfWords;
	protected static String[] wordArray;
	protected String[] lWordArray;
	protected static Cell[][] cArray;
	protected Cell[][] lCArray;
	protected static JPanel grid;
	protected JPanel lGrid;
	
	GameField(){
	}

	public JPanel getGrid() {
		return grid;
	}

	public void setGrid(JPanel grid) {
		GameField.grid = grid;
		grid.setBackground(Color.black);
	}

	public Cell[][] getcArray() {
		return cArray;
	}

	public void setcArray(Cell[][] cArray) {
		GameField.cArray = cArray;
	}

	public String[] getWordArray() {
		return wordArray;
	}

	public void setWordArray(String[] wordArray) {
		GameField.wordArray = wordArray;
	}

	public static int getnOfWords() {
		return nOfWords;
	}

	public static void setnOfWords(int nOfWords) {
		GameField.nOfWords = nOfWords;
	}
}
