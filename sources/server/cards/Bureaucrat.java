package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Game;
import moteur.Player;


public class Bureaucrat extends Card
{
	/**
	 * @author Maluna
	 * Carte Bureaucrat
	 * 
	 */
	
	public Bureaucrat() {}
	
	public int getId()
	{
		return 28;
	}

	public String getName()
	{
		return "Bureaucrate";
	}
	
	public int getFixedPrice()
	{
		return 4;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_ACTION);
		l.add(TYPE_ATTACK);
		
		return l;
	}
	
	public void attaque(Player lanceur, Player cible)
	{
		ArrayList<Card> victories = new ArrayList<Card>();
		for(Card c : cible.getMain().cards())
		{
			if(c.getTypes().contains(TYPE_VICTORY))
				victories.add(c);
		}
		
		if(!victories.isEmpty())		// Si le joueur possèdes des cartes victoires.
			victories = cible.getControleur().needChooseCards(victories, 1);
		
		if(!victories.isEmpty())
		{
			// TODO Révéler la carte.
			Card c = cible.getMain().draw(victories.get(0).getId());	// On retire la carte choisie de sa main.
			cible.getDeck().add(c, true);								// On la place sur le deck.
		}
		else
			; // TODO Révéler une main sans victories.
	}
	
	public void action(Player p)
	{
		Card c = p.getGame().getOtherPile()[Game.SILVER].draw();		// Gain carte Silver
		p.getDeck().add(c, true);										// Ajout sur le dessus du deck.
	}
}