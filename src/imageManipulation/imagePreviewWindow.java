package imageManipulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class imagePreviewWindow extends JFrame{
	Point startPoint = new Point(0, 0);
	Point endPoint = new Point(0, 0);
	int finalWidth = 0;
	int finalHeight = 0;
	int scaleConst = 1;
	int sortAreaLength = 0;
	int sortAreaHeight = 0;
	ArrayList<Shape> shapeList = new ArrayList<Shape>();
	static JLabel infoText = null;
	static JPanel jpl = null;
	pixelSort ps;
	
	@SuppressWarnings("unused")
	public imagePreviewWindow(pixelSort ps1, String filepath, String windowTitle, int x, int y, boolean isHistogram){
		ps = ps1;
		
		Color bColor = new Color(0x0F1924);
		Color fColor = new Color(0xCA68CC);
		Color xColor = new Color(0x5868AD);
		Color cColor = Color.DARK_GRAY;
		
		final ImageIcon img = new ImageIcon(filepath);
		final Image resized = img.getImage();
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
		setSize(finalWidth, finalHeight + 50); //Magic number is magic
		getContentPane().setBackground(bColor);
		setResizable(false);
		
		jpl = new JPanel();
		jpl.setSize(finalWidth, finalHeight);
		jpl.setLocation(0, -5);
		jpl.setBackground(bColor);
		
		infoText = new JLabel("Sort Area: " + (img.getIconWidth() - (ps.xMinBound + ps.xMaxBound)) + "x" + (img.getIconHeight() - (ps.yMinBound + ps.yMaxBound) + " pixels"));
		infoText.setSize(335, 13);
		infoText.setLocation(0, finalHeight);
		infoText.setForeground(fColor);
		
		
		//Window Listeners
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
			}
			@Override
			public void windowClosed(WindowEvent arg0) {
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				resized.flush();
			}
			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}
			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}
			@Override
			public void windowIconified(WindowEvent arg0) {				
			}
			@Override
			public void windowOpened(WindowEvent arg0) {				
			}
			
		});
		//
		
		//Mouse Listeners
		jpl.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
//				System.out.println("Mouse has entered bounds of the panel");
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
//				System.out.println("Mouse has left the bounds of the panel");
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				startPoint = jpl.getMousePosition();
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				shapeList.clear();
				
				endPoint = jpl.getMousePosition();
				if (endPoint != null){
					int[] bounds = boundaryBoxCalc();
					ps.xMinBound = bounds[0] * scaleConst;
					ps.xMaxBound = bounds[1] * scaleConst;
					ps.yMinBound = bounds[2] * scaleConst;
					ps.yMaxBound = bounds[3] * scaleConst;
					
					sortAreaLength = img.getIconWidth() - (ps.xMinBound + ps.xMaxBound);
					sortAreaHeight = img.getIconHeight() - (ps.yMinBound + ps.yMaxBound);
					
					infoText.setText("Sort Area: " + sortAreaLength + "x" + sortAreaHeight + " pixels");
					repaint();
				}
				else {
					infoText.setText("Invalid selection, please try again!");
				}
			}
		});
		//
		
		jpl.add(label);

		this.getContentPane().add(infoText);	
		this.getContentPane().add(jpl);
		setVisible(true);
	}
	
	public void paint(Graphics g){
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g;
		Shape r = makeRectangle(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
		shapeList.add(r);
		g2.setColor(Color.ORANGE);
		
		for (Shape s: shapeList){
			g2.draw(s);
		}
	}
	
	public Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2){
		return new Rectangle2D.Float(Math.min(x1, x2) + 3, Math.min(y1, y2) + 21, Math.abs(x1 - x2), Math.abs(y1 - y2));
	}
	
	public int[] boundaryBoxCalc(){
		int[] dataValues = new int[4]; //0 = yMinBound, 1 = yMaxBound, 2 = xMinBound, 3 = xMaxBound
		
		double aX = Math.abs(startPoint.getX());
		double aY = Math.abs(startPoint.getY());
		double bX = Math.abs(endPoint.getX());
		double bY = Math.abs(endPoint.getY());
		
		int xMaxBound1 = (int) Math.abs(finalWidth - aX);
		int xMaxBound2 = (int) Math.abs(finalWidth - bX);
		
		if (xMaxBound1 < xMaxBound2){
			dataValues[1] = xMaxBound1;
		}
		else {
			dataValues[1] = xMaxBound2;
		}
		
		int yMaxBound1 = (int) Math.abs(finalHeight - aY);
		int yMaxBound2 = (int) Math.abs(finalHeight - bY);
		
		if (yMaxBound1 < yMaxBound2){
			dataValues[3] = yMaxBound1;
		}
		else {
			dataValues[3] = yMaxBound2;
		}
		
		if (aX < bX){
			dataValues[0] = (int) aX;
		}
		else {
			dataValues[0] = (int) bX;
		}
		
		if (aY < bY){
			dataValues[2] = (int) aY;
		}
		else {
			dataValues[2] = (int) bY;
		}		
		return dataValues;
	}
	
	public void resetBounds(){
		shapeList.clear();
		jpl.repaint();
		infoText.setText("Sort Area reset to full resolution!");
	}
}
