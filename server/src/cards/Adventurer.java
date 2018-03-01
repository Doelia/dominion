package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Adventurer extends Card
{
	/**
	 * @author Maluna
	 * Carte Adventurer
	 * 
	 */
	
	public Adventurer() {}
	
	public int getId()
	{
		return 31;
	}

	public String getName()
	{
		return "Aventurier";
	}
	
	public int getFixedPrice()
	{
		return 6;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_ACTION);
		
		return l;
	}
	
	public void action(Player p)
	{
		int cptTresors = 0;
		ArrayList<Card> treasures = new ArrayList<Card>();
		ArrayList<Card> others = new ArrayList<Card>();
		do
		{
			if(p.getDeck().isEmpty())
				p.onDeckEmpty();
			
			// TODO Révéler la carte.
			Card c = p.getDeck().draw();
			if(c.getTypes().contains(TYPE_TREASURE))
			{
				treasures.add(c);
				cptTresors++;
			}
			else
				others.add(c);
			
		} while(cptTresors < 2);
		
		p.getMain().cards().addAll(treasures);			// On ajoute les trésors à la main.
		p.getDefausse().cards().addAll(others);			// On défausse les autres.
	}
}