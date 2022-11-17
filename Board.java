package application;

import java.util.Random;

public class Board {
	public enum Cell {EMPTY, S, O};
	private int TOTALROWS = 8;
	private int TOTALCOLS = 8;
	private int gameMode = 0; //	0 = Simple Game, 1 = General Game
	private Cell[][] grid;
	private char turn;
	private int moveCounter;
	private int RIGHT_BOARD_EDGE = TOTALCOLS - 1;
	private int LEFT_BOARD_EDGE = 0;
	private int BOTTOM_BOARD_EDGE = TOTALROWS - 1;
	private int TOP_BOARD_EDGE = 0;
	
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
		//turn = 'O';
	}
	
	//Gonna test the auto move by having the computer only being able to make O moves first
	public void makeAutoOMove() {
		Random random = new Random();
		int row = random.nextInt(getRows());
		int col = random.nextInt(getCols());
		if (grid[row][col] == Cell.O || grid[row][col] == Cell.S) {
			row = random.nextInt(getRows());
			col = random.nextInt(getCols());
		}
		makeOMove(row, col);
	}
	
	public void makeAutoSMove() {
		Random random = new Random();
		int row = random.nextInt(getRows());
		int col = random.nextInt(getCols());
		if (grid[row][col] == Cell.O || grid[row][col] == Cell.S) {
			row = random.nextInt(getRows());
			col = random.nextInt(getCols());
		}
		makeSMove(row, col);
	}

	public boolean isValidMove(int row, int col) {
		if (row >= 0 && row < TOTALROWS && col >= 0 && col < TOTALCOLS)
		{
			return true;
		}
		return false;
	}
	
	public boolean sgSOSCheck(int row, int col) {
		boolean temp = false;
		if (ggVertCheck(row, col) || ggHorCheck(row, col) || checkRightDiag(row, col) || checkLeftDiag(row, col)) {
			temp = true;
		}
		return temp;
	}
	
	public int ggSOSCheck(int row, int col) {
		int points = 0;
		if (ggVertCheck(row, col)) {
			points = points + 1;
		}
		if (ggHorCheck(row,col)) {
			points = points + 1;
		}
		if (checkLeftDiag(row,col)) {
			points = points + 1;
		}
		if (checkRightDiag(row,col)) {
			points = points + 1;
		}

		System.out.println(points); //this just prints the number of points a player scored in a given turn
		return points;
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
		return temp;
	}
	
	public boolean ggVertCheck(int row, int col) {
		boolean temp = false;
		if (getCell(row,col) == Cell.S) {
			if (row - 2 < TOP_BOARD_EDGE) {
				if ((grid[row][col] == Cell.S) && (grid[row+1][col] == Cell.O) && (grid[row+2][col] == Cell.S)) {
					temp = true;
				}
			}
			else if (row + 2 > BOTTOM_BOARD_EDGE) {
				if ((grid[row][col] == Cell.S) && (grid[row-1][col] == Cell.O) && (grid[row-2][col] == Cell.S)) {
					temp = true;
				}
			}
			else if (row - 2 < TOP_BOARD_EDGE && row + 2 > BOTTOM_BOARD_EDGE) {
				temp = false;
			}
			else {
				if ((grid[row][col] == Cell.S) && (grid[row+1][col] == Cell.O) && (grid[row+2][col] == Cell.S)) {
					temp = true;
				}
				
				if ((grid[row][col] == Cell.S) && (grid[row-1][col] == Cell.O) && (grid[row-2][col] == Cell.S)) {
					temp = true;
				}
			}
		}
		
		if (getCell(row,col) == Cell.O) {
			if (row - 1 >= TOP_BOARD_EDGE && row + 1 <= BOTTOM_BOARD_EDGE) {
				if ((grid[row][col] == Cell.O) && (grid[row-1][col] == Cell.S) && (grid[row+1][col] == Cell.S)) {
					temp = true;
				}
			}
		}
		
		return temp;
	}
	
	/*
	public boolean ggVertCheck(int row, int col) {
		boolean temp = false;
		if (row > TOTALROWS - 3) {
			row = TOTALROWS -3;
		}
		if ((grid[row][col] == Cell.S) && (grid[row+1][col] == Cell.O) && (grid[row+2][col] == Cell.S)) {
			temp = true;
		}
		return temp;
	}
	*/
	
	
	public boolean horCheck() {
		boolean temp = false;
		for (int row = 0; row < TOTALROWS; row++) {
			for (int col = 0; col < TOTALCOLS - 2; col++) {
				if ((grid[row][col] == Cell.S) && (grid[row][col+1] == Cell.O) && (grid[row][col+2] == Cell.S)) {
					temp = true;
				}	
			}
		}
		return temp;
	}
	
	public boolean ggHorCheck(int row, int col) {
		boolean temp = false;
		
		//This if block checks the horizontal when an S is placed
		if (getCell(row,col)== Cell.S) {
			if (col + 2 > RIGHT_BOARD_EDGE) {
				if ((grid[row][col] == Cell.S) && (grid[row][col-1] == Cell.O) && (grid[row][col-2] == Cell.S)) {
					temp = true;
				}
			}
			else if (col - 2 < LEFT_BOARD_EDGE ) {
				if ((grid[row][col] == Cell.S) && (grid[row][col+1] == Cell.O) && (grid[row][col+2] == Cell.S)) {
					temp = true;
				}
			}
			else if (col - 2 < LEFT_BOARD_EDGE && col + 2 > RIGHT_BOARD_EDGE) {
				temp = false;
			}
			
			else {
				if ((grid[row][col] == Cell.S) && (grid[row][col+1] == Cell.O) && (grid[row][col+2] == Cell.S)) {
					temp = true;
				}
				
				if ((grid[row][col] == Cell.S) && (grid[row][col-1] == Cell.O) && (grid[row][col-2] == Cell.S)) {
					temp = true;
				}
			}
		}
		
		//This if block checks the horizontal when an O is placed
		if (getCell(row,col)==Cell.O) {
			if (col + 1 <= TOTALCOLS - 1 && col - 1 >= 0) {
				if ((grid[row][col] == Cell.O) && (grid[row][col-1] == Cell.S) && (grid[row][col+1] == Cell.S)) {
					temp = true;
				}
			}
		}

		return temp;
	}
	
	//Above ggHorCheck is the updated version
	//Commented out below is the original
	/*
	public boolean ggHorCheck(int row, int col) {
		boolean temp = false;
		if (col > TOTALCOLS - 3) {
			col = TOTALCOLS - 3;
		}
		if ((grid[row][col] == Cell.S) && (grid[row][col+1] == Cell.O) && (grid[row][col+2] == Cell.S)) {
			temp = true;
		}
		return temp;
	}
	*/
	
	public boolean diagCheckRD() {
		boolean temp = false;
		for (int row = 0; row < TOTALROWS - 2; row++) {
			for (int col = 0; col < TOTALCOLS - 2; col++) {
				if ((grid[row][col] == Cell.S) && (grid[row+1][col+1] == Cell.O) && (grid[row+2][col+2] == Cell.S)) {
					temp = true;
				}	
			}
		}
		return temp;
		
	}
	
	/* I consider this a left diagonal
	 *   0 1 2 3 4 5 6 7
	 * 0           S 
	 * 1             O 
	 * 2               S
	 * 3   
	 * 4  
	 * 5
	 * 6
	 * 7 
	 */
	
	public boolean checkLeftDiag(int row, int col) {
		boolean temp = false;
		if (getCell(row, col) == Cell.S) {
			if(col - 2 >= LEFT_BOARD_EDGE && row - 2 >= TOP_BOARD_EDGE) {
				if ((grid[row][col] == Cell.S) && (grid[row-1][col-1] == Cell.O) && (grid[row-2][col-2] == Cell.S)) {
					temp = true;
				}
			}
			if (col + 2 <= RIGHT_BOARD_EDGE && row + 2 <= BOTTOM_BOARD_EDGE) {
				if ((grid[row][col] == Cell.S) && (grid[row+1][col+1] == Cell.O) && (grid[row+2][col+2] == Cell.S)) {
					temp = true;
				}
			}
		}
		
		if (getCell(row, col) == Cell.O) {
			if ((row - 1 >= TOP_BOARD_EDGE && col - 1 >= LEFT_BOARD_EDGE) && (row + 1 <= BOTTOM_BOARD_EDGE && col + 1 <= RIGHT_BOARD_EDGE)) {
				if ((grid[row][col] == Cell.O) && (grid[row+1][col+1] == Cell.S) && (grid[row-1][col-1] == Cell.S)) {
					temp = true;
				}
			}
		}
		return temp;
	}
	
	/*
	public boolean checkLeftDiag(int row, int col) {
		boolean temp = false;
		if (getCell(row, col) == Cell.S) {
			if (col + 2 > RIGHT_BOARD_EDGE || row + 2 > BOTTOM_BOARD_EDGE) {
				if ((grid[row][col] == Cell.S) && (grid[row-1][col-1] == Cell.O) && (grid[row-2][col-2] == Cell.S)) {
					temp = true;
				}
			}
			else if (col - 2 < LEFT_BOARD_EDGE || row - 2 < TOP_BOARD_EDGE) {
				if ((grid[row][col] == Cell.S) && (grid[row+1][col+1] == Cell.O) && (grid[row+2][col+2] == Cell.S)) {
					temp = true;
				}
			}
			else if ((col - 2 < LEFT_BOARD_EDGE && row - 2 < TOP_BOARD_EDGE) || (col + 2 > RIGHT_BOARD_EDGE && row + 2 > BOTTOM_BOARD_EDGE)) {
				temp = false;
			}
			else {
				if ((grid[row][col] == Cell.S) && (grid[row-1][col-1] == Cell.O) && (grid[row-2][col-2] == Cell.S)) {
					temp = true;
				}
				
				if ((grid[row][col] == Cell.S) && (grid[row+1][col+1] == Cell.O) && (grid[row+2][col+2] == Cell.S)) {
					temp = true;
				}
			}
		}
		
		if (getCell(row, col) == Cell.O) {
			if ((row - 1 >= TOP_BOARD_EDGE && col - 1 >= LEFT_BOARD_EDGE) && (row + 1 <= BOTTOM_BOARD_EDGE && col + 1 <= RIGHT_BOARD_EDGE)) {
				if ((grid[row][col] == Cell.O) && (grid[row+1][col+1] == Cell.S) && (grid[row-1][col-1] == Cell.S)) {
					temp = true;
				}
			}
		}
		return temp;
	}
	*/
	
	public boolean checkLeftAscend(int row, int col) {
		boolean temp = false;
		int limit = 2;
		/*
		 * Since im checking the two spaces previous from the placed piece,
		 * if 'row' or 'col' - 2 is less than the lower limit of the board
		 * (i.e. 0), then I don't want to run the check so that an invalid
		 * board space is not attempted to be accessed.
		 */
		if (row - limit < 0 || col - limit < 0) {
			temp = false;
		}
		else {
			if ((grid[row][col] == Cell.S) && (grid[row-1][col-1] == Cell.O) && (grid[row-2][col-2] == Cell.S)) {
				temp = true;
			}
		}
		return temp;
	}
	
	public boolean checkLeftDescend(int row, int col) {
		boolean temp = false;
		int limit = 2;
		if (row + limit > TOTALROWS || col + limit > TOTALCOLS) {
			temp = false;
		}
		else {
			if ((grid[row][col] == Cell.S) && (grid[row+1][col+1] == Cell.O) && (grid[row+2][col+2] == Cell.S)) {
				temp = true;
			}
		}
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
		return temp;
	}
	
	/* I consider this a right Diagonal
	 *   0 1 2 3 4 5 6 7
	 * 0 
	 * 1     S
	 * 2   O  
	 * 3 S  
	 * 4  
	 * 5
	 * 6
	 * 7 
	 */
	
	public boolean checkRightDiag(int row, int col) {
		boolean temp = false;
		if(getCell(row, col) == Cell.S) {
			if(col + 2 <= RIGHT_BOARD_EDGE && row - 2 >= TOP_BOARD_EDGE) {
				if ((grid[row][col] == Cell.S) && (grid[row-1][col+1] == Cell.O) && (grid[row-2][col+2] == Cell.S)) {
					temp = true;
				}
			}
			
			if (col - 2 >= LEFT_BOARD_EDGE && row + 2 <= BOTTOM_BOARD_EDGE) {
				if ((grid[row][col] == Cell.S) && (grid[row+1][col-1] == Cell.O) && (grid[row+2][col-2] == Cell.S)) {
					temp = true;
				}
			}
		}
		
		if(getCell(row,col) == Cell.O) {
			if ((row - 1 >= TOP_BOARD_EDGE && col + 1 >= LEFT_BOARD_EDGE) && (row + 1 <= BOTTOM_BOARD_EDGE && col - 1 <= RIGHT_BOARD_EDGE)) {
				if ((grid[row][col] == Cell.O) && (grid[row-1][col+1] == Cell.S) && (grid[row+1][col-1] == Cell.S)) {
					temp = true;
				}
			}
		}
		return temp;
	}
	
	/*
	public boolean checkRightDiag(int row, int col) {
		boolean temp = false;
		if(getCell(row, col) == Cell.S) {
			if (col + 2 > RIGHT_BOARD_EDGE || row - 2 > TOP_BOARD_EDGE) {
				if ((grid[row][col] == Cell.S) && (grid[row+1][col-1] == Cell.O) && (grid[row+2][col-2] == Cell.S)) {
					temp = true;
				}
			}
			else if (col - 2 < LEFT_BOARD_EDGE || row + 2 > BOTTOM_BOARD_EDGE) {
				if ((grid[row][col] == Cell.S) && (grid[row-1][col+1] == Cell.O) && (grid[row-2][col+2] == Cell.S)) {
					temp = true;
				}
			}
			else if ((col + 2 > RIGHT_BOARD_EDGE || row - 2 > TOP_BOARD_EDGE) && (col - 2 < LEFT_BOARD_EDGE || row + 2 > BOTTOM_BOARD_EDGE)) {
				temp = false;
			}
			else {
				if ((grid[row][col] == Cell.S) && (grid[row+1][col-1] == Cell.O) && (grid[row+2][col-2] == Cell.S)) {
					temp = true;
				}
				if ((grid[row][col] == Cell.S) && (grid[row-1][col+1] == Cell.O) && (grid[row-2][col+2] == Cell.S)) {
					temp = true;
				}
			}
		}
		
		if(getCell(row,col) == Cell.O) {
			if ((row - 1 >= TOP_BOARD_EDGE && col + 1 >= LEFT_BOARD_EDGE) && (row + 1 <= BOTTOM_BOARD_EDGE && col - 1 <= RIGHT_BOARD_EDGE)) {
				if ((grid[row][col] == Cell.O) && (grid[row-1][col+1] == Cell.S) && (grid[row+1][col-1] == Cell.S)) {
					temp = true;
				}
			}
		}
		return temp;
	}
	*/
	
	public boolean checkRightAscend(int row, int col) {
		boolean temp = false;
		int limit = 2;
		if (row - limit < 0 || col + limit > TOTALCOLS) {
			temp = false;
		}
		else {
			if ((grid[row][col] == Cell.S) && (grid[row-1][col+1] == Cell.O) && (grid[row-2][col+2] == Cell.S)) {
				temp = true;
			}	
		}
		return temp;
	}
	
	public boolean checkRightDescend(int row, int col) {
		boolean temp = false;
		int limit = 2;
		if (row + limit > TOTALROWS || col - limit < 0) {
			temp = false;
		}
		else {
			if ((grid[row][col] == Cell.S) && (grid[row+1][col-1] == Cell.O) && (grid[row+2][col-2] == Cell.S)) {
				temp = true;
			}	
		}
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
		System.out.println("checkIfFull = " + temp);
		return temp;
	}
}