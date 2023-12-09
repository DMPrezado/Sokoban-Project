package pt.iscte.poo.sokobanstarter.interfaces;

import pt.iscte.poo.sokobanstarter.GameElement;

public interface Pickable {
	
	public void pick(GameElement element);
	public boolean wasPicked();
	
}
