package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.sokobanstarter.elements.Empilhadora;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE; 
	private ImageMatrixGUI gui; 
	private List<GameElement> tileList;
	private Empilhadora bobcat;
	private Level level;
	private Player player;
	private int lastKeyPressed;
	


	private GameEngine() {
		tileList = new ArrayList<>();
	}


	public static GameEngine getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	/********************************* Inicio *********************************/
	public void start() {

		gui = ImageMatrixGUI.getInstance(); 
		String nome = gui.askUser("Username");
		if(nome == null)System.exit(0);
		player = new Player(nome, 0);
		
		String level = gui.askUser("Nível Inicial");
		if(level == null)System.exit(0);
		levelInit(Integer.valueOf(level));
		
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);
		gui.registerObserver(this); 
		gui.go(); 

		
	}
	
	public void levelInit(int i) {
		player.resetMoves();
		
		int levelNum = 0;
		if(level != null)
			levelNum = level.getLevelNumber();
		level = new Level(levelNum+i);
		
		tileList = level.getTileList();
		bobcat = level.getBobcat();
		sendImagesToGUI();
		
		gui.update();
		gui.setStatusMessage(String.format("Sokoban - %s - Energia:%3d%% - Moves: %3d", player.getName(), bobcat.getEnergia(), player.getMoves()));
		level.getTimer().start();
		update(null);
	}


	/**********************************Images and Updates************************************/
	@Override
	public void update(Observed source) {
		if(source!=null) {
			
			lastKeyPressed = gui.keyPressed();
			if(lastKeyPressed==27)System.exit(0);		//Tecla ESC - Para encerrar o Jogo
			if(lastKeyPressed==82)levelFailed();		//Tecla R   - Reiniciar o nivel
			if(lastKeyPressed==81) {					//Letra Q   - Escolher o nível
				String level = gui.askUser("Nível");
				if(level == null)System.exit(0);
				levelInit(Integer.valueOf(level));
			}	
			
			if(Direction.isDirection(lastKeyPressed))
				bobcat.move(Direction.directionFor(lastKeyPressed));
		}
		gui.setStatusMessage(String.format("Sokoban - %s - Energia:%3d%% - Moves: %3d - Tempo: %3d", player.getName(), bobcat.getEnergia(), player.getMoves(), level.getTimer().getSeconds()));
		gui.update(); 
	}

	
	private void sendImagesToGUI() {
		for(GameElement ge : tileList)
			gui.addImage(ge);
	}
	
	/*****************************GETTERS**********************************/
	public List<GameElement> getTileList() {return tileList;}
	public ImageMatrixGUI getGui() {return gui;}
	public Level getLevel() {return level;}
	public Player getPlayer() {return player;}
	public int getLastKeyPressed() {return lastKeyPressed;}
	
	
	/*******************************Level Methods*******************************/
	public void levelFailed(){
		level.getTimer().stop();
		gui.setMessage("Perdeste!");
		clearLevel(0);
	}

	public void win() {
		level.getTimer().stop();
		gui.setMessage("Ganhaste!");
		level.showHighScores();
		clearLevel(1);
	}
	
	public void clearLevel(int i) {
		tileList.clear();
		gui.clearImages();
		levelInit(i);
		}
}
