//Hochschule Darmstadt, Fb Informatik
//Software Engineering WS 2019/20
//(c) Alexander del Pino

package softwareengineering;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;

import javax.swing.JComponent;

public class GameViewer extends JComponent {

	private static final long serialVersionUID = -6038152322943882143L;
	
	Game game;
	int numRows;
	int numColumns;
	
	int cellSize = 50;
	int inset = 2;
	int nameWidth = 120;
	int savedStoneWidth = 10;
	
	ArrayList<Color> colors;
	
	public GameViewer(Game game) {
		this.game = game;		
		numRows = game.getNumRows();
		numColumns = game.getNumColumns();
		
		// define the colors
	
		colors = new ArrayList<>();
		colors.add(new Color(255, 51, 51));
		colors.add(new Color(0, 102, 255));
		colors.add(new Color(0, 204, 0));
		colors.add(new Color(255, 204, 0));
		
	}
	
	
	
	public void paintComponent(Graphics g) {

		requestFocus();	// needed to detect key strokes
	
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		// render the board
		
		for (int row=0; row<numRows; row++) {
			for (int column=0; column<numColumns; column++) {
				int value = game.getField(row, column);
				
				if (value > 0) {
					g2.setColor(colors.get(value-1));
					int x = column * cellSize + inset;
					int y = row * cellSize + inset;
					int width = cellSize - 2 * inset;
					int height = cellSize - 2 * inset;
					g2.fillArc(x, y, width, height, 0, 360);					
				} else if (value == 0) {
					g2.setColor(Color.LIGHT_GRAY);
					int x = column * cellSize + cellSize/2-2;
					int y = row * cellSize + cellSize/2-2;
					g2.fillArc(x, y, 5, 5, 0, 360);					
					
				} else {
				
					int x = column * cellSize + inset;
					int y = row * cellSize + inset;
					int width = cellSize - 2 * inset;
					int height = cellSize - 2 * inset;
					g2.setColor(colors.get(-value-1));
					Stroke originalStroke = g2.getStroke();
					g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
					g2.drawRect(x, y, width, height);
					g2.setStroke(originalStroke);
				}
				
			}
		}
		
		
		// render the players
		
		int numPlayers = game.getNumPlayers();
		int leftBorder = (numColumns + 2) * cellSize;
		for (int i=0; i<numPlayers; i++) {
			
			// player label
			
			g2.setColor(Color.LIGHT_GRAY);
			if (game.getPlayerIsDisqualified(i+1)) {
				g2.setColor(Color.RED);				
			}
			g2.setFont(new Font("Sans", Font.PLAIN, 24));
			int y = (i + 1) * cellSize;
			String label = game.getPlayerName(i+1);
			Integer rank = game.getRank(i+1);
			if (rank != null) {
				label = rank + ". " + label;
			}
			g2.drawString(label, leftBorder, y - 17);
			
			// saved stones bar
			
			g2.setColor(colors.get(i));
			int x = leftBorder + nameWidth;
			int width = game.getNumSavedStones(i+1) * savedStoneWidth;
			int height = cellSize - 2 * inset;
			g2.fillRect(x, y - cellSize, width, height);							
		}
		
		
		
	}
	
}
