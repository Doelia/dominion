package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Library extends Card
{
	/**
	 * @author Maluna
	 * Carte Library
	 * 
	 */
	
	public Library() {}
	
	public int getId()
	{
		return 24;
	}

	public String getName()
	{
		return "Bibliothèque";
	}
	
	public int getFixedPrice()
	{
		return 5;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_ACTION);
		
		return l;
	}
	
	
	public void action(Player p)
	{
		ArrayList<Card> cote = new ArrayList<Card>();
		
		// On pioche jusqu'à ce que l'on ait 7 cartes en main.
		for(int i = p.getMain().count() ; i < 7 ; i++)
		{
			if(p.getDeck().isEmpty())
				p.onDeckEmpty();
			
			Card c = p.getDeck().draw();
			if(c.getTypes().contains(TYPE_ACTION))		// Si c'est une carte action
			{
				if(p.getControleur().needChoice("Mettre de cote ?"))	// On demande au joueur s'il veut la mettre de côté.
					cote.add(c);
				else
					p.getMain().add(c);
			}
			else
				p.getMain().add(c);
		}
		p.getDefausse().cards().addAll(cote);
	}	
}