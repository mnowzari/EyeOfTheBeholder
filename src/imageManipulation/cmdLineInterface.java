package imageManipulation;

import java.util.Scanner;

public class cmdLineInterface {
	pixelSort ps;
	dataSaver ds;
	public cmdLineInterface(pixelSort ps, dataSaver ds){
		this.ps = ps;
		this.ds = ds;
	}
	
	public void mainLoop(){
		Scanner fx = new Scanner(System.in);
		String userInput = "";
		System.out.println("PixelSorter Shell Interface");
		while(!userInput.equalsIgnoreCase("exit")){
			userInput = fx.nextLine();
			
			if (userInput.equals("load")){
				System.out.println("Enter filepath: ");
				userInput = fx.nextLine();
				String filepath = userInput;
				ps.filename = filepath;
				ps.filepath = filepath;
				ps.loadJPG(filepath);
				System.out.println("Image Loaded");
			}
			else if (userInput.equals("sort")){
				ps.finalSort();
				System.out.println("Sorting Complete");
			}
			else if (userInput.equals("buildLogEnable")){
				ps.buildLog = true;
			}
			fx.close();
		}
	}
}
