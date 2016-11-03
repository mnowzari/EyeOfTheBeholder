package imageManipulation;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class waitWindow extends JFrame implements Runnable{

	JProgressBar bar;
	boolean isShowing;
	pixelSort ps;
	public waitWindow(pixelSort ps){
		this.ps = ps;
		isShowing = false;
		Color bColor = new Color(0x0F1924);
		Color fColor = new Color(0xCA68CC);
		Color xColor = new Color(0x5868AD);
//		Color cColor = Color.DARK_GRAY;
		
		setLayout(null);
		setTitle("Sorting...");
		setSize(275, 60);
		setLocationRelativeTo(null);
		getContentPane().setBackground(bColor);
		setResizable(false);
		setAlwaysOnTop(true);
		
		bar = new JProgressBar(0, 100);
		bar.setBorder(BorderFactory.createLineBorder(bColor));
		bar.setBackground(xColor);
		bar.setForeground(fColor);
		
		JLabel sorting = new JLabel("Sorting...");
		sorting.setForeground(fColor);
		
		JPanel pane = new JPanel();
		pane.setSize(275, 100);
		pane.setBackground(bColor);
		
		pane.add(sorting);
		pane.add(bar);
		
		add(pane);
	}

	public void run() {
		this.isShowing = true;
		bar.setValue(ps.progress);			
		bar.repaint();
		this.repaint();
	}
}
