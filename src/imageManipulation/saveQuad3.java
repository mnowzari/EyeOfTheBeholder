package imageManipulation;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class saveQuad3 implements Runnable{
	Color[][] colorArray;
	BufferedImage img;
	public saveQuad3(Color[][] colorArray, BufferedImage img){
		this.colorArray = colorArray;
		this.img = img;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < colorArray.length/2; i++){			//loop for getting 2D array back into a BufferedImage for saving
			for (int k = colorArray[0].length/2; k < colorArray[0].length; k++){
				int pixel = colorArray[i][k].getRGB();
				img.setRGB(i, k, pixel);
			}
		}
	}

}
