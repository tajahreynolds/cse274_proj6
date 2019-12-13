
public class Board {
	private char[] position;
	
	public Board() {
		position = new char[9];
		for (int i = 0; i < 9; i++) {
			position[i] = '-';
		}
	}
	
	public Board(String string) {
		position = new char[9];
		for (int i = 0; i < 9; i++) {
			position[i] = string.charAt(i);
		}
	}

	public char getPosition(int index) {
		return position[index];
	}
	
	public void setPosition(int index, char character) {
		position[index] = character;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < 9; i++) {
			str += position[i];
		}
		return str;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 1;
		for (int i = 0; i < 9; i++) {
			hashCode = 31 * hashCode + (int)position[i];
		}
		return hashCode;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Board) {
			Board that = ((Board) o); 
			for (int i = 0; i < 9; i++) {
				if (this.position[i] != that.position[i]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
