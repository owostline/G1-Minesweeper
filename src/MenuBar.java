import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar implements ActionListener{
	MineSweeperFrame thisFrame;
	JMenuItem settingsItem;
	JMenuItem sound;
	JMenuItem exitItem;
	JMenu menu;
	JMenu directions;
	JLabel howTo;
	public MenuBar(MineSweeperFrame frame) {
		thisFrame = frame;
		
		settingsItem = new JMenuItem("Settings");
		settingsItem.addActionListener(this);
		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(this);
		sound = new JMenuItem("Sound OFF");
		sound.addActionListener(this);
		howTo = new JLabel("<html>- Uncover all safe tiles without clicking a mine.<br>- Numbers show nearby mines.<br>- Right click to flag.<br>- Click on Mr. Smiley to reset the game. <br>- Mr. Smiley wears sunglasses when you WIN the game.<br>Created by: G1 of BSCS 1-2</html>");
		
		menu = new JMenu("Menu");
		directions = new JMenu("How to Play");
		
		menu.add(sound);
		menu.add(settingsItem);
		menu.add(exitItem);

		directions.add(howTo);
				
		this.add(menu);
		this.add(directions);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == settingsItem) {
			thisFrame.displaySettings();
		}
		if (e.getSource() == exitItem) {
			System.exit(0);
		}
		if (e.getSource() == sound) {
			this.thisFrame.displayPanel.audioPlayer.turnVolumeOnOff();
			if (this.thisFrame.displayPanel.audioPlayer.getVolumeOnOrOff())
				sound.setText("Sound OFF");
			else
				sound.setText("Sound ON");
		}
	}
}
