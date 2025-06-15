import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class MineField extends JButton{
	// field state
	private boolean hasMine;
	private boolean isFlagged;
	private boolean isEnabled;
	private boolean exploded;
	// field position
	private final int xlocation;
	private final int ylocationY;
	private int proximity;
	// visual assets
	ImageIcon mineIcon;
	ImageIcon flagIcon;
	ImageIcon explosion;
	// references
	MinePanel containingPanel;
	DisplayPanel display;
	public MineField(int x, int y, DisplayPanel display, MinePanel panel) {
		this.display = display;
		containingPanel = panel;
		
		//state initialization
		this.hasMine = false;
		this.isFlagged = false;
		this.isEnabled = true;
		this.exploded = false;
		
		this.xlocation = x;
		this.ylocationY = y;
		
		mineIcon = new ImageIcon("src\\Images\\mine.png");
		flagIcon = new ImageIcon("src\\Images\\red flag.png");
		explosion = new ImageIcon("src\\Images\\explosion.png");
		
		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) { // right click	
					if (isEnabled) {
						display.playSound(8); // play sound
						if (flagTheField()) // flagging
							display.removeFromMinesLeft();
						else
							display.addToMinesLeft();
					}
				}
			}
		});
		this.setPreferredSize(new Dimension(20,20));
	}
	
	public boolean getEnabled() { // returns if clickable
		return this.isEnabled;
	}
	public void setProximity(int proximity) { // sets number of adjacent mines
		this.proximity = proximity;
	}
	public boolean flagTheField() {
		this.isFlagged =!this.isFlagged;
		if (this.isFlagged)
			this.setIcon(flagIcon);
		else
			this.setIcon(null);
		return this.isFlagged;
	}
	public boolean hasMine() { 
		return this.hasMine;
	}
	public boolean setMine() { 
		if (this.hasMine == false) {
			this.hasMine = true;
			return true;
		}
		return false;
	}
	public void revealField() {
		if (this.hasMine) {
			this.setIcon(this.exploded? this.explosion:this.mineIcon); // show mine icon
			this.setBackground(Color.LIGHT_GRAY);
			this.isEnabled = false;
		}
		else {
			revealNonMineField(); // reveal field
		}
	}
	public void revealNonMineField() {

		String s = getProximityImage(this.proximity); // get number image path
		this.setIcon(new ImageIcon(s));	// show proximity number
		this.setBackground(Color.LIGHT_GRAY);
		this.isEnabled = false; // disable clicking
		if (this.isFlagged)
			this.display.addToFlagsCounter(); // adjust flag number
		if (this.proximity == 0) // if no adjacent mines
			this.containingPanel.recursiveReveal(getXcoordinate(),getYcoordinate()); // reveal
	}
	public int getXcoordinate() { // return position
		return this.xlocation;
	}
	public int getYcoordinate() { // return position
		return this.ylocationY;
	}
	public void openField(boolean revealFlag) {
		if (this.isFlagged && !revealFlag) // can't open flagged fields
			return;
		if (!this.isEnabled) // can't open revealed fields
			return;
		if (this.hasMine) {
			this.boom(); // explode!
		}
		else {
			if (!revealFlag)
				this.display.playSound(1); // play sound
			revealNonMineField(); // reveal field
		}
	}
	private void boom() {
		this.setBackground(Color.LIGHT_GRAY);
		this.isEnabled = false;
		this.exploded = true; // mark as exploded
		this.containingPanel.revealAllFields(); // show entire board
		this.display.displayDefeat(); // update display as defeat
		this.display.playSound(3); // sound
	}
	public String toString() {
		return "(" + getXcoordinate() + "," + getYcoordinate() + ")";
	}
	private String getProximityImage(int proximity) {
		String imageString = "";
		switch (proximity) {
			case 0:
				break;
			case 1:
				imageString = "src\\Images\\one.png";
				break;
			case 2:
				imageString = "src\\Images\\two.png";
				break;
			case 3:
				imageString = "src\\Images\\three.png";
				break;
			case 4:
				imageString = "src\\Images\\four.png";
				break;
			case 5:
				imageString = "src\\Images\\five.png";
				break;
			case 6:
				imageString = "src\\Images\\six.png";
				break;
			case 7:
				imageString = "src\\Images\\seven.png";
				break;
			case 8:
				imageString = "src\\Images\\eight.png";
		}
		return imageString;
	}
}
