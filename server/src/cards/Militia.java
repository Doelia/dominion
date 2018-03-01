package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Militia extends Card
{
	/**
	 * @author Maluna
	 * Carte Militia
	 * 
	 */
	
	public Militia() {}
	
	public int getId()
	{
		return 26;
	}

	public String getName()
	{
		return "Milice";
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
	
	public void attaque(Player lanceur, Player cible)
	{
		while(cible.getMain().count() > 3)
		{
			ArrayList<Card> rep = cible.getControleur().needChooseCards(cible.getMain().cards(), 1);
			if(!rep.isEmpty())
				cible.discard(cible.getMain().draw(rep.get(0).getId()));	// On retire la carte de la main
																			// et on la défausse.
		}
	}
	
	public void action(Player p)
	{
		p.addMoney(2);			// +2 monnaie
	}
}