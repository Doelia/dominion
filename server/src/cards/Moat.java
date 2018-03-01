package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Moat extends Card
{
	/**
	 * @author Maluna
	 * Carte Moat
	 * 
	 */
	
	public Moat() {}
	
	public int getId()
	{
		return 32;
	}

	public String getName()
	{
		return "Douves";
	}
	
	public int getFixedPrice()
	{
		return 2;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_ACTION);
		l.add(TYPE_REACTION);
		
		return l;
	}
	
	public void reaction()
	{
		// Annule l'attaque.
	}
	
	public void action(Player p)
	{
		p.drawNCard(2);		// +2 cartes.
	}
}