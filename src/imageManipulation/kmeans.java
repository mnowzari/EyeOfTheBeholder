package imageManipulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class kmeans {
	Color[][] img;
	Color[] kPoints = new Color[3];
	ArrayList<Color> c0 = new ArrayList<>();
	ArrayList<Color> c1 = new ArrayList<>();
	ArrayList<Color> c2 = new ArrayList<>();
	int width;
	int height;
	boolean randomKGen;
	public kmeans(Color[][] img, int width, int height, boolean randomKGeneration){
		this.img = img;
		this.width = width;
		this.height = height;
		randomKGen = randomKGeneration;
	}
	
	public int randomPixel(int max){
		Random rand = new Random();
		int coord = rand.nextInt(max);
		return coord;
	}
	
	public Color[][] generateK(){
		if (randomKGen == true){
			for (int i = 0; i < kPoints.length; i++){
				int x = randomPixel(width);
				int y = randomPixel(height);
				kPoints[i] = img[x][y]; 
			}	
		}
		else {
			kPoints[0] = img[width / 2][height / 2];
			kPoints[1] = img[width / 4][height / 2];
			kPoints[2] = img[(width * 3) / 4][(height * 3) / 4];
		}
		return img;
	}
	
	public double calcDistance(int r, int g, int b, int r1, int g1, int b1){
		int addition = ((r - r1)^2) + ((g - g1)^2) + ((b - b1)^2);
		double result = Math.sqrt(Math.abs(addition));
		return result;
	}
	
	public Color[] calc(){		
		generateK();
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				int red = img[i][j].getRed();
				int green = img[i][j].getGreen();
				int blue = img[i][j].getBlue();
				
				ArrayList<Double> results = new ArrayList<Double>();
				for (int k = 0; k < kPoints.length; k++){ //arrayList of three distance values
					double distance = calcDistance(red, green, blue, kPoints[k].getRed(), kPoints[k].getGreen(), kPoints[k].getBlue());
					results.add(distance);
				}
								
				double smallestDistance = results.get(0);
				int indexLocation = 0;
				for (int n = 0; n < results.size(); n++){ //find smallest distance
					if (results.get(n) < smallestDistance){
						indexLocation = n;
					}
				}
				if (indexLocation == 0){
					c0.add(img[i][j]);
				}
				else if (indexLocation == 1){
					c1.add(img[i][j]);
				}
				else if (indexLocation == 2){
					c2.add(img[i][j]);
				}
			}
		}
		Color[] finalColors = new Color[3];
		verifySizeAndCenter(c0, finalColors, 0);
		verifySizeAndCenter(c1, finalColors, 1);
		verifySizeAndCenter(c2, finalColors, 2);
		
//		visualRenderer vs = new visualRenderer(c0, c1, c2, finalColors);
		
		return finalColors;
	}
	
	public void verifySizeAndCenter(ArrayList<Color> c, Color[] finalColors, int index){
		if (c.size() == 0){
			finalColors[index] = kPoints[index];
		}
		else {
			finalColors[index] = calcClusterCenter(c);			
		}
	}
	
	public Color calcClusterCenter(ArrayList<Color> c){
		int redTotal = 0, greenTotal = 0, blueTotal = 0;
		int redAvg = 0, greenAvg = 0, blueAvg = 0;	
		for (int i = 0; i < c.size(); i++){
			redTotal += c.get(i).getRed();
			redAvg = redTotal / c.size();
			
			greenTotal += c.get(i).getGreen();
			greenAvg = greenTotal / c.size();
			
			blueTotal += c.get(i).getBlue();
			blueAvg = blueTotal / c.size();
		}
		Color finalColor = new Color(Math.abs(redAvg), Math.abs(greenAvg), Math.abs(blueAvg));
		return finalColor;
	}
}
