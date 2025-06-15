import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MineSweeperFrame extends JFrame {
	int mines;
	final int singleFieldSize = 20; // px of individual mine field
	final int displayRoom = 35; // top display panel
	int margin = 25;
	int fieldWidth; // columns
	int fieldHeight; // rows
	MenuBar menu; // menu
	MinePanel minePanel; // minefield
	DisplayPanel displayPanel; // top panel
	SettingsPanel settingsPanel; // settings
	public MineSweeperFrame(int fieldWidth, int fieldHeight, int mines) { // UI
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
	private void setNewMinePanel() { // creates new mine panel (no. of mines, size)
		this.minePanel = new MinePanel(fieldWidth, fieldHeight, mines, displayPanel );
		this.minePanel.setPreferredSize(new Dimension(fieldWidth*singleFieldSize, fieldHeight*singleFieldSize));
		this.minePanel.mineTheBoard();
		this.minePanel.setProximityForFields();
	}
	public void displaySettings() { // replaces game panel with settings
		settingsPanel = new SettingsPanel(this.fieldWidth, this.fieldHeight, this.mines,this);
		this.displayPanel.enableDisableReset();
		this.displayPanel.pauseClock();
		this.remove(minePanel);
		this.add(settingsPanel);
		this.pack();
	}
	public void returnToGame() { // goes back to gamepanel
		this.remove(settingsPanel);
		this.add(minePanel);
		this.displayPanel.enableDisableReset();
		this.displayPanel.resumeClock(); // resumes timer
		this.repaint();
		this.pack();
	}
	public void setNewGame(int width, int height, int newNumOfMines) { // new difficulty + new game; used for changing difficulties
	
		this.dispose();
		new MineSweeperFrame(width, height, newNumOfMines);
	}
	public void reset() { // same difficulty + new game
		this.remove(minePanel);
		this.setNewMinePanel();
		this.add(this.minePanel);
		this.displayPanel.pauseClock(); 
		this.displayPanel.resetTimer();
		this.displayPanel.resetFlagDisplay(this.mines);
		this.displayPanel.resumeClock();
		this.repaint();
		this.pack();
	}
}