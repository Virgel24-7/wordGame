package homePage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import startGame.Game;

public class LeaderBoards extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static File file;
	private static JButton reset;
	private static JButton backButton;
	static String top = System.getProperty("user.dir") + File.separator + "top.db";
	private static String topNew = System.getProperty("user.dir") + File.separator + "topNew.db";
	
	LeaderBoards() throws IOException{
		file = new File(top);
		if(!file.exists() || getNumOfLines(file) != 4) {
			resetTop(false);
		}
		else {
			setUndecorated(true);
			displayLeaderBoards();
		}
	}

	static int getNumOfLines(File file2) {
		try(LineNumberReader lineNumberReader =
			    new LineNumberReader(new FileReader(file2))) {
			  	lineNumberReader.skip(Long.MAX_VALUE);
			  	return lineNumberReader.getLineNumber();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return 0;
	}

	private void displayLeaderBoards() throws IOException {
		Main.setBgm("files/leadBG.wav");
		Main.stopMusic();
		Main.playMusic();
		
		reset = new JButton("Reset Leaderboards");
		reset.addActionListener(this);
		reset.setBackground(new Color(174, 193, 252));
		reset.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		reset.setAlignmentX(Component.CENTER_ALIGNMENT);
		reset.setBorder(Main.getRaised());
		
		JPanel bPanel = new JPanel();
		bPanel.setBackground(new Color(175, 238, 238));
		bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.Y_AXIS));
		bPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		bPanel.add(reset);
		
		
		JLabel l = new JLabel("LEADERBOARDS");
		l.setAlignmentX(Component.CENTER_ALIGNMENT);
		l.setAlignmentY(Component.CENTER_ALIGNMENT);
		l.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		bPanel.add(l);
		
		JPanel boards = new JPanel(new GridLayout(4,3,3,0));
		boards.setBackground(new Color(240, 255, 255));
		boards.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		JLabel la = new JLabel("Difficulty");
		la.setHorizontalAlignment(SwingConstants.CENTER);
		la.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		boards.add(la);
		
		JLabel lb = new JLabel("Name");
		lb.setHorizontalAlignment(SwingConstants.CENTER);
		lb.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		boards.add(lb);

		JLabel lc = new JLabel("Time(MM:SS)");
		lc.setHorizontalAlignment(SwingConstants.CENTER);
		lc.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		boards.add(lc);
		
		for(int i = 1; i<=3; i++) {
			StringTokenizer st = new StringTokenizer(Files.readAllLines(new File(top).toPath()).get(i), ";");
			JLabel a = new JLabel(st.nextToken());
			a.setHorizontalAlignment(SwingConstants.CENTER);
			a.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
			boards.add(a);
			
			JLabel b = new JLabel(st.nextToken());
			b.setHorizontalAlignment(SwingConstants.CENTER);
			b.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
			boards.add(b);
			
			JLabel c = new JLabel(convertTime(st.nextToken()));
			c.setHorizontalAlignment(SwingConstants.CENTER);
			c.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
			boards.add(c);
		}
		bPanel.add(boards);
		
		getContentPane().add(bPanel);
		
		JPanel lPad = new JPanel();
		lPad.setBackground(new Color(0, 0, 128));
		getContentPane().add(lPad, BorderLayout.WEST);
		
		JPanel rPad = new JPanel();
		rPad.setBackground(new Color(0, 0, 139));
		getContentPane().add(rPad, BorderLayout.EAST);
		
		JPanel dPad = new JPanel();
		dPad.setBackground(new Color(0, 0, 128));
		getContentPane().add(dPad, BorderLayout.SOUTH);
		
		backButton = new JButton("Back");
		backButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		backButton.addActionListener(this);
		backButton.setBackground(new Color(174, 193, 252));
        backButton.setForeground(Color.BLACK);
        backButton.setBorder(Main.getRaised());
		dPad.add(backButton);
		
		
		JPanel nPad = new JPanel();
		nPad.setBackground(new Color(0, 0, 139));
		getContentPane().add(nPad, BorderLayout.NORTH);
		setSize(500,500);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private String convertTime(String sTime) {
		if(!sTime.equals("NA")) {
			String string = "";
			double time = Double.parseDouble(sTime);
			
			string += (int)(Math.floor(time/1)) + ":";
			int sec = (int) ((time - Math.floor(time/1))*60);
			string += sec < 10? "0" + sec: sec + "";
			
			return string;
		}
		
		return sTime;
	}

	public static boolean newHighScore() {
		double time = Game.totalTime;
		
		try{
			StringTokenizer st = new StringTokenizer(Files.readAllLines(new File(top).toPath()).get(Game.level), ";");
	        String sTime = "";
	        
			while(st.hasMoreTokens()) {
				sTime = st.nextToken();
			}
    
	        if(sTime.equals("NA") || Double.parseDouble(sTime) > time) {
	        	return true;
	        }
	      } 
	      catch(IOException e){
	        System.out.println(e);
	      }
		
		return false;
	}

	public static void update(String name) throws IOException {
		File fToChange = new File(top);
        String oldContent = "";
        BufferedReader reader = null;
        String oldString = Files.readAllLines(Paths.get(fToChange.getPath())).get(Game.level);
        String newString = Game.diffList[Game.level-1] + ";" + name + ";" + Game.totalTime;
        
        FileWriter writer = null;
         
        try
        {
            reader = new BufferedReader(new FileReader(fToChange));             
            String line = reader.readLine();
             
            while (line != null) 
            {
                oldContent = oldContent + line + System.lineSeparator();
                line = reader.readLine();
            }
            String newContent = oldContent.replaceAll(oldString, newString);
            writer = new FileWriter(fToChange);
            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            { 
                reader.close();
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton) {
			Main.setBgm("files/homeBG.wav");
			Main.stopMusic();
			Main.playMusic();
			
			dispose();
		}

		else if(e.getSource() == reset) {
			int choice = JOptionPane.showOptionDialog(this, "Are you sure you want to reset high scores?", "MESSAGE", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, 0);
			if(choice == JOptionPane.YES_OPTION) {
				resetTop(true);
			}
		}
		
	}

	void resetTop(Boolean open) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(new File(topNew), true));
			bw.write("Top");
			bw.newLine();
			
			bw.write("Easy;NA;NA");
			bw.newLine();
			
			bw.write("Average;NA;NA");
			bw.newLine();
			
			bw.write("Difficult;NA;NA");
			bw.close();
		} catch (Exception f) {
		}
		
		File f = new File(top);
		f.delete();
		
		File fg = new File(topNew);
		fg.renameTo(new File(top));
		fg.setReadOnly();
		
		if(open) {
			dispose();
			try {
				new LeaderBoards();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
