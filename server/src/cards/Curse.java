package cards;

import java.util.ArrayList;

import moteur.Card;


public class Curse extends Card
{
	/**
	 * @author Maluna
	 * Carte Curse
	 * 
	 */
	
	public Curse() {}
	
	public int getId()
	{
		return 7;
	}

	public String getName()
	{
		return "Malédiction";
	}
	
	public int getFixedPrice()
	{
		return 0;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_CURSE);
		
		return l;
	}
	
	
	public int victory()
	{
		return -1;
	}
}
