package pt.iscte.poo.sokobanstarter.elements;


import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement{
	
	public Alvo(Point2D point){
		super(point, "Alvo", 1);
		
	}
	
	public boolean hasBox() {
		for(GameElement element : GameEngine.getInstance().getTileList())
			if(element instanceof Caixote && element.getPosition().equals(getPosition()))
				return true;
		return false;
	}
	
	public static void isDone() {
		for(GameElement element : GameEngine.getInstance().getTileList())
			if(element instanceof Alvo && !(((Alvo)element).hasBox()))
				return;
		GameEngine.getInstance().win();
	}
}
