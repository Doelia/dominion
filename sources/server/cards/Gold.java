package cards;

import java.util.ArrayList;

import moteur.Card;


public class Gold extends Card
{
	/**
	 * @author Maluna
	 * Carte Gold
	 * 
	 */
	
	public Gold() {}
	
	public int getId()
	{
		return 3;
	}

	public String getName()
	{
		return "Or";
	}
	
	public int getFixedPrice()
	{
		return 6;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_TREASURE);
		
		return l;
	}
	
	public int tresor()
	{
		return 3;
	}
}
