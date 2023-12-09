package pt.iscte.poo.sokobanstarter.elements;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.sokobanstarter.interfaces.Pickable;
import pt.iscte.poo.utils.Point2D;

public class Martelo extends GameElement implements Pickable{
	
	private static Martelo martelo;
	
	public Martelo(Point2D point2D){
		super(point2D, "Martelo", 2);
		martelo = this;
	}

	@Override
	public void pick(GameElement element) {
		if(!(element instanceof Empilhadora))return;
		super.addPickable(martelo);
		ImageMatrixGUI.getInstance().removeImage(this);
		ImageMatrixGUI.getInstance().update();
		GameEngine.getInstance().getTileList().remove(this);
	}
	
	@Override
	public boolean wasPicked() {
		return GameElement.getElementsPicked().contains(martelo);
	}
	
	public static Martelo getMartelo() {
		return martelo;
	}
}

