package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Moneylender extends Card
{
	/**
	 * @author Maluna
	 * Carte Moneylender
	 * 
	 */
	
	public Moneylender() {}
	
	public int getId()
	{
		return 20;
	}

	public String getName()
	{
		return "Prêteur";
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
		int i = 0;
		// Tant que l'on ne trouve pas de cuivre (id = 1).
		while(i < p.getMain().count() && p.getMain().cards().get(i).getId() != 1)
			i++;
			
		if(i < p.getMain().count()) // Si on a trouvé un cuivre.
		{
			if(p.getControleur().needChoice("Voulez-vous trah un cuivre ? (Si oui +3 monnaie)"))
			{
				p.trashCard(p.getMain().draw(1));		// On pioche un cuivre (id 1) et on le trash.
				p.addMoney(3);							// +3 monnaie si fait.
			}
		}
	}	
}