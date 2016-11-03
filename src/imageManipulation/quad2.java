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
		for (int i = img.getWidth()/2; i < img.getWidth(); i++){				//Q2
			for (int k = 0; k < img.getHeight()/2; k++){
				Color pixelValue = new Color(img.getRGB(i, k));
				colorArray[i][k] = pixelValue;
			}
		}
		isDone = true;
	}
	
	public Color[][] getColorArray(){
		return this.colorArray;
	}

}
