//Hochschule Darmstadt, Fb Informatik
//Software Engineering WS 2019/20
//(c) Alexander del Pino

package softwareengineering;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JFrame;

import softwareengineering.rotmilan.*;

public class Game {

	// game
	
	private int numRows;
	private int numColumns;
	private int [][] board;
	
	private ArrayList<Player> players;
	private boolean [] playerIsDisqualified;
	private long [] elapsedTime;
	private int [] savedStones;
	private Integer [] rank;
	private int [] numPasses;
	private int [] numMovesDone;
	
	// graphics
	
	JFrame frame;
	GameViewer gameViewer;
	
	
	public Game(String filename) {
		
		
		players = new ArrayList<>();
		
		if (new File(filename).exists() == false) {
			throw new RuntimeException("Game file " + filename + " not found");
		}
		
		ArrayList<String> lines = new ArrayList<>();
		try {
			BufferedReader f = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = f.readLine();
				if (line == null) break;
				lines.add(line);
			}
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		numRows = lines.size();
		numColumns = lines.get(0).split("\t").length;
		board = new int[numRows][numColumns];
		
		for (int row=0; row<numRows; row++) {
			String line = lines.get(row);
			String [] fields = line.split("\t");
			assert fields.length == numColumns;
			for (int column=0; column<numColumns; column++) {
				board[row][column] = Integer.valueOf(fields[column]);
			}
		}
			
		
		
		// graphics
		
        frame = new JFrame("Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameViewer = new GameViewer(this);
        frame.getContentPane().add(gameViewer);
        frame.setPreferredSize(new Dimension(1024, 768));
		frame.pack();
        frame.setVisible(true);
        //frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);	// maximize the JFrame

		
		
	}
	
	
	
	
	
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	
	/*
	public void start(long delay) {
		
		elapsedTime = new long[players.size()];
		
		savedStones = new int[players.size()];
		
		rank = new Integer[players.size()];
		
		playerIsDisqualified = new boolean[players.size()];
		
		numPasses = new int[players.size()];
		
		int moveNumber = 0;
		int playerIndex = -1;
		int nextRank = 1;
		while (true) {
			
			// show the current state of the game
			
			gameViewer.repaint();
			if (delay > 0) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e1) {
				}
			}
			
			// at least one not disqualified player left ?
			
			boolean atLeastOneNotDisqualifiedPlayerLeft = false;
			for (int i=0; i<players.size(); i++) {
				if (playerIsDisqualified[i] == false) {
					atLeastOneNotDisqualifiedPlayerLeft = true;
					break;
				}
			}
			if (atLeastOneNotDisqualifiedPlayerLeft == false) {
				System.out.println("No more qualified players left, game over.");
				break;
			}
			
			
			// at least one stone on the board
			
			boolean atLeastOneStoneLeft = false;
			for (int row=0; row<numRows; row++) {
				for (int column=0; column<numColumns; column++) {
					if (board[row][column] > 0) {
						atLeastOneStoneLeft = true;
						break;
					}
				}
			}
			if (atLeastOneStoneLeft == false) {
				System.out.println("No more stones left on the board, game over.");
				showBoard(null);
				break;				
			}
			
			
			// next move
			
			moveNumber++;
			
			
			// forward to the next player
			
			playerIndex = (playerIndex+1) % players.size();
			if (playerIsDisqualified[playerIndex]) continue;
			if (rank[playerIndex] != null) continue;
			Player player = players.get(playerIndex);
			int color = player.getColor();
			
			
			
			// get a move
			
			long startTime = System.currentTimeMillis();
			Move move = null;
			try {
				move = player.nextMove();
			} catch (Exception e) {
				System.out.println("Game over for player " + player.toString() + " because of an exception, stack trace follows. Please debug");
				e.printStackTrace();
				disqualifyPlayer(playerIndex);
			}
			long endTime = System.currentTimeMillis(); 
			elapsedTime[playerIndex] += endTime - startTime;

			if (move != null) {
				numPasses[playerIndex] = 0;
				System.out.println("\nmove " + moveNumber + " " + player.toString() + " plays " + move + ",  " + elapsedTime[playerIndex] + " msec used, score " + savedStones[playerIndex]);
				showBoard(move);
			} else {
				System.out.println("\nmove " + moveNumber + " " + player.toString() + " passes, " + elapsedTime[playerIndex] + " msec used, score " + savedStones[playerIndex]);
				showBoard(null);
				
				numPasses[playerIndex]++;
				if (numPasses[playerIndex] == 3) {
					System.out.println(player.toString() + " passed three times. Game over for " + player.toString());
					disqualifyPlayer(playerIndex);
				}
				
				continue;
			}
			
			
			// move valid ?
						
			if (move.row < 0 || move.row > numRows) {
				System.out.println("Game over for player " + player.toString() + " because of an invalid row value, row=" + move.row);
				disqualifyPlayer(playerIndex);
				continue;				
			}
			
			if (move.column < 0 || move.column > numColumns) {
				System.out.println("Game over for player " + player.toString() + " because of an invalid column value, column=" + move.column);
				disqualifyPlayer(playerIndex);
				continue;				
			}
			
			
			if (move.direction == Direction.UP) {
				if (board[(move.row+numRows-1)%numRows][move.column] == 0) {
					board[(move.row+numRows-1)%numRows][move.column] = board[move.row][move.column];
					board[move.row][move.column] = 0;
					continue;					
				}
				
				if (board[(move.row+numRows-1)%numRows][move.column] == -color) {
					board[move.row][move.column] = 0;
					savedStones[playerIndex]++;
					System.out.println("player " + player.toString() + " saved a stone, score is " + savedStones[playerIndex]);
					if (allStonesSaved(color)) rank[playerIndex] = nextRank++;
					continue;
				}

				System.out.println("Game over for player " + player.toString() + " because of an up move to a non-empty field");
				disqualifyPlayer(playerIndex);
				continue;					
			}
			
			
			if (move.direction == Direction.DOWN) {
				if (board[(move.row+1)%numRows][move.column] == 0) {
					board[(move.row+1)%numRows][move.column] = board[move.row][move.column];
					board[move.row][move.column] = 0;
					continue;										
				}
				
				if (board[(move.row+1)%numRows][move.column] == -color) {
					board[move.row][move.column] = 0;
					savedStones[playerIndex]++;
					System.out.println("player " + player.toString() + " saved a stone, score is " + savedStones[playerIndex]);
					if (allStonesSaved(color)) rank[playerIndex] = nextRank++;
					continue;
				}

				System.out.println("Game over for player " + player.toString() + " because of a down move to a non-empty field");
				disqualifyPlayer(playerIndex);
				continue;										
			}
			
			
			if (move.direction == Direction.LEFT) {
				if (board[move.row][(move.column+numColumns-1)%numColumns] == 0) {
					board[move.row][(move.column+numColumns-1)%numColumns] = board[move.row][move.column];
					board[move.row][move.column] = 0;
					continue;						
					
				}
				
				if (board[move.row][(move.column+numColumns-1)%numColumns] == -color) {
					board[move.row][move.column] = 0;
					savedStones[playerIndex]++;
					System.out.println("player " + player.toString() + " saved a stone, score is " + savedStones[playerIndex]);
					if (allStonesSaved(color)) rank[playerIndex] = nextRank++;
					continue;
				}
				
				System.out.println("Game over for player " + player.toString() + " because of a left move to a non-empty field");
				disqualifyPlayer(playerIndex);
				continue;						
			}
			
			
			if (move.direction == Direction.RIGHT) {
				if (board[move.row][(move.column+1)%numColumns] == 0) {
					board[move.row][(move.column+1)%numColumns] = board[move.row][move.column];
					board[move.row][move.column] = 0;
					continue;
				}
				
				if (board[move.row][(move.column+1)%numColumns] == -color) {
					board[move.row][move.column] = 0;
					savedStones[playerIndex]++;
					System.out.println("player " + player.toString() + " saved a stone, score is " + savedStones[playerIndex]);
					if (allStonesSaved(color)) rank[playerIndex] = nextRank++;
					continue;
				}

				System.out.println("Game over for player " + player.toString() + " because of a right move to a non-empty field");
				disqualifyPlayer(playerIndex);
				continue;					
			}
			
		} // while true
		
	}
	*/
	
	public void start(long delay, int maxMoves) {
		
		elapsedTime = new long[players.size()];
		
		savedStones = new int[players.size()];
		
		rank = new Integer[players.size()];
		
		playerIsDisqualified = new boolean[players.size()];
		
		numPasses = new int[players.size()];		
		numMovesDone = new int[players.size()];
		
		int moveNumber = 0;
		int playerIndex = -1;
		int nextRank = 1;
		while (true) {
			
			// show the current state of the game
			
			gameViewer.repaint();
			if (delay > 0) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e1) {
				}
			}
			
			// at least one not disqualified player left ?
			
			boolean atLeastOneNotDisqualifiedPlayerLeft = false;
			for (int i=0; i<players.size(); i++) {
				if (playerIsDisqualified[i] == false) {
					atLeastOneNotDisqualifiedPlayerLeft = true;
					break;
				}
			}
			if (atLeastOneNotDisqualifiedPlayerLeft == false) {
				System.out.println("No more qualified players left, game over.");
				break;
			}
			
			
			// at least one stone on the board
			
			boolean atLeastOneStoneLeft = false;
			for (int row=0; row<numRows; row++) {
				for (int column=0; column<numColumns; column++) {
					if (board[row][column] > 0) {
						atLeastOneStoneLeft = true;
						break;
					}
				}
			}
			if (atLeastOneStoneLeft == false) {
				System.out.println("No more stones left on the board, game over.");
				showBoard(null);
				break;				
			}
			
			
			// next move
			
			moveNumber++;
			
			
			// forward to the next player
			
			playerIndex = (playerIndex+1) % players.size();
			if (playerIsDisqualified[playerIndex]) continue;
			if (rank[playerIndex] != null) continue;
			Player player = players.get(playerIndex);
			int color = player.getColor();
			
			
			// check, whether this player has still a move left
			
			if (numMovesDone[playerIndex] == maxMoves) {
				System.out.println("Game over for player " + player.toString() + " because number of moves exceeded");
				disqualifyPlayer(playerIndex);
				continue;
			}
			
			
			// get a move
			
			long startTime = System.currentTimeMillis();
			Move move = null;
			try {
				move = player.nextMove();
			} catch (Exception e) {
				System.out.println("Game over for player " + player.toString() + " because of an exception, stack trace follows. Please debug");
				e.printStackTrace();
				disqualifyPlayer(playerIndex);
			}
			long endTime = System.currentTimeMillis(); 
			elapsedTime[playerIndex] += endTime - startTime;

			if (move != null) {
				numPasses[playerIndex] = 0;
				numMovesDone[playerIndex]++;
				System.out.println("\nmove " + moveNumber + " (" + numMovesDone[playerIndex] + ") " + player.toString() + " plays " + move + ",  " + elapsedTime[playerIndex] + " msec used, score " + savedStones[playerIndex]);
				showBoard(move);
			} else {
				System.out.println("\nmove " + moveNumber + " " + player.toString() + " passes, " + elapsedTime[playerIndex] + " msec used, score " + savedStones[playerIndex]);
				showBoard(null);
				
				numPasses[playerIndex]++;
				if (numPasses[playerIndex] == 3) {
					System.out.println(player.toString() + " passed three times. Game over for " + player.toString());
					disqualifyPlayer(playerIndex);
				}
				
				continue;
			}
			
			
			// move valid ?
						
			if (move.row < 0 || move.row > numRows) {
				System.out.println("Game over for player " + player.toString() + " because of an invalid row value, row=" + move.row);
				disqualifyPlayer(playerIndex);
				continue;				
			}
			
			if (move.column < 0 || move.column > numColumns) {
				System.out.println("Game over for player " + player.toString() + " because of an invalid column value, column=" + move.column);
				disqualifyPlayer(playerIndex); 
				continue;				
			}
			
			if (board[move.row][move.column] != color) {
				System.out.println("Game over for player " + player.toString() + " because something else was moved");
				disqualifyPlayer(playerIndex); 
				continue;								
			}
			
			
			if (move.direction == Direction.UP) {
				if (board[(move.row+numRows-1)%numRows][move.column] == 0) {
					board[(move.row+numRows-1)%numRows][move.column] = board[move.row][move.column];
					board[move.row][move.column] = 0;
					continue;					
				}
				
				if (board[(move.row+numRows-1)%numRows][move.column] == -color) {
					board[move.row][move.column] = 0;
					savedStones[playerIndex]++;
					System.out.println("player " + player.toString() + " saved a stone, score is " + savedStones[playerIndex]);
					if (allStonesSaved(color)) rank[playerIndex] = nextRank++;
					continue;
				}

				System.out.println("Game over for player " + player.toString() + " because of an up move to a non-empty field");
				disqualifyPlayer(playerIndex);
				continue;					
			}
			
			
			if (move.direction == Direction.DOWN) {
				if (board[(move.row+1)%numRows][move.column] == 0) {
					board[(move.row+1)%numRows][move.column] = board[move.row][move.column];
					board[move.row][move.column] = 0;
					continue;										
				}
				
				if (board[(move.row+1)%numRows][move.column] == -color) {
					board[move.row][move.column] = 0;
					savedStones[playerIndex]++;
					System.out.println("player " + player.toString() + " saved a stone, score is " + savedStones[playerIndex]);
					if (allStonesSaved(color)) rank[playerIndex] = nextRank++;
					continue;
				}

				System.out.println("Game over for player " + player.toString() + " because of a down move to a non-empty field");
				disqualifyPlayer(playerIndex);
				continue;										
			}
			
			
			if (move.direction == Direction.LEFT) {
				if (board[move.row][(move.column+numColumns-1)%numColumns] == 0) {
					board[move.row][(move.column+numColumns-1)%numColumns] = board[move.row][move.column];
					board[move.row][move.column] = 0;
					continue;						
					
				}
				
				if (board[move.row][(move.column+numColumns-1)%numColumns] == -color) {
					board[move.row][move.column] = 0;
					savedStones[playerIndex]++;
					System.out.println("player " + player.toString() + " saved a stone, score is " + savedStones[playerIndex]);
					if (allStonesSaved(color)) rank[playerIndex] = nextRank++;
					continue;
				}
				
				System.out.println("Game over for player " + player.toString() + " because of a left move to a non-empty field");
				disqualifyPlayer(playerIndex);
				continue;						
			}
			
			
			if (move.direction == Direction.RIGHT) {
				if (board[move.row][(move.column+1)%numColumns] == 0) {
					board[move.row][(move.column+1)%numColumns] = board[move.row][move.column];
					board[move.row][move.column] = 0;
					continue;
				}
				
				if (board[move.row][(move.column+1)%numColumns] == -color) {
					board[move.row][move.column] = 0;
					savedStones[playerIndex]++;
					System.out.println("player " + player.toString() + " saved a stone, score is " + savedStones[playerIndex]);
					if (allStonesSaved(color)) rank[playerIndex] = nextRank++;
					continue;
				}

				System.out.println("Game over for player " + player.toString() + " because of a right move to a non-empty field");
				disqualifyPlayer(playerIndex);
				continue;					
			}
			
		} // while true
		
	}
	
	
	public void showBoard(Move move) {
		for (int row=0; row<numRows; row++) {
			for (int column=0; column<numColumns; column++) {
				String label = String.format("%-3d", board[row][column]);
				if (move != null && row == move.row && column == move.column) {
					label = String.format("%-2d", board[row][column]);
					if (move.direction == Direction.UP) label = String.format("^%-2d", board[row][column]);
					if (move.direction == Direction.DOWN) label = String.format(".%-2d", board[row][column]);
					if (move.direction == Direction.LEFT) label = String.format("%-1d< ", board[row][column]);
					if (move.direction == Direction.RIGHT) label = String.format(">%-1d ", board[row][column]);
				}
				System.out.print(label);
			}
			System.out.println();
		}
	}
	

	
	public boolean allStonesSaved(int code) {
		for (int row=0; row<numRows; row++) {
			for (int column=0; column<numColumns; column++) {
				if (board[row][column] == code) return false;
			}
		}
		return true;
	}
	

	
	
	public void disqualifyPlayer(int playerIndex) {
		
		Player player = players.get(playerIndex);
		int color = player.getColor();
		
		for (int row=0; row<numRows; row++) {
			for (int column=0; column<numColumns; column++) {
				if (board[row][column] == color) {
					board[row][column] = 0;
				}
			}
		}

		playerIsDisqualified[playerIndex] = true;
	}
	
	
	
	public int getNumRows() {
		return numRows;
	}
	
	
	
	public int getNumColumns() {
		return numColumns;
	}
	

	
	public int getField(int row, int column) {
		return board[row][column];
	}
	
	
	
	public int getNumPlayers() {
		return players.size();
	}
	
	
	
	public String getPlayerName(int color) {
		return players.get(color-1).toString();
	}
	
	
	public boolean getPlayerIsDisqualified(int color) {
		return playerIsDisqualified[color-1];
	}
	
	
	public int getNumSavedStones(int color) {
		return savedStones[color-1];
	}
	
	
	
	public Integer getRank(int color) {
		return rank[color-1];
	}
	
	
	
	public static void main(String[] args) {
		
		
		if (args.length != 3) {
			System.out.println("usage: Game <configfile> <delay> <maxMoves>");
			System.exit(0);
		}
		

		Game game = new Game(args[0]);
		
		game.addPlayer(new Rotmilan(1, "Rotmilan", game));
		game.addPlayer(new RandomPlayer(2, "Player1", game));
		game.addPlayer(new RandomPlayer(3, "Player2", game));
		game.addPlayer(new RandomPlayer(4, "Player3", game));
		//game.addPlayer(new RandomPlayer(4, "Player4", game));

		long delay = Long.valueOf(args[1]);
		int maxMoves = Integer.valueOf(args[2]);
		//game.start(delay);
		game.start(delay, maxMoves);
	}

}
