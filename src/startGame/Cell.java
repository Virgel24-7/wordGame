package startGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JPanel;


public class Cell implements ActionListener, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char letter = '\0';
	private boolean dEnd = false;
	private boolean[] openUrdl = {true, true, true, true};
	int y, x, color = 0;
	Boolean colored = false;
	public static Color[] clr = {new Color(241, 255, 255), new Color(178, 247, 239), new Color(155, 164, 181), new Color(223,225,217),
			new Color(250, 210, 225), new Color(199,216,215), new Color(190, 225, 230), new Color(158, 184, 217), 
			new Color(223, 231, 253), new Color(205, 218, 253), new Color(174, 193, 252), new Color(237, 220, 210),
			new Color(237,233,226), new Color(153, 193, 222), new Color(253, 228, 207), new Color(255, 207, 210)};
	
	private static final boolean[] allC = {false, false, false, false};
	public static Cell currCell;
	public JButton btn;
	public JPanel cBox, up, right, down, left;
	Key key;
	
	public Cell() {
	}
	
	public JPanel cellBox() {
		cBox = new JPanel();
		cBox.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		cBox.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 40, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 40, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setBackground(clr[0]);
		panel.setLayout(gbl_panel);
		
		up = new JPanel();
		GridBagConstraints gbc_up = new GridBagConstraints();
		gbc_up.insets = new Insets(0, 0, 0, 0);
		gbc_up.fill = GridBagConstraints.BOTH;
		gbc_up.gridx = 1;
		gbc_up.gridy = 0;
		up.setBackground(clr[0]);
		panel.add(up, gbc_up);
		
		left = new JPanel();
		GridBagConstraints gbc_left = new GridBagConstraints();
		gbc_left.insets = new Insets(0, 0, 0, 0);
		gbc_left.fill = GridBagConstraints.BOTH;
		gbc_left.gridx = 0;
		gbc_left.gridy = 1;
		left.setBackground(clr[0]);
		panel.add(left, gbc_left);
		
		JPanel btnPanel = new JPanel();
		GridBagConstraints gbc_btnPanel = new GridBagConstraints();
		gbc_btnPanel.insets = new Insets(0, 0, 0, 0);
		gbc_btnPanel.fill = GridBagConstraints.BOTH;
		gbc_btnPanel.gridx = 1;
		gbc_btnPanel.gridy = 1;
		btnPanel.setBackground(clr[0]);
		panel.add(btnPanel, gbc_btnPanel);
		
		btn = new JButton(""+letter);
		btn.setMargin(new Insets(0,0,0,0));
		btn.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		btn.addActionListener(this);
		btn.setFocusable(true);
		key = new Key();
		btn.addKeyListener(key);
		btnPanel.setLayout(new GridLayout(0, 1, 0, 0));
		btn.setBorderPainted(false);
		btn.setBackground(clr[0]);
		
		btnPanel.add(btn);
		
		right = new JPanel();
		GridBagConstraints gbc_right = new GridBagConstraints();
		gbc_right.insets = new Insets(0, 0, 0, 0);
		gbc_right.fill = GridBagConstraints.BOTH;
		gbc_right.gridx = 2;
		gbc_right.gridy = 1;
		right.setBackground(clr[0]);
		panel.add(right, gbc_right);
		
		down = new JPanel();
		GridBagConstraints gbc_down = new GridBagConstraints();
		gbc_down.insets = new Insets(0, 0, 0, 0);
		gbc_down.fill = GridBagConstraints.BOTH;
		gbc_down.gridx = 1;
		gbc_down.gridy = 2;
		down.setBackground(clr[0]);
		panel.add(down, gbc_down);
		
		return cBox;
	}
	
	public void setLetter(char l) {
		letter = l;
	}
	
	public char getLetter() {
		return letter;
	}
	
	public void closeCell() {
		dEnd = true;
	}
	
	public boolean isDEnd() {
		return dEnd;
	}
	
	public boolean isDirOpen(int index) {
		return openUrdl[index];
	}
	
	public void closeDirection(int num) {
		openUrdl[num] = false;
		if(Arrays.equals(openUrdl, allC)){
			this.closeCell();
		}
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getX()	{
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getY()	{
		return y;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!colored) {
			Game.resetLArray(false);
			currCell = GameField.cArray[y][x];
			Game.setlAtArray(currCell.y, currCell.x);
			Game.setnOfHLetters(Game.getnOfHLetters()+1);
			currCell.btn.setBackground(clr[Game.wordDex+1].darker());
			Game.settWord(Game.gettWord().concat(currCell.getLetter()+""));
		}
	}

	public void resetColor() {
		btn.setBackground(clr[0]);
		up.setBackground(clr[0]);
		right.setBackground(clr[0]);
		down.setBackground(clr[0]);
		left.setBackground(clr[0]);
	}
}
