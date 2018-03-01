package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Laboratory extends Card
{
	/**
	 * @author Maluna
	 * Carte Laboratory
	 * 
	 */
	
	public Laboratory() {}
	
	public int getId()
	{
		return 12;
	}

	public String getName()
	{
		return "Laboratoire";
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
		p.drawNCard(2);							// +2 cartes.
		p.addActions(1);						// +1 action.
	}	
}