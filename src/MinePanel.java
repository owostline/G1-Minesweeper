import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
public class MinePanel extends JPanel implements ActionListener {
	MineField[][] field;
	private int minesToBePlaced;
	private final int fieldWidth;
	private final int fieldHeight;
	DisplayPanel display;
	public MinePanel(int width, int height, int mines, DisplayPanel display) {
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
	public void mineTheBoard() {
		while (this.minesToBePlaced>0) {
			int randomX = ((int) (Math.random()*this.fieldWidth));
			int randomY = ((int) (Math.random()*this.fieldHeight));
			if (field[randomY][randomX].setMine())
				minesToBePlaced--;
		}
	}
	public void setProximityForFields() {
		for (int i = 0 ; i<field.length ; i++) {
			for (int j = 0 ; j<field[0].length ; j++) {
				int proximity = getProximityInPosition(j, i);
				field[i][j].setProximity(proximity);
			}
		}
	}
	private int getProximityInPosition(int x, int y) {
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
	private boolean validCoordiantes(int x, int y) {
		return !(x>(fieldWidth-1) || x<0 || y>(fieldHeight-1) || y<0);
	}
	public void revealAllFields() {
		for (int i = 0 ; i<field.length ; i++) {
			for (int j = 0 ; j<field[0].length ; j++) {
				field[i][j].revealField();
			}
		}
	}
	public void recursiveReveal(int x, int y) {
		int[] surroundingsX = {-1,0,1,1,1,0,-1,-1};
		int[] surroundingsY = {1,1,1,0,-1,-1,-1,0};
		for (int i = 0 ; i < 8 ; i++ ) {
			if (validCoordiantes(x+surroundingsX[i], y+surroundingsY[i]) &&
				field[y+surroundingsY[i]][x+surroundingsX[i]].isEnabled()) {
					field[y+surroundingsY[i]][x+surroundingsX[i]].openField(true);;
			}
		}
	}
	public boolean checkVictory() {
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
	public void actionPerformed(ActionEvent e) {
		for (int i = 0 ; i<field.length ; i++) {
			for (int j = 0 ; j<field[0].length ; j++) {
				if (e.getSource() == field[i][j]) {
					field[i][j].openField(false);
					if (checkVictory()) {
						this.display.playSound(4);
						victory();
					}
				}
			}
		}
	}
}
