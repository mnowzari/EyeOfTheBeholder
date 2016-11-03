package imageManipulation;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class histogramPreviewWindow extends JFrame{
	Point startPoint = new Point(0, 0);
	Point endPoint = new Point(0, 0);
	int finalWidth = 0;
	int finalHeight = 0;
	int scaleConst = 1;
	pixelSort ps;
	
	@SuppressWarnings("unused")
	public histogramPreviewWindow(pixelSort ps1, String filepath, String windowTitle, int x, int y){
		ps = ps1;
		
		Color bColor = new Color(0x0F1924);
		Color fColor = new Color(0xCA68CC);
		Color xColor = new Color(0x5868AD);
		Color cColor = Color.DARK_GRAY;
		
		ImageIcon img = new ImageIcon(filepath);
		Image resized = img.getImage();
		int scaleDivisorWidth = 800;
		int scaleDivisorHeight = 600;
		
		if (img.getIconWidth() > scaleDivisorWidth){
			if (img.getIconWidth() > img.getIconHeight()){
				scaleConst =  (img.getIconWidth() / scaleDivisorWidth);
			}
			else {
				scaleConst =  (img.getIconHeight() / scaleDivisorHeight);
			}			
		}

		finalWidth = img.getIconWidth() / scaleConst;
		finalHeight = img.getIconHeight() / scaleConst;
		
		Image resizedImg = resized.getScaledInstance(finalWidth, finalHeight, java.awt.Image.SCALE_SMOOTH);
		ImageIcon newImg = new ImageIcon(resizedImg);
		
		JLabel label = new JLabel();
		label.setIcon(newImg);
		
		setLayout(null);
		setTitle(windowTitle);
		setLocation(x, y);
		setSize(finalWidth + 6, finalHeight + 24); //Magic number is magic
		getContentPane().setBackground(bColor);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		final JPanel jpl = new JPanel();
		jpl.setSize(finalWidth, finalHeight);
		jpl.setLocation(0, -5);//Evil Wizard was here, and he dropped some magic numbers for y'all
		jpl.setBackground(bColor);
		
		jpl.add(label);

		this.getContentPane().add(jpl);
		setVisible(true);
	}
	
}
