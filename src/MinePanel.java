import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
public class MinePanel extends JPanel implements ActionListener {
	MineField[][] field; // array of the minefield
	private int minesToBePlaced; // counter for mines that still needs to be placed
	private final int fieldWidth; // columns
	private final int fieldHeight; // rows
	DisplayPanel display;
	public MinePanel(int width, int height, int mines, DisplayPanel display) { // setups button with coordinates and event listeners
		this.display = display;
		this.fieldWidth = width;
		this.fieldHeight = height;
		this.minesToBePlaced = mines;
		field = new MineField[height][width];
		for (int i = 0 ; i<field.length ; i++) {
			for (int j = 0 ; j<field[0].length ; j++) {
				field[i][j] = new MineField(j,i,display, this);
				field[i][j].addActionListener(this);
				this.add(field[i][j]);
			}
		}
		this.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		this.setBackground(Color.LIGHT_GRAY);
	}
	public void mineTheBoard() { // places the mine in the board
		while (this.minesToBePlaced>0) {
			int randomX = ((int) (Math.random()*this.fieldWidth));
			int randomY = ((int) (Math.random()*this.fieldHeight));
			if (field[randomY][randomX].setMine())
				minesToBePlaced--;
		}
	}
	public void setProximityForFields() { // sets proximity number for each field
		for (int i = 0 ; i<field.length ; i++) {
			for (int j = 0 ; j<field[0].length ; j++) {
				int proximity = getProximityInPosition(j, i); // count nearby mines
				field[i][j].setProximity(proximity); // set the number
			}
		}
	}
	private int getProximityInPosition(int x, int y) { // counts 8 surrounding positions for mines
		int[] surroundingsX = {-1,0,1,1,1,0,-1,-1};
		int[] surroundingsY = {1,1,1,0,-1,-1,-1,0};
		int counter = 0;
		for (int i = 0 ; i < 8 ; i++ ) {
			if (field[y][x].hasMine())
				continue;
			if (validCoordiantes(x+surroundingsX[i], y+surroundingsY[i]) &&
				field[y+surroundingsY[i]][x+surroundingsX[i]].hasMine()) {
					counter++;
			}
		}
		return counter;
	}
	private boolean validCoordiantes(int x, int y) { // checks coordinates; prevents array out of bounds
		return !(x>(fieldWidth-1) || x<0 || y>(fieldHeight-1) || y<0);
	}
	public void revealAllFields() { // shows the entire field
		for (int i = 0 ; i<field.length ; i++) {
			for (int j = 0 ; j<field[0].length ; j++) {
				field[i][j].revealField();
			}
		}
	}
	public void recursiveReveal(int x, int y) { // revealing empty areas
		int[] surroundingsX = {-1,0,1,1,1,0,-1,-1};
		int[] surroundingsY = {1,1,1,0,-1,-1,-1,0};
		for (int i = 0 ; i < 8 ; i++ ) {
			if (validCoordiantes(x+surroundingsX[i], y+surroundingsY[i]) &&
				field[y+surroundingsY[i]][x+surroundingsX[i]].isEnabled()) {
					field[y+surroundingsY[i]][x+surroundingsX[i]].openField(true);;
			}
		}
	}
	public boolean checkVictory() { // All non-mine fields revealed AND all mine fields still hidden
		for (int i = 0 ; i<field.length ; i++) {
			for (int j = 0 ; j<field[0].length ; j++) { 
				MineField fieldBeingChecked = field[i][j];
				if (
						(!fieldBeingChecked.hasMine() && fieldBeingChecked.getEnabled()) ||
						(fieldBeingChecked.hasMine() && !fieldBeingChecked.getEnabled())
						)
					return false;
			}
		}
		return true;
	}
	private void victory() {
		this.display.displayVictory();
		this.revealAllFields();
	}
	public void actionPerformed(ActionEvent e) { // handles clicks
		for (int i = 0 ; i<field.length ; i++) { 
			for (int j = 0 ; j<field[0].length ; j++) {
				if (e.getSource() == field[i][j]) {
					field[i][j].openField(false); // reveal the clicked field
					if (checkVictory()) { // check if player won
						this.display.playSound(4);
						victory();
					}
				}
			}
		}
	}
}
