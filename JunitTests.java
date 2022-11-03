package application;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import application.Board.Cell;

public class JunitTests {
	
//	MAKE SURE THE BOARD IS BEING ASSIGNED THE CORRECT NUMBER OF COLUMNS
	@Test
	public void checkCols() {
		Board test_board = new Board();
		test_board.setBoardSize(5);
		
		assertEquals(5, test_board.getCols());
	}

// MAKE SURE THE BOARD IS BEIGN ASSIGNED THE CORRECT NUMBER OF COLUMNS	
	@Test
	public void checkRows() {
		Board test_board = new Board();
		test_board.setBoardSize(3);
		
		assertEquals(3, test_board.getRows());
	}
	
// MAKE SURE THE BOARD IS BEING CREATED AS A 'NxN' BOARD
	@Test
	public void checkNxNBoard() {
		Board test_board = new Board();
		test_board.setBoardSize(6);
		
		assertEquals(6, test_board.getCols());
		assertEquals(6, test_board.getRows());
	}
	
	@Test
	public void invalidBoard() {
		Board test_board = new Board();
		test_board.setBoardSize(10);
		
		assertFalse(test_board.getRows() == 10);
		assertFalse(test_board.getCols() == 10);
	}
	
// TEST TO MAKE SURE THE INITIAL BOARD IS EMPTY
	@Test
	public void testInit() {
		Board test_board = new Board();
		test_board.setBoardSize(3);
		for (int row = 0; row < test_board.getRows(); row++) {
			for (int col = 0; col < test_board.getCols(); col++) {
				//grid[row][col] = Cell.EMPTY;
				assertEquals(Cell.EMPTY, test_board.getCell(row, col));
			}
		}
	}
	
// TEST TO MAKE SURE AN 'S' IS PLACED
	@Test
	public void sIsPlaced() {
		Board board = new Board();
		board.setBoardSize(6);
		board.makeSMove(5, 5);
		
		assertEquals(Cell.S, (board.getCell(5, 5)));
	}


// TEST TO MAKE SURE AN 'O' IS PLACED
	@Test
	public void oIsPlaced() {
		Board board = new Board();
		board.setBoardSize(8);
		board.makeOMove(3, 6);
		
		assertEquals((Cell.O), (board.getCell(3, 6)));
	}

	
// TEST TO SEE IF A VERTICAL SOS IS DETECTED
	@Test
	public void vertSOS() {
		Board board = new Board();
		board.setBoardSize(6);
		board.makeSMove(3, 1);
		board.makeOMove(4, 1);
		board.makeSMove(5, 1);
		
		assertEquals(true, board.vertCheck());
	}
	
// Test to check and make sure that the checkIfFull() method properly returns true when the board is full
	@Test
	public void boardFullTest() {
		Board board = new Board();
		board.setBoardSize(3);
		board.makeSMove(0, 0);
		board.makeSMove(0, 1);
		board.makeOMove(0, 2);
		board.makeSMove(1, 0);
		board.makeSMove(1, 1);
		board.makeOMove(1, 2);
		board.makeSMove(2, 0);
		board.makeOMove(2, 1);
		board.makeSMove(2, 2);
		
		assertEquals(true, board.checkIfFull());
	}
	
// Test to check and make sure that the checkIfFull() method properly returns false when the board is not full
	@Test
	public void boardNotFull() {
		Board board = new Board();
		board.setBoardSize(5);
		board.makeSMove(1, 3);
		board.makeOMove(2, 0);
		
		assertEquals(false, board.checkIfFull());
	}
}