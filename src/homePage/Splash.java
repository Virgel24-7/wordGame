package homePage;

import java.awt.Font;
import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class Splash extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JProgressBar progressBar;
	public static JLabel loadX;
	private static JLabel loading;
	private final JLabel load = new JLabel("");

	public Splash(){
		setUndecorated(true);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Main.getThemeCol());
		
		progressBar = new JProgressBar();
		progressBar.setBounds((23*Main.fw)/Main.w, (639*Main.fh)/Main.h, (1235*Main.fw)/Main.w, (40*Main.fh)/Main.h);
		getContentPane().add(progressBar);
		
		loading = new JLabel("Now Loading...");
		loading.setVerticalAlignment(SwingConstants.BOTTOM);
		loading.setFont(new Font("Comic Sans MS", loading.getFont().getStyle() | Font.ITALIC, loading.getFont().getSize() + 10));
		loading.setBounds((40*Main.fw)/Main.w, (568*Main.fh)/Main.h, (278*Main.fw)/Main.w, (63*Main.fh)/Main.h);
		getContentPane().add(loading);
		
		loadX = new JLabel("23");
		loadX.setVerticalAlignment(SwingConstants.BOTTOM);
		loadX.setFont(new Font("Comic Sans MS", loadX.getFont().getStyle() | Font.ITALIC , loadX.getFont().getSize() + 10));
		loadX.setBounds((215*Main.fw)/Main.w, (568*Main.fh)/Main.h, (278*Main.fw)/Main.w, (63*Main.fh)/Main.h);
		getContentPane().add(loadX);
		load.setHorizontalAlignment(SwingConstants.CENTER);
		load.setIcon(new ImageIcon(getClass().getClassLoader().getResource("files/load.GIF")));
		load.setBounds((130*Main.fw)/Main.w, (90*Main.fh)/Main.h, (1028*Main.fw)/Main.w, (459*Main.fh)/Main.h);
		getContentPane().add(load);
		
		setVisible(true);
		
		int x;
		try {
			for(x = 0; x<=100; x++) {
				progressBar.setValue(x);
				Thread.sleep(50);
				loadX.setText(x+"%");
				if(x == 100) {
					dispose();
					Main.frame.setVisible(true);
					
					Main.playMusic();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
