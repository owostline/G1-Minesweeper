import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar implements ActionListener{
	MineSweeperFrame thisFrame;
	JMenuItem settingsItem;
	JMenuItem sound;
	JMenuItem exitItem;
	JMenu menu;
	public MenuBar(MineSweeperFrame frame) {
		thisFrame = frame;
		
		settingsItem = new JMenuItem("Settings");
		settingsItem.addActionListener(this);
		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(this);
		sound = new JMenuItem("Sound OFF");
		sound.addActionListener(this);
		
		menu = new JMenu("Menu");
		
		menu.add(sound);
		menu.add(settingsItem);
		menu.add(exitItem);
				
		this.add(menu);
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
