package pt.iscte.poo.sokobanstarter.elements;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Point2D;

public class Parede extends GameElement{
	
	public Parede(Point2D point2D){
		super(point2D, "Parede", 3);
	}

	public Parede(Point2D point2d, String string) {
		super(point2d, string , 3);
	}
	
}

