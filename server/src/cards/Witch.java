package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Game;
import moteur.Player;


public class Witch extends Card
{
	/**
	 * @author Maluna
	 * Carte Witch
	 * 
	 */
	
	public Witch() {}
	
	public int getId()
	{
		return 13;
	}

	public String getName()
	{
		return "Sorcière";
	}
	
	public int getFixedPrice()
	{
		return 5;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_ACTION);
		l.add(TYPE_ATTACK);
		
		return l;
	}
	
	public void attaque(Player lanceur, Player cible)
	{
		// Gain curse card.
		cible.gainCard(lanceur.getGame().getOtherPile()[Game.CURSE]);
	}
	
	public void action(Player p)
	{
		p.drawNCard(2);					// +2 cartes.
	}	
}