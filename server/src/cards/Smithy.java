package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Smithy extends Card
{
	/**
	 * @author Maluna
	 * Carte Smithy
	 * 
	 */
	
	public Smithy() {}
	
	public int getId()
	{
		return 11;
	}

	public String getName()
	{
		return "Forge";
	}
	
	public int getFixedPrice()
	{
		return 4;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_ACTION);
		
		return l;
	}
	
	
	public void action(Player p)
	{
		p.drawNCard(3);					// +3 cartes.
	}	
}