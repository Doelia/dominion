package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Feast extends Card
{
	/**
	 * @author Maluna
	 * Carte Feast
	 * 
	 */
	
	public Feast() {}
	
	public int getId()
	{
		return 19;
	}

	public String getName()
	{
		return "Festin";
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
		// Trash this card.
		p.trashCard(p.getInPlay().draw(getId()));
		
		// Gain carte coutant 5 au max.
		p.ajoutDefausse(p.getControleur().needChooseAActionCard(5));
	}	
}