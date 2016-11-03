package imageManipulation;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class singleChannelWindow extends JFrame{
	public singleChannelWindow(final pixelSort ps){
		Color bColor = new Color(0x0F1924);
		Color fColor = new Color(0xCA68CC);
		Color xColor = new Color(0x5868AD);
		Color cColor = Color.DARK_GRAY;

		setLayout(null);
		setTitle("Single Channel Sequential");
		setSize(300, 285);
		setLocationRelativeTo(null);
		getContentPane().setBackground(bColor);
		setResizable(false);
		
		//Color Select for Single-Channel Sort
		JPanel lp = new JPanel();
		lp.setSize(200, 20);
		lp.setLocation(15, 15);
		lp.setBackground(bColor);
		JLabel label = new JLabel("Color Channel to Sort By:");
		label.setForeground(fColor);
		
		lp.add(label);
		
		JPanel singleChanColor = new JPanel();
		singleChanColor.setSize(250, 35);
		singleChanColor.setLocation(18, 40);
		singleChanColor.setBackground(xColor);
		singleChanColor.setBorder(BorderFactory.createLineBorder(cColor));
		
		JRadioButton rb6 = new JRadioButton("Red");
		rb6.setBackground(xColor);
		rb6.setForeground(Color.RED);
		JRadioButton rb7 = new JRadioButton("Green");
		rb7.setBackground(xColor);
		rb7.setForeground(Color.GREEN);
		JRadioButton rb8 = new JRadioButton("Blue");
		rb8.setBackground(xColor);
		rb8.setForeground(Color.BLUE);
		
		if (ps.c1 == Color.RED){
			rb6.setSelected(true);
		}
		else if (ps.c1 == Color.GREEN){
			rb7.setSelected(true);
		}
		else if (ps.c1 == Color.BLUE){
			rb8.setSelected(true);
		}
		
		ButtonGroup gpl3 = new ButtonGroup();
		gpl3.add(rb6);
		gpl3.add(rb7);
		gpl3.add(rb8);

		rb6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ps.c1 = Color.RED;
			}
		});
		
		rb7.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ps.c1 = Color.GREEN;
			}
		});
		
		rb8.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ps.c1 = Color.BLUE;
			}
		});
		
		singleChanColor.add(rb6);
		singleChanColor.add(rb7);
		singleChanColor.add(rb8);
		//
		
		//</> option
		JPanel biasPane = new JPanel();
		biasPane.setSize(190, 50);
		biasPane.setLocation(0, 90);
		biasPane.setBackground(bColor);
		biasPane.setLayout((LayoutManager) new BoxLayout(biasPane, BoxLayout.PAGE_AXIS));
		
		final JRadioButton rb4 = new JRadioButton("Greater-than Sorting");
		rb4.setBackground(bColor);
		rb4.setForeground(fColor);
		rb4.setSelected(true);
		JRadioButton rb5 = new JRadioButton("Less-than Sorting");
		rb5.setBackground(bColor);
		rb5.setForeground(fColor);
		rb5.setSelected(false);
		
		ButtonGroup gpl2 = new ButtonGroup();
		gpl2.add(rb4);
		gpl2.add(rb5);
		
		if (ps.greaterThan == true){
			rb4.setSelected(true);
		}
		else if (ps.lessThan == true){
			rb5.setSelected(true);
		}
		
		rb4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ps.greaterThan = true;
				ps.lessThan = false;
			}
		});
		
		rb5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ps.lessThan = true;
				ps.greaterThan = false;
			}
		});
		
		biasPane.add(rb4);
		biasPane.add(rb5);
		//
		
		//Slope Parameter
		JPanel slopePanel = new JPanel();
		slopePanel.setSize(250, 35);
		slopePanel.setLocation(0, 150);
		slopePanel.setBackground(bColor);
		slopePanel.setLayout((LayoutManager) new BoxLayout(slopePanel, BoxLayout.Y_AXIS));
		
		final JLabel slopeLabel = new JLabel("Slope value: " + ps.slope);
		slopeLabel.setForeground(fColor);
		
		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 360, ps.slope);
		slider.setBackground(bColor);
		slider.setForeground(fColor);
		
		slider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				ps.slope = slider.getValue();
				slopeLabel.setText("Slope value: " + String.valueOf(slider.getValue()));
			}
		});
		
		slopePanel.add(slopeLabel);
		slopePanel.add(slider);
		//
		
		//Apply Button
		JPanel applyPane = new JPanel();
		applyPane.setSize(80, 35);
		applyPane.setLocation(95, 195);
		applyPane.setBackground(bColor);
		
		JButton apply = new JButton("Apply");
		apply.setBackground(bColor);
		apply.setForeground(fColor);
//		apply.setBorder(BorderFactory.createLineBorder(fColor));

		applyPane.add(apply);
		
		apply.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
				dispose();
			}
		});
		//
		
		add(slopePanel);
		add(biasPane);
		add(applyPane);
		add(lp);
		add(singleChanColor);
	}
}
