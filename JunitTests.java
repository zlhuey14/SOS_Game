package application;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import application.Board.Cell;

public class JunitTests {
	Board test_board = new Board();
	Board test_board2 = new Board();
	Cell[][] grid;
	
//	MAKE SURE THE BOARD IS BEING ASSIGNED THE CORRECT NUMBER OF COLUMNS
	@Test
	public void checkCols() {
		test_board.setBoardSize(5);
		
		assertEquals(5, test_board.getCols());
	}

// MAKE SURE THE BOARD IS BEIGN ASSIGNED THE CORRECT NUMBER OF COLUMNS	
	@Test
	public void checkRows() {
		test_board.setBoardSize(3);
		
		assertEquals(3, test_board.getRows());
	}
	
// MAKE SURE THE BOARD IS BEING CREATED AS A 'NxN' BOARD
	@Test
	public void checkNxNBoard() {
		test_board.setBoardSize(6);
		
		assertEquals(6, test_board.getCols());
		assertEquals(6, test_board.getRows());
	}
	
	@Test
	public void invalidBoard() {
		test_board.setBoardSize(10);
		
		assertFalse(test_board.getRows() == 10);
		assertFalse(test_board.getCols() == 10);
	}
	
// TEST TO MAKE SURE THE INITIAL BOARD IS EMPTY
	@Test
	public void testInit() {
		test_board.setBoardSize(3);
		grid = new Cell[test_board.getRows()][test_board.getCols()];
		
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
		test_board.setBoardSize(6);
		test_board.makeSMove(5, 5);
		
		assertEquals(Cell.S, (test_board.getCell(5, 5)));
	}
	
// TEST TO MAKE SURE AN 'O' IS PLACED
	@Test
	public void oIsPlaced() {
		test_board2.setBoardSize(8);
		test_board2.makeOMove(3, 6);
		
		assertEquals((Cell.O), (test_board2.getCell(3, 6)));
	}
}