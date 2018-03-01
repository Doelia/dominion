package moteur;

import java.util.ArrayList;

import utils.Functions;

import cards.*;

	/**
	 * @author doelia
	 * Gére une partie
	 * 
	 */

public class Game extends Thread {

	public final static int COPPER = 0;
	public final static int SILVER = 1;
	public final static int GOLD = 2;
	public final static int ESTATE = 3;
	public final static int DUCHY = 4;
	public final static int PROVINCE = 5;
	public final static int CURSE = 6;
	
	private static int ai = 0; // d�part a 1
	
	private int _id;
	ArrayList<Player> _players; // Joueurs de la partie (2 à 4)
	int _actif; // Joueur à qui c'est le tour
	private int _nTour;
	
	Pile _trash; // Cimetières des cartes
	Pile[] _kingdomPile; // Les 10 piles d'achat
	Pile[] _otherPile; // Pile d'argent et victoires (3 + 3 + 1)
	
	private Log _logCommun;

	public Game(int nombreJoueurs)
	{
		if (Appli.DISPLAYDEBUG) Appli.echo("CREATION D'UNE GAME");
		
		_id = ++ai;
		_nTour = 1;
		
		_trash = new Pile();
		
		if (Appli.DISPLAYDEBUG) Appli.echo("Game #"+_id);
		
		// Création des 10 piles d'action vides
		_kingdomPile = new Pile[10];
		for (int i=0; i < _kingdomPile.length; i++) // Pour chaque pile
			_kingdomPile[i] = new Pile();
		
		// Création des 7 piles d'achats
		_otherPile = new Pile[7];
			for (int i=0; i < _otherPile.length; i++) // Pour chaque pile
				_otherPile[i] = new Pile();
		
		// Remplisage des piles
		createPiles();
		
		_logCommun = new Log();
		
		if (Appli.DISPLAYDEBUG) Appli.echo("Création des joueurs...");
		
		// Création des joueur
		_players = new ArrayList<Player>();
		
		for(int i = 0 ; i < nombreJoueurs ; i++)
		{
			String name = "NaN";
			switch (i)
			{
				case 0:
					name = "Player 1";
					break;
				case 1:
					name = "Player 2";
					break;
				case 2:
					name = "Player 3";
					break;
				case 3:
					name = "Player 4";
					break;
			}
			// Ajout du joueur à la liste.
			
			_players.add(new Player(name, this));
			_logCommun.addRecepteur(_players.get(i));
		}
		
		
		if (Appli.DISPLAYDEBUG) Appli.echo("Début du tour du joueur");
		
		_actif = 0;
		getActif().onStartTurn();
		
		if (Appli.DISPLAYDEBUG) Appli.echo("Fin création de la game");
	}
	
	public int getID() {
		return _id;
	}
	
	public ArrayList<Player> getPlayers() {
		return _players;
	}
	
	public int getNombrePlayers() {
		return _players.size();
	}
	
	public Pile[] getKingdomPile()
	{
		return _kingdomPile;
	}
	
	public Pile[] getOtherPile()
	{
		return _otherPile;
	}
	
	public Pile getTrash()
	{
		return _trash;
	}

	public int getNTour() {
		return _nTour;
	}

	
	/**
	 * @param Pile à remplir.
	 * @return Vrai si la pile kingdom existe déjà.
	 */
	private boolean existInstance(int ind, int id)
	{
		int i = 0;
		while(i < ind && id != _kingdomPile[i].cards().get(0).getId())
			i++;
		return i < ind;
	}
	
	/**
	 * @action Rempli les 19 piles d'achat
	 */
	public void createPiles()
	{
		if (Appli.DISPLAYDEBUG) Appli.echo("Création des piles...");
				
		for (int c=0; c < 10; c++) // 10 cartes à ajouter
		{
			int card;
			do
			{
				card = Functions.alea(8, 32);
			} while(existInstance(c, card));	// Tant que l'instance existe déjà.
			
			for(int i = 0 ; i < 10 ; i++)
				_kingdomPile[c].add(Card.getInstance(card));
		}
	
		for (int c=0; c < 10; c++)
		{
			_otherPile[COPPER]	.add(new Copper());
			_otherPile[SILVER]	.add(new Silver());
			_otherPile[GOLD]	.add(new Gold());
			_otherPile[ESTATE]	.add(new Estate());
			_otherPile[DUCHY]	.add(new Duchy());
			_otherPile[PROVINCE].add(new Province());
			_otherPile[CURSE]	.add(new Curse());
		}
		
		if (Appli.DISPLAYDEBUG) Appli.echo("\tPilé crées !");
	}
	
	/**
	 * @param n l'index de la pile
	 * @return La carte piochée
	 * 
	 */
	public Card getFirstOther(int n)
	{
		Card c = _otherPile[n].draw();
		if (_otherPile[n].count() == 0)
		{
			testIfTerminate();
		}
		return c;
	}
	
	/**
	 * @param n l'index de la pile dans les piles de kingdom cards.
	 * @return La carte piochée
	 * 
	 */
	public Card getAKingdom(int n)
	{
		Card c = _kingdomPile[n].draw();
		if (_kingdomPile[n].count() == 0)
		{
			testIfTerminate();
		}
		return c;
	}
	
	/**
	 * @param Pile d'achat.
	 * @return La carte piochée.
	 * 
	 */
	public Card getABuyCard(Pile p)
	{
		Card c = p.draw();
		if (p.count() == 0)
		{
			testIfTerminate();
		}
		return c;
	}
	
	
	/**
	 * @param n l'index de la pile dans les piles de base cards.
	 * @return La carte piochée
	 * 
	 */
	public Card getABaseCard(int n)
	{
		Card c = _otherPile[n].draw();
		if (_otherPile[n].count() == 0)
		{
			testIfTerminate();
		}
		return c;
	}

	/**
	 * @action Test si la partie est terminée
	 * @strategie Compte le nombre de piles vides
	 */
	private void testIfTerminate()
	{
		int nEmpty = 0;
		for (Pile p : _kingdomPile)
			if (p.count() == 0)
				nEmpty++;
		
		for (Pile p : _otherPile)
			if (p.count() == 0)
				nEmpty++;
		
		if (nEmpty >= 3)
		{
			onGameTerminated();
		}
	}
	
	public Player getActif()
	{
		return _players.get(_actif);
	}
	
	public int getNActif()
	{
		return _actif;
	}
	
	
	
	public int getNPlayer(Player p) 
	{
		int n = 0;
		for(Player j : getPlayers())
		{
			if(j.equals(p))
				return n;
			n++;
		}
		return -1;
	}
	
	
	/**
	 * @param : Carte à trasher.
	 * @action : Ajoute la carte c à la pile trash.
	 */
	public void addInTrash(Card c)
	{
		if (c != null)
			_trash.add(c);
	}
	
	/**
	 * @action Changer le joueur actif
	 * @æction Déclanche le tour du nouveau joueur actif
	 */
	public void onEndTurn()
	{
		changePlayer();
		getActif().onStartTurn();
	}
	
	private void changePlayer()
	{
		_actif++;
		if (_actif > _players.size() - 1)
			_actif = 0;
		
		if (Appli.DISPLAYDEBUG) Appli.echo("Joueur suivant");
		_nTour++;
	}
	
	/**
	 * @on Quand la partie se termine
	 */
	
	private void onGameTerminated()
	{
		if (Appli.DISPLAYDEBUG) Appli.echo("LA PARTIE EST TERMINEE");
		
		
		for(Player p : getPlayers())
			get_log().add(Log.T_COUNTVICTORIES, p, p.countVictories());
	}
	
	public String toString()
	{
		return "Game " + getId() + "(" + getPlayers().size() + " joueurs)";
	}
	
	public Pile getPileAchat(int idCard)
	{
		for(Pile p : getKingdomPile())
		{
			if(p.contains(idCard))
				return p;
		}
		
		for(Pile p : getOtherPile())
		{
			if(p.contains(idCard))
				return p;
		}
		return null;
	}
	
	/**
	 * Envoi l'état du jeu à tous les controleurs
	 */
	public void sendEtatJeu()
	{
		for (Player p : getPlayers())
		{
			if (p.getControleur() != null)
				p.getControleur().sendEtatJeu();
		}
		
	}
	
	public Log get_log()
	{
		return _logCommun;
	}
	
	public int getPlacesLibres()
	{
		int cpt = 0;
		for (Player p : _players)
			if (p.getControleur() == null)
				cpt++;
		return cpt;
	}
	
	
	public Player getPlacePlayer()
	{
		for (Player p : _players)
			if (p.getControleur() == null)
				return p;
		
		return null;
	}
	
	public boolean isIn(String pseudo)
	{
		for (Player p : _players)
			if (p.getControleur() != null && p.getControleur().get_pseudo().equals(pseudo))
				return true;
		
		return false;
	}
}
