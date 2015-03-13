package cards;

import java.util.ArrayList;

import moteur.Card;


public class Silver extends Card
{
	/**
	 * @author Maluna
	 * Carte Silver
	 * 
	 */
	
	public Silver() {}
	
	public int getId()
	{
		return 2;
	}

	public String getName()
	{
		return "Argent";
	}
	
	public int getFixedPrice()
	{
		return 3;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_TREASURE);
		
		return l;
	}
	
	public int tresor()
	{
		return 2;
	}
}
