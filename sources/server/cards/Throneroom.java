package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Throneroom extends Card
{
	/**
	 * @author Maluna
	 * Carte Throne Room
	 * 
	 */
	
	public Throneroom() {}
	
	public int getId()
	{
		return 22;
	}

	public String getName()
	{
		return "Salle du trône";
	}
	
	public int getFixedPrice()
	{
		return 4;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_ACTION);
		
		return l;
	}
	
	
	public void action(Player p)
	{			
		ArrayList<Card> listeActions = new ArrayList<Card>();
		for(Card c : p.getMain().cards())
		{
			if(c.getTypes().contains(TYPE_ACTION))	// Si c'est une carte action.
				listeActions.add(c);
		}
		listeActions = p.getControleur().needChooseCards(listeActions, 1);
		
		if(!listeActions.isEmpty())
		{
			Card c = p.getMain().draw(listeActions.get(0).getId());
			p.getInPlay().add(c);
			
			for(int i = 0 ; i < 2 ; i++)		// On joue la carte action deux fois.
			{
				c.action(p);
				if(c.getTypes().contains(Card.TYPE_ATTACK))
					p.lauchAttack(c);
			}
		}
	}	
}