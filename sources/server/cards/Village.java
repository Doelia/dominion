package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Village extends Card
{
	/**
	 * @author Maluna
	 * Carte Village
	 * 
	 */
	
	public Village() {}
	
	public int getId()
	{
		return 10;
	}

	public String getName()
	{
		return "Village";
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
		p.drawNCard(1);						// +1 carte.
		p.addActions(2);					// +2 actions.
	}	
}