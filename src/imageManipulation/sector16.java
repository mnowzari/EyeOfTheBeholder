package imageManipulation;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class sector16 implements Runnable{
	Color[][] sector;
	BufferedImage img;
	int sectorNumber;
	boolean isDone = false;
	int wSector;
	int hSector;
	public sector16(Color[][] sector, BufferedImage img, int sectorNumber, int wSector, int hSector){
		this.sector = sector;
		this.img = img;
		this.sectorNumber = sectorNumber;
		this.wSector = wSector;
		this.hSector = hSector;
	}

	public void run() {
		for (int i = 0; i < wSector; i++){
			for (int k = 0; k < hSector; k++){
				if (sectorNumber == 1){
					sector[i][k] = new Color(img.getRGB(i, k));
				}
				else if (sectorNumber == 2){
					sector[i][k] = new Color(img.getRGB(i + wSector, k));
				}
				else if (sectorNumber == 3){
					sector[i][k] = new Color(img.getRGB(i + (2*wSector), k));
				}
				else if (sectorNumber == 4){
					sector[i][k] = new Color(img.getRGB(i, k + hSector));
				}
				else if (sectorNumber == 5){
					sector[i][k] = new Color(img.getRGB(i + wSector, k + hSector));
				}
				else if (sectorNumber == 6){
					sector[i][k] = new Color(img.getRGB(i + (2*(wSector)), k + hSector));
				}
			}
		}
		isDone = true;
	}
}
