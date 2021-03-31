//Hochschule Darmstadt, Fb Informatik
//Software Engineering WS 2019/20
//(c) Alexander del Pino

package softwareengineering;


public class Move {
	
	public int row;
	public int column;
	public Direction direction;
	
	
	
	public Move(int row, int column, Direction direction) {
		this.row = row;
		this.column = column;
		this.direction = direction;
	}
	
	
	
	public String toString() {
		return row + "." + column + "." + direction;
	}
	
	
}
