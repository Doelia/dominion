package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Councilroom extends Card
{
	/**
	 * @author Maluna
	 * Carte Council Room
	 * 
	 */
	
	public Councilroom() {}
	
	public int getId()
	{
		return 23;
	}

	public String getName()
	{
		return "Salle du conseil";
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
		p.drawNCard(4);				// +4 cartes.
		p.addBuys(1);				// +1 achat.
		
		for(Player play : p.getGame().getPlayers())
		{
			if(!play.equals(p))
				play.drawNCard(1);
		}
	}	
}