package pt.iscte.poo.sokobanstarter.elements;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.sokobanstarter.interfaces.Interactable;
import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends Parede implements Interactable{
	
	
	public ParedeRachada(Point2D point2D){
		super(point2D, "ParedeRachada");
	}

	@Override
	public void interact(GameElement element) {
		if(!(element instanceof Empilhadora))return;
		if(Martelo.getMartelo().wasPicked()) {
			((Movable)element).move(getPosition());
			ImageMatrixGUI.getInstance().removeImage(this);
			ImageMatrixGUI.getInstance().update();
			GameEngine.getInstance().getTileList().remove(this);
		}
	}

	
}

