package application;

public class Board {
	public enum Cell {EMPTY, S, O};
	private int TOTALROWS = 8;
	private int TOTALCOLS = 8;
	private int gameMode = 0; //	0 = Simple Game, 1 = General Game
	private Cell[][] grid;
	private char turn;
	private int moveCounter = 0;
	private int SOSCount1;
	private int SOSCount2;
	
	public Board() {
		grid = new Cell[TOTALROWS][TOTALCOLS];
		for (int row = 0; row < TOTALROWS; row++) {
			for (int col = 0; col < TOTALCOLS; col++) {
				grid[row][col] = Cell.EMPTY;
			}
		}
		turn = 'S';
	}
	
	public int getMoveCount() {
		return moveCounter;
	}
	
	public void resetMoveCount() {
		moveCounter = 0;
	}
	
	public void moveCountInc() {
		moveCounter++;
	}
	
	public int getMode()
	{
		return gameMode;
	}
	
	public void setMode(int mode) {
		gameMode = mode;
	}
	
	public Boolean setBoardSize(int size)
	{
		if (size < 3 || size > 8)
		{			
			System.out.println("Board size must be between 3 and 8.");
			return false;
		}
		else
		{
			TOTALCOLS = size;
			TOTALROWS = size;
			return true;
		}
	}
	
	public int getRows() {
		return TOTALROWS;
	}
	
	public int getCols() {
		return TOTALCOLS;
	}
	
	public Cell getCell(int row, int col) {
		if (isValidMove(row, col))
		{
			return grid[row][col];
		}
		return null;
	}
	
	public char getTurn() {
		return turn;
	}
	
	public void clearBoard() {
		for (int row = 0; row < TOTALROWS; row++) {
			for (int col = 0; col < TOTALCOLS; col++) {
				grid[row][col] = Cell.EMPTY;
			}
		}
	}

	public void makeSMove(int row, int col) {
		if(isValidMove(row,col) && grid[row][col] == Cell.EMPTY) {
			grid[row][col] = Cell.S;
			//System.out.println(row + ", " + col);
		}
		turn = 'S';
	}
	

	public void makeOMove(int row, int col) {
		if(isValidMove(row,col) && grid[row][col] == Cell.EMPTY) {
			grid[row][col] = Cell.O;
			//System.out.println(row + ", " + col);
		}
		turn = 'O';
	}

	public boolean isValidMove(int row, int col) {
		if (row >= 0 && row < TOTALROWS && col >= 0 && col < TOTALCOLS)
		{
			return true;
		}
		
		return false;
	}
	
	public void checkGameState() {
		if (getMode() == 0) {
			//game logic here to check for an SOS in a SIMPLE GAME
			//Need to check if Player 1 or Player 2 completed the SOS
			//End the game on the first SOS
			if (SOSCheck() && (moveCounter % 2 == 0)) {
				System.out.println("Player 1 wins.");
				//insert a method to end the game
			}
			else if (SOSCheck() && !(moveCounter % 2 == 0)) {
				System.out.println("Player 2 wins.");
				//insert a method to end the game.
			}
			else if (!(SOSCheck()) && checkIfFull()) {
				System.out.println("Game Draw.");
			}
		}
		
		if (getMode() == 1) {
			//game logic here to check for an SOS in a GENERAL GAME
			//Need to check if Player 1 or Player 2 completed the SOS
			//Award a point to a player if they complete an SOS
			if (getMoveCount() % 2 == 0) {
				SOSCount1++;
			}
			else {
				SOSCount2++;
			}
		}
	}
	
	public boolean SOSCheck() {
		boolean temp = false;
		if (vertCheck() || horCheck() || diagCheckRD() || diagCheckLD()) {
			temp = true;
		}
		return temp;
	}
	
	public boolean vertCheck() {
		boolean temp = false;
		for (int row = 0; row < TOTALROWS - 2; row++) {
			for (int col = 0; col < TOTALCOLS; col++) {
				if ((grid[row][col] == Cell.S) && (grid[row+1][col] == Cell.O) && (grid[row+2][col] == Cell.S)) {
					temp = true;
				}	
			}
		}
		//System.out.println("Vertical Detect: " + temp);
		return temp;
	}
	
	public boolean horCheck() {
		boolean temp = false;
		for (int row = 0; row < TOTALROWS; row++) {
			for (int col = 0; col < TOTALCOLS - 2; col++) {
				if ((grid[row][col] == Cell.S) && (grid[row][col+1] == Cell.O) && (grid[row][col+2] == Cell.S)) {
					temp = true;
				}	
			}
		}
		//System.out.println("Horizontal Detect: " + temp);
		return temp;
	}
	
	public boolean diagCheckRD() {
		boolean temp = false;
		for (int row = 0; row < TOTALROWS - 2; row++) {
			for (int col = 0; col < TOTALCOLS - 2; col++) {
				if ((grid[row][col] == Cell.S) && (grid[row+1][col+1] == Cell.O) && (grid[row+2][col+2] == Cell.S)) {
					temp = true;
				}	
			}
		}
		//System.out.println("Right Diagonal Check: " + temp);
		return temp;
		
	}
	
	public boolean diagCheckLD () {
		boolean temp = false;
		for (int row = 2; row < TOTALROWS; row++) {
			for (int col = 0; col < TOTALCOLS - 2; col++) {
				if ((grid[row][col] == Cell.S) && (grid[row-1][col+1] == Cell.O) && (grid[row-2][col+2] == Cell.S)) {
					temp = true;
				}	
			}
		}
		//System.out.println("Left Diagonal Check: " + temp);
		return temp;
	}
	
	public boolean checkIfFull() {
		boolean temp = true;
		for (int row = 0; row < TOTALROWS; row++) {
			for (int col = 0; col < TOTALCOLS; col++) {
				if ((grid[row][col] == Cell.S) || (grid[row][col] == Cell.O)) {
					continue;
				}
				else if (grid[row][col] == Cell.EMPTY){
					temp = false;
					break;
				}
			}
		}
		return temp;
	}
}