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
import javax.swing.JTextField;


public class crossChRand extends JFrame{

	public crossChRand(final pixelSort ps){
		Color bColor = new Color(0x0F1924);
		Color fColor = new Color(0xCA68CC);
		Color xColor = new Color(0x5868AD);
		Color cColor = Color.DARK_GRAY;
		
		setLayout(null);
		setTitle("Cross-Channel Random");
		setSize(300, 325);
		setLocationRelativeTo(null);
		getContentPane().setBackground(bColor);
		
		JPanel lp = new JPanel();
		lp.setSize(200, 20);
		lp.setLocation(15, 15);
		lp.setBackground(bColor);
		
		JLabel label = new JLabel("Color Channel 1:");
		label.setForeground(fColor);
		
		lp.add(label);
		
		JPanel singleChanColor1 = new JPanel();
		singleChanColor1.setSize(250, 35);
		singleChanColor1.setLocation(18, 40);
		singleChanColor1.setBackground(xColor);
		singleChanColor1.setBorder(BorderFactory.createLineBorder(cColor));
		
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
		
		singleChanColor1.add(rb6);
		singleChanColor1.add(rb7);
		singleChanColor1.add(rb8);
		
		//----------------------
		
		JPanel lp2 = new JPanel();
		lp2.setSize(200, 20);
		lp2.setLocation(15, 90);
		lp2.setBackground(bColor);
		
		JLabel label2 = new JLabel("Color Channel 2:");
		label2.setForeground(fColor);
		
		lp2.add(label2);
		
		JPanel singleChanColor2 = new JPanel();
		singleChanColor2.setSize(250, 35);
		singleChanColor2.setLocation(18, 115);
		singleChanColor2.setBackground(xColor);
		singleChanColor2.setBorder(BorderFactory.createLineBorder(cColor));
		
		JRadioButton rb9 = new JRadioButton("Red");
		rb9.setBackground(xColor);
		rb9.setForeground(Color.RED);
		JRadioButton rb10 = new JRadioButton("Green");
		rb10.setBackground(xColor);
		rb10.setForeground(Color.GREEN);
		JRadioButton rb11 = new JRadioButton("Blue");
		rb11.setBackground(xColor);
		rb11.setForeground(Color.BLUE);
		
		if (ps.c2 == Color.RED){
			rb9.setSelected(true);
		}
		else if (ps.c2 == Color.GREEN){
			rb10.setSelected(true);
		}
		else if (ps.c2 == Color.BLUE){
			rb11.setSelected(true);
		}
		
		ButtonGroup gpl4 = new ButtonGroup();
		gpl4.add(rb9);
		gpl4.add(rb10);
		gpl4.add(rb11);
		
		rb9.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ps.c2 = Color.RED;
			}
		});
		
		rb10.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ps.c2 = Color.GREEN;
			}
		});
		
		rb11.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ps.c2 = Color.BLUE;
			}
		});
		
		singleChanColor2.add(rb9);
		singleChanColor2.add(rb10);
		singleChanColor2.add(rb11);
		
		
		//</> option
		JPanel biasPane = new JPanel();
		biasPane.setSize(190, 50);
		biasPane.setLocation(0, 175);
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
		
		//Apply Button
		JPanel applyPane = new JPanel();
		applyPane.setSize(80, 35);
		applyPane.setLocation(105, 235);
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
		
		add(biasPane);
		add(singleChanColor1);
		add(singleChanColor2);
		add(lp2);
		add(lp);
		add(applyPane);
	}
}
