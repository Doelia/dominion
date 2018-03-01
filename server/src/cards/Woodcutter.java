package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Woodcutter extends Card
{
	/**
	 * @author Maluna
	 * Carte Woodcutter
	 * 
	 */
	
	public Woodcutter() {}
	
	public int getId()
	{
		return 15;
	}

	public String getName()
	{
		return "Bûcheron";
	}
	
	public int getFixedPrice()
	{
		return 3;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_ACTION);
		
		return l;
	}
	
	public void action(Player p)
	{
		p.addBuys(1);						// +1 achat.
		p.addMoney(2);						// +2 pièces.
	}	
}