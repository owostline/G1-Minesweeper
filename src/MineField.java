import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class MineField extends JButton{
	private boolean hasMine;
	private boolean isFlagged;
	private boolean isEnabled;
	private boolean exploded;
	private final int xlocation;
	private final int ylocationY;
	private int proximity;
	ImageIcon mineIcon;
	ImageIcon flagIcon;
	ImageIcon explosion;
	MinePanel containingPanel;
	DisplayPanel display;
	public MineField(int x, int y, DisplayPanel display, MinePanel panel) {
		this.display = display;
		containingPanel = panel;
		
		this.hasMine = false;
		this.isFlagged = false;
		this.isEnabled = true;
		this.exploded = false;
		
		this.xlocation = x;
		this.ylocationY = y;
		
		mineIcon = new ImageIcon("src\\Images\\mine.png");
		flagIcon = new ImageIcon("src\\Images\\red-flag.png");
		explosion = new ImageIcon("src\\Images\\explosion.png");
		
		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					if (isEnabled) {
						display.playSound(8);
						if (flagTheField())
							display.removeFromMinesLeft();
						else
							display.addToMinesLeft();
					}
				}
			}
		});
		this.setPreferredSize(new Dimension(20,20));
	}
	
	public boolean getEnabled() {
		return this.isEnabled;
	}
	public void setProximity(int proximity) {
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
			this.setIcon(this.exploded? this.explosion:this.mineIcon);
			this.setBackground(Color.LIGHT_GRAY);
			this.isEnabled = false;
		}
		else {
			revealNonMineField();
		}
	}
	public void revealNonMineField() {

		String s = getProximityImage(this.proximity);
		this.setIcon(new ImageIcon(s));
		this.setBackground(Color.LIGHT_GRAY);
		this.isEnabled = false;
		if (this.isFlagged)
			this.display.addToFlagsCounter();
		if (this.proximity == 0)
			this.containingPanel.recursiveReveal(getXcoordinate(),getYcoordinate());
	}
	public int getXcoordinate() {
		return this.xlocation;
	}
	public int getYcoordinate() {
		return this.ylocationY;
	}
	public void openField(boolean revealFlag) {
		if (this.isFlagged && !revealFlag)
			return;
		if (!this.isEnabled)
			return;
		if (this.hasMine) {
			this.boom();
		}
		else {
			if (!revealFlag)
				this.display.playSound(1);
			revealNonMineField();
		}
	}
	private void boom() {
		this.setBackground(Color.LIGHT_GRAY);
		this.isEnabled = false;
		this.exploded = true;
		this.containingPanel.revealAllFields();
		this.display.displayDefeat();
		this.display.playSound(3);
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
