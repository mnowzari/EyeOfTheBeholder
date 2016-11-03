package imageManipulation;
import javax.swing.JFrame;


public class main {
	public static void main(String[] args){
		pixelSort ps = new pixelSort();
		dataSaver ds = new dataSaver(ps);
		mainWindow mw = new mainWindow(ps, ds);
		mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mw.setVisible(true);
//		cmdLineInterface cmd = new cmdLineInterface(ps, ds);
//		cmd.mainLoop();
	}
}
