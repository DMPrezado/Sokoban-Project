package pt.iscte.poo.sokobanstarter.elements;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.sokobanstarter.interfaces.Interactable;
import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement implements Interactable {
	
	public Bateria(Point2D point2D){
		super(point2D, "Bateria", 2);
	}

	@Override
	public void interact(GameElement element) {
		if(!(element instanceof Empilhadora))return;
		
		((Empilhadora)element).setEnergia(((Empilhadora)element).getEnergia()+50);
			
		ImageMatrixGUI.getInstance().removeImage(this);
		ImageMatrixGUI.getInstance().update();
		GameEngine.getInstance().getTileList().remove(this);
		
	}

}

