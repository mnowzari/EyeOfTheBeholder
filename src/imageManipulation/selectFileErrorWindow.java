package imageManipulation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class selectFileErrorWindow extends JFrame{
	public selectFileErrorWindow(){
		Color bColor = new Color(0x0F1924);
		Color fColor = new Color(0xCA68CC);
		Color cColor = Color.DARK_GRAY;
		
		setLayout(null);
		setTitle("Choose a File!");
		setSize(275, 100);
		setLocationRelativeTo(null);
		getContentPane().setBackground(bColor);
		
		JPanel warningText = new JPanel();
		warningText.setSize(250, 250);
		warningText.setBackground(bColor);
		
		JLabel warningLabel = new JLabel("Please select a JPEG to sort!");
		warningLabel.setForeground(fColor);
		
		JPanel contPane = new JPanel();
		contPane.setSize(60, 25);
		contPane.setLocation(97, 25);
		contPane.setBackground(bColor);
		
		JButton cont = new JButton("Continue");
		cont.setSize(60, 35);
		cont.setBackground(bColor);
		cont.setForeground(fColor);
		cont.setBorder(BorderFactory.createLineBorder(cColor));
		
		cont.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
				dispose();
			}
		});
		
		contPane.add(cont);

		warningText.add(warningLabel);
		
		add(contPane);
		add(warningText);
	}
}
