package startGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import filing.SaveData;
import homePage.LeaderBoards;
import homePage.Main;
import java.awt.FlowLayout;

public class Congrats extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private Boolean high = false;
	private JButton okBtn;
	
	public Congrats(){
		
		if(LeaderBoards.newHighScore()) {
			high = true;
		}
		
		setUndecorated(true);
		getContentPane().setBackground(new Color(253, 245, 230));
		setSize(new Dimension(500, 300));
		setLocationRelativeTo(null);
		
		Main.setBgm("files/winBG.wav");
		Main.stopMusic();
		Main.playMusic();
		displayCongrats();
	}

	private void displayCongrats() {
		JPanel top = new JPanel();
		getContentPane().add(top, BorderLayout.NORTH);
		top.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel clear = new JPanel();
		clear.setBackground(new Color(0, 0, 128));
		top.add(clear);
		clear.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel clear2 = new JPanel();
		clear2.setBackground(new Color(224, 255, 255));
		clear2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		clear.add(clear2);
		
		JLabel clearText = new JLabel("GRID CLEAR! ");
		clear2.add(clearText);
		clearText.setBackground(new Color(224, 255, 255));
		clearText.setFont(new Font("Comic Sans MS", clearText.getFont().getStyle() | Font.BOLD, clearText.getFont().getSize() + 20));
		clearText.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel time = new JPanel();
		time.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		time.setBackground(new Color(0, 0, 128));
		top.add(time);
		time.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel diff = new JPanel();
		diff.setBackground(new Color(245, 255, 250));
		diff.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		time.add(diff);
		
		JLabel diffText = new JLabel("" + Game.diffList[Game.level-1]);
		diffText.setHorizontalAlignment(SwingConstants.CENTER);
		diffText.setFont(new Font("Comic Sans MS", diffText.getFont().getStyle(), diffText.getFont().getSize() + 10));
		diff.add(diffText);
		
		JPanel time2 = new JPanel();
		time2.setBackground(new Color(245, 255, 250));
		time2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		time.add(time2);
		
		JLabel timeText = new JLabel("Time: " + getMinutes(Game.totalTime));
		timeText.setHorizontalAlignment(SwingConstants.CENTER);
		timeText.setFont(new Font("Comic Sans MS", timeText.getFont().getStyle(), timeText.getFont().getSize() + 10));
		timeText.setBackground(new Color(224, 255, 255));
		time2.add(timeText);
		
		JPanel center = new JPanel();
		center.setBackground(new Color(245, 255, 250));
		center.setBorder(null);
		getContentPane().add(center, BorderLayout.CENTER);
		GridBagLayout gbl_center = new GridBagLayout();
		gbl_center.columnWidths = new int[]{0, 0};
		gbl_center.rowHeights = new int[]{40, 108, 0};
		gbl_center.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_center.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		center.setLayout(gbl_center);
		
		JLabel highText = new JLabel(high? "Wait!...Congratulations! New RECORD!": "Great Game!");
		highText.setFont(new Font("Comic Sans MS", highText.getFont().getStyle(), highText.getFont().getSize() + 7));
		highText.setHorizontalAlignment(SwingConstants.CENTER);
		highText.setBackground(new Color(244, 255, 250));
		GridBagConstraints gbc_highText = new GridBagConstraints();
		gbc_highText.insets = new Insets(0, 0, 5, 0);
		gbc_highText.gridx = 0;
		gbc_highText.gridy = 0;
		center.add(highText, gbc_highText);
		
		JPanel nameBox = new JPanel();
		GridBagConstraints gbc_nameBox = new GridBagConstraints();
		gbc_nameBox.fill = GridBagConstraints.BOTH;
		gbc_nameBox.gridx = 0;
		gbc_nameBox.gridy = 1;
		center.add(nameBox, gbc_nameBox);
		nameBox.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel highName = new JPanel();
		highName.setBackground(new Color(0, 0, 128));
		nameBox.add(highName);
		highName.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(25, 25, 112));
		if(high) highName.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel nameLabel = new JLabel("Enter Your Name: ");
		nameLabel.setForeground(new Color(255, 255, 255));
		nameLabel.setBackground(new Color(255, 255, 255));
		nameLabel.setFont(new Font("Comic Sans MS", nameLabel.getFont().getStyle(), nameLabel.getFont().getSize() + 6));
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(nameLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Comic Sans MS", textField.getFont().getStyle(), textField.getFont().getSize() + 6));
		textField.setColumns(10);
		panel.add(textField);
		
		JPanel okPanel = new JPanel();
		okPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		nameBox.add(okPanel);
		okPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		okBtn = new JButton("OK");
		okBtn.addActionListener(this);
		okBtn.setBackground(new Color(224, 255, 255));
		okBtn.setFont(new Font("Comic Sans MS", okBtn.getFont().getStyle() | Font.BOLD, okBtn.getFont().getSize() + 10));
		okPanel.add(okBtn);
		
		JPanel ePad = new JPanel();
		ePad.setBackground(new Color(0, 0, 128));
		getContentPane().add(ePad, BorderLayout.EAST);
		
		JPanel wPad = new JPanel();
		wPad.setBackground(new Color(0, 0, 128));
		getContentPane().add(wPad, BorderLayout.WEST);
		
		JPanel dPad = new JPanel();
		dPad.setBackground(new Color(0, 0, 128));
		getContentPane().add(dPad, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	public String getMinutes(double time) {
		String string = "";
		
		string += (int)(Math.floor(time/1)) + ":";
		int sec = (int) ((time - Math.floor(time/1))*60);
		string += sec < 10? "0" + sec: sec + "";
		
		return string;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okBtn) {
			Boolean allowX = true;
			
			if(high) {
				if(textField.getText().length() == 0 || textField.getText().length() > 10 || !checkChars(textField.getText())) {
					JPanel j = new JPanel();
					JLabel error = new JLabel("Only Alphanumerics allowed (Maximum 10)");
					error.setFont(new Font("Comic Sans MS", error.getFont().getStyle() | Font.BOLD, error.getFont().getSize() + 10));
					j.add(error);
					j.setBackground(new Color(253, 245, 230));
					JOptionPane.showMessageDialog(null, j, "", JOptionPane.PLAIN_MESSAGE);
					textField.setText("");
					allowX = false;
				}
				else {
					try {
						LeaderBoards.update(textField.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			if(Game.isGameLoaded()) {
				File f = new File(SaveData.savePath);
				f.delete();
			}
			
			if(allowX) {
				Main.setBgm("files/gameBG.wav");
				Main.stopMusic();
				Main.playMusic();
				dispose();
			}
		}
		
	}

	private boolean checkChars(String text) {
		for(int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if((!(ch >= '0' && ch <= '9') && !(ch >= 'a' && ch <= 'z') && !(ch >= 'A' && ch <= 'Z'))) {
				return false;
			}
		}
		
		return true;
	}
	
	
}
