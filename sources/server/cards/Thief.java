package cards;

import java.util.ArrayList;

import moteur.Card;
import moteur.Player;


public class Thief extends Card
{
	/**
	 * @author Maluna
	 * Carte Thief
	 * 
	 */
	
	public Thief() {}
	
	public int getId()
	{
		return 29;
	}

	public String getName()
	{
		return "Voleur";
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
		ArrayList<Card> treasures = new ArrayList<Card>();
		ArrayList<Card> others = new ArrayList<Card>();
		
		// Chaque joueur révèle les deux premières cartes de son deck.
		for(int i = 0 ; i < 2 ; i++)
		{
			if(cible.getDeck().isEmpty())
				cible.onDeckEmpty();
			
			Card c = cible.getDeck().draw();
			if(c.getTypes().contains(TYPE_TREASURE))		// Si c'est un trésor on ajoute dans la liste correspondante.
				treasures.add(c);
			else
				others.add(c);
		}
		
		// S'il y a au moins 1 ou plusieurs trésors, le lanceur en choisit un à trash.
		ArrayList<Card> choix = new ArrayList<Card>();
		if(treasures.size() == 1)			/** 1 trésor **/
			choix.add(treasures.get(0));		// La carte est automatiquement choisie.
		else if(treasures.size() > 1)		/** 2 trésors **/
		{
			// Si il y a deux trésors, le lanceur choisit celui à trash.
			choix = lanceur.getControleur().needChooseCards(treasures, 1);
			if(!choix.isEmpty())		// Si on a récupéré le choix.
			{
				for(Card c : treasures)
				{
					// Si ç'est la carte choisie, on la supprime de la liste.
					if(c.getId() == choix.get(0).getId())
						treasures.remove(c);
				}
			}
			cible.ajoutDefausse(treasures.get(0)); // On défausse la 2e.
		}
		
		if(!choix.isEmpty())
		{
			Card c = choix.get(0);
		
			if(lanceur.getControleur().needChoice("Voulez-vous récupérer la carte ?"))
				lanceur.ajoutDefausse(c);		// On gagne la carte.
			else
				cible.trashCard(c);			// Sinon elle est trash.
		}
		
		for(Card c : others)			// Les autres cartes vont à la défausse.
			cible.ajoutDefausse(c);
	}
}