package imageManipulation;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

public class pixelSort{
	BufferedImage img;
	Color[][] colorArray;
	Color c1;
	Color c2;
	Color[] kColors;
	String filepath;
	String filepathOfHistogram;
	String filename;
	String savedFilename;
	boolean generateHistogram;
	boolean hasHistogramBeenGenerated;
	boolean isDone;
	boolean hasKMeansBeenGenerated;
	boolean greaterThan;
	boolean lessThan;
	boolean multithreaded;
	boolean buildLog;
	int slope;
	int imgWidth;
	int imgHeight;
	int sortType; //0 = single channel, 1 = cross-channel sequential, 2 = cross-channel random
	int redMax;
	int greenMax;
	int blueMax;
	int redMedian;
	int greenMedian;
	int blueMedian;
	int progress;
	int sortAreaLength;
	int sortAreaHeight;
	int xMinBound;
	int xMaxBound;
	int yMinBound;
	int yMaxBound;
	double analysisTime;
	double sortTime;
	
	
	public pixelSort(){
		filepath = null;
		generateHistogram = false;
		hasHistogramBeenGenerated = false;
		hasKMeansBeenGenerated = false;
		multithreaded = false;
		buildLog = false;
		sortType = 0;
		c1 = Color.RED;
		c2 = Color.RED;
		greaterThan = true;
		lessThan = false;
		slope = 0;
		filename = "";
		savedFilename = "";
		filepathOfHistogram = "";
		imgWidth = 0;
		imgHeight = 0;
		kColors = new Color[3];
		progress = 0;
		img = null;
		isDone = false;
		sortAreaLength = 0;
		sortAreaHeight = 0;
		xMinBound = 0;
		xMaxBound = 0;
		yMinBound = 0;
		yMaxBound = 0;
		analysisTime = 0;
		sortTime = 0;
	}
	
	public void finalSort() {
		long startTime = System.nanoTime();		
		img = loadJPG(filepath);
		
		progress = 10;
		savedFilename = "sorted_" + filename;
		progress = 30;
		
		sortPixels(colorArray, img);
		progress = 50;
		saveToJPG(colorArray, img, savedFilename);
		progress = 75;
		
		long endTime2 = System.nanoTime();
		sortTime = (endTime2 - startTime)/1000000000.0;
		
		sortAreaLength = img.getWidth() - (xMinBound + xMaxBound);
		sortAreaHeight = img.getHeight() - (yMinBound + yMaxBound);
		progress = 85;
		if (this.buildLog == true){
			buildLog(img, this.filepath);
			progress = 95;
		}
		progress = 100;
		this.img = null;
		this.colorArray = null;
		isDone = true;
		System.gc();
		
//		System.out.println(sortTime);

	}
	
	public boolean checkIfLandscape(){
		boolean isLandscape = false;
		if (imgWidth >= imgHeight){
			isLandscape = true;
		}
		else if (imgWidth < imgHeight){
			isLandscape = false;
		}
		return isLandscape;
	}
	
	private void generateHistogram(Color[][] colorArray){
		int[] redCh = new int[256];
		int[] greenCh = new int[256];
		int[] blueCh = new int[256];

		for (int i = 0; i < colorArray.length; i++){
			for (int k = 0; k < colorArray[0].length; k++){
				int redVal = colorArray[i][k].getRed();
				int greenVal = colorArray[i][k].getGreen();
				int blueVal = colorArray[i][k].getBlue();
				
				redCh[redVal]++;
				greenCh[greenVal]++;
				blueCh[blueVal]++;
			}
		}
		
		histogramVisualization(redCh, greenCh, blueCh, "RGBHistogram.png");
	}
	
	public void histogramVisualization(int[] redCh, int[] greenCh, int[] blueCh, String filepathname){
		int width = 256;
		int height = 320;
		BufferedImage hist = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		
		Color[][] localArray = new Color[width][height];
		
		for (int i = 0; i < localArray.length; i++){
			for (int k = 0; k < localArray[0].length; k++){
				hist.setRGB(i, k, Color.GRAY.getRGB());
			}
		}
		
		convertTo2DArray(localArray, hist);
		
		int[] biggest = new int[3];
		biggest[0] = findMaxHelper(redCh, "red");
		biggest[1] = findMaxHelper(greenCh, "green");
		biggest[2] = findMaxHelper(blueCh, "blue");
		
		int largest = biggest[0];
		for (int i = 0; i < biggest.length; i++){
			if (biggest[i] > largest){
				largest = biggest[i];
			}
		}
		
		makeHist(localArray, redCh, Color.RED, largest, height);
		makeHist(localArray, greenCh, Color.GREEN, largest, height);
		makeHist(localArray, blueCh, Color.BLUE, largest, height);
		
		findMedian(redCh, greenCh, blueCh);
		
		this.filepathOfHistogram = filepathname;
		saveToJPG(localArray, hist, this.filepathOfHistogram);
	}
	
	private Color[][] makeHist(Color[][] localArray, int[] channelArray, Color color, int largest, int height){		
		double scale = ((largest / height) + 1);
		for (int i = 0; i < localArray.length; i++){
			for (int k = height - 1; k > (height - (channelArray[i] / scale)); k--){
				localArray[i][k] = color;
			}
		}		
		return localArray;
	}
	
	private int findMaxHelper(int[] channelArray, String whatColor){ //finds maximum value in given channel array
		int largest = channelArray[0];
		int savedIndex = 0;
		for (int i = 1; i < channelArray.length; i++){
			if (channelArray[i] > largest){
				largest = channelArray[i];
				savedIndex = i;
			}
		}
				
		if (whatColor.equals("red")){
			redMax = savedIndex;
		}
		else if (whatColor.equals("green")){
			greenMax = savedIndex;
		}
		else if (whatColor.equals("blue")){
			blueMax = savedIndex;
		}
		
		return largest;
	}
	
	private void findMedian(int[] redCh, int[] greenCh, int[] blueCh){
		int[] redCh2 = new int[256];
		int[] greenCh2 = new int[256];
		int[] blueCh2 = new int[256];
		
		for (int i = 0; i < 256; i++){
			redCh2[i] = redCh[i];
		}
		for (int i = 0; i < 256; i++){
			greenCh2[i] = greenCh[i];
		}
		for (int i = 0; i < 256; i++){
			blueCh2[i] = blueCh[i];
		}
		
		quicksortColor(redCh2, 0, 255);
		quicksortColor(greenCh2, 0, 255);
		quicksortColor(blueCh2, 0, 255);
		
		for (int i = 0; i < redCh.length; i++){
			if (redCh[i] == redCh2[128]){
				redMedian = i;
			}
		}
		for (int i = 0; i < redCh.length; i++){
			if (greenCh[i] == greenCh2[128]){
				greenMedian = i;
			}
		}
		for (int i = 0; i < redCh.length; i++){
			if (blueCh[i] == blueCh2[128]){
				blueMedian = i;
			}
		}
	}
	
	private int partitionHelper(int[] channelArray, int left, int right){
		int i = left, j = right;
		int tmp;
		int pivot = channelArray[(left + right) / 2];
		
		while (i <= j){
			while (channelArray[i] < pivot){
				i++;
			}
			while (channelArray[j] > pivot){
				j--;
			}
			if (i <= j){
				tmp = channelArray[i];
				channelArray[i] = channelArray[j];
				channelArray[j] = tmp;
				i++;
				j--;
			}
		}
		return i;
	}
	
	private void quicksortColor(int[] channelArray, int left, int right){
		int index = partitionHelper(channelArray, left, right);
		if (left < index - 1){
			quicksortColor(channelArray, left, index - 1);
		}
		if (index < right){
			quicksortColor(channelArray, index, right);
		}
	}
	
	private Color[][] convertTo2DArray(Color[][] colorArray, BufferedImage img){
		boolean multiBypass = false;
		if ((img.getWidth() % 2) != 0 || (img.getHeight() % 2) != 0){
			multiBypass = true;
		}
		
		if (multithreaded == true && multiBypass == false){
			multithreadedConvert(colorArray, img);
		}
		else if (multithreaded == false || multiBypass == true){
			for (int i = 0; i < img.getWidth(); i++){
				for (int k = 0; k < img.getHeight(); k++){
					colorArray[i][k] = new Color(img.getRGB(i, k));
				}
			}
		}
		return colorArray;
	}
	
	public void multithreadedConvert(Color[][] colorArray, BufferedImage img){
		Color[][] quadrant1 = new Color[img.getWidth()/2][img.getHeight()/2];
		Color[][] quadrant2 = new Color[img.getWidth()/2][img.getHeight()/2];
		Color[][] quadrant3 = new Color[img.getWidth()/2][img.getHeight()/2];
		Color[][] quadrant4 = new Color[img.getWidth()/2][img.getHeight()/2];

		quad1 q1 = new quad1(quadrant1, img);
		Thread t1 = new Thread(q1);
		quad2 q2 = new quad2(quadrant2, img);
		Thread t2 = new Thread(q2);
		quad3 q3 = new quad3(quadrant3, img);
		Thread t3 = new Thread(q3);
		quad4 q4 = new quad4(quadrant4, img);
		Thread t4 = new Thread(q4);
		
		Thread[] threadArr = new Thread[4];
		threadArr[0] = t1;
		threadArr[1] = t2;
		threadArr[2] = t3;
		threadArr[3] = t4;
		
		for (int t = 0; t < 4; t++){
			threadArr[t].start();
		}
		
		for (int k = 0; k < threadArr.length; k++){
			try {
				threadArr[k].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < img.getWidth()/2; i++){
			for (int k = 0; k < img.getHeight()/2; k++){
				colorArray[i][k] = quadrant1[i][k];
				colorArray[i + img.getWidth()/2][k] = quadrant2[i][k];
				colorArray[i][k + img.getHeight()/2] = quadrant3[i][k];
				colorArray[i + img.getWidth()/2][k + img.getHeight()/2] = quadrant4[i][k];
			}
		}
	}
	
	public void multithreadedConvert_16(Color[][] colorArray, BufferedImage img){
		int wSector = img.getWidth()/4;
		int hSector = img.getHeight()/4;
		
		ArrayList<Color[][]> sectors = new ArrayList<Color[][]>();
		for (int j = 0; j < 16; j++){
			sectors.add(new Color[wSector][hSector]);
		}
		
		Thread[] threadArr = new Thread[16];
		for (int i = 0; i < 16; i++){
			Thread t = new Thread(new sector16(sectors.get(i), img, i + 1));
			threadArr[i] = t;
			t.start();
		}
		
		for (int k = 0; k < threadArr.length; k++){
			try { threadArr[k].join();} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
		
		for (int j = 0; j < sectors.size(); j++){
			for (int i = 0; i < wSector; i++){
				for (int k = 0; k < hSector; k++){
					if (sectors.get(j)[i][k] == null){
						System.out.println(i + ", " + k);
					}
					else if (j == 0){
						colorArray[i][k] = sectors.get(j)[i][k];
					}
					else if (j == 1){
						colorArray[i + wSector][k] = sectors.get(j)[i][k];
					}
					else if (j == 2){
						colorArray[i + 2*wSector][k] = sectors.get(j)[i][k];
					}
					else if (j == 3){
						colorArray[i + 3*wSector][k] = sectors.get(j)[i][k];
					}
					else if (j == 4){
						colorArray[i][k + hSector] = sectors.get(j)[i][k];
					}
					else if (j == 5){
						colorArray[i + wSector][k + hSector] = sectors.get(j)[i][k];
					}
					else if (j == 6){
						colorArray[i + 2*wSector][k + hSector] = sectors.get(j)[i][k];
					}
					else if (j == 7){
						colorArray[i + 3*wSector][k + hSector] = sectors.get(j)[i][k];
					}
					else if (j == 8){
						colorArray[i][k + 2*hSector] = sectors.get(j)[i][k];
					}
					else if (j == 9){
						colorArray[i + wSector][k + 2*hSector] = sectors.get(j)[i][k];
					}
					else if (j == 10){
						colorArray[i + 2*wSector][k + 2*hSector] = sectors.get(j)[i][k];
					}
					else if (j == 11){
						colorArray[i + 3*wSector][k + 2*hSector] = sectors.get(j)[i][k];
					}
					else if (j == 12){
						colorArray[i][k + 3*hSector] = sectors.get(j)[i][k];
					}
					else if (j == 13){
						colorArray[i + wSector][k + 3*hSector] = sectors.get(j)[i][k];
					}
					else if (j == 14){
						colorArray[i + 2*wSector][k + 3*hSector] = sectors.get(j)[i][k];
					}
					else if (j == 15){
						colorArray[i + 3*wSector][k + 3*hSector] = sectors.get(j)[i][k];
					}
				}
			}
		}
	}
	
	
	public BufferedImage loadJPG(String filename){
		this.img = null;
		File newFile = new File(filename);
		try{
			img = ImageIO.read(newFile);
		} catch (IOException e){
			e.printStackTrace();
		}
		
		imgWidth = img.getWidth();
		imgHeight = img.getHeight();
		
		long startTime = System.nanoTime();		
		
		colorArray = new Color[img.getWidth()][img.getHeight()];
		convertTo2DArray(colorArray, img);
		if (hasHistogramBeenGenerated == false){
			generateHistogram(colorArray);
			hasHistogramBeenGenerated = true;
		}
		
		if (hasKMeansBeenGenerated == false){
		kmeans kms = new kmeans(colorArray, img.getWidth(), img.getHeight(), false);
		kColors = kms.calc();
		progress = 50;
	}
		
		long endTime = System.nanoTime();
		analysisTime = (endTime - startTime)/1000000000.0;		
		return img;
	}
	
	
	private int randomPixel(int max){
		Random rand = new Random();
		int coord = rand.nextInt(max);
		return coord;
	}
	
	
	public void sortPixels(Color[][] colorArray, BufferedImage img){
		int row1 = (this.slope + 1) + xMinBound;
		int row2 = colorArray.length - xMaxBound;
		int column1 = 1 + yMinBound;
		int column2 = colorArray[0].length - yMaxBound;
		
//		int row3 = colorArray.length - 1;
//		int row4 = 1;
//		int column3 = colorArray[0].length - 1;
//		int column4 = 1;
				
		for (int i = row1; i < row2; i++){
			for (int k = column1; k < column2; k++){

				switch(this.sortType){
				case 0:
					if(c1.equals(Color.RED)){
						if (greaterThan == true){
							if (colorArray[i][k].getRed() > colorArray[i - 1][k - 1].getRed()){
								sortHelper(colorArray, this.slope, i, k);
							}
						}
						else if (lessThan == true){
							if (colorArray[i][k].getRed() < colorArray[i - 1][k - 1].getRed()){
								sortHelper(colorArray, this.slope, i, k);
							}
						}
					}
					else if (c1.equals(Color.GREEN)){
						if (greaterThan == true){
							if (colorArray[i][k].getGreen() > colorArray[i - 1][k - 1].getGreen()){
								sortHelper(colorArray, this.slope, i, k);
							}
						}
						else if (lessThan == true){
							if (colorArray[i][k].getGreen() < colorArray[i - 1][k - 1].getGreen()){
								sortHelper(colorArray, this.slope, i, k);
							}
						}
					}
					else if (c1.equals(Color.BLUE)){
						if (greaterThan == true){
							if (colorArray[i][k].getBlue() > colorArray[i - 1][k - 1].getBlue()){
								sortHelper(colorArray, this.slope, i, k);
							}
						}
						else if (lessThan == true){
							if (colorArray[i][k].getBlue() < colorArray[i - 1][k - 1].getBlue()){
								sortHelper(colorArray, this.slope, i, k);
							}
						}
					}
					break;
				case 1:
					if (greaterThan == true){
						if (c1.equals(Color.RED)){
							if (c2.equals(Color.RED)){
								if (colorArray[i][k].getRed() > colorArray[i - 1][k - 1].getRed()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
							else if (c2.equals(Color.GREEN)){
								if (colorArray[i][k].getRed() > colorArray[i - 1][k - 1].getGreen()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
							else if (c2.equals(Color.BLUE)){
								if (colorArray[i][k].getRed() > colorArray[i - 1][k - 1].getBlue()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
						}
						else if (c1.equals(Color.GREEN)){
							if (c2.equals(Color.RED)){
								if (colorArray[i][k].getGreen() > colorArray[i - 1][k - 1].getRed()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
							else if (c2.equals(Color.GREEN)){
								if (colorArray[i][k].getGreen() > colorArray[i - 1][k - 1].getGreen()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
							else if (c2.equals(Color.BLUE)){
								if (colorArray[i][k].getGreen() > colorArray[i - 1][k - 1].getBlue()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
						}
						else if (c1.equals(Color.BLUE)){
							if (c2.equals(Color.RED)){
								if (colorArray[i][k].getBlue() > colorArray[i - 1][k - 1].getRed()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
							else if (c2.equals(Color.GREEN)){
								if (colorArray[i][k].getBlue() > colorArray[i - 1][k - 1].getGreen()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
							else if (c2.equals(Color.BLUE)){
								if (colorArray[i][k].getBlue() > colorArray[i - 1][k - 1].getBlue()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
						}
					}
					else if (lessThan == true){
						if (c1.equals(Color.RED)){
							if (c2.equals(Color.RED)){
								if (colorArray[i][k].getRed() < colorArray[i - 1][k - 1].getRed()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
							else if (c2.equals(Color.GREEN)){
								if (colorArray[i][k].getRed() < colorArray[i - 1][k - 1].getGreen()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
							else if (c2.equals(Color.BLUE)){
								if (colorArray[i][k].getRed() < colorArray[i - 1][k - 1].getBlue()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
						}
						else if (c1.equals(Color.GREEN)){
							if (c2.equals(Color.RED)){
								if (colorArray[i][k].getGreen() < colorArray[i - 1][k - 1].getRed()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
							else if (c2.equals(Color.GREEN)){
								if (colorArray[i][k].getGreen() < colorArray[i - 1][k - 1].getGreen()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
							else if (c2.equals(Color.BLUE)){
								if (colorArray[i][k].getGreen() < colorArray[i - 1][k - 1].getBlue()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
						}
						else if (c1.equals(Color.BLUE)){
							if (c2.equals(Color.RED)){
								if (colorArray[i][k].getBlue() < colorArray[i - 1][k - 1].getRed()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
							else if (c2.equals(Color.GREEN)){
								if (colorArray[i][k].getBlue() < colorArray[i - 1][k - 1].getGreen()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
							else if (c2.equals(Color.BLUE)){
								if (colorArray[i][k].getBlue() < colorArray[i - 1][k - 1].getBlue()){
									sortHelper(colorArray, this.slope, i, k);
								}
							}
						}
					}
					break;
				case 2:
					int boundWidth = img.getWidth();
					int boundHeight = img.getHeight();
					
					if (xMaxBound != 0){
						boundWidth = Math.abs(xMaxBound - xMinBound);
						boundHeight = Math.abs(yMaxBound - yMinBound);
					}
					
					int iSub = randomPixel(boundWidth);
					int kSub = randomPixel(boundHeight);
					if (greaterThan == true){
						if (c1.equals(Color.RED)){
							if (c2.equals(Color.RED)){
								if (colorArray[i][k].getRed() > colorArray[iSub][kSub].getRed()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
							else if (c2.equals(Color.GREEN)){
								if (colorArray[i][k].getRed() > colorArray[iSub][kSub].getGreen()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
							else if (c2.equals(Color.BLUE)){
								if (colorArray[i][k].getRed() > colorArray[iSub][kSub].getBlue()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
						}
						else if (c1.equals(Color.GREEN)){
							if (c2.equals(Color.RED)){
								if (colorArray[i][k].getGreen() > colorArray[iSub][kSub].getRed()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
							else if (c2.equals(Color.GREEN)){
								if (colorArray[i][k].getGreen() > colorArray[iSub][kSub].getGreen()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
							else if (c2.equals(Color.BLUE)){
								if (colorArray[i][k].getGreen() > colorArray[iSub][kSub].getBlue()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
						}
						else if (c1.equals(Color.BLUE)){
							if (c2.equals(Color.RED)){
								if (colorArray[i][k].getBlue() > colorArray[iSub][kSub].getRed()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
							else if (c2.equals(Color.GREEN)){
								if (colorArray[i][k].getBlue() > colorArray[iSub][kSub].getGreen()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
							else if (c2.equals(Color.BLUE)){
								if (colorArray[i][k].getBlue() > colorArray[iSub][kSub].getBlue()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
						}
					}
					else if (lessThan == true){
						if (c1.equals(Color.RED)){
							if (c2.equals(Color.RED)){
								if (colorArray[i][k].getRed() < colorArray[iSub][kSub].getRed()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
							else if (c2.equals(Color.GREEN)){
								if (colorArray[i][k].getRed() < colorArray[iSub][kSub].getGreen()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
							else if (c2.equals(Color.BLUE)){
								if (colorArray[i][k].getRed() < colorArray[iSub][kSub].getBlue()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
						}
						else if (c1.equals(Color.GREEN)){
							if (c2.equals(Color.RED)){
								if (colorArray[i][k].getGreen() < colorArray[iSub][kSub].getRed()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
							else if (c2.equals(Color.GREEN)){
								if (colorArray[i][k].getGreen() < colorArray[iSub][kSub].getGreen()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
							else if (c2.equals(Color.BLUE)){
								if (colorArray[i][k].getGreen() < colorArray[iSub][kSub].getBlue()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
						}
						else if (c1.equals(Color.BLUE)){
							if (c2.equals(Color.RED)){
								if (colorArray[i][k].getBlue() < colorArray[iSub][kSub].getRed()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
							else if (c2.equals(Color.GREEN)){
								if (colorArray[i][k].getBlue() < colorArray[iSub][kSub].getGreen()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
							else if (c2.equals(Color.BLUE)){
								if (colorArray[i][k].getBlue() < colorArray[iSub][kSub].getBlue()){
									randomSortHelper(colorArray, slope, i, k, iSub, kSub);
									iSub = randomPixel(boundWidth);
									kSub = randomPixel(boundHeight);
								}
							}
						}
					}
					break;
				}
			}
		}
	}
	
	
	private void randomSortHelper(Color[][] colorArray, int slope, int i, int k, int iSub, int kSub){
		colorArray[i][k] = colorArray[i][k - 1];
	}
	
	
	private void sortHelper(Color[][] colorArray, int slope, int i, int k){
		colorArray[i][k] = colorArray[i - slope][k - 1];
	}
	
	
	public void saveToJPG(Color[][] colorArray, BufferedImage img, String filename){
		for (int i = 0; i < colorArray.length; i++){			//loop for getting 2D array back into a BufferedImage for saving
			for (int k = 0; k < colorArray[0].length; k++){
				int pixel = colorArray[i][k].getRGB();
				img.setRGB(i, k, pixel);
			}
		}
		
		try {
		    BufferedImage bi = img;
		    File outputfile = new File(filename);
		    ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void multithreadedSaveToJPG(Color[][] colorArray, BufferedImage img, String filename){
//		System.out.println("MULTITHREADED SAVE WORKING");
		saveQuad1 sq1 = new saveQuad1(colorArray, img);
		Thread t1 = new Thread(sq1);
		saveQuad2 sq2 = new saveQuad2(colorArray, img);
		Thread t2 = new Thread(sq2);
		saveQuad3 sq3 = new saveQuad3(colorArray, img);
		Thread t3 = new Thread(sq3);
		saveQuad4 sq4 = new saveQuad4(colorArray, img);
		Thread t4 = new Thread(sq4);
		
		Thread[] threadArr = new Thread[4];
		threadArr[0] = t1;
		threadArr[1] = t2;
		threadArr[2] = t3;
		threadArr[3] = t4;
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		for (int k = 0; k < threadArr.length; k++){
			try {
				threadArr[k].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
		    File outputfile = new File(filename);
		    ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void buildLog(BufferedImage img, String filepath){
		String correctedFilename = " ";
		if (savedFilename.contains(".jpg")){
			correctedFilename = savedFilename.replace(".jpg", "_");
		}
		String buildFile = correctedFilename + "buildLog.txt";
		double megapixels = img.getWidth() * img.getHeight();
		try
		{
			PrintWriter w = new PrintWriter(buildFile);
			w.println("File Location: " + filepath);
			w.println("Image Resolution: " + img.getWidth() + "x" + img.getHeight() + ", " + Math.round(megapixels/1000000) + " megapixels");
			w.println("Analysis Time: " + analysisTime + " s");
			w.println("Total Sort Time: " + sortTime + " s");
			w.println("-----Settings Used-----");
			w.println("C1: " + "R " + c1.getRed() + ", G " + c1.getGreen() + ", B " + c1.getBlue());
			w.println("C2: " + "R " + c2.getRed() + ", G " + c2.getGreen() + ", B " + c2.getBlue());
			w.println("Slope Value: " + slope);
			w.println("Sort Mode: " + sortType);
			if (greaterThan == true){
				w.println("Bias: Greater-than");
			}
			else{
				w.println("Bias: Less-than");
			}
			w.println("Multithreaded Enabled: " + multithreaded);
			w.println("Total Area of Pixels Sorted: " + sortAreaLength + "x" + sortAreaHeight);
			w.println("-----K-Means Color-----");
			w.println("Cluster 1 Color: " + "R " + kColors[0].getRed() + ", G " + kColors[0].getGreen() + ", B " + kColors[0].getBlue());
			w.println("Cluster 2 Color: " + "R " + kColors[1].getRed() + ", G " + kColors[1].getGreen() + ", B " + kColors[1].getBlue());
			w.println("Cluster 3 Color: " + "R " + kColors[2].getRed() + ", G " + kColors[2].getGreen() + ", B " + kColors[2].getBlue());	

			w.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void resetBounds(){
		xMinBound = 0;
		xMaxBound = 0;
		yMinBound = 0;
		yMaxBound = 0;
	}
}
