package imageManipulation;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class sector16 implements Runnable{
	Color[][] sector;
	BufferedImage img;
	int sectorNumber;
	boolean isDone = false;
	public sector16(Color[][] sector, BufferedImage img, int sectorNumber){
		this.sector = sector;
		this.img = img;
		this.sectorNumber = sectorNumber;
	}

	public void run() {
		for (int i = 0; i < img.getWidth()/4; i++){
			for (int k = 0; k < img.getHeight()/4; k++){
				switch(sectorNumber){
				case 1:
					sector[i][k] = new Color(img.getRGB(i, k));
				case 2:
					sector[i][k] = new Color(img.getRGB(i + img.getWidth()/4, k));
				case 3:
					sector[i][k] = new Color(img.getRGB(i + (2*(img.getWidth()/4)), k));
				case 4:
					sector[i][k] = new Color(img.getRGB(i + (3*(img.getWidth()/4)), k));
				case 5:
					sector[i][k] = new Color(img.getRGB(i, k + img.getHeight()/4));
				case 6:
					sector[i][k] = new Color(img.getRGB(i + img.getWidth()/4, k + img.getHeight()/4));
				case 7:
					sector[i][k] = new Color(img.getRGB(i + (2*(img.getWidth()/4)), k + img.getHeight()/4));
				case 8:
					sector[i][k] = new Color(img.getRGB(i + (3*(img.getWidth()/4)), k + img.getHeight()/4));
				case 9:
					sector[i][k] = new Color(img.getRGB(i, k + (2*(img.getHeight()/4))));
				case 10:
					sector[i][k] = new Color(img.getRGB(i + img.getWidth()/4, k + (2*(img.getHeight()/4))));
				case 11:
					sector[i][k] = new Color(img.getRGB(i + (2*(img.getWidth()/4)), k + (2*(img.getHeight()/4))));
				case 12:
					sector[i][k] = new Color(img.getRGB(i + (3*(img.getWidth()/4)), k + (2*(img.getHeight()/4))));
				case 13:
					sector[i][k] = new Color(img.getRGB(i, k + (3*(img.getHeight()/4))));
				case 14:
					sector[i][k] = new Color(img.getRGB(i + img.getWidth()/4, k + (3*(img.getHeight()/4))));
				case 15:
					sector[i][k] = new Color(img.getRGB(i + (2*(img.getWidth()/4)), k + (3*(img.getHeight()/4))));
				case 16:
					sector[i][k] = new Color(img.getRGB(i + (3*(img.getWidth()/4)), k + (3*(img.getHeight()/4))));
				}
				isDone = true;
			}
		}
	}
}
