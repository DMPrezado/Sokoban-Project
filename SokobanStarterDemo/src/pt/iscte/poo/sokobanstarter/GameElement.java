package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {
	
	private Point2D point2D;
	private String name;
	private int layer;
	private static List<GameElement> elementsPicked;
	
	public GameElement(Point2D point2D, String name, int layer) {
		this.point2D = point2D;
		this.name = name;
		this.layer = layer;
		elementsPicked = new ArrayList<GameElement>();
	}
	
	public static void addPickable(GameElement element){
		elementsPicked.add(element);
	}
	
	public static List<GameElement> getElementsPicked() {
		return elementsPicked;
	}
	
	public void setPosition(Point2D point2d) {
		point2D = point2d;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	public String getName() {
		return name;
	}

	public Point2D getPosition() {
		return point2D;
	}

	public int getLayer() {
		return layer;
	}
	
	
	public static List<GameElement> listOfObjectsInThisPoint(Point2D pos){
		List<GameElement> lista = new ArrayList<GameElement>();
		for (GameElement element : GameEngine.getInstance().getTileList())
			if(element.getPosition().equals(pos))
				lista.add(element);
		return lista;
	}
	
//	public static GameElement objectInThisPoint(Point2D pos) {
//		List<GameElement> lista = listOfObjectsInThisPoint(pos);
//		for (GameElement gameElement : lista)
//			if(gameElement.getLayer()>1)
//				System.out.println(gameElement);
//				return gameElement;
//		return null;
//	}
	
	public GameElement objectInThisPoint(Point2D pos) {
        List<GameElement> lista = listOfObjectsInThisPoint(pos);
        if(lista.size()==0)return null;
        GameElement topElement = lista.get(0);
        for (GameElement gameElement : lista)
            if(gameElement.getLayer()> topElement.getLayer())
                topElement = gameElement;
        if(topElement.getLayer() > 1)
            return topElement;
        return null;
    }
	
	
	
}
