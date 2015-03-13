package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Spy extends Card
{
	/**
	 * @author Maluna
	 * Carte Spy
	 * 
	 */
	
	public Spy() {}
	
	public int getId()
	{
		return 27;
	}

	public String getName()
	{
		return "Espion";
	}
	
	public int getFixedPrice()
	{
		return 4;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_ACTION);
		l.add(TYPE_ATTACK);
		
		return l;
	}
	
	public boolean attackIncludeLanceur()
	{
		return true;
	}
	
	public void attaque(Player lanceur, Player cible)
	{
		if(cible.getDeck().isEmpty())
			cible.onDeckEmpty();
		
		// TODO Révéler la carte.
		Card c;
		if((c = cible.getDeck().draw()) != null)
		{
			// On demande au lanceur ce qu'il veut faire de la carte.
			if(lanceur.getControleur().needChoice("Voulez-vous défausser la carte ?"))
				cible.ajoutDefausse(c);				// On défausse la carte.
			else
				cible.getDeck().add(c, true);		// On la remet sur le deck.
		}
	}
	
	public void action(Player p)
	{
		p.drawNCard(1);		// +1 carte.
		p.addActions(1);	// +1 action
	}
}