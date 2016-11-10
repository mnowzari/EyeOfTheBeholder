package imageManipulation;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class quad2 implements Runnable{

	Color[][] colorArray;
	BufferedImage img;
	boolean isDone = false;
	
	public quad2(Color[][] colorArray, BufferedImage img){
		this.colorArray = colorArray;
		this.img = img;
	}
	
	public void run() {
		for (int i = 0; i < colorArray.length; i++){				//Q2
			for (int k = 0; k < colorArray[0].length; k++){
				colorArray[i][k] = new Color(img.getRGB(i + img.getWidth()/2, k));
			}
		}
		isDone = true;
	}
	
	public Color[][] getColorArray(){
		return this.colorArray;
	}

}
