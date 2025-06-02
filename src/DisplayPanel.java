import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DisplayPanel extends JPanel{
	private boolean resetEnabled;
	ImageIcon smile;
	ImageIcon dead;
	ImageIcon cool;
	private int numOfMines;
	JLabel minesLeft;
	JButton reset;
	JLabel timer;
	private boolean clockActive;
	private int time;
	Timer clock;
	TimerTask tick;
	MineSweeperFrame frame;
	AudioPlayer audioPlayer;
	public DisplayPanel(int mines, MineSweeperFrame frame) {
		try { audioPlayer = new AudioPlayer(); } 
		catch (LineUnavailableException e1) {e1.printStackTrace();} 
		catch (IOException e1) {e1.printStackTrace();} 
		catch (UnsupportedAudioFileException e1) {e1.printStackTrace();}
		
		this.frame = frame;
		
		resetEnabled = true;
		clockActive = true;
		
		Border border = BorderFactory.createLineBorder(new Color(255,50,50),2);
		
		smile = new ImageIcon("src\\Images\\smile.png");
		dead = new ImageIcon("src\\Images\\dead.png");
		cool = new ImageIcon("src\\Images\\cool.png");
		
		numOfMines = mines;
		minesLeft = new JLabel();
		updateMinesDisplay(mines);
		minesLeft.setHorizontalAlignment(JLabel.CENTER);
		minesLeft.setFont(new Font("Monospaced",Font.BOLD,20));
		minesLeft.setPreferredSize(new Dimension(40,30));
		minesLeft.setOpaque(true);
		minesLeft.setForeground(new Color(255,50,50));
		minesLeft.setBackground(Color.BLACK);
		minesLeft.setBackground(Color.BLACK);
		minesLeft.setBorder(border);
		
		timer = new JLabel("000");
		timer.setHorizontalAlignment(JLabel.CENTER);
		timer.setFont(new Font("Monospaced",Font.BOLD,20));
		timer.setPreferredSize(new Dimension(40,30));
		timer.setOpaque(true);
		timer.setForeground(new Color(255,50,50));
		timer.setBackground(Color.BLACK);
		timer.setBorder(border);
		
		//start timer
		time = 0;
		setNewClock();
		
		clock.scheduleAtFixedRate(tick, 0 ,1000);
		reset = new JButton();
		reset.setIcon(smile);
		reset.setPreferredSize(new Dimension(30,30));
		reset.setFocusable(false);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resetEnabled()) {
					reset.setIcon(smile);
					playSound(7);
					frame.reset();
				}
			}
		}); 
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER,15,0));
		this.add(minesLeft);
		this.add(reset);
		this.add(timer);
		playSound(2);
	}
	public void removeMine() {
		numOfMines--;
		updateMinesDisplay(numOfMines);
	}
	private void updateMinesDisplay(int mines) {
		if (numOfMines<10)
			minesLeft.setText("00"+numOfMines);
		else if (numOfMines>9 && numOfMines<99)
			minesLeft.setText("0"+numOfMines);
		else if (numOfMines>98)
			minesLeft.setText(""+numOfMines);
	}
	public void addToMinesLeft() {
		this.numOfMines++;
		updateMinesDisplay(this.numOfMines);
	}
	public void removeFromMinesLeft() {
		this.numOfMines--;
		updateMinesDisplay(this.numOfMines);
	}
	private void setTime() {
		if (time<10)
			timer.setText("00"+time);
		else if (time>9 && time<=99)
			timer.setText("0"+time);
		else if (time>99 && time != 999)
			timer.setText(""+time);
		else {
			timer.setText("999");
			clock.cancel();
		}
	}
	private boolean resetEnabled() {
		return this.resetEnabled;
	}
	public void enableDisableReset() {
		this.resetEnabled = !this.resetEnabled;
	}
	public void pauseClock() {
		clockActive = false;
		this.clock.cancel();
	}
	public void setNewClock() {
		this.clock = new Timer();
		this.tick = new TimerTask() {
			public void run() {
				setTime();
				time++;
			}
		};
	}
	public void resumeClock() {
		if (clockActive)
			;
		else {
			setNewClock();
			this.clock.scheduleAtFixedRate(tick, 0 ,1000);
		}
	}
	public void resetTimer() {
		timer.setText("000");
		this.time = 0;
	}
	public void resetFlagDisplay(int mines) {
		this.numOfMines = mines;
		updateMinesDisplay(this.numOfMines);
	}
	public void addToFlagsCounter() {
		this.numOfMines++;
		updateMinesDisplay(this.numOfMines);
	}
	public void displayVictory() {
		this.reset.setIcon(cool);
		this.pauseClock();
		this.minesLeft.setText("000");
	}
	public void displayDefeat() {
		this.reset.setIcon(dead);
		this.pauseClock();
	}
	public void playSound(int i) {
		this.audioPlayer.playEffect(i);
	}
}
