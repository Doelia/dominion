package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Workshop extends Card
{
	/**
	 * @author Maluna
	 * Carte Workshop
	 * 
	 */
	
	public Workshop() {}
	
	public int getId()
	{
		return 18;
	}

	public String getName()
	{
		return "Atelier";
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
		// Gain carte coutant 4 au max, ajoutée à la défausse.
		p.ajoutDefausse(p.getControleur().needChooseAActionCard(4));
	}	
}