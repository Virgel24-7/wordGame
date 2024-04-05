package startGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import filing.LoadData;
import filing.SaveData;
import homePage.Main;

public class Game extends GameField implements ActionListener, Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
public static JPanel container;
	public static String[] diffList = {"Easy", "Average", "Difficult"};
	
	//Game state
	public static int level;
	public int lLevel;
	static JPanel found, notYet;
	static JButton[] wordButton;
	static int wordDex = 0;
	int lWordDex;
	static WordLoc foundArr[];
	WordLoc lfoundArr[];
	
	private static JButton backButton, muteButton;
	static boolean loadedGame = false;
	
	//temporary
	public static String tWord = "";
	private static int[][] lArray;
	public static int nOfHLetters = 0;
	
	//time
	static long startTime, tPassed = 0, endTime;
	long tPass;
	public static double totalTime;
	
	public Game() {
		lArray = new int[15][2];
		int x = nOfWords+1;		
		foundArr = new WordLoc[x];
		wordDex = 0;
		makeContainer();
		startTime = System.currentTimeMillis();
	}

	public Game(LoadData loadData) {
		loadedGame = true;
		oNofWords = loadData.getG().loNofWords;
		setnOfWords(loadData.getG().lNOfWords);
		grid = loadData.getG().lGrid;
		cArray = loadData.getG().lCArray;
		wordArray = loadData.getG().lWordArray;
		wordDex = loadData.getG().lWordDex;
		foundArr = new WordLoc[oNofWords+1];
		for(int i = 0; i < loadData.getG().lfoundArr.length-2; i++) {
			foundArr[i] = loadData.getG().lfoundArr[i];
		}
		
		tPassed = loadData.getG().tPass;
		level = loadData.getG().lLevel;
		
		
		makeContainer();
		startTime = System.currentTimeMillis();
		lArray = new int[15][2];
	}
	
	private void makeContainer() {
		Main.setBgm("files/gameBG.wav");
		Main.stopMusic();
		Main.playMusic();
		
		Font font = new Font("Comic Sans MS", Font.BOLD, 20);
		wordButton = new JButton[oNofWords];
		
		container = new JPanel();
		container.setOpaque(false);
		container.setLayout(null);
		int size = (625*Main.fh)/Main.h;
		grid.setBounds((Main.fw/2)-(size/2), ((Main.fh-(Main.fh/12))/2)-(size/2), size, size);
		grid.setBorder(new LineBorder(Color.BLACK));
		container.add(grid);
		container.setFocusable(true);
		container.requestFocusInWindow();
		
		found = new JPanel();
		found.setBackground(Cell.clr[0]);
		found.setBorder(new TitledBorder(null, "Found Words", TitledBorder.LEADING, TitledBorder.TOP, font , null));
		found.setBounds((972*Main.fw)/Main.w, (69*Main.fh)/Main.h, (300*Main.fw)/Main.w, (560*Main.fh)/Main.h);
		found.setLayout(new GridLayout(0,1,2,2));
		
		for(int i = 0; i<wordDex; i++) {
			wordButton[i] = new JButton(foundArr[i].text);
			wordButton[i].setFont(font);
			wordButton[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			wordButton[i].setOpaque(false);
			wordButton[i].setBackground(Cell.clr[0]);
			wordButton[i].addActionListener(new WordListener(foundArr[i].text));
			found.add(wordButton[i]);
		}
		container.add(found);
		
		backButton = new JButton("Back");
		backButton.setFont(font);
		backButton.addActionListener(this);
		backButton.setBackground(new Color(174, 193, 252));
        backButton.setForeground(Color.BLACK);
		backButton.setBounds((10*Main.fw)/Main.w, (11*Main.fh)/Main.h, (94*Main.fw)/Main.w, (47*Main.fh)/Main.h);
		container.add(backButton);
		
		muteButton = new JButton(Main.getUnmuteIcon());
        muteButton.addActionListener(this);
        muteButton.setBackground(new Color(174, 193, 252));
        muteButton.setForeground(Color.BLACK);
        muteButton.setBounds((1178*Main.fw)/Main.w, (11*Main.fh)/Main.h, (94*Main.fw)/Main.w, (47*Main.fh)/Main.h);
		container.add(muteButton);
		
		notYet = new JPanel();
		notYet.setBackground(Cell.clr[0]);
		notYet.setBorder(new TitledBorder(null, "Words to find", TitledBorder.LEADING, TitledBorder.TOP, font, null));
		notYet.setBounds((10*Main.fw)/Main.w, (69*Main.fh)/Main.h, (300*Main.fw)/Main.w, (560*Main.fh)/Main.h);
		notYet.setLayout(new GridLayout(0,1,2,2));
		for(int i = 0; i<getnOfWords(); i++) {
			JLabel wrd = new JLabel(GameField.wordArray[i]);
			wrd.setFont(font);
			wrd.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			notYet.add(wrd);
		}
		container.add(notYet);
		
		Main.frame.remove(Main.content);
		Main.content = container;
		Main.frame.getContentPane().add(Main.content);
		Main.frame.repaint();
		Main.frame.validate();	
	}

	public JPanel getPanel() {
		return container;
	}
	
	public static String gettWord() {
		return tWord;
	}

	public static void settWord(String tWord) {
		Game.tWord = tWord;
	}

	public static int[][] getlArray() {
		return lArray;
	}

	public static void setlAtArray(int y, int x) {
		lArray[nOfHLetters][0] = y;
		lArray[nOfHLetters][1] = x;
	}

	public static int getnOfHLetters() {
		return nOfHLetters;
	}

	public static void setnOfHLetters(int nOfHLetters) {
		Game.nOfHLetters = nOfHLetters;
	}
	
	public static boolean isGameLoaded() {
		return loadedGame;
	}

	public static void resetLArray(Boolean found) {
		int temp[][] = Game.getlArray();
		
		if(!found) {
			for(int i=0; i<Game.getnOfHLetters(); i++) {
				cArray[temp[i][0]][ temp[i][1]].resetColor();
				cArray[temp[i][0]][ temp[i][1]].colored = false;
			}
		}
		else if(found) {
			foundArr[wordDex] = new WordLoc(Game.tWord);
			
			for(int i=0; i<Game.getnOfHLetters(); i++) {
				foundArr[wordDex].lLocs[i][0] = temp[i][0];
				foundArr[wordDex].lLocs[i][1] = temp[i][1];
				
				cArray[temp[i][0]][ temp[i][1]].colored = true;
				cArray[temp[i][0]][ temp[i][1]].color = wordDex + 1;
			}
		}
		
		nOfHLetters = 0;
		lArray = new int[15][2];
		Game.tWord = "";
		Cell.currCell = null;
	}

	public static boolean isWrdFound(String tWord) {
		for(int i = 0; i<getnOfWords(); i++) {
			if(tWord.equals(wordArray[i])) {
				wordArray[i] = "";
				
				shiftWordArray(i);
				return true;
			}
		}
		return false;
	}

	private static void shiftWordArray(int i) {
		for(;i<getnOfWords()-1; i++) {
			wordArray[i] = wordArray[i+1];
		}
		setnOfWords(getnOfWords() - 1);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton) {
			
			if(getnOfWords() > 0) {
				String[] options = {"YES", "NO"};
				int x = JOptionPane.showOptionDialog(Main.frame, "Save this Game for Later?"
        					, "Message", 0
        					, JOptionPane.PLAIN_MESSAGE, null, options, null);
        		if(x == 0) {
        			saveStatics();
    				try {
    					new SaveData(this);
    				} catch (Exception e1) {
    					e1.printStackTrace();
    				}
        		}
        		
        		if(x == 1) {
        			if(isGameLoaded()) {
        				File f = new File(SaveData.savePath);
        				f.delete();
        			}
        		}
			}
			
			Main.setBgm("files/homeBG.wav");
			Main.stopMusic();
			Main.playMusic();
			
			Main.frame.remove(Main.content);
			Main.content = Main.home;
			Main.frame.getContentPane().add(Main.content);
			Main.frame.repaint();
			Main.frame.validate();
			
			Main.game = null;
		}
		
		else if(e.getSource() == muteButton) {
			if(Main.isMute()) {	
				Main.clip.start();
				Main.setMute(false);
				muteButton.setIcon(Main.getUnmuteIcon());
			}
			else if(!Main.isMute()) {
				Main.clip.stop();
				Main.setMute(true);
				muteButton.setIcon(Main.getMuteIcon());
			}
		}		
	}

	public static void deleteFromFound(String string) {
		int i;
		for(i = 0; i<wordDex; i++) {
			if(string.equals(foundArr[i].text)) {
				for(int j = 0; j<string.length(); j++) {
					cArray[foundArr[i].lLocs[j][0]][foundArr[i].lLocs[j][1]].colored = false;
					cArray[foundArr[i].lLocs[j][0]][foundArr[i].lLocs[j][1]].resetColor();
				}
				foundArr[i] = null;
				break;
			}
		}
		for(;i<wordDex-1; i++) {
			foundArr[i] = foundArr[i+1];
		}
		
		wordArray[getnOfWords()] = string;
		setnOfWords(getnOfWords() + 1);
		wordDex--;
		
		Game.found.removeAll();
		for(i = 0; i<Game.wordDex; i++) {
			Game.wordButton[i] = new JButton(Game.foundArr[i].text);
			Game.wordButton[i].setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			Game.wordButton[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			Game.wordButton[i].setOpaque(false);
			Game.wordButton[i].setBackground(Cell.clr[0]);
			wordButton[i].addActionListener(new WordListener(foundArr[i].text));
			Game.found.add(Game.wordButton[i]);
		}
		
		
		Game.notYet.removeAll();
		for(i = 0; i<Game.getnOfWords(); i++) {
			JLabel wrd2 = new JLabel(Game.wordArray[i]);
			wrd2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			
			Game.notYet.add(wrd2);
		}
		
		Main.frame.repaint();
		Main.frame.validate();	
		
	}

	public void saveStatics() {
		Game.resetLArray(false);
		this.lGrid = Game.grid;
		this.lCArray = Game.cArray;
		this.lNOfWords = Game.getnOfWords();
		this.lWordArray = Game.wordArray;
		this.lWordDex = Game.wordDex;
		this.tPass = System.currentTimeMillis() - Game.startTime;
		this.lLevel = Game.level;
		this.loNofWords = Game.oNofWords;
		this.lfoundArr = Game.foundArr;
	}
}
