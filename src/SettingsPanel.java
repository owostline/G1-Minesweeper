import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SettingsPanel extends JPanel implements ActionListener{
	final int singlefieldSize = 20;
	ButtonGroup difficulty;
	JRadioButton easy;
	JRadioButton normal;
	JRadioButton hard;
	JLabel difficultyLabel;
	JButton back;
	JButton select;
	JPanel difficultySettings;
	MineSweeperFrame frame;
	
	public SettingsPanel(int fieldWidth, int fieldHeight, int minesNum, MineSweeperFrame frame) {
		
		this.frame = frame;
		
		easy = new JRadioButton("Easy ");
		easy.setBackground(Color.LIGHT_GRAY);
		easy.setSize(new Dimension(20,20));
		easy.addActionListener(this);
		normal = new JRadioButton("Normal ");
		normal.setBackground(Color.LIGHT_GRAY);
		normal.addActionListener(this);
		hard = new JRadioButton("Hard ");
		hard.addActionListener(this);
		hard.setBackground(Color.LIGHT_GRAY);
		
		difficulty = new ButtonGroup();
		difficulty.add(easy);
		difficulty.add(normal);
		difficulty.add(hard);
		difficultyLabel = new JLabel("        Difficulty selection:");
		
		back = new JButton("Back");
		back.addActionListener(this);
		select = new JButton("Select");
		select.addActionListener(this);
		
		difficultySettings = new JPanel();
		difficultySettings.setLayout(new BoxLayout(difficultySettings, BoxLayout.Y_AXIS));
		difficultySettings.setPreferredSize(new Dimension(180,180));
		difficultySettings.setBackground(Color.LIGHT_GRAY);
		difficultySettings.setBorder(BorderFactory.createLineBorder(Color.black,1));
		
		// Center align all components
		difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		easy.setAlignmentX(Component.CENTER_ALIGNMENT);
		normal.setAlignmentX(Component.CENTER_ALIGNMENT);
		hard.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Button panel for side-by-side buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(back);
		buttonPanel.add(select);
		
		difficultySettings.add(Box.createVerticalStrut(10));
		difficultySettings.add(difficultyLabel);
		difficultySettings.add(Box.createVerticalStrut(5));
		difficultySettings.add(easy);
		difficultySettings.add(Box.createVerticalStrut(5));
		difficultySettings.add(normal);
		difficultySettings.add(Box.createVerticalStrut(5));
		difficultySettings.add(hard);
		difficultySettings.add(Box.createVerticalStrut(10));
		difficultySettings.add(buttonPanel);
		
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(fieldHeight*singlefieldSize, fieldHeight*singlefieldSize));
		this.add(difficultySettings);
		this.setOpaque(true);
		this.setBackground(Color.LIGHT_GRAY);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			frame.returnToGame();
		}
		if (e.getSource() == select) {
			if (easy.isSelected()) {
				frame.setNewGame(9,9,10);
			}
			if (normal.isSelected()) {
				frame.setNewGame(16,16,40);
			}
			if (hard.isSelected()) {
				frame.setNewGame(32,16,99);
			}
		}
	}
}