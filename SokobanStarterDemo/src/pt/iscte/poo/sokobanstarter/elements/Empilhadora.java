package pt.iscte.poo.sokobanstarter.elements;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.sokobanstarter.interfaces.Interactable;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends Movable {
	
	private int energia;
	
	public Empilhadora(Point2D initialPosition) {
		super(initialPosition, "Empilhadora_D", 3);
		this.energia=100;
	}
	
	public void move(Point2D position, Direction direction) {
		setPosition(position);
		setName(changeImage(direction));
	}
	
	
	public void move(Direction direction) {
		GameElement nextElement = objectInThisPoint(getPosition().plus(direction.asVector()));
		
		if(nextElement instanceof Movable && ((Movable) nextElement).canMove(direction)) {
			((Movable) nextElement).move(direction);
			setEnergia(--energia);
		}
		
		if(nextElement instanceof Interactable)
			((Interactable) nextElement).interact(this);
		else if(this.canMove(direction)) {
			super.move(direction);
			setName(changeImage(direction));
			GameEngine.getInstance().getPlayer().increaseMoves();
			setEnergia(--energia);
		}
	}
	
	@Override
	public boolean canMove(Direction direction) {
		if(objectInThisPoint(getPosition().plus(direction.asVector())) == null) 
			return true;
		return objectInThisPoint(getPosition().plus(direction.asVector())).getLayer() <3;
	}
	

	private String changeImage(Direction direction) {
		switch(direction) {
			case UP:
				return "Empilhadora_U";
			case DOWN:
				return "Empilhadora_D";
			case RIGHT:
				return "Empilhadora_R";
			case LEFT:
				return "Empilhadora_L";
			default:
				return "";
		}
	}
	
	
	public int getEnergia() {
		return energia;
	}
	
	public void setEnergia(int energia) {
		if(energia>=100)energia=100;
		if(energia<=0) GameEngine.getInstance().levelFailed();
		this.energia = energia;
	}
}