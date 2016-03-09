package solver;


public class SudokuBoard {
	private int[][] board;

	public SudokuBoard() {
		board = new int[9][9];
	}
	
/** Imports a board in int[][] format. Copies value and checks if board is valid.
 * 	Empty boxes should have value 0.
 * @param b int[9][9] [0...9]
 * @return true if board is valid, false if not valid
 */
	public boolean importBoard(int[][] b) {
		board = new int[9][9];
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (b[y][x] != 0) {
					if (!check(y,x,b[y][x])){
						return false;
					}
					board[y][x] = b[y][x];
				}
			}
		}
		return true;

	}
	
	
	/** Returns board in int[9][9] format 
	 * @return int[][]
	 */
	public int[][] exportBoard() {
		return board;
	}
	

	/**
	 * Checks specific board if valid if its value is changed to input value.
	 * Doesn't change the value, even if possible. Checks row, column and lastly
	 * box's 3x3 group. If the box is an original value, returns true if the values
	 * match, else false.
	 * 
	 * @param y
	 * @param x
	 * @param value          
	 * @return boolean
	 */
	private boolean check(int y, int x, int value) {
		
		// Checks if legal parameters
		if (x < 0 || y < 0 || x > 9 || y > 9 || value < 1 || value > 9){
			return false;
		}
		// Checks if original
		if (board[y][x]!=0) {
			return board[y][x] == value;
		}
		// Checks row, column
		for (int i = 0; i < 9; i++) {
			if (board[y][i]==value || board[i][x]==value) {
				return false;
			}
		}

		// Checks region. ( (x/3)=0,0,0,1,1,1,2,2,2 )
		for (int i = (y / 3) * 3; i < ((y / 3) * 3) + 3; i++) {
			for (int j = (x / 3) * 3; j < ((x / 3) * 3) + 3; j++) {
				if (board[i][j] == value)
					return false;
			}
		}
		return true;
	}


	/**
	 * Solves the SudokuBoard if possible.
	 * 
	 * @return True if solved, False if impossible
	 */
	public boolean solve() {
		return solve(0, 0);
	}

	/**
	 * Solves the specific box. If the box has a starting value, it won't be
	 * changed.
	 * 
	 * @param y
	 * @param x
	 * @return boolean
	 */
	private boolean solve(int y, int x) {
		Point next = next(y, x);
		// If original value
		if (board[y][x]!=0) {
			if (y == 8 && x == 8) {
				return true;
			} else {
				return solve(next.y, next.x);
			}
		}
		// Else
		for (int i = 1; i < 10; i++) {
			if (check(y, x, i)) {
				board[y][x] = i;
				if (y == 8 && x == 8) {
					return true;
				}
				if (solve(next.y, next.x)) {
					return true;
				} else {
					board[y][x] = 0;
				}
			}
			
		}
		return false;
	}
	
	/**
	 * Returns a Point with x,y coordinates of next box, null if last box (8,8)
	 * @param x
	 * @param y
	 * @return Point next
	 */
	private Point next(int y, int x) {
		Point next = new Point(y, x);
		if (x == 8 && y == 8) {
			next = null;
		} else if (x == 8 && y < 8) {
			next.x = 0;
			next.y++;
		} else if (x < 8) {
			next.x++;
		}
		return next;
	}

	/**
	 * Constructs a Point with x,y coordinates Use point.x and point.y to
	 * edit/get
	 */
	private class Point {
		int y, x;

		private Point(int y, int x) {
			this.x = x;
			this.y = y;
		}
	}
}
