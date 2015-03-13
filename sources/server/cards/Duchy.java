package cards;

import java.util.ArrayList;

import moteur.Card;


public class Duchy extends Card
{
	/**
	 * @author Maluna
	 * Carte Duchy
	 * 
	 */
	
	public Duchy() {}
	
	public int getId()
	{
		return 5;
	}

	public String getName()
	{
		return "Duché";
	}
	
	public int getFixedPrice()
	{
		return 5;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_VICTORY);
		
		return l;
	}
	
	
	public int victory()
	{
		return 3;
	}
}
