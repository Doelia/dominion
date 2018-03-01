package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Remodel extends Card
{
	/**
	 * @author Maluna
	 * Carte Remodel
	 * 
	 */
	
	public Remodel() {}
	
	public int getId()
	{
		return 21;
	}

	public String getName()
	{
		return "Rénovation";
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
		ArrayList<Card> liste = p.getMain().cards();
		liste = p.getControleur().needChooseCards(liste, 1);
		
		if(!liste.isEmpty())
		{
			Card c = p.getMain().draw(liste.get(0).getId());
			p.trashCard(c);
			p.ajoutDefausse(p.getControleur().needChooseAActionCard(c.getCurrentPrice() + 2));
		}
	}	
}