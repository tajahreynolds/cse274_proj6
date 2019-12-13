import java.util.Scanner;

public class TicTacToeInteraction {
	
	public static void main(String[] args) {
		TicTacToe game = new TicTacToe();
		boolean stop = false;
		Scanner in = new Scanner(System.in);
		int position = -1;
		
		while (!stop) {
			displayBoard(game.getCurrentBoard());
			System.out.print("Where would you like to move? ");
			position = in.nextInt();
			if (!game.makeMove(position)) {
				System.out.println("Invalid position");
				continue;
			} else {
				if (game.isOver(position) < 2) {
					if (game.isOver(position) == 1) {
						displayBoard(game.getCurrentBoard());
						System.out.println("YOU WIN!");
						stop = true;
					} else {
						displayBoard(game.getCurrentBoard());
						System.out.println("TIE GAME!");
						stop = true;
					}
				} else {
					position = game.getBestMove(game.getCurrentBoard().toString());
					game.makeMove(position);
					if (game.isOver(position) == 1) {
						displayBoard(game.getCurrentBoard());
						System.out.println("YOU LOSE!");
						stop = true;
					}
				}
			}
		}
		in.close();
	}
	
	private static void displayBoard(String board) {
		String str = " ";
		for (int i = 0; i < 9; i++) {
			str += board.charAt(i) + " ";
			if (i != 2 && i != 5 && i != 8) {
				str += "| ";
			} else if (i != 8) {
				str += "\n-----------\n ";
			}
			
		}
		System.out.println(str);
	}
	
	private static void displayBoard(Board board) {
		displayBoard(board.toString());
	}

}
