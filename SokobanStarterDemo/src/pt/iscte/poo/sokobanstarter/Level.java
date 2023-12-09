package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.sokobanstarter.elements.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import pt.iscte.poo.utils.Point2D;

public class Level {
	private List<GameElement> tileList;
	private Empilhadora bobcat;
	private ImageMatrixGUI gui; 
	private Timer timer;
	private final int TIME_LEFT = 20;
	
	private List<Player> highScores = new ArrayList<Player>();
	
	private int levelNumber;
	
	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
		tileList = new ArrayList<>();
		createLevel();
		gui = GameEngine.getInstance().getGui();
		timer = new Timer(TIME_LEFT);
	}
	
	public int getLevelNumber() {
		return levelNumber;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public List<GameElement> getTileList() {
		return tileList;
	}
	
	public Empilhadora getBobcat() {
		return bobcat;
	}
	
	public void createLevel() {
		try {
			Scanner sc = new Scanner(new File("levels/level"+getLevelNumber()+".txt"));
			int y = 0;
			while (sc.hasNext()) {
				String line = sc.nextLine();
				for(int x = 0; x<line.length();x++) {
					switch (line.charAt(x)) {
					case '#':
						tileList.add(new Parede(new Point2D(x,y)));
						break;
					case 'X':
						tileList.add(new Alvo(new Point2D(x,y)));
						break;
					case 'C':
						tileList.add(new Chao(new Point2D(x,y)));
						tileList.add(new Caixote(new Point2D(x,y)));
						break;
					case 'E':
						bobcat = new Empilhadora(new Point2D(x,y));
						tileList.add(new Chao(new Point2D(x,y)));
						tileList.add(bobcat);
						break;
					case 'B':
						tileList.add(new Chao(new Point2D(x,y)));
						tileList.add(new Bateria(new Point2D(x,y)));
						break;
					case '=':
						tileList.add(new Vazio(new Point2D(x,y)));
						break;
					case 'T':
						tileList.add(new Chao(new Point2D(x,y)));
						tileList.add(new Teleporte(new Point2D(x,y)));
						break;
					case ' ':
						tileList.add(new Chao(new Point2D(x,y)));
						break;
					case 'O':
						tileList.add(new Buraco(new Point2D(x,y)));
						break;
					case 'P':
						tileList.add(new Chao(new Point2D(x,y)));
						tileList.add(new Palete(new Point2D(x,y)));
						break;
					case 'M':
						tileList.add(new Chao(new Point2D(x,y)));
						tileList.add(new Martelo(new Point2D(x,y)));
						break;
					case '%':
						tileList.add(new Chao(new Point2D(x,y)));
						tileList.add(new ParedeRachada(new Point2D(x,y)));
					}
				}
				y++;
			}
			
			sc.close();
			} catch (FileNotFoundException e) {
				System.exit(0);
		}
	}
	
	
	private void createHighScoreList() {
		try {
			Scanner sc = new Scanner(new File("HighScoresOfLevel"+getLevelNumber()+".txt"));
			sc.nextLine();
			while(sc.hasNextLine()) {
				highScores.add(new Player(sc.nextLine()));
			}
		} catch (FileNotFoundException e) {
			gui.setMessage("Ficheiro dos HighScores não encontrado");
		} catch (NoSuchElementException e) {}
	}
	
	public void showHighScores() {
		createHighScoreList();
		highScores.add(GameEngine.getInstance().getPlayer());
		Collections.sort(highScores);
		try {
			File file = new File("HighScoresOfLevel"+getLevelNumber()+".txt");
			PrintWriter myWriter = new PrintWriter(file);
			
			String str = "HIGHSCORES\n";
			myWriter.println("HIGHSCORES");
			
			for (int i = 0; i<3 ; i++)
				if(i<highScores.size()) {
					str += highScores.get(i).getName()+"===>"+highScores.get(i).getMoves()+"\n";
					myWriter.println(highScores.get(i).toString());
				}
			myWriter.close();
			gui.setMessage(str);
			} catch (Exception e) {
				System.out.println("An error occurred.");
				System.exit(1);
		}
	}
	
}
