package imageManipulation;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class dataSaver {
	pixelSort ps;
	String filename;
	Document document;
	
	public dataSaver(pixelSort ps){
		filename = " ";
		this.ps = ps;
		loadXMLToMemory();
	}
	
	public Document initXMLFile(){
		Document doc = null;
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			// root elements
			doc = docBuilder.newDocument();
			
			Element rootElement = doc.createElement("photoDatabase");
			doc.appendChild(rootElement);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			StreamResult result = new StreamResult(new File("photoDatabase/photoDatabase.xml"));
			transformer.transform(source, result);
		}
		catch (ParserConfigurationException | TransformerException pce) {
			pce.printStackTrace();
		}
		return doc;
	}
	
	public void loadXMLToMemory(){
		checkFileExists();
		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			document = docBuilder.parse(new File("photoDatabase/photoDatabase.xml"));	
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void checkFileExists(){
		File xmlFile = new File("photoDatabase/photoDatabase.xml");
		if (!xmlFile.exists()){
			initXMLFile();
		}
	}
	
	private String correctFilename(){
		filename = filename.replace(" ", "");
		filename = filename.trim();
		return filename;
	}
	
	private boolean isRedundant(Document doc){ 
		boolean isRedundant = false;
		try {
			NodeList nList = doc.getElementsByTagName("Photo");

			for (int i = 0; i < nList.getLength(); i++){
				Node n = nList.item(i);
				Element e = (Element) n;
				String id = e.getAttribute("id");
								
				if (id.equalsIgnoreCase(filename)){
					String red = e.getElementsByTagName("red").item(0).getTextContent();
					String green = e.getElementsByTagName("green").item(0).getTextContent();
					String blue = e.getElementsByTagName("blue").item(0).getTextContent();
					int uniqueValueFromXML = (Integer.valueOf(red) * Integer.valueOf(green) * Integer.valueOf(blue));
					int uniqueValueFromJPG = (ps.kColors[0].getRed() * ps.kColors[0].getGreen() * ps.kColors[0].getBlue());
					
					if (uniqueValueFromXML == uniqueValueFromJPG){
						isRedundant = true;						
					}
					else {
						filename = uniqueValueFromJPG + "_" + filename;
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return isRedundant;
	}
	
	public void appendDataToXML(){
		try {
			Element root = document.getDocumentElement();
			
			//data
			this.filename = "x_" + ps.filename;
			this.filename = correctFilename();			
			if (isRedundant(document) == false){
				
				Element photo = document.createElement("Photo");
				
				Attr photoAttr = document.createAttribute("id");
				photoAttr.setValue(filename);
				photo.setAttributeNode(photoAttr);
					
					//Predominant Color 1
				Element pdc1 = document.createElement("PDC1");
				photo.appendChild(pdc1);
					
				Element red = document.createElement("red");
				red.setTextContent(Integer.toString(ps.kColors[0].getRed()));
				pdc1.appendChild(red);
					
				Element green = document.createElement("green");
				green.setTextContent(Integer.toString(ps.kColors[0].getGreen()));
				pdc1.appendChild(green);
					
				Element blue = document.createElement("blue");
				blue.setTextContent(Integer.toString(ps.kColors[0].getBlue()));
				pdc1.appendChild(blue);
								
					//Predominant Color 2
				Element pdc2 = document.createElement("PDC2");
				photo.appendChild(pdc2);
					
				Element red2 = document.createElement("red");
				red2.setTextContent(Integer.toString(ps.kColors[1].getRed()));
				pdc2.appendChild(red2);
					
				Element green2 = document.createElement("green");
				green2.setTextContent(Integer.toString(ps.kColors[1].getGreen()));
				pdc2.appendChild(green2);
					
				Element blue2 = document.createElement("blue");
				blue2.setTextContent(Integer.toString(ps.kColors[1].getBlue()));
				pdc2.appendChild(blue2);
								
				//Predominant Color 3
				Element pdc3 = document.createElement("PDC3");
				photo.appendChild(pdc3);
					
				Element red3 = document.createElement("red");
				red3.setTextContent(Integer.toString(ps.kColors[2].getRed()));
				pdc3.appendChild(red3);
					
				Element green3 = document.createElement("green");
				green3.setTextContent(Integer.toString(ps.kColors[2].getGreen()));
				pdc3.appendChild(green3);
					
				Element blue3 = document.createElement("blue");
				blue3.setTextContent(Integer.toString(ps.kColors[2].getBlue()));
				pdc3.appendChild(blue3);
					
				root.appendChild(photo);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(document);
				
				StreamResult result = new StreamResult(new File("photoDatabase/photoDatabase.xml"));
				transformer.transform(source, result);
			}	
			
		} catch (Exception  tfe) {
			tfe.printStackTrace();
		}
	}
	
	private void generateHistogramHelper(NodeList nList, ArrayList<int[]> values){
		for (int i = 0; i < nList.getLength(); i++){
			Node n = nList.item(i);
			Element e = (Element) n;
			String red = e.getElementsByTagName("red").item(0).getTextContent();
			String green = e.getElementsByTagName("green").item(0).getTextContent();
			String blue = e.getElementsByTagName("blue").item(0).getTextContent();
			int[] rgb = new int[3];
			rgb[0] = Integer.parseInt(red);
			rgb[1] = Integer.parseInt(green);
			rgb[2] = Integer.parseInt(blue);
			values.add(rgb);
		}
	}
	
	public void generateHistogramData(){
		int[] redValues = new int[256];
		int[] greenValues = new int[256];
		int[] blueValues = new int[256];
		ArrayList<int[]> rgbValues = new ArrayList<int[]>();
		
		try {
//			loadXMLToMemory();
//			Element root = document.getDocumentElement();

			NodeList nList = document.getElementsByTagName("PDC1");
			generateHistogramHelper(nList, rgbValues);

			nList = document.getElementsByTagName("PDC2");			
			generateHistogramHelper(nList, rgbValues);

			nList = document.getElementsByTagName("PDC3");			
			generateHistogramHelper(nList, rgbValues);
			
			for (int i = 0; i < rgbValues.size(); i++){
				redValues[rgbValues.get(i)[0]]++;
				greenValues[rgbValues.get(i)[1]]++;
				blueValues[rgbValues.get(i)[2]]++;
			}
//			ps.histogramVisualization(redValues, greenValues, blueValues, "XML_Histogram.png");
			saveHistogramXML(redValues, greenValues, blueValues);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveHistogramXML(int[] red, int[] green, int[] blue){
		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document histogramDoc = docBuilder.newDocument();
			
//			Element root = histogramDoc.getDocumentElement();
			
			Element rgbValues = histogramDoc.createElement("RGBVALUES");
			
			Element redValues = histogramDoc.createElement("RED");
			rgbValues.appendChild(redValues);
			
			Element greenValues = histogramDoc.createElement("GREEN");
			rgbValues.appendChild(greenValues);
			
			Element blueValues = histogramDoc.createElement("BLUE");
			rgbValues.appendChild(blueValues);
			
			for (int i = 0; i < red.length; i++){
				Element e = histogramDoc.createElement("Color");
				e.setTextContent(Integer.toString(red[i]));
				redValues.appendChild(e);
				e = histogramDoc.createElement("Color");
				e.setTextContent(Integer.toString(green[i]));
				greenValues.appendChild(e);
				e = histogramDoc.createElement("Color");
				e.setTextContent(Integer.toString(blue[i]));
				blueValues.appendChild(e);
			}
			histogramDoc.appendChild(rgbValues);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(histogramDoc);
			
			StreamResult result = new StreamResult(new File("photoDatabase/histogram.xml"));
			transformer.transform(source, result);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
