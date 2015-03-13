package moteur;

import java.util.ArrayList;

import cards.*;

public class Player 
{		
	/**
	 * @author Maluna
	 * Représente un joueur.
	 * 
	 */
	
	public final static int PHASE_ACTION = 1;
	public final static int PHASE_MONEY = 2;
	public final static int PHASE_BUY = 3;
	
	/**		Attributs 		**/
	private String _name;		// Nom du joueur.
	
	private Pile _main;
	private Pile _deck;
	private Pile _defausse;
	private Pile _inPlay;	// Cartes en cours d'action.
	
	private Game _g;		// Partie en cours.
	
	private int _phase;
	private int _buysDispos;		// Nombre d'achats dispos pour le tour actuel.
	private int _actionsDispos;		// Nombre d'actions.
	private int _moneyDispo;		// Argent du joueur pendant son tour.
	
	private Controleur _c;
	private Log _log;
	
	/**		Constructeurs	**/
	
	
	/**
	 * Constructeur par défaut.
	 * @param : Nom du joueur 
	 * 			Partie du joueur.
	 */
	public Player(String name, Game g)
	{
		if(Appli.DISPLAYDEBUG) Appli.echo("Création joueur");
		
		_name = name;
		
		_main = new Pile();		// Initialisation des Piles à vide.
		_deck = new Pile();
		_defausse = new Pile();
		_inPlay = new Pile();
		
		_g = g;
		
		_buysDispos = 0;
		_actionsDispos = 0;
		_moneyDispo = 0;
		
		_log = new Log();
		
		// Chaque joueur commence avec 7 copper et 3 estate.
		for(int j = 0 ; j < 7 ; j++)
			ajoutDefausse(new Copper());	
		for(int j = 0 ; j < 3 ; j++)
			ajoutDefausse(new Estate());
		
		// Création du deck et préparation du prochain tour.
		onDeckEmpty();
		prepareTurn();
		
		_c = null;
		
		if(Appli.DISPLAYDEBUG) Appli.echo("Joueur créé");
	}
	
	
	/**		Méthodes	**/
	
	/** Getters **/
	public String getName() {
		return _name;
	}
	public Pile getMain() {
		return _main;
	}
	public Pile getDeck() {
		return _deck;
	}
	public Pile getDefausse() {
		return _defausse;
	}
	public Pile getInPlay() {
		return _inPlay;
	}
	public Game getGame() {
		return _g;
	}
	public int getPhase() {
		return _phase;
	}
	public int getBuysDispos() {
		return _buysDispos;
	}
	public int getActionsDispos() {
		return _actionsDispos;
	}
	public int getMoneyDispo() {
		return _moneyDispo;
	}
	public Controleur getControleur() {
		return _c;
	}
	public Log getLog() {
		return _log;
	}
	
	/** Setters	**/
	public void setPhase(int phase) {
		_phase = phase;
	}
	public void setBuysDispos(int buysDispos) {
		_buysDispos = buysDispos;
	}
	public void setActionsDispos(int actionsDispos) {
		_actionsDispos = actionsDispos;
	}
	public void setMoneyDispo(int moneyDispo) {
		_moneyDispo = moneyDispo;
	}
	public void set_c(Controleur c)
	{
		_name = c.get_pseudo();
		_c = c;
	}
	
	/** 
	 * @return : String état Joueur.
	 */
	public String toString()
	{
		return "Joueur " + getName() + " (Partie " + getGame()
				+ ") : Achats=" + getBuysDispos() + " Actions=" + getActionsDispos() + " Money=" + getMoneyDispo();
	}
	
	public int getN()
	{
		return getGame().getNPlayer(this);
	}
	
	/**
	 * @return true si le joueur est le joueur actif.
	 */
	private boolean myTurn()
	{
		return (getGame().getActif() == this);
	}
	
	/**
	 * @param : Nombre d'actions à ajouter.
	 * @action : Ajoute n actions au joueur.
	 */
	public void addActions(int n)
	{
		setActionsDispos(getActionsDispos() + n);
	}
	
	/**
	 * @param : Nombre d'achats à ajouter.
	 * @action : Ajoute n achats au joueur.
	 */
	public void addBuys(int n)
	{
		setBuysDispos(getBuysDispos() + n);
	}
	
	/**
	 * @param : Nombre d'argent à ajouter.
	 * @action : Ajoute n argent au joueur.
	 */
	public void addMoney(int n)
	{
		setMoneyDispo(getMoneyDispo() + n);
	}
	
	/**
	 * @action : Pioche la première carte du deck et l'ajoute à la main.
	 */
	private void drawOneCard()
	{
		if(getDeck().count() <= 0)
			onDeckEmpty();
		
		Card c = getDeck().draw();
		if (c != null)
		{
			getMain().add(c);  // Ajout de la carte piochée dans la main.
			getLog().add(Log.T_DRAW_PERSO, c);
		}
	}
	
	/**
	 * @param Nombre de cartes à piocher.
	 * @action Pioche n cartes dans le deck et les ajoute à la main.
	 */
	public void drawNCard(int n)
	{
		_g.get_log().add(Log.T_DRAW, this, n);
		for(int i = 0 ; i < n ; i++)
			drawOneCard();
	}
	
	/**
	 * @prerequis Pile non nulle.
	 * @param Pile p.
	 * @action Pioche une carte dans la pile d'achat correspondante
	 * 		   et la met à la défausse.
	 * @return Carte gagnée (pour la méthode buyCard).
	 */
	public Card gainCard(Pile p)
	{
		Card c = getGame().getABuyCard(p);		// Pioche dans la pile d'achat.
		ajoutDefausse(c);
		return c;
	}
	
	/**
	 * @prerequis Pile achat non nulle.
	 * @param  Pile d'achat.
	 * @action Achète une carte de la pile index.
	 */
	public void buyCard(Pile p)
	{
		Card c = gainCard(p);
		
		if (c != null)
		{
			addBuys(-1);								// Décrémentation des achats dispos.
			addMoney(-(c.getCurrentPrice()));			// Retrait argent.
			
			_g.get_log().add(Log.T_BUYCARD, this, c);
		}
	}

	/**
	 * @param Id carte à acheter.
	 * @action Teste si l'achat est possible, si oui exécute la méthode buyCard.
	 * @verif Si le joueur est le joueur actif.
	 * 		  Si getMoney() >= cout achat.
	 * 		  Si getAchatsDispos() >= 1
	 * 		  Si la pile existe.
	 * 		  S'il reste des cartes dans la pile.
	 */
	public boolean onBuyCard(int idCard)
	{
		Pile p;
		
		if(!myTurn())
			return false;
		
		if(getBuysDispos() < 1)
			return false;
		
		if((p = getGame().getPileAchat(idCard)) == null)
			return false;
		
		if(p.getACard(idCard).getCurrentPrice() > getMoneyDispo())
			return false;
		
		buyCard(p);
		
		return true;
	}
	
	/**
	 * @param Carte à défausser.
	 * @action Mets la carte passée en paramètre à la défausse.
	 */
	public void ajoutDefausse(Card c)
	{
		getDefausse().add(c);
	}
	
	/**
	 * @param Carte à défausser.
	 * @action Défausse une carte.
	 */
	public void discard(Card c)
	{	
		_g.get_log().add(Log.T_DISCARD, this, c);
		
		ajoutDefausse(c);
	}
	
	/**
	 * @action Defausse toutes les cartes de la main du joueur.
	 */
	public void defausseMain()
	{
		_g.get_log().add(Log.T_DISCARDHAND, this);
		
		getDefausse().add(getMain());
	}
	
	public Card getLastCardDefausse()
	{
		ArrayList<Card> defausse = getDefausse().cards();
		if(defausse.size() > 0)
			return defausse.get(defausse.size() - 1);
		else
			return null;
	}
	
	/**
	 * @param Carte à jeter.
	 * @action Mets la carte passée en paramètre dans la pile trash.
	 */
	public void trashCard(Card c)
	{
		_g.get_log().add(Log.T_TRASHCARD, this, c);
		
		getGame().addInTrash(c);
	}
	
	
	/**
	 * @prerequis Carte type attaque.
	 * @param Carte Attaque à appliquer à chaque joueur.
	 * @action Lance une attaque à chaque joueur. 
	 */
	public void lauchAttack(Card c)
	{
		for(Player p : getGame().getPlayers())
		{
			if(c.attackIncludeLanceur() || !p.equals(this))
			{
				// TODO La reaction doit être révélée.
				if(!p.getControleur().needChoice("Voulez-vous jouer une carte réaction ?"))
					c.attaque(this, p);
				else
				{
					// On cherche les cartes réactions.
					ArrayList<Card> reactions = new ArrayList<Card>();
					for(Card card : p.getMain().cards())
					{
						if(card.getTypes().contains(Card.TYPE_REACTION))
							reactions.add(card);
					}
					
					int idCard = 0;
					if(!reactions.isEmpty())
					{
						reactions = p.getControleur().needChooseCards(reactions, 1);
						if(!reactions.isEmpty())
						{
							reactions.get(0).reaction();		// Réaction.
							idCard = reactions.get(0).getId();
						}
					}
					
					if(idCard != 32)	// Si ce n'est pas les douves.
						c.attaque(this, p);
				}
			}
		}
	}
	
	private boolean canPlay(Card c, int phase)
	{
		// TODO Terminer phase Money si 1 AchatsDispos == 0.
		return (phase == PHASE_ACTION && getActionsDispos() > 0 
				&& c.getTypes().contains(Card.TYPE_ACTION))
			|| ((phase == PHASE_MONEY || phase == PHASE_ACTION) 
					&& c.getTypes().contains(Card.TYPE_TREASURE));
	}
	
	/**
	 * @prerequis Carte non nulle (test dans le controlleur).
	 * @param Id carte à jouer.
	 * @action Joue la carte c en la mettant dans la pile inPlay.
	 * @verif Si joueur est le joueur actif.
	 */
	public void onPlayCard(int idCard)
	{
		if(!myTurn())
			return;
		
		Card cardPlay = getMain().getACard(idCard);
		if (cardPlay == null)
		{
			getControleur().send("ER_A_2_card null");
			return;
		}
		
		if(canPlay(cardPlay, getPhase()))
		{			
			_g.get_log().add(Log.T_PLAYCARD, this, cardPlay);
			
			getInPlay().add(getMain().draw(idCard));
			
			if(cardPlay.getTypes().contains(Card.TYPE_ACTION) && getActionsDispos() > 0)
			{
				addActions(-1);
				cardPlay.action(this);
				
				if(cardPlay.getTypes().contains(Card.TYPE_ATTACK))
					lauchAttack(cardPlay);
			}
			else if(cardPlay.getTypes().contains(Card.TYPE_TREASURE))
			{
				setPhase(PHASE_MONEY);
				addMoney(cardPlay.tresor());
			}
			
			if(getActionsDispos() < 1)
				setPhase(PHASE_MONEY);

			getControleur().sendEtatJeu();
		}
	}
	
	
	/**
	 * @prerequis Deck vide.
	 * @action Reforme le deck avec les cartes de la défausse.
	 */
	public void onDeckEmpty()
	{
		getDefausse().shuffle();
		getDeck().add(getDefausse());
	}
	
	
	/**
	 * @action Effectue toutes les actions de début de tour.
	 * 		   Initialise les achats dispos et les actions dispos à 1.
	 */
	public void onStartTurn()
	{
		setBuysDispos(1);
		setActionsDispos(1);
		setMoneyDispo(0);
		
		setPhase(PHASE_ACTION);
		
		_g.get_log().add(Log.T_STARTTURN, this);
		
		if (_c != null)
			_c.prevenirDebutTour();
	}
	
	
	/**
	 * Fin de tour déclenchée par le joueur.
	 * @action  Appelle la méthode de la classe Game terminant le tour du joueur.
	 */
	public void endTurn()
	{
		onEndTurn();
		getControleur().abandonnerChoix();
	}
	
	
	
	/**
	 * @action Effectue toutes les actions de fin de tour.
	 * 		   Defausse la main et pioche 5 nouvelles cartes.
	 */
	public void onEndTurn()
	{
		if(!myTurn())
			return;
		
		// On met les cartes jouées dans la défausse
		getDefausse().add(getInPlay());
		
		prepareTurn();
		_g.get_log().add(Log.T_ENDTURN, this);
			
		getGame().onEndTurn();			// Prévient le serveur que le tour est terminé.
	}
	
	/**
	 * @action toutes les procédures nécessaires à un nouveau tour
	 */
	public void prepareTurn()
	{
		defausseMain();
		drawNCard(5);
	}


	/**
	 * @prerequis : Partie terminée.
	 * @return : Nombre de cartes victoires dans le deck du joueur.
	 */
	public int countVictories()
	{	
		int sum = 0;
		for(Card c : this.getDefausse().cards())
			sum += c.victory();
		
		return sum;
	}
	
	
}
