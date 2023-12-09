package pt.iscte.poo.sokobanstarter.elements;


import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.sokobanstarter.interfaces.Interactable;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement implements Interactable{
	private static int nAlvos=0;
	
	public Alvo(Point2D point){
		super(point, "Alvo", 2);
		nAlvos++;
		
	}
	
	public static int getnAlvos() {return nAlvos;}
	
	public boolean hasBox() {
		for(GameElement element : GameEngine.getInstance().getTileList())
			if(element instanceof Caixote && element.getPosition().equals(getPosition()))
				return true;
		return false;
	}
	
	public void canWin() {
		if(Caixote.getnCaixotes()<nAlvos)
			GameEngine.getInstance().levelFailed();
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
