package startGame;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JLabel;

import homePage.Main;

public class Key extends Cell implements KeyListener, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == 'w') {
			try {
				if(!GameField.cArray[Cell.currCell.getY()-1][Cell.currCell.getX()].colored) {
					Cell.currCell.btn.setBackground(Cell.clr[Game.wordDex+1]);
					Cell.currCell.up.setBackground(Cell.clr[Game.wordDex+1]);
					Cell.currCell = GameField.cArray[Cell.currCell.getY()-1][Cell.currCell.getX()];
					Cell.currCell.down.setBackground(Cell.clr[Game.wordDex+1]);
					changeState();
				}
			}catch(Exception f) {
				
			}
		}
		else if(e.getKeyChar() == 'a') {
			try {
				if(!GameField.cArray[Cell.currCell.getY()][Cell.currCell.getX()-1].colored) {
					Cell.currCell.btn.setBackground(Cell.clr[Game.wordDex+1]);
					Cell.currCell.left.setBackground(Cell.clr[Game.wordDex+1]);
					Cell.currCell = GameField.cArray[Cell.currCell.getY()][Cell.currCell.getX()-1];
					Cell.currCell.right.setBackground(Cell.clr[Game.wordDex+1]);
					changeState();
				}
			}catch(Exception f) {
				
			}
		}
		else if(e.getKeyChar() == 's') {
			try {
				if(!GameField.cArray[Cell.currCell.getY()+1][Cell.currCell.getX()].colored) {
					Cell.currCell.btn.setBackground(Cell.clr[Game.wordDex+1]);
					Cell.currCell.down.setBackground(Cell.clr[Game.wordDex+1]);
					Cell.currCell = GameField.cArray[Cell.currCell.getY()+1][Cell.currCell.getX()];
					Cell.currCell.up.setBackground(Cell.clr[Game.wordDex+1]);
					changeState();
				}
			}catch(Exception f) {
				
			}
		}
		else if(e.getKeyChar() == 'd') {
			try {
				if(!GameField.cArray[Cell.currCell.getY()][Cell.currCell.getX()+1].colored) {
					Cell.currCell.btn.setBackground(Cell.clr[Game.wordDex+1]);
					Cell.currCell.right.setBackground(Cell.clr[Game.wordDex+1]);
					Cell.currCell = GameField.cArray[Cell.currCell.getY()][Cell.currCell.getX()+1];
					Cell.currCell.left.setBackground(Cell.clr[Game.wordDex+1]);
					changeState();
				}
			}catch(Exception f) {
				
			}
		}
		
		if(Game.getnOfHLetters()>2 && Cell.currCell.getY() == Game.getlArray()[Game.getnOfHLetters()-3][0] &&
				Cell.currCell.getX() == Game.getlArray()[Game.getnOfHLetters()-3][1]) {
			
			Cell x = Game.cArray[Game.getlArray()[Game.getnOfHLetters()-2][0]][Game.getlArray()[Game.getnOfHLetters()-2][1]];
			x.resetColor();
			closeColor(Cell.currCell.getX(), Cell.currCell.getY(), Game.getlArray()[Game.getnOfHLetters()-2][1],Game.getlArray()[Game.getnOfHLetters()-2][0]);
			Game.setnOfHLetters(Game.getnOfHLetters()-2);
			
			Game.settWord(Game.gettWord().substring(0, Game.gettWord().length()-2));
		}
		
		if(Game.getnOfHLetters() > 10) {
			Game.resetLArray(false);
		}
		
		if(Game.isWrdFound(Game.gettWord())) {
			Main.playSound("files/found.wav");
			Cell.currCell.btn.setBackground(Cell.clr[Game.wordDex+1]);
			Game.resetLArray(true);
			Game.wordDex += 1;
			repaintCon();
			Cell.currCell = null;
		}
		
		if(Game.getnOfWords() == 0) {
			Game.endTime = System.currentTimeMillis();
			Game.totalTime = (Game.tPassed + Game.endTime - Game.startTime)/60000.0000;
			
			new Congrats();
		}

	}

	private void changeState() {
		Cell.currCell.btn.setBackground(Cell.clr[Game.wordDex+1].darker());
		Game.setlAtArray(Cell.currCell.getY(), Cell.currCell.getX());
		Game.setnOfHLetters(Game.getnOfHLetters()+1);
		Game.settWord(Game.gettWord().concat(Cell.currCell.getLetter()+""));
	}
	
	private void closeColor(int x, int y, int nullX, int nullY) {
		if(y-nullY == 1)	Cell.currCell.up.setBackground(Cell.clr[0]);
		if(x-nullX == -1)	Cell.currCell.right.setBackground(Cell.clr[0]);
		if(y-nullY == -1)	Cell.currCell.down.setBackground(Cell.clr[0]);
		if(x-nullX == 1)	Cell.currCell.left.setBackground(Cell.clr[0]);
	}

	private void repaintCon() {
		Game.found.removeAll();
		for(int i = 0; i<Game.wordDex; i++) {
			Game.wordButton[i] = new JButton(Game.foundArr[i].text);
			Game.wordButton[i].setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			Game.wordButton[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			Game.wordButton[i].setOpaque(false);
			Game.wordButton[i].setBackground(Cell.clr[0]);
			String x = Game.foundArr[i].text;
			Game.wordButton[i].addActionListener(new WordListener(x));
			Game.found.add(Game.wordButton[i]);
		}
		
		
		Game.notYet.removeAll();
		for(int i = 0; i<Game.getnOfWords(); i++) {
			JLabel wrd2 = new JLabel(Game.wordArray[i]);
			wrd2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			
			Game.notYet.add(wrd2);
		}
		
		Main.frame.repaint();
		Main.frame.validate();	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
