package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Gardens extends Card
{
	/**
	 * @author Maluna
	 * Carte Gardens
	 * 
	 */
	
	public Gardens() {}
	
	public int getId()
	{
		return 25;
	}

	public String getName()
	{
		return "Jardins";
	}
	
	public int getFixedPrice()
	{
		return 4;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_VICTORY);
		
		return l;
	}
	
	public int victory(Player p)
	{
		return p.getDeck().count() / 10;
	}
}