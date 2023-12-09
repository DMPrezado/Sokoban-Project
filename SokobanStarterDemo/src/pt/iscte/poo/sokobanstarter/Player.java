package pt.iscte.poo.sokobanstarter;


public class Player implements Comparable<Player>{
	private String name;
	private int moves;
	
	public Player(String name, int moves) {
		this.name = name;
		this.moves = moves;
	}
	
	public Player(String str) {
		String[] player = str.split(",");
		name=player[0];
		moves = Integer.valueOf(player[1]);
	}

	public String getName() {
		return name;
	}
	
	public int getMoves() {
		return moves;
	}

	public void increaseMoves() {
		moves++;
	}
	
	public void resetMoves() {
		moves=0;
	}
	
	@Override
	public String toString() {
		return getName() + ","+getMoves();
	}

	public boolean equals(Player obj) {
		if(getName()==obj.getName())
			return true;
		return false;
	}

	@Override
	public int compareTo(Player o) {
		return getMoves()-o.getMoves();
	}
}
