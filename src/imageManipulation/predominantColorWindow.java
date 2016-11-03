package imageManipulation;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class predominantColorWindow extends JFrame{
	public predominantColorWindow(pixelSort ps, int x, int y){
		setLayout(null);
		setTitle("MCC");
		setSize(262, 145);
		setLocation(x, y);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JPanel textPane = new JPanel();
		textPane.setSize(270, 60);
		textPane.setLocation(0,  0);
		textPane.setBackground(new Color(ps.redMax, ps.greenMax, ps.blueMax));
		
		JLabel mostCommon = new JLabel("Most Common Color");
		mostCommon.setForeground(Color.WHITE);
		JLabel redColorText = new JLabel("R: " + ps.redMax);
		redColorText.setForeground(Color.WHITE);
		JLabel greenColorText = new JLabel("G: " + ps.greenMax);
		greenColorText.setForeground(Color.WHITE);
		JLabel blueColorText = new JLabel("B: " + ps.blueMax);
		blueColorText.setForeground(Color.WHITE);
		
		textPane.add(mostCommon);
		textPane.add(redColorText);
		textPane.add(greenColorText);
		textPane.add(blueColorText);
		
		JPanel textPane2 = new JPanel();
		textPane2.setSize(270,  60);
		textPane2.setLocation(0, 60);
		textPane2.setBackground(new Color(ps.redMedian, ps.greenMedian, ps.blueMedian));
		
		JLabel median = new JLabel("Median Color");
		median.setForeground(Color.WHITE);
		JLabel rct = new JLabel("R: " + ps.redMedian);
		rct.setForeground(Color.WHITE);
		JLabel gct = new JLabel("G: " + ps.greenMedian);
		gct.setForeground(Color.WHITE);
		JLabel bct = new JLabel("B: " + ps.blueMedian);
		bct.setForeground(Color.WHITE);
		
		textPane2.add(median);
		textPane2.add(rct);
		textPane2.add(gct);
		textPane2.add(bct);
		
		add(textPane);
		add(textPane2);
	}
}
