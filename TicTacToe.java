/**
 * TicTacToe class designed to be used with the TicTacToeInteraction class.
 * 
 * @author TaJah Reynolds
 *
 */
public class TicTacToe {
	private HashedDictionary<Board, Board> gameDictionary;
	private Board currentBoard;
	private boolean move;
	
	/**
	 * Default constructor initializes the Board, first move as X, and
	 * generates the best move for all valid board positions.
	 */
	public TicTacToe() {
		currentBoard = new Board();
		move = true;
		gameDictionary = new HashedDictionary<Board, Board>();
		generateBoards(new Board(), 9, move);
	}
	
	/**
	 * @return the current board.
	 */
	public Board getCurrentBoard() {
		return currentBoard;
	}
	
	/**
	 * Makes a move at given position i.
	 * @param i The position to make a move.
	 * @return true if the move was made, and false otherwise.
	 */
	public boolean makeMove(int i) {
		if (i < 0 || i > 8) {
			return false;
		}
		char character = 'X';
		if (!move) {
			character = 'O';
		}
		if (currentBoard.getPosition(i) == '-') {
			currentBoard.setPosition(i, character);
			move = !move;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the best move for a valid board.
	 * @param board The board to get the best move for.
	 * @return The int position of the best move.
	 */
	public int getBestMove(String board) {
		String best = findBestMove(new Board(board)).toString();
		
		for (int i = 0; i < 9; i++) {
			if (best.charAt(i) != board.charAt(i))
				return i;
		}
		
		return -1;
	}

	private void generateBoards(Board board, int numberOfEmptySpaces, boolean move) {
		if (numberOfEmptySpaces == 0)
			return;
		
		for (int i = 0; i < 9; i++) {
			if (board.getPosition(i) == '-') {
				if (move)
					board.setPosition(i, 'X');
				else
					board.setPosition(i, 'O');
				
				if (boardIsValid(board) && !gameDictionary.contains(board))
					gameDictionary.add(new Board(board.toString()), findBestMove(board));
				
				generateBoards(board, numberOfEmptySpaces - 1, !move);
				board.setPosition(i, '-');
			}
		}
	}
	
	private boolean boardIsValid(Board board) {
		if (countEmptySpaces(board) == 0) {
			return false;
		}
		int countX = 0, countO = 0;
		for (int i = 0; i < 9; i++) {
			if (board.getPosition(i) == 'X') {
				if (rowWin(board, i, true) || columnWin(board, i, true) || diagonalWin(board, i, true))
					return false;
				else
					countX++;
			} else if (board.getPosition(i) == 'O') {
				if (rowWin(board, i, false) || columnWin(board, i, false) || diagonalWin(board, i, false))
					return false;
				else
					countO++;
			}
		}
		if (countO > countX || (countX - countO) > 1)
			return false;
		else
			return true;
	}
	
	private Board findBestMove(Board board) {
		if (canWin(board, getMove(board))) {
			return getWinningMove(board);
		} else if (canLose(board)) {
			return blockWinningMove(board);
		} else {
			return futureMoves(board);
		}
	}
	
	private boolean canWin(Board board, boolean move) {
		for (int i = 0; i < 9; i++) {
			if (board.getPosition(i) == '-') {
				if (rowWin(board, i, move) || columnWin(board, i, move) || diagonalWin(board, i, move)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private Board getWinningMove(Board board) {
		boolean move = getMove(board);
		Board updated = new Board(board.toString());
		char player = 'X';
		if (!move)
			player = 'O';
		for (int i = 0; i < 9; i++) {
			if (board.getPosition(i) == '-') {
				if (rowWin(board, i, move) || columnWin(board, i, move) || diagonalWin(board, i, move)) {
					updated.setPosition(i, player);
					return updated;
				}
			}
		}
		return new Board("INCORRECT");
	}
	
	private Board blockWinningMove(Board board) {
		boolean move = getMove(board);
		Board updated = new Board(board.toString());
		char player = 'X';
		if (!move)
			player = 'O';
		for (int i = 0; i < 9; i++) {
			if (board.getPosition(i) == '-') {
				if (rowWin(board, i, !move) || columnWin(board, i, !move) || diagonalWin(board, i, !move)) {
					updated.setPosition(i, player);
					return updated;
				}
			}
		}
		return new Board("INCORRECT");
	}
	
	/**
	 * Returns true if making a move at position i will end the game, and false otherwise.
	 * @param i the position to be checked for ending the game.
	 * @return true if making a move at position i will end the game, and false otherwise.
	 */
	public int isOver(int i) {
		if (rowWin(currentBoard, i, !move) || columnWin(currentBoard, i, !move) || diagonalWin(currentBoard, i, !move)) {
			return 1;
		} 
		if (countEmptySpaces(currentBoard) == 0) {
			return 0;
		}
		return 2;
	}
	
	private boolean rowWin(Board board, int i, boolean move) {
		char player = 'X';
		if (!move) {
			player = 'O';
		}
		switch (i) {
		case 0: return (board.getPosition(1) == player && board.getPosition(2) == player);
		case 1: return (board.getPosition(0) == player && board.getPosition(2) == player);
		case 2: return (board.getPosition(0) == player && board.getPosition(1) == player);
		case 3: return (board.getPosition(4) == player && board.getPosition(5) == player);
		case 4: return (board.getPosition(3) == player && board.getPosition(5) == player);
		case 5: return (board.getPosition(3) == player && board.getPosition(4) == player);
		case 6: return (board.getPosition(7) == player && board.getPosition(8) == player);
		case 7: return (board.getPosition(6) == player && board.getPosition(8) == player);
		case 8: return (board.getPosition(6) == player && board.getPosition(7) == player);
		default: return false;
		}
	}
	
	private boolean columnWin(Board board, int i, boolean move) {
		char player = 'X';
		if (!move) {
			player = 'O';
		}
		switch (i) {
		case 0: return (board.getPosition(3) == player && board.getPosition(6) == player);
		case 1: return (board.getPosition(4) == player && board.getPosition(7) == player);
		case 2: return (board.getPosition(5) == player && board.getPosition(8) == player);
		case 3: return (board.getPosition(0) == player && board.getPosition(6) == player);
		case 4: return (board.getPosition(1) == player && board.getPosition(7) == player);
		case 5: return (board.getPosition(2) == player && board.getPosition(8) == player);
		case 6: return (board.getPosition(0) == player && board.getPosition(3) == player);
		case 7: return (board.getPosition(1) == player && board.getPosition(4) == player);
		case 8: return (board.getPosition(2) == player && board.getPosition(5) == player);
		default: return false;
		}
	}
	
	private boolean diagonalWin(Board board, int i, boolean move) {
		char player = 'X';
		if (!move) {
			player = 'O';
		}
		switch (i) {
		case 0: return (board.getPosition(4) == player && board.getPosition(8) == player);
		case 2: return (board.getPosition(4) == player && board.getPosition(6) == player);
		case 4: return ((board.getPosition(0) == player && board.getPosition(8) == player) || (board.getPosition(2) == player && board.getPosition(6) == player));
		case 6: return (board.getPosition(4) == player && board.getPosition(2) == player);
		case 8: return (board.getPosition(4) == player && board.getPosition(0) == player);
		default: return false;
		}
	}
	
	private boolean canLose(Board board) {
		return canWin(board, !getMove(board));
	}
	
	private Board futureMoves(Board board) {
		Board future = new Board(board.toString());
		int available = -1;
		int count = countEmptySpaces(board);
		boolean flag = false;
		if (count == 1)
			flag = true;
		
		for (int i = 0; i < 9; i++) {
			if (board.getPosition(i) == '-') {
				available = i;
				if (getMove(board)) {
					future.setPosition(i, 'X');
				} else {
					future.setPosition(i, 'O');
				}
				if (canWin(future, getMove(board))) {
					return future;
				} else {
					if (flag)
						return future;
					future.setPosition(i, '-');
				}
			}
		}
		if (available == -1)
			return board;
		else {
			if (getMove(board)) {
				future.setPosition(available, 'X');
			} else {
				future.setPosition(available, 'O');
			}
			return future;
		}
	}
	
	private int countEmptySpaces(Board board) {
		int count = 0;
		for (int i = 0; i < 9; i++) {
			if (board.getPosition(i) == '-')
				count++;
		}
		return count;
	}
	
	private boolean getMove(Board board) {
		int countX = 0, countO = 0;
		for (int i = 0; i < 9; i++) {
			if (board.getPosition(i) == 'X')
				countX++;
			else if (board.getPosition(i) == 'O')
				countO++;
		}
		return (countX == countO);
	}

}
