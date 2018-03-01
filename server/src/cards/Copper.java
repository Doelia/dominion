package cards;

import java.util.ArrayList;

import moteur.Card;


public class Copper extends Card
{
	/**
	 * @author Maluna
	 * Carte Copper
	 * 
	 */
	
	public Copper() {}
	
	public int getId()
	{
		return 1;
	}

	public String getName()
	{
		return "Cuivre";
	}
	
	public int getFixedPrice()
	{
		return 0;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_TREASURE);
		
		return l;
	}
	
	public int tresor()
	{
		return 1;
	}
}
