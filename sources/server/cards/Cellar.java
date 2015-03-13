package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Cellar extends Card
{
	/**
	 * @author Maluna
	 * Carte Cellar
	 * 
	 */
	
	public Cellar() {}
	
	public int getId()
	{
		return 8;
	}

	public String getName()
	{
		return "Cave";
	}
	
	public int getFixedPrice()
	{
		return 2;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_ACTION);
		
		return l;
	}

	
	public void action(Player p)
	{
		p.addActions(1);					// +1 action.
			
		ArrayList<Card> liste = p.getMain().cards();
		liste = p.getControleur().needChooseCards(liste, p.getMain().count());
		for(Card c : liste)
		{
			p.getMain().draw(c.getId());	// Pour chaque carte défaussée,
			p.discard(c);					
		}
											
		p.drawNCard(liste.size());			// On pioche une carte.			
	}
}