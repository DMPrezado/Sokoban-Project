package pt.iscte.poo.sokobanstarter.elements;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.sokobanstarter.interfaces.Interactable;
import pt.iscte.poo.utils.Point2D;


public class Buraco extends GameElement implements Interactable{
	
	
	
	public Buraco(Point2D point2D){
		super(point2D, "Buraco", 2);
	}

	@Override
	public void interact(GameElement element) {
		if(element instanceof Palete) {
			((Movable)element).move(getPosition());
			GameEngine.getInstance().getTileList().remove(element);
			GameEngine.getInstance().getTileList().remove(this);
		}
		else if(element instanceof Caixote) {
			Caixote.decreaseNCaixotes();
			((Movable)element).move(getPosition());
			GameEngine.getInstance().getTileList().remove(element);
			ImageMatrixGUI.getInstance().removeImage(element);
		}else
			GameEngine.getInstance().levelFailed();
	}

}

