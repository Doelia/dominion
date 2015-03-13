package cards;

import java.util.ArrayList;

import moteur.Card;


public class Province extends Card
{
	/**
	 * @author Maluna
	 * Carte Province
	 * 
	 */
	
	public Province() {}
	
	public int getId()
	{
		return 6;
	}

	public String getName()
	{
		return "Province";
	}
	
	public int getFixedPrice()
	{
		return 8;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_VICTORY);
		
		return l;
	}
	
	
	public int victory()
	{
		return 6;
	}
}
