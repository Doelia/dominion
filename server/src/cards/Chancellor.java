package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Chancellor extends Card
{
	/**
	 * @author Maluna
	 * Carte Chancellor
	 * 
	 */
	
	public Chancellor() {}
	
	public int getId()
	{
		return 16;
	}

	public String getName()
	{
		return "Chancelier";
	}
	
	public int getFixedPrice()
	{
		return 3;
	}
	
	public ArrayList<Integer> getTypes()
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(TYPE_ACTION);
		
		return l;
	}
	
	
	public void action(Player p)
	{
		p.addMoney(2);						// +2 pièces.
		
		if(p.getControleur().needChoice("Voulez-vous défaussez votre deck ?"))
			p.getDefausse().add(p.getDeck());	// Défausse l'ensemble du deck.
	}	
}