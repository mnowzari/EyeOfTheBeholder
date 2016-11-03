package imageManipulation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class mainWindow extends JFrame{
	boolean greater = true;
	boolean less = false;
	boolean isHistDisplayed = false;
	boolean dbEnabled = true;
	String filepath = null;
	imagePreviewWindow imagePreviewWindow = null;
	boolean doesIPWExist = false;
	boolean isKColorDisplayed = false;
	histogramPreviewWindow histogramPreviewWindow = null;
	predominantColorWindow pdcw = null;
	kMeansWindow kmw = null;
	dataSaver ds;
	
	public mainWindow(final pixelSort ps, final dataSaver ds){
		
		Color bColor = new Color(0x0F1924);
		Color fColor = new Color(0xCA68CC);
		Color cColor = Color.DARK_GRAY;
//		Color xColor = new Color(0x5868AD);
		
		this.ds = ds;
		
		setLayout(null);
		setTitle("Pixel Sorter v0.3");
		setSize(290, 440);
		setLocation(1000, 220);
//		setLocationRelativeTo(null);
		getContentPane().setBackground(bColor);
		setResizable(false);
	
		//File Browser
		JPanel filePane = new JPanel();
		filePane.setSize(275, 40);
		filePane.setLocation(5, 25);
		filePane.setBackground(bColor);
		filePane.setLayout(new BoxLayout(filePane, BoxLayout.Y_AXIS));
//		filePane.setBorder(BorderFactory.createLineBorder(cColor));

		final JLabel fileLabel = new JLabel("File Not Selected");
		fileLabel.setForeground(fColor);
		
		JButton openJPG = new JButton("Select JPEG");
		openJPG.setBackground(bColor);
		openJPG.setForeground(fColor);
		openJPG.setBorder(BorderFactory.createLineBorder(fColor));
		
		filePane.add(openJPG);
		filePane.add(fileLabel);
				
		openJPG.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			    JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "JPG Images", "jpg");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(mainWindow.this);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	filepath = chooser.getSelectedFile().getPath();
			    	fileLabel.setText(chooser.getSelectedFile().getPath()); 
			    	
			    	loadImageWindow loadWindow = new loadImageWindow();
			    	loadWindow.setVisible(true);
			    	
			    	ps.filename = chooser.getSelectedFile().getName();
					ps.filepath = filepath;
					
				    ps.hasHistogramBeenGenerated = false;
				    ps.hasKMeansBeenGenerated = false;
					ps.loadJPG(filepath);
					ps.resetBounds();
					
				    if (isHistDisplayed == true){
					    histogramPreviewWindow.setVisible(false);
					    histogramPreviewWindow.dispose();
					    pdcw.setVisible(false);
					    pdcw.dispose();
				    	isHistDisplayed = false;
				    }
				    
				    if (isHistDisplayed == false){
						ps.hasHistogramBeenGenerated = true;
						histogramPreviewWindow = new histogramPreviewWindow(ps, ("RGBHistogram.png"), "Histogram", 1307, 220);
						histogramPreviewWindow.setVisible(true);
						pdcw = new predominantColorWindow(ps, 1307, 575);
						pdcw.setVisible(true);
						isHistDisplayed = true;
				    }
				    
				    if (isKColorDisplayed == true){
					    ps.hasKMeansBeenGenerated = false;
				    	isKColorDisplayed = false;
					    kmw.setVisible(false);
					    kmw.dispose();
				    }
				    
					if (isKColorDisplayed == false){
						ps.hasKMeansBeenGenerated = true;
						kmw = new kMeansWindow(ps);
						kmw.setVisible(true);	
						isKColorDisplayed = true;
					}
				    
				    if (doesIPWExist == false){
				    	loadWindow.setVisible(false);
				    	loadWindow.dispose();
				    	if (ps.checkIfLandscape() == true){
							imagePreviewWindow = new imagePreviewWindow(ps, ps.filepath, ("Image Preview " + ps.filename), 125, 220, false);				    		
				    	}
				    	else if (ps.checkIfLandscape() == false){
							imagePreviewWindow = new imagePreviewWindow(ps, ps.filepath, ("Image Preview " + ps.filename), 575, 220, false);				    		
				    	}
						imagePreviewWindow.setVisible(true);	
						doesIPWExist = true;
				    }
				    else{
				    	loadWindow.setVisible(false);
				    	loadWindow.dispose();
				    	imagePreviewWindow.dispose();
				    	if (ps.checkIfLandscape() == true){
							imagePreviewWindow = new imagePreviewWindow(ps, ps.filepath, ("Image Preview " + ps.filename), 125, 220, false);				    		
				    	}
				    	else if (ps.checkIfLandscape() == false){
							imagePreviewWindow = new imagePreviewWindow(ps, ps.filepath, ("Image Preview " + ps.filename), 575, 220, false);				    		
				    	}						
						imagePreviewWindow.setVisible(true);	
				    }
				    
				    if (dbEnabled == true){
				    	ds.appendDataToXML();
				    	ds.generateHistogramData();
				    }
			    }
			}
		});
		//
		
		//Check1
		JPanel checkPanel = new JPanel();
		checkPanel.setSize(275, 120);
		checkPanel.setBackground(bColor);
		checkPanel.setLocation(5, 80);
		checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.Y_AXIS));
		checkPanel.setBorder(BorderFactory.createLineBorder(cColor));
		
		JLabel sortModeLabel = new JLabel(" Sorting Mode");
		sortModeLabel.setForeground(fColor);
		
		final JRadioButton rb1 = new JRadioButton("Single Channel Sequential Sort");
		rb1.setBackground(bColor);
		rb1.setForeground(fColor);
		rb1.setSelected(true);
		final JRadioButton rb2 = new JRadioButton("Cross Channel Sequential Sort");
		rb2.setBackground(bColor);
		rb2.setForeground(fColor);
		rb2.setSelected(false);
		final JRadioButton rb3 = new JRadioButton("Cross Channel Random Sort");
		rb3.setBackground(bColor);
		rb3.setForeground(fColor);
		rb3.setSelected(false);
		
		ButtonGroup sortGroup = new ButtonGroup();
		sortGroup.add(rb1);
		sortGroup.add(rb2);
		sortGroup.add(rb3);
		
		rb1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (rb1.isSelected() == true){
					ps.sortType = 0;
				}
			}
		});
		
		rb2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (rb2.isSelected() == true){
					ps.sortType = 1;
				}
			}
		});
		
		rb3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (rb3.isSelected() == true){
					ps.sortType = 2;
				}
			}
		});
		
		checkPanel.add(sortModeLabel);
		checkPanel.add(rb1);
		checkPanel.add(rb2);
		checkPanel.add(rb3);
		//
		
		//User-Defined Sort Area Information
		JPanel areaPane = new JPanel();
		areaPane.setSize(275, 40);
		areaPane.setBackground(bColor);
		areaPane.setLocation(5, 205);
		areaPane.setLayout(new BoxLayout(areaPane, BoxLayout.PAGE_AXIS));
		areaPane.setBorder(BorderFactory.createLineBorder(cColor));
		
		JLabel bounds = new JLabel("Pixel Subset Sorting");
		bounds.setForeground(fColor);
		
		JButton clearBounds = new JButton("Reset User-Defined Sort Area");
		clearBounds.setBackground(bColor);
		clearBounds.setForeground(fColor);
		clearBounds.setBorder(BorderFactory.createLineBorder(fColor));
		
		clearBounds.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ps.resetBounds();
				if (doesIPWExist == true){
					imagePreviewWindow.resetBounds();
				}
			}
		});
		
		areaPane.add(bounds);
		areaPane.add(clearBounds);
		//
		
		//GenHistogram Check box
		JPanel advancedOptions = new JPanel();
		advancedOptions.setSize(275, 90);
		advancedOptions.setBackground(bColor);
		advancedOptions.setLocation(5, 250);
		advancedOptions.setLayout(new BoxLayout(advancedOptions, BoxLayout.PAGE_AXIS));
		advancedOptions.setBorder(BorderFactory.createLineBorder(cColor));
		
		JLabel advancedLabel = new JLabel(" Advanced Options");
		advancedLabel.setForeground(fColor);
		
		final JCheckBox saveToDB = new JCheckBox("Save Dominant Colors to XML?");
		saveToDB.setBackground(bColor);
		saveToDB.setForeground(fColor);
		saveToDB.setSelected(true);
		
		final JCheckBox buildLog = new JCheckBox("Generate Build Log After Pixel Sort");
		buildLog.setBackground(bColor);
		buildLog.setForeground(fColor);
		
		final JCheckBox multithread = new JCheckBox("Enable Multithreaded Analysis");
		multithread.setBackground(bColor);
		multithread.setForeground(fColor);
		
		advancedOptions.add(advancedLabel);
		advancedOptions.add(saveToDB);
		advancedOptions.add(buildLog);
		advancedOptions.add(multithread);
		
		saveToDB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (saveToDB.isSelected() == true){
					dbEnabled = true;
				}
				else {
					dbEnabled = false;
				}
			}	
		});
		
		multithread.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (multithread.isSelected() == true){
					ps.multithreaded = true;
				}
				else {
					ps.multithreaded = false;
				}
			}
		});
		
		buildLog.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (buildLog.isSelected() == true){
					ps.buildLog = true;
				}
				else {
					ps.buildLog = false;
				}
			}
		});
		//
		
		//Sort Mode Options
		JPanel sortModePane = new JPanel();
		sortModePane.setSize(140, 30);
		sortModePane.setLocation(-3, 175);
		sortModePane.setBackground(bColor);
		
		JButton sortModeOptions = new JButton("Sort Mode Options");
		sortModeOptions.setBackground(bColor);
		sortModeOptions.setForeground(fColor);
		sortModeOptions.setBorder(BorderFactory.createLineBorder(fColor));
		
		checkPanel.add(sortModeOptions);
		
		sortModeOptions.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (rb1.isSelected()){
					singleChannelWindow sm = new singleChannelWindow(ps); 
					sm.setVisible(true);
				}
				else if (rb2.isSelected()){
					crossChSeq ccs = new crossChSeq(ps);
					ccs.setVisible(true);
				}
				else if (rb3.isSelected()){
					crossChRand ccr = new crossChRand(ps);
					ccr.setVisible(true);
				}
			}
		});
		//
		
		//SORT button
		JPanel sortPane = new JPanel();
		sortPane.setSize(200, 25);
		sortPane.setBackground(bColor);
		sortPane.setLocation(40, 350);
		
		final JButton sort = new JButton("Initiate Pixel Sorting");
		sort.setBackground(bColor);
		sort.setForeground(fColor);
		sort.setBorder(BorderFactory.createLineBorder(fColor));
	
		sortPane.add(sort);
		
		sort.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (filepath == null){
					selectFileErrorWindow selectFilePlz = new selectFileErrorWindow();
					selectFilePlz.setVisible(true);
				}
				else {
					waitWindow ww = new waitWindow(ps);
					ww.setVisible(true);	
					ps.finalSort();
					ww.dispose();
					imagePreviewWindow.dispose();
			    	if (ps.checkIfLandscape() == true){
						imagePreviewWindow = new imagePreviewWindow(ps, ps.savedFilename, ("Image Preview " + ps.filename), 125, 220, false);				    		
			    	}
			    	else if (ps.checkIfLandscape() == false){
						imagePreviewWindow = new imagePreviewWindow(ps, ps.savedFilename, ("Image Preview " + ps.filename), 575, 220, false);				    		
			    	}							
			    	imagePreviewWindow.setVisible(true);			    	
				}
			}
		});
		
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				deleteHistogramOnClose(ps);				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				deleteHistogramOnClose(ps);				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		add(filePane);
		add(advancedOptions);
		add(checkPanel);
		add(sortPane);
		add(areaPane);
	}
	
	public static void deleteHistogramOnClose(pixelSort ps){
	    File toDelete = new File(ps.filepathOfHistogram);
		toDelete.delete();	
	}
}
