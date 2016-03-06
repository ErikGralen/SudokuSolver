package solver;


public class SudokuBoard {
	int[][] board;
	boolean[][] original;

	public SudokuBoard() {
		board = new int[9][9];
		original = new boolean[9][9];
	}

	public boolean importBoard(int[][] b) {
		board = new int[9][9];
		original = new boolean[9][9];
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (b[y][x] != 0) {
					if (!check(y,x,b[y][x])){
						return false;
					}
					board[y][x] = b[y][x];
					original[y][x] = true;
				}
			}
		}
		return true;

	}

	public int[][] exportBoard() {
		return board;
	}

	/**
	 * Checks specific board if valid if its value is changed to input value.
	 * Doesn't change the value, even if possible. Checks row, column and lastly
	 * box's 3x3 group. If the box is an original, returns true if the values
	 * match, else false.
	 * 
	 * @param y
	 * @param x
	 * @param value
	 *            [1-9]
	 * @return boolean
	 */
	public boolean check(int y, int x, int value) {
		
		// Checks if legal parameters
		if (x < 0 || y < 0 || x > 9 || y > 9 || value < 1 || value > 9){
			return false;
		}
		// Checks if original
		if (original[y][x]) {
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
	 * Returns a Point with x,y coordinates of next box, null if last box (8,8)
	 * 
	 * @param x
	 * @param y
	 * @return Point next
	 */
	public Point next(int y, int x) {
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
		if (original[y][x]) {
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
	 * Prints board in console.
	 */
	public void printBoard() {
		System.out.println();
		System.out.println("-----------------------------------");
		for (int i = 0; i < 9; i++) {
			System.out.print("\n");
			if (i % 3 == 0)
				System.out.print("\n");
			for (int j = 0; j < 9; j++) {
				if (j % 3 == 0)
					System.out.print(" ");
				if (board[i][j] == 0)
					System.out.print(". ");
				if (board[i][j] == 1)
					System.out.print("1 ");
				if (board[i][j] == 2)
					System.out.print("2 ");
				if (board[i][j] == 3)
					System.out.print("3 ");
				if (board[i][j] == 4)
					System.out.print("4 ");
				if (board[i][j] == 5)
					System.out.print("5 ");
				if (board[i][j] == 6)
					System.out.print("6 ");
				if (board[i][j] == 7)
					System.out.print("7 ");
				if (board[i][j] == 8)
					System.out.print("8 ");
				if (board[i][j] == 9)
					System.out.print("9 ");
			}
		}
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
