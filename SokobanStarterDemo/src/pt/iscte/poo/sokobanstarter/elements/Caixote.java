package pt.iscte.poo.sokobanstarter.elements;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends Movable{

	public Caixote(Point2D point2D) {
		super(point2D, "Caixote", 4);
	}
	
	@Override
	public void move(Direction direction) {
		super.move(direction);
		Alvo.isDone();
	}

}
