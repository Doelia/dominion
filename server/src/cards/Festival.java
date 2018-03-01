package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Festival extends Card
{
	/**
	 * @author Maluna
	 * Carte Festival
	 * 
	 */
	
	public Festival() {}
	
	public int getId()
	{
		return 17;
	}

	public String getName()
	{
		return "Festival";
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
		p.addActions(2);				// +2 actions.
		p.addBuys(1);					// +1 achat.
		p.addMoney(2);					// +2 pièces.
	}	
}