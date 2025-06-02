import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MineSweeperFrame extends JFrame {
	int mines;
	final int singleFieldSize = 20;
	final int displayRoom = 35;
	int margin = 25;
	int fieldWidth;
	int fieldHeight;
	MenuBar menu;
	MinePanel minePanel;
	DisplayPanel displayPanel;
	SettingsPanel settingsPanel;
	public MineSweeperFrame(int fieldWidth, int fieldHeight, int mines) {
		this.fieldWidth=fieldWidth;
		this.fieldHeight=fieldHeight;
		this.mines = mines;
		
		menu = new MenuBar(this);
		
		displayPanel = new DisplayPanel(mines, this);
		displayPanel.setPreferredSize(new Dimension(fieldWidth*singleFieldSize, displayRoom));
		
		setNewMinePanel();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(400, 200);
		this.setIconImage(new ImageIcon("src\\Images\\mine.png").getImage());
		this.setTitle("MineSweeper");
		this.setJMenuBar(menu);
		this.setLayout(new BorderLayout(0,0));
		this.add(minePanel,BorderLayout.CENTER);
		this.add(displayPanel,BorderLayout.NORTH);
		this.setSize(fieldWidth*singleFieldSize, fieldHeight*singleFieldSize+displayRoom);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	private void setNewMinePanel() {
		this.minePanel = new MinePanel(fieldWidth, fieldHeight, mines, displayPanel );
		this.minePanel.setPreferredSize(new Dimension(fieldWidth*singleFieldSize, fieldHeight*singleFieldSize));
		this.minePanel.mineTheBoard();
		this.minePanel.setProximityForFields();
	}
	public void displaySettings() {
		settingsPanel = new SettingsPanel(this.fieldWidth, this.fieldHeight, this.mines,this);
		this.displayPanel.enableDisableReset();
		this.displayPanel.pauseClock();
		this.remove(minePanel);
		this.add(settingsPanel);
		this.pack();
	}
	public void returnToGame() {
		this.remove(settingsPanel);
		this.add(minePanel);
		this.displayPanel.enableDisableReset();
		this.displayPanel.resumeClock();
		this.repaint();
		this.pack();
	}
	public void setNewGame(int width, int height, int newNumOfMines) {
		this.dispose();
		new MineSweeperFrame(width, height, newNumOfMines);
	}
	public void reset() { // fix later mines display wont reset
		this.remove(minePanel);
		this.setNewMinePanel();
		this.add(this.minePanel);
		this.displayPanel.pauseClock(); // pause then resumed to prevent timer overlap
		this.displayPanel.resetTimer();
		this.displayPanel.resetFlagDisplay(this.mines);
		this.displayPanel.resumeClock();
		this.repaint();
		this.pack();
	}
}