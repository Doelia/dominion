package parties;

import java.util.ArrayList;

import moteur.Controleur;
import moteur.Game;

public class GameManager {

	private static GameManager instance;
	public static GameManager getInstance()
	{
		if (instance == null)
			instance = new GameManager();
		
		return instance;
	}
	
	private ArrayList<Game> _games;
	
	private GameManager()
	{
		_games = new ArrayList<Game>();
	}
	
	public void createGame(int nombreJoueur)
	{
		_games.add(new Game(nombreJoueur));
	}
	
	public void listGames(Controleur c)
	{
		
	}

	public ArrayList<Game> getGames() {
		return _games;
	}

	
	public String getPacketGames(Controleur controleur) {
		String s = "";
		for (Game g : getGames())
		{
			s += g.getID()+","+g.getNombrePlayers()+","+(g.isIn(controleur.get_pseudo())?1:0)+","+g.getPlacesLibres()+"|";
		}
		return s;
	}
	
	
}
