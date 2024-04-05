package homePage;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

import filing.LoadData;
import filing.SaveData;
import startGame.Cell;
import startGame.Game;

import static javax.sound.sampled.Clip.LOOP_CONTINUOUSLY;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Main extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	public static int fw, fh, w = 1280, h = 720;
	public static Game game;
    public static JPanel content, centerPanel, home;
	public static JButton muteButton;
	private static ImageIcon muteIcon;
	private static ImageIcon unmuteIcon;
	private static boolean mute = false;
	private JButton loadButton;
	private JButton leaderBoards;
	Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("files/icon.png"));
	private static Border raised;
	private static Color themeCol;
	private static URL bgm;
	public static Clip clip;
	private JButton tutor;

    public Main()
    {
    	setBgm("files/homeBG.wav");
    			
    	frame = new JFrame();
        frame.setTitle("Word Search Game");
        frame.getContentPane().setLayout(null);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        fw = Toolkit.getDefaultToolkit().getScreenSize().width;
        fh = Toolkit.getDefaultToolkit().getScreenSize().height;
        System.out.println(fw + " " + fh);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setIconImage(icon);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                if(game!=null) {
                	if(Game.getnOfWords() > 0) {
        				String[] options = {"YES", "NO"};
        				int x = JOptionPane.showOptionDialog(Main.frame, "Save this Game for Later?"
                					, "Message", 0
                					, JOptionPane.PLAIN_MESSAGE, null, options, null);
                		if(x == 0) {
                			game.saveStatics();
            				
            				try {
            					new SaveData(game);
            				} catch (Exception e1) {
            					// TODO Auto-generated catch block
            					e1.printStackTrace();
            				}
                		}
                		
                		else if(x == 1) {
                			if(Game.isGameLoaded()) {
                				File f = new File(SaveData.savePath);
                				f.delete();
                			}
                		}
        			}
                }
                frame.dispose();
                System.exit(0);
            }
        });
        frame.setLocationRelativeTo(null);
        
        frame.setContentPane(new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void paintComponent(Graphics g) {
				Image img;
				try {
					img = ImageIO.read(getClass().getClassLoader().getResource("files/homepageBG.png"));
					Image image = img.getScaledInstance(fw, fh, Image.SCALE_SMOOTH);
		               super.paintComponent(g);
		               g.drawImage(image, 0, 0, this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
         });
        
        muteIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("files/mute.png")).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        unmuteIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("files/unmute.png")).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        
        raised = new SoftBevelBorder(BevelBorder.LOWERED, new Color(0, 0, 190), new Color(0, 0, 190), new Color(0, 0, 205), new Color(0, 191, 255));
        
        muteButton = new JButton(getUnmuteIcon());
        muteButton.addActionListener(this);
        muteButton.setBackground(new Color(174, 193, 252));
        muteButton.setForeground(Color.BLACK);
        muteButton.setBorder(getRaised());
        muteButton.setBounds((1180*Main.fw)/Main.w, (11*Main.fh)/Main.h, (85*Main.fw)/Main.w, (47*Main.fh)/Main.h);

        frame.getContentPane().setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(0, 0, (1280*fw)/w, (300*fh)/h);
        topPanel.add(muteButton);
        
        ImageIcon searchIcon = new ImageIcon(getClass().getClassLoader().getResource("files/appName.png"));
        searchIcon.getImage().getScaledInstance(topPanel.getWidth(), topPanel.getHeight(), Image.SCALE_SMOOTH);
        JLabel searchLabel = new JLabel(searchIcon);
        searchLabel.setBounds((Main.fw/2)-(((1243*fw)/w)/2), (0*fh)/h, (1243*fw)/w, searchIcon.getIconHeight());
        
        content = new JPanel();
        content.setLayout(null);
		content.add(topPanel);
        topPanel.add(searchLabel);
        topPanel.setOpaque(false);
        
        content.add(topPanel);
        
        tutor = new JButton("TUTORIAL");
        tutor.addActionListener(this);
        tutor.setBackground(new Color(174, 193, 252));
        tutor.setForeground(Color.BLACK);
        tutor.setBorder(getRaised());
        tutor.setBounds((20*Main.fw)/Main.w, (11*Main.fh)/Main.h, (94*Main.fw)/Main.w, (47*Main.fh)/Main.h);
        tutor.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        topPanel.add(tutor);
        
        JPanel centerPanel = new JPanel();
		centerPanel.setBounds(0, (271*fh)/h, (1263*fw)/w, (311*fh)/h);
		content.add(centerPanel);
		centerPanel.setLayout(null);
        centerPanel.add(createDifficultyButton(new EasyLevel(), new Color(35, 89, 91))); 
        centerPanel.add(createDifficultyButton(new AverageLevel(), new Color(35, 89, 91))); 
        centerPanel.add(createDifficultyButton(new DifficultLevel(), new Color(35, 89, 91)));
        createLoadButton();
        centerPanel.add(loadButton);
        createLeaderBoards();
        centerPanel.add(leaderBoards);
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setOpaque(false);

        content.setBackground(Color.BLACK);
        content.setOpaque(false);
        
        home = content;
        frame.getContentPane().setBackground(themeCol.brighter());
        frame.getContentPane().add(content);
    }

	private void createLeaderBoards() {
		leaderBoards = new JButton("LEADERBOARDS");
		leaderBoards.addActionListener(this);
        leaderBoards.setBackground(new Color(35, 89, 91));
        leaderBoards.setForeground(Color.WHITE);
        leaderBoards.setBorder(getRaised());
        leaderBoards.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        leaderBoards.setBounds((475*fw)/w, (251*fh)/h, (334*fw)/w, (49*fh)/h);
	}

	private void createLoadButton() {
		loadButton = new JButton("LOAD");
		loadButton.addActionListener(this);
        loadButton.setBackground(new Color(35, 89, 91));
        loadButton.setForeground(Color.WHITE);
        loadButton.setBorder(getRaised());
        loadButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        loadButton.setBounds((475*fw)/w, (191*fh)/h, (334*fw)/w, (49*fh)/h);   
	}

    private JButton createDifficultyButton(DifficultyLevel difficultyLevel, Color color) 
    {
        JButton button = new JButton(difficultyLevel.getName());
        button.addActionListener(new DifficultyButtonListener(difficultyLevel));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        
        switch(difficultyLevel.getName()) {
	        case "EASY":{
	        	button.setBounds((475*fw)/w, (11*fh)/h, (334*fw)/w, (49*fh)/h);
	        	break;
	        }
	        case "AVERAGE":{
	        	button.setBounds((475*fw)/w, (71*fh)/h, (334*fw)/w, (49*fh)/h);
	        	break;
	        }
	        case "DIFFICULT":{
	        	button.setBounds((475*fw)/w, (131*fh)/h, (334*fw)/w, (49*fh)/h);
	        	break;
	        }
        }
        button.setBorder(getRaised());
        
        return button;
    }

    private class DifficultyButtonListener implements ActionListener
    {
        private DifficultyLevel difficultyLevel;

        DifficultyButtonListener(DifficultyLevel difficultyLevel) 
        {
            this.difficultyLevel = difficultyLevel;
        }

        @Override
        public void actionPerformed(ActionEvent e) 
        {
        	playSound("files/mainClick.wav");
        	
        	String[] options = {"YES", "LOAD", "DELETE SAVE", "BACK"};
        	File f = new File(SaveData.savePath);
        	
        	if(f.exists()) {
        		int x = JOptionPane.showOptionDialog(Main.frame, "Save File(" 
        				+ Game.diffList[new LoadData(f).getG().lLevel - 1] 
        					+ ") available. Are you sure you want to create new Grid?"
        					, "Message", 0
        					, JOptionPane.PLAIN_MESSAGE, null, options, null);
        		
        		if(x == 0) {
        			difficultyLevel.startGame();
        		}
        		else if(x == 1) {
        			game = new Game(new LoadData(f));
        		}
        		else if(x == 2) {
        			f.delete();
        			return ;
        		}
        		else if(x == 3) {
        			return;
        		}
        	}
        	else {
        		difficultyLevel.startGame();
        	}
        }
    }

    public static void main(String[] args) 
    {
    	themeCol = Cell.clr[(int)(1 + Math.floor(Math.random()*15))].darker();
        SwingUtilities.invokeLater(() -> new Main());
        File file = new File(LeaderBoards.top);
        new Splash();
		if(!file.exists() || LeaderBoards.getNumOfLines(file) != 4) {
			try {
				new LeaderBoards().resetTop(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

	public static ImageIcon getUnmuteIcon() {
		return unmuteIcon;
	}
	
	public static Icon getMuteIcon() {
		return muteIcon;
	}

	public static boolean isMute() {
		return mute;
	}

	public static void setMute(boolean mute) {
		Main.mute = mute;
	}

	public static Color getThemeCol() {
		return themeCol;
	}

	public static void setThemeCol(Color themeCol) {
		Main.themeCol = themeCol;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		playSound("files/mainClick.wav");
		
		if(e.getSource() == muteButton) {
			if(isMute()) {
				clip.start();
				setMute(false);
				muteButton.setIcon(unmuteIcon);
			}
			else if(!isMute()) {
				clip.stop();
				setMute(true);
				muteButton.setIcon(muteIcon);
			}
		}
		else if(e.getSource() == loadButton) {
		
			File f = new File(SaveData.savePath);
			if(f.exists()) game = new Game(new LoadData(f));
			else	JOptionPane.showMessageDialog(frame, "Save state not available");
		}
		else if(e.getSource() == leaderBoards) {
			try {
				new LeaderBoards();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == tutor) {
			Font font = new Font("Comic Sans MS", Font.PLAIN, 15);
			JPanel container = new JPanel();
			container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
			
			JPanel panel = new JPanel();
			panel.setBackground(new Color(0, 0, 128));
			container.add(panel, BorderLayout.NORTH);
			panel.setLayout(new GridLayout(0, 1, 0, 0));
			
			JLabel tut = new JLabel("TUTORIAL");
			tut.setForeground(new Color(255, 255, 255));
			tut.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
			tut.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(tut);
			
			JPanel body = new JPanel();
			body.setBorder(new LineBorder(new Color(0, 0, 128), 8));
			body.setBackground(new Color(240, 255, 255));
			container.add(body, BorderLayout.CENTER);
			body.setLayout(new GridLayout(0, 1, 0, 0));
			
			JLabel l1 = new JLabel("Letters can be connected in any orthogonal"
					+ "(up, left, down, right) direction");
			l1.setFont(font);
			l1.setHorizontalAlignment(SwingConstants.CENTER);
			body.add(l1);
			
			JLabel l2 = new JLabel("Controls: Click a cell as a starting line, "
					+ "then use WASD keys to move around and highlight cells");
			l2.setFont(font);
			l2.setHorizontalAlignment(SwingConstants.CENTER);
			body.add(l2);
			
			JLabel l3 = new JLabel("Clicking a new cell would reset current word search attempt");
			l3.setFont(font);
			l3.setHorizontalAlignment(SwingConstants.CENTER);
			body.add(l3);
			
			JLabel l4 = new JLabel("You cannot pick a cell that has been highlighted already");
			l4.setFont(font);
			l4.setHorizontalAlignment(SwingConstants.CENTER);
			body.add(l4);
			
			JLabel l5 = new JLabel("If stuck, click the corresponding word "
					+ "button on the right side to remove it from the list of words found.");
			l5.setFont(font);
			l5.setHorizontalAlignment(SwingConstants.CENTER);
			body.add(l5);
			
			JLabel l6 = new JLabel("Have Fun!");
			l6.setFont(font);
			l6.setHorizontalAlignment(SwingConstants.CENTER);
			body.add(l6);
			
			JOptionPane.showMessageDialog(null, container, "MESSAGE", JOptionPane.PLAIN_MESSAGE);
		}
		
	}

	public static void playSound(String string) {
		
		if(!mute) {
			try {
				AudioInputStream sound = AudioSystem.getAudioInputStream(Main.class.getClassLoader().getResource(string));
				Clip clip = AudioSystem.getClip();
				clip.open(sound);
				clip.setFramePosition(0);
				clip.start();
				
			} catch (UnsupportedAudioFileException e1) {
				
			} catch (IOException e1) {

			} catch (LineUnavailableException e1) {
				
			}
		}
		
	}
	
	public static void playMusic() {
		try {
			AudioInputStream sound = AudioSystem.getAudioInputStream(getBgm());
			clip = AudioSystem.getClip();
			clip.open(sound);
			clip.setFramePosition(0);
			clip.loop(LOOP_CONTINUOUSLY);
			clip.start();
			
		} catch (UnsupportedAudioFileException e1) {
			
		} catch (IOException e1) {

		} catch (LineUnavailableException e1) {
			
		}
		
	}
	
	public static void stopMusic() {
		clip.stop();
		
	}

	public static Border getRaised() {
		return raised;
	}

	public static URL getBgm() {
		return bgm;
	}

	public static void setBgm(String path) {
		Main.bgm = Main.class.getClassLoader().getResource(path);
	}
}