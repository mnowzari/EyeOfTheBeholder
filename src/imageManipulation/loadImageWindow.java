package imageManipulation;

import java.awt.Color;

import javax.swing.JFrame;

public class loadImageWindow extends JFrame{
	public loadImageWindow(){
		Color bColor = new Color(0x0F1924);
		Color fColor = new Color(0xCA68CC);
		Color xColor = new Color(0x5868AD);
		Color cColor = Color.DARK_GRAY;
		
		setLayout(null);
		setTitle("Loading...");
		setSize(275, 60);
		setLocationRelativeTo(null);
		getContentPane().setBackground(bColor);
		setResizable(false);
		setAlwaysOnTop(true);
	}
}
