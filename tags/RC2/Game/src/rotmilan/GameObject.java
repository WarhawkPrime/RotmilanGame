package rotmilan;

public class GameObject {
	
	private int row;
	private int column;
	private int color;
	private double tempCalculatedHDistanceToTarget;	//abstand über heuristik, wird benutzt für die Auswahl an Steinen
	private Objecttype type;
	private Path gameObjectPath;
	
	
	public GameObject(int row, int column, int color, Objecttype type) {
		this.row = row;
		this.column = column;
		this.type = type;
		this.color = color;
	}
	
	//Konstruktor für Tests
	public GameObject(int row, int column, int color) {
		this.row = row;
		this.column = column;
		this.color = color;
	}
	
	//Anfang Methoden
	//Getter und Setter
	
	public int getColor() {return color;}

	public int getRow() {return row;}
	public void setRow(int row) {this.row = row;}

	public int getColumn() {return column;}
	public void setColumn(int column) {this.column = column;}

	public Objecttype getType() {return type;}
	public void setGameObjectPath(Path path) {this.gameObjectPath = path;}
	
	public Path getGameObjectPath() {return this.gameObjectPath;}
	
	public void setTempCalculatedHDistanceToTarget(double tempAbstand) {this.tempCalculatedHDistanceToTarget = tempAbstand;}
	public double getTempCalculatedHDistanceToTarget() {return tempCalculatedHDistanceToTarget;}
	
	//Ende methoden
}
