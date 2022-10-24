package application;

public class Board {
	public enum Cell {EMPTY, S, O};
	private static int TOTALROWS = 8;
	private static int TOTALCOLS = 8;
	private int gameMode = 0;
//	0 = Simple Game, 1 = General Game
	private Cell[][] grid;
	private char turn;
	
	public Board() {
		grid = new Cell[TOTALROWS][TOTALCOLS];
		for (int row = 0; row < TOTALROWS; row++) {
			for (int col = 0; col < TOTALCOLS; col++) {
				grid[row][col] = Cell.EMPTY;
			}
		}
		turn = 'S';
	}
	
	public int getMode()
	{
		return gameMode;
	}
	
	public void changeMode(int mode) {
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

	public void makeSMove(int row, int col) {
		if(isValidMove(row,col) && grid[row][col] == Cell.EMPTY) {
			grid[row][col] = Cell.S;
		}
		turn = 'S';
	}
	

	public void makeOMove(int row, int col) {
		if(isValidMove(row,col) && grid[row][col] == Cell.EMPTY) {
			grid[row][col] = Cell.O;
		}
		turn = 'O';
	}

	public boolean isValidMove(int row, int col) {
		if (row >= 0 && row < getRows() && col >= 0 && col < getCols())
		{
			return true;
		}
		return false;
	}
}