package cards;

import java.util.ArrayList;

import moteur.Card;


public class Estate extends Card
{
	/**
	 * @author Maluna
	 * Carte Estate
	 * 
	 */
	
	public Estate() {}
	
	public int getId()
	{
		return 4;
	}

	public String getName()
	{
		return "Domaine";
	}
	
	public int getFixedPrice()
	{
		return 2;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_VICTORY);
		
		return l;
	}
	
	
	public int victory()
	{
		return 1;
	}
}
