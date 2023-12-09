package pt.iscte.poo.sokobanstarter.elements;


import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.sokobanstarter.interfaces.Interactable;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement implements Interactable{
	
	public Alvo(Point2D point){
		super(point, "Alvo", 2);
		
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

	@Override
	public void interact(GameElement element) {
		((Movable)element).move(getPosition());
		isDone();
	}
	
	
}
