package pt.iscte.poo.sokobanstarter.elements;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.sokobanstarter.interfaces.Interactable;
import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement implements Interactable{
	
	public Teleporte(Point2D point2D){
		super(point2D, "Teleporte", 2);
	}
	
//	public static Teleporte outroTeleporte(Teleporte tele){
//		for (GameElement element : GameEngine.getInstance().getTileList())
//			if(!(element.equals(tele)) && element instanceof Teleporte)
//				return (Teleporte) element;
//		return null;
//	}
//	
//	public static GameElement teleporteContent(GameElement element) {
//		for (GameElement element2 : listOfObjectsInThisPoint(element.getPosition())) 
//			if(element2 instanceof Movable) {
//				return element2;
//			}
//		return null;
//	}
//
//	public static GameElement nextElement(GameElement nextElement) {
//		if(Teleporte.teleporteContent((GameElement)nextElement) instanceof Movable) {
//			return Teleporte.teleporteContent((GameElement)nextElement);
//		}else {
//			nextElement = Teleporte.outroTeleporte((Teleporte)nextElement);
//			return Teleporte.teleporteContent((GameElement)nextElement);
//		}
//	}

//	@Override
//	public void interact(GameElement element) {
//		element.setPosition(outroTeleporte(this).getPosition());
//	}
	
	/************************************Novo código**********************************/
	public Point2D getSecondTeleportPosition() {
        for(GameElement element : GameEngine.getInstance().getTileList()) {
            if(element instanceof Teleporte && !(element.equals(this))) {
                return element.getPosition();
            }
        }
        return null;
    }

    public boolean hasObject(Point2D position) {
        for(GameElement element : GameEngine.getInstance().getTileList())
            if(element instanceof Movable && element.getPosition().equals(position))
                return true;
        return false;
    }

    @Override
    public void interact(GameElement element) {
        Point2D teleportPosition = getSecondTeleportPosition();
        if(teleportPosition != null && !(hasObject(teleportPosition))) {
        	((Movable)element).move(teleportPosition);
        }
    }
	
	
	
	
}

