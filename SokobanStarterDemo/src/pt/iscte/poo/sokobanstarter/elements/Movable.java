package pt.iscte.poo.sokobanstarter.elements;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.sokobanstarter.interfaces.Interactable;
import pt.iscte.poo.sokobanstarter.interfaces.Pickable;
import pt.iscte.poo.utils.Direction;


public abstract class Movable extends GameElement{

	public Movable(Point2D point2d, String name, int layer) {
		super(point2d, name, layer);
	}
	
	
	public void move(Direction direction) {
		GameElement nextElement = objectInThisPoint(getPosition().plus(direction.asVector()));
		
		if(nextElement instanceof Interactable)
			((Interactable) nextElement).interact(this);
		
		else if(nextElement instanceof Pickable)
			((Pickable) nextElement).pick(this);
		else
		setPosition(getPosition().plus(direction.asVector()));
		GameEngine.getInstance().getGui().update();
		
	}



	public boolean canMove(Direction direction) {
		if(objectInThisPoint(getPosition().plus(direction.asVector())) == null) return true;
		return objectInThisPoint(getPosition().plus(direction.asVector())).getLayer()<3;
	}
	

	public void move(Point2D position) {
		Direction direction = Direction.directionFor(GameEngine.getInstance().getLastKeyPressed());
		if(this instanceof Empilhadora) {
			((Empilhadora)this).move(position, direction);
		}else {
			setPosition(position);
		}
			
	}
	
}
