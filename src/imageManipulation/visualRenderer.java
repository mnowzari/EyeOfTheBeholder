package imageManipulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class visualRenderer {

	public visualRenderer(final ArrayList<Color> c0, final ArrayList<Color> c1, final ArrayList<Color> c2, final Color[] finalColors){
		JFrame frame = new JFrame();
		Container pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		
		final JPanel renderPanel = new JPanel(){
			public void paintComponent(Graphics g){
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.BLACK);
				g2.fillRect(0, 0, getWidth(), getHeight());
				
//				g2.translate(getWidth() / 2, getHeight() / 2);
				drawVisual(c0, finalColors[0], g2);
				drawVisual(c1, finalColors[1], g2);
				drawVisual(c2, finalColors[2], g2);
			}
		};
		
		pane.add(renderPanel);
		frame.setSize(256, 256);
		frame.setVisible(true);
	}
	
	private void drawVisual(ArrayList<Color> c, Color color, Graphics2D g2){
		for (int i = 0; i < c.size(); i++){
//			Color boxColor = new Color(c.get(i).getRed(), c.get(i).getGreen(), c.get(i).getBlue());
			g2.setColor(color);
			Rectangle2D rect = new Rectangle2D.Double();
			rect.setRect(c.get(i).getRed(), c.get(i).getBlue(), 1, 1);
			g2.draw(rect);	
		}
	}
}
