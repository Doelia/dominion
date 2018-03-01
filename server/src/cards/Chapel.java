package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Chapel extends Card
{
	/**
	 * @author Maluna
	 * Carte Chapel
	 * 
	 */
	
	public Chapel() {}
	
	public int getId()
	{
		return 9;
	}

	public String getName()
	{
		return "Chapelle";
	}
	
	public int getFixedPrice()
	{
		return 2;
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
		liste = p.getControleur().needChooseCards(liste, 4);
		
		int i = 0;
		while(i < liste.size() && i < 4)		// Trash jusqu'à 4 cartes.
		{
			Card c = liste.get(i);
			p.getMain().draw(c.getId());
			p.trashCard(c);
			i++;
		}
	}	
}