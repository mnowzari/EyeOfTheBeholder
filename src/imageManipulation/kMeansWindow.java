package imageManipulation;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class kMeansWindow extends JFrame{

	public kMeansWindow(pixelSort ps){
		int sizeOfColorBlock = 666 / 3; //height of window / K value
		setLayout(null);
		setTitle("Dominant Colors");
		setSize(262, 666);
		setLocation(1585, 220);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JPanel colorPane1 = new JPanel();
		colorPane1.setSize(270, sizeOfColorBlock);
		colorPane1.setLocation(0,  0);
		colorPane1.setBackground(ps.kColors[0]);
		JLabel redColorText = new JLabel("R: " + ps.kColors[0].getRed());
		redColorText.setForeground(Color.WHITE);
		JLabel greenColorText = new JLabel("G: " + ps.kColors[0].getGreen());
		greenColorText.setForeground(Color.WHITE);
		JLabel blueColorText = new JLabel("B: " + ps.kColors[0].getBlue());
		blueColorText.setForeground(Color.WHITE);
		colorPane1.add(redColorText);
		colorPane1.add(greenColorText);
		colorPane1.add(blueColorText);
		
		JPanel colorPane2 = new JPanel();
		colorPane2.setSize(270,  sizeOfColorBlock);
		colorPane2.setLocation(0, sizeOfColorBlock);
		colorPane2.setBackground(ps.kColors[1]);
		JLabel redColorText2 = new JLabel("R: " + ps.kColors[1].getRed());
		redColorText2.setForeground(Color.WHITE);
		JLabel greenColorText2 = new JLabel("G: " + ps.kColors[1].getGreen());
		greenColorText2.setForeground(Color.WHITE);
		JLabel blueColorText2 = new JLabel("B: " + ps.kColors[1].getBlue());
		blueColorText2.setForeground(Color.WHITE);
		colorPane2.add(redColorText2);
		colorPane2.add(greenColorText2);
		colorPane2.add(blueColorText2);

		JPanel colorPane3 = new JPanel();
		colorPane3.setSize(270,  sizeOfColorBlock);
		colorPane3.setLocation(0, sizeOfColorBlock * 2);
		colorPane3.setBackground(ps.kColors[2]);
		JLabel redColorText3 = new JLabel("R: " + ps.kColors[2].getRed());
		redColorText3.setForeground(Color.WHITE);
		JLabel greenColorText3 = new JLabel("G: " + ps.kColors[2].getGreen());
		greenColorText3.setForeground(Color.WHITE);
		JLabel blueColorText3 = new JLabel("B: " + ps.kColors[2].getBlue());
		blueColorText3.setForeground(Color.WHITE);
		colorPane3.add(redColorText3);
		colorPane3.add(greenColorText3);
		colorPane3.add(blueColorText3);

		add(colorPane1);
		add(colorPane2);
		add(colorPane3);
	}
}
