package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Mine extends Card
{
	/**
	 * @author Maluna
	 * Carte Mine
	 * 
	 */
	
	public Mine() {}
	
	public int getId()
	{
		return 30;
	}

	public String getName()
	{
		return "Mine";
	}
	
	public int getFixedPrice()
	{
		return 5;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_ACTION);
		
		return l;
	}
	
	public void action(Player p)
	{
		ArrayList<Card> treasures = new ArrayList<Card>();
		for(Card c : p.getMain().cards())
		{
			if(c.getTypes().contains(TYPE_TREASURE))
				treasures.add(c);
		}
		
		// Si le joueur possède au moins un trésor.
		if(!treasures.isEmpty())
		{
			// Il choisit la carte à trash.
			treasures = p.getControleur().needChooseCards(treasures, 1);
			
			if(!treasures.isEmpty())
			{
				Card c = p.getMain().draw(treasures.get(0).getId());
				p.trashCard(c);
				
				p.getMain().add(p.getControleur().needChooseTreasureCard(c.getCurrentPrice() + 3));
			}
		}
	}
}