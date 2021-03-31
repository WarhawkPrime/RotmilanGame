package rotmilan;

import java.util.ArrayList;

public class SteinAuswahl {
	
	private int goal_row, goal_column, gameRows, gameColumns;
	

	public SteinAuswahl(int goal_row, int goal_column, int gameRows, int gameColumns) {
		super();
		this.goal_row = goal_row;
		this.goal_column = goal_column;
		this.gameRows = gameRows;
		this.gameColumns = gameColumns;
	}

	public double abstandBerechnen(int stone_x, int stone_y) {

		int heuristicValue = 0;
		int absoluteXValue, absoluteYValue;

		absoluteXValue = Math.abs(stone_x - goal_row);
		absoluteYValue = Math.abs(stone_y - goal_column);

		//f�r x achse
		if(absoluteXValue > (Math.floor(gameRows / 2 )) ) {
			if(stone_x < goal_row) {
				absoluteXValue = (stone_x - 0) + (gameRows - (goal_row))  + 1;
			}
			else if(stone_x > goal_row) {
				absoluteXValue = (goal_row - 0) + (gameRows - (stone_x))  + 1;
			}
		}
		//f�r y achse
		if(absoluteYValue > (Math.floor(gameColumns / 2 )) ) {
			if(stone_y < goal_column) {
				absoluteYValue = (stone_y - 0) + (gameColumns - (goal_column))  + 1;
			}
			else if(stone_y > goal_column) {
				absoluteYValue = (goal_column - 0) + (gameColumns - (stone_y))  + 1;
			}
		}

		heuristicValue = absoluteXValue + absoluteYValue;
		return heuristicValue;
	}

	
	
	public GameObject bestenSteinAuswaehlen(ArrayList<GameObject> ourStones) {
		double abstandzuziel = -1;
		double tempAbstand;
		int currentMinAbstandStone = 0;
		int stone_x, stone_y;

		//wenn sich kein Stein bewegen kann sind alle Steine aus der Liste gelöscht, wir müssen skippen
		if(ourStones.size() == 0) {
			return null;
		}
		
		for (int i = 0; i<ourStones.size(); i++) {
			GameObject stone = ourStones.get(i);
			stone_x = stone.getRow();
			stone_y = stone.getColumn();
			if(stone.getType() == Objecttype.GOAL) {
				continue;
			}
			tempAbstand = abstandBerechnen(stone_x, stone_y);

			if(tempAbstand < abstandzuziel || abstandzuziel == -1) {
				abstandzuziel = tempAbstand;
				currentMinAbstandStone = i;
			}
		}
		return ourStones.get(currentMinAbstandStone);
	}
	

}
