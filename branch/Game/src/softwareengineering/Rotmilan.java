package softwareengineering;

import java.util.Random;

public class Rotmilan implements Player {
	int color;
	String name;
	Game game;
	Random random;
	
	
	public Rotmilan(int color, String name, Game game) {
		this.color = color;
		this.name = name;
		this.game = game;
		random = new Random();
	}
	
	
	
	public String toString() {
		return name;
	}
	

	
	public int getColor() {
		return color;
	}
	
	
	
	public Move nextMove() {
		
		int numRows = game.getNumRows();
		int numColumns = game.getNumColumns();
		
		for (int i=0; i<10000; i++) {
			
			int row = random.nextInt(numRows);
			int column = random.nextInt(numColumns);
			
			int value = game.getField(row, column);			
			if (value != color) continue;
			
			Direction direction = Direction.values()[random.nextInt(4)];
			Move move = new Move(row, column, direction);
			
			if (direction == Direction.UP && (game.getField((row+numRows-1)%numRows, column) == 0 || game.getField((row+numRows-1)%numRows, column) == -color)) return move;
			if (direction == Direction.DOWN && (game.getField((row+1)%numRows, column) == 0 || game.getField((row+1)%numRows, column) == -color)) return move;
			if (direction == Direction.LEFT && (game.getField(row, (column+numColumns-1)%numColumns) == 0 || game.getField(row, (column+numColumns-1)%numColumns) == -color)) return move;
			if (direction == Direction.RIGHT && (game.getField(row, (column+1)%numColumns) == 0 || game.getField(row, (column+1)%numColumns) == -color)) return move;
		}
		
		return null;
	}
	
		


}
