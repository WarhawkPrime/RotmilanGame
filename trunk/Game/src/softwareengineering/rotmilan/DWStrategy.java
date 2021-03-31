package softwareengineering.rotmilan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import softwareengineering.*;


public class DWStrategy implements Strategy {

	Game game = null;

	int[][] gameBoard = null;
	int boardRows;
	int boardColumns;
	int ownColor;
	//Node goalNode = null;
	ArrayList<Node> ownStones = null;
	ArrayList<Node> ownGoals = null;
	ArrayList<GameObject> enemyStones = null;
	AStarAlgorithm astar = new AStarAlgorithm();
	ArrayList<Node> enemyGoals = new ArrayList<>();
	
	int[] stratI = null;
	int maxBoards = 10;
	ArrayList<int[][]> boards = new ArrayList<>();
	

	public DWStrategy(int[][] board, int gameRows, int gameColumns, ArrayList<Node> ownGoals , ArrayList<Node> ownStonesWithoutGoal, ArrayList<GameObject> enemyStones, int ownColor, Game game, ArrayList<int[][]> boards ) {

		this.gameBoard = board;
		this.boardRows = gameRows;
		this.boardColumns = gameColumns;
		this.ownColor = ownColor;
		//this.goalNode = goalNode;
		this.ownStones = ownStonesWithoutGoal;
		this.ownGoals = ownGoals;
		this.enemyStones = enemyStones;

		this.game = game;
	}

	public DWStrategy() {

	}

	
	public int analyze() {
		
		boolean goalsAreProtected = false;
        boolean goalsUnderAttack = false;
        boolean readyToAttack = false;
        boolean zeroGoals=false;
        
        
        if(ownGoals.size() == 0) {
            zeroGoals = true;
            return 5;
        }
        
        //einmalig alle gegner Tore erstellen
        if(enemyGoals.size() == 0) {
            for(int row = 0; row < boardRows; row++) {
                for(int col = 0; col < boardColumns; col++) {
                    if( (gameBoard[row][col] >= 0) && (gameBoard[row][col] != -ownColor) ) {
                        Node goal = astar.createNewNode(row, col, gameBoard, ownColor, null, boardColumns);
                        enemyGoals.add(goal);
                    }
                }
            }
        }
        
        //Analisieren wie unsere Situation ist
        for(Node g: ownGoals) {
            if(isProtected(g) ==  false) {
                goalsAreProtected=false;
            }
            else {
                goalsAreProtected=true;
            }
        }

        //Analisieren ob unsere Tore angegrifen werden
        for(Node g: ownGoals) {
            if(isUnderAttack(g) == true) {
                goalsUnderAttack=true;
            }
            else {
                goalsUnderAttack=false;
            }
        }
        
        //wann können wir angreifen? wenn die gegnerTore nicht so weit weg sind und alle unsere Tore beschützt sind
        if(goalsAreProtected == true && ownStones.size() > 5) {
            //wenn die Entfernung zu unserem Durchschnittlich besten Tor und dem dazu nächstem gegner klein genug ist
            int temp = 0;
            int divisor = 0;
            int average = 0;
            for(Node g: enemyGoals) {
                for(Node n: ownStones) {
                    temp += astar.calculateHeuristic(n, g, boardRows, boardColumns);
                    divisor++;
                }
            }
            if(divisor != 0) {
                average = temp / divisor;
            }
            if( average < (boardRows/3) || average < (boardColumns) ) {
                readyToAttack = true;
            }
        }

        //wenn die anzahl an eigene Steinen größer gleich als 1.75 bei (anzahl steine / (4*Anzahl tore) )
        //dann kann die protect Strategie genutzt werden. Ansonsten DW.getMOve();
        if(zeroGoals == true) {
            return 5;
        }
        if(goalsAreProtected == false && ( (ownStones.size() / (4 *ownGoals.size())) >= 1.75 ) ) {
            return 1;
        }
        if(goalsAreProtected == true && readyToAttack == true) {
            return 2;
        }
        else {
            return 0;
        }
	}

		/*
		if(boards.size() >= 2) {
			
			System.out.println("Board grï¿½ï¿½er als 2");
			
			stratI = new int[maxBoards];
			
			for(int c = 0; c < boards.size() -1; c++) {
				

				for(int row = 0; row < boardRows; row++) {
					for(int col = 0; col < boardColumns; col++) {

						//ein Gegner hat sich von seiner alten Position bewegt
						if((boards.get(c)[row][col] != ownColor && boards.get(c)[row][col] > 0) &&
								boards.get(c)[row][col] != boards.get(c +1)[row][col] ) {

							//welcher Gegner?
							playerArray[playerArray.length] = boards.get(c)[row][col];
							int goalNumber = -boards.get(c)[row][col];
							
							//h vom letzten Stand
							Node enemyNode = new Node(row, col, true, -goalNumber);
							
							int hTOG = astar.calculateHeuristic(enemyNode, goalNode, boardRows, boardColumns);
							int hTEG = astar.calculateHeuristic(enemyNode, getEnemyGoal(enemyNode, goalNumber), boardRows, boardColumns);
							
							//wohin?
							int rowMod = 0;
							int colMod = 0;;

							
							//========== UP ==========
							rowMod = row -1;
							if(rowMod == -1) {
								rowMod = boardRows - 1;
							}
							
							if(boards.get(c)[rowMod][col] == 0 ) {
								if(boards.get(c +1)[rowMod][col] == playerArray[playerArray.length]) {
									Node enemyTNode = new Node(rowMod, col, true);
									
									System.out.println("UP");
									
									int hToOwnGoal = astar.calculateHeuristic(enemyTNode, goalNode, boardRows, boardColumns);
									int hToEnemyGoal = astar.calculateHeuristic(enemyTNode, getEnemyGoal(enemyTNode, goalNumber), boardRows, boardColumns); //tor des gegners
									
									if(hToOwnGoal < hTOG && hToEnemyGoal > hTEG) {
										stratI[stratI.length] = 1;
									}
									else if(hToOwnGoal > hTOG && hToEnemyGoal < hTEG) {
										stratI[stratI.length] = 0;
									}
								}
							}
							
							//========== RIGHT ==========
							colMod = col +1;
							if(colMod > boardColumns) {
								colMod = 0;
							}
							System.out.println(" Test for RIGHT");

							
							if(boards.get(c)[row][colMod] == 0 ) {
								System.out.println("RIGHT war vorher 0");

								if(boards.get(c +1)[row][colMod] == playerArray[playerArray.length]) {
									Node enemyTNode = new Node(row, colMod, true);
									
									System.out.println("RIGHT");
									
									int hToOwnGoal = astar.calculateHeuristic(enemyTNode, goalNode, boardRows, boardColumns);
									int hToEnemyGoal = astar.calculateHeuristic(enemyTNode, getEnemyGoal(enemyTNode, goalNumber), boardRows, boardColumns); //tor des gegners
									
									if(hToOwnGoal < hTOG && hToEnemyGoal > hTEG) {
										stratI[stratI.length] = 1;
									}
									else if(hToOwnGoal > hTOG && hToEnemyGoal < hTEG) {
										stratI[stratI.length] = 0;
									}
								}
							}
							
							
							//========== DOWN ==========
							rowMod = row +1;
							if(rowMod > boardRows) {
								rowMod = 0;
							}
							
							if(boards.get(c)[rowMod][col] == 0 ) {
								if(boards.get(c +1)[rowMod][col] == playerArray[playerArray.length]) {
									Node enemyTNode = new Node(rowMod, col, true);
									
									System.out.println("DOWN");
									
									int hToOwnGoal = astar.calculateHeuristic(enemyTNode, goalNode, boardRows, boardColumns);
									int hToEnemyGoal = astar.calculateHeuristic(enemyTNode, getEnemyGoal(enemyTNode, goalNumber), boardRows, boardColumns); //tor des gegners
									
									if(hToOwnGoal < hTOG && hToEnemyGoal > hTEG) {
										stratI[stratI.length] = 1;
									}
									else if(hToOwnGoal > hTOG && hToEnemyGoal < hTEG) {
										stratI[stratI.length] = 0;
									}
								}
							}
							
							
							//========== LEFT ==========
							colMod = col -1;
							if(colMod == -1) {
								colMod = boardColumns -1;
							}
							
							if(boards.get(c)[row][colMod] == 0 ) {
								if(boards.get(c +1)[row][colMod] == playerArray[playerArray.length]) {
									Node enemyTNode = new Node(row, colMod, true);
									
									System.out.println("LEFT");
									
									int hToOwnGoal = astar.calculateHeuristic(enemyTNode, goalNode, boardRows, boardColumns);
									int hToEnemyGoal = astar.calculateHeuristic(enemyTNode, getEnemyGoal(enemyTNode, goalNumber), boardRows, boardColumns); //tor des gegners
									
									if(hToOwnGoal < hTOG && hToEnemyGoal > hTEG) {
										stratI[stratI.length] = 1;
									}
									else if(hToOwnGoal > hTOG && hToEnemyGoal < hTEG) {
										stratI[stratI.length] = 0;
									}
								}
							}
						}
						else {	//kein Unterschied festgestellt:
							
						}
					}
				}
				//Durch Board n durch, nï¿½chstes Board
			}
			//Alle Boards durch, hier jetzt den Vergleich weiter
			int oGCount = 0;
			int eGCount = 0;
			
			for(int i = 0; i < stratI.length; i++) {
				if(stratI[i] == 0) {
					eGCount ++;
				}
				else if(stratI[i] == 1) {
					oGCount ++;
				}
			}
			
			if(oGCount > eGCount) {
				stratI = null;
				//System.out.println(1);
				return 1;
			}
			else if(oGCount < eGCount) {
				stratI = null;
				//System.out.println(0);
				return 0;
			}
		}
		else {	//nur ein Board, kein Vergleich mï¿½glich
			return 3;
		}
		
		return 5;
	}
	*/
	
	
	
	
	
	
	public Move getMove() {
		AStarAlgorithm astar = new AStarAlgorithm();

		//Node goalNode = new Node(goalNode.getRowNumber(), goalNode.getColumnNumber(), false, null);	//goal
		Node nodeToMove = null;	//welcher Stein soll whin bewegt werden
		Node endNode = null;	//wohin soll der Stein bewegt werden
		Path shortestPath = null;	//pfad zu berechnen
		Direction direction = null;	//direction

		//ziel bestimmen
		endNode = chooseBetterGoal(ownGoals, ownStones );
		//Stein bestimmen
		nodeToMove = chooseStoneToMove(endNode, ownStones, gameBoard, boardRows, boardColumns, ownColor, astar);

		//2: Pfad von Start zu Ziel bestimmen
		//shortestPath = astar.findShortestPath(nodeToMove, endNode, gameBoard, boardRows, boardColumns, ownColor);
		//3. Path der Node zuweisen
		//nodeToMove.setPath(shortestPath);
		//4. Queue sortieren
		nodeToMove.setPathToStart(astar.findShortestPath(nodeToMove, endNode, gameBoard, ownColor));
		
		//! mï¿½glicher fehler
		//nodeToMove.getPathToStart().sortNodesToQueue(nodeToMove.getRowPosition(), nodeToMove.getColumnPosition() );
		//5. Direction bekommen
		direction = nodeToMove.getNextDirection();

		Move move = null;
		
		if(direction == direction.UP) {
			int row = nodeToMove.getRowPosition() - 1;
			int column = nodeToMove.getColumnPosition();
			
			if(row == -1) {
				row = boardRows - 1;
			}
			
			if(gameBoard[row][column] != 0 && gameBoard[row][column] != -ownColor) {
				direction = null;
			}
		}
		else if(direction == direction.DOWN) {
			int row = nodeToMove.getRowPosition() +1;
			int column = nodeToMove.getColumnPosition();
			
			if(row > boardRows -1) {
				row = 0;
			}
			
			if(gameBoard[row][column] != 0 && gameBoard[row][column] != -ownColor) {
				direction = null;
			}
			
		}
		else if(direction == direction.LEFT) {
			int row = nodeToMove.getRowPosition();
			int column = nodeToMove.getColumnPosition() -1;
			
			if(column == -1) {
				column = boardColumns -1;
			}
			
			if(gameBoard[row][column] != 0 && gameBoard[row][column] != -ownColor) {
				direction = null;
			}
			
		}
		else if(direction == direction.RIGHT) {
			int row = nodeToMove.getRowPosition();
			int column = nodeToMove.getColumnPosition() +1;
			
			if(column > boardColumns -1) {
				column = 0;
			}
			
			if(gameBoard[row][column] != 0 && gameBoard[row][column] != -ownColor) {
				direction = null;
			}
			
		}
		
		//Abfrage ob direction 0 ist
				if(direction == null) {
					return move;
				}
			
			
		move = new Move(nodeToMove.getRowPosition(), nodeToMove.getColumnPosition(), direction);
		return move;

	}

	
	
	

	//gibt dazugehï¿½riges Tor zurï¿½ck
	public Node getEnemyGoal(Node enemyNode, int goalNumber) {
		for(Node goal : enemyGoals) {
			if(goal.getColor() == goalNumber) {
				return goal;
			}
		}
		return null;
	}


	public Node chooseGoal(ArrayList<Node> ownGoals, ArrayList<Node> ownStones) {
		
		if(ownGoals.size() == 1) {
			return ownGoals.get(0);
		}
		else {
			//wie ein Tor auswählen wenn der Startpunkt noch nicht bekannt ist und vom ziel abhängt?
			int average  = Integer.MAX_VALUE;
			int bestAvGoal = 0;
			
			for(int i = 0; i < ownGoals.size(); i++) {
				int t = 0;
				for(int j = 0; j < ownStones.size(); j++) {
					t += astar.calculateHeuristic(ownStones.get(j), ownGoals.get(i), boardRows, boardColumns);
				}
				if(t < average) {
					average = t;
					bestAvGoal = i;
				}
			}
			return ownGoals.get(bestAvGoal);
		}
	}

public Node chooseBetterGoal(ArrayList<Node> ownGoals, ArrayList<Node> ownStones) {
        
        HashMap<Integer, Integer> goalSet = new HashMap<Integer, Integer>();
        ArrayList<Node> candidates = new ArrayList<Node>();
        
        if(ownGoals.size() == 0) {
        	return noOwnGoals(ownStones);
        }
        
        if(ownGoals.size() == 1) {
            return ownGoals.get(0);
        }
        else {
            for(Node goal : ownGoals) {
                
                int blockedCounter = 0;
                
                for(Node stone : ownStones) {
                    int h = astar.calculateHeuristic(stone, goal, boardRows, boardColumns);
                    Path p = astar.findShortestPath(stone, goal, gameBoard, ownColor);
                    ArrayList<Node> list = p.getPathInNodes();
                    stone.setPathToStart(null);
                    stone.setParent(null);
                    
                    for(Node n : list) {
                        if(n.getNodeID() == goal.getNodeID()) {
                            blockedCounter++;
                        }
                    }
                }
                
                goalSet.put(goal.getNodeID(), blockedCounter);
            }
            
            for(Map.Entry<Integer, Integer> entry : goalSet.entrySet() ) {
                int key = entry.getKey();
                int counter = entry.getValue();
                
                if(counter > 0 ) {
                    candidates.add(getNodeByID(ownGoals, key));
                }
            }
            
            if(candidates.size() == 0) {
                return chooseGoal(ownGoals, ownStones);
            }
            else {
                return chooseGoal(candidates, ownStones);
            }
        }
    }


	public Node getNodeByID(ArrayList<Node> list, int id) {
		for(Node n : list) {
			if(n.getNodeID()==id) {
				return n;
			}
		}
		return null;
	}


	//aktuell: sucht den Stein der am nï¿½chsten am Tor ist und sich mindestens ein Feld bewegen kann
	public Node chooseStoneToMove(Node endNode, ArrayList<Node> listWithoutGoals, int[][] board, int gameRowsNumber, int gameColumnsNumber, int ownColor, AStarAlgorithm astar) {


		//Prioritï¿½ten setzten:
		//1: Stein hat freie Bahn und den kï¿½rzesten Weg zum Ziel
		//2: Stein hat zwar keine freie Bahn aber kann den nï¿½chsten Sinnvollen Schritt zum Ziel machen
		//3: Stein hat keine freie Bahn und kann auch keinen sinnvollen Schritt machen und ist weiter vom Ziel Weg
		//4: Stein kann sich nicht bewegen, dann ist es egal

		Comparator<Node> comparator = new NodePathComparator();
		Comparator<Node> turnedComparator = new NodeTurnedPathSizeComparator();
		
		PriorityQueue<Node> stoneQueueToGoal = new PriorityQueue<Node>(comparator);
		PriorityQueue<Node> stoneQueueBlockedGoal = new PriorityQueue<Node>(turnedComparator);

		//berechnen von h fï¿½r jeden eigenen Stein zum Ziel und sortierung in die Queue
		for(Node stone : ownStones) {
			stone.setPathToStart(astar.findShortestPath(stone, endNode, board, ownColor));

			if(stone.getPathToStart().getPathInNodes().get(0).getRowPosition() == endNode.getRowPosition() && 
					stone.getPathToStart().getPathInNodes().get(0).getColumnPosition() == endNode.getColumnPosition() ) 
			{

				stoneQueueToGoal.add(stone);

			}
			else {
				stoneQueueBlockedGoal.add(stone);
			}
		}

		Node tempNode = null;
		if(!stoneQueueToGoal.isEmpty()) {
			tempNode = stoneQueueToGoal.poll();
		}
		else {
			tempNode = stoneQueueBlockedGoal.poll();
		}

		return tempNode;
	}

	
	
	public boolean isProtected(Node goal) {
		ArrayList<Node> neighbours = astar.findAllNeighbours(goal, gameBoard, boardRows, boardColumns, ownColor, 1);
		for(Node neighbour : neighbours) {
			if(neighbour.getColor() != ownColor && neighbour.getColor() >= 0 ) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isUnderAttack(Node goal) {
		ArrayList<Node> neighbours = astar.findAllNeighbours(goal, gameBoard, boardRows, boardColumns, ownColor, 1);
		for(Node neighbour : neighbours) {
			if(neighbour.getColor() != ownColor && neighbour.getColor() > 0 ) {
				return true;
			}
		}
		return false;
	}

	
	
	//was soll gemacht werden wenn es keine eigenen Knoten gibt?
	//Ziel: bis zur unendlichkeit überleben, egal was passiert.
	//kleinste sicherste Einheit: Wenn die hergestellt werden kann, dann geht es unendlich weiter
	
	//1	1 1
	//1 1 1
	//1 0 1
	//0 1 0
	
	//ein 9x9 Feld mit eigenen Steinen erstellen
		//wie? nach einem Stein mit 8 Nachbarn suchen: Diagonale!
		//wenn keiner gefunden wurde, den Stein mit den meisten suchen und den Rest der Steine in die fehlenden Positionen bringen
	//wenn ein 9x9 Feld entsteht, bewege einen Stein der Platz hat nach draußen
	//nun bewege nurnoch den Mittleren Stein dahin wo er Platz hat
	
	public Node noOwnGoals(ArrayList<Node> ownStones) {
		
		for(Node n: ownStones) {
			/*
			if(hasNineNeighbours(n) == true) {
				
			}
			else {
				countNeighbours(n);
			}
			*/
		}
		
		return null;
	}
	
	//eigenes Tor schützen
	//4 Steine herum platzieren
	//danach die restlichen Steine ganz normal zum Tor laufen lassen
	//wenn bemerkt wird, dass ein eigener Stein vor den Schutzsteinen steht, dann bewege den Schutzstein in das Tor
	// dann greift wieder die prüfung ob die eigenen Tore gesichert sind, der neue Stein wird zum Schutzstein
	public Node moveToProtect(ArrayList<Node> ownGoals, ArrayList<Node> ownStones)  {
		return null;
	}
	
	
	
	
	
	
	
	/*
	public boolean testForOptimalNextNode(Node currentNode) {

		Node nextPlannedNode = currentNode.getPathToStart().getNextNode(currentNode.getNodeID());
		if(nextPlannedNode == null) {
			return false;
		}
		nextPlannedNode.setPathToStart(astar.findShortestPath(nextPlannedNode, goalNode, gameBoard, ownColor));

		if(currentNode.getPathToStart().getPathInNodes().size() > nextPlannedNode.getPathToStart().getPathInNodes().size() ) {
			return true;
		}
		return false;
	}
	*/

}
