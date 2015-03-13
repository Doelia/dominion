package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Market extends Card
{
	/**
	 * @author Maluna
	 * Carte Market
	 * 
	 */
	
	public Market() {}
	
	public int getId()
	{
		return 14;
	}

	public String getName()
	{
		return "Marché";
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
		p.drawNCard(1);						// +1 carte.
		p.addActions(1);					// +1 action.
		p.addBuys(1);						// +1 achat.
		p.addMoney(1);						// +1 pièce.
	}	
}