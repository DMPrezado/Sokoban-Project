package pt.iscte.poo.sokobanstarter.elements;

import pt.iscte.poo.utils.Point2D;

public class Caixote extends Movable{
	
	private static int nCaixotes=0;

	public Caixote(Point2D point2D) {
		super(point2D, "Caixote", 4);
		nCaixotes++;
	}
	
	public static int getnCaixotes() {return nCaixotes;}

	public static void decreaseNCaixotes() {
		nCaixotes--;
	}
}
