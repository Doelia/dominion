package moteur;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author doelia
 * Gére un ensemble de cartes
 */

public class Pile {
	
	ArrayList<Card> _cards;
	
	public Pile()
	{
		_cards = new ArrayList<Card>();
	}
	
	/**
	 * @action Ajoute la carte c à la fin de la pile
	 */
	public void add(Card c)
	{
		add(c, false);
	}
	
	/**
	 * @param atFirst, false si on veut ajouter la carte à la fin, true sinon
	 */
	public void add(Card c, boolean atFirst)
	{
		if (!atFirst)
			_cards.add(c); // Ajout à la fin
		else
			_cards.add(0, c);
	}
	
	/**
	 * @param Une pile de carte
	 * @action 	Déplace toutes les cartes de la pile reçu à la fin de la pile actuelle
	 */
	public void add(Pile p)
	{
		_cards.addAll(p._cards);
		p.clear();
	}
	
	/**
	 * @param Id carte à retirer.
	 * @return Retire de la pile la carte idCard s'il la trouve.
	 */
	public Card draw(int idCard)
	{
		if(count() == 0)
			return null;
		
		for(Card c : _cards)
		{
			if(c.getId() == idCard)
			{
				_cards.remove(c);
				return  c;
			}
		}
		return null;
	}
	
	/**
	 * @return La première carte de la pile, null si la pile est vide
	 * @action Supprime la première carte de la pile
	 */
	public Card draw()
	{
		if (count() == 0)
			return null;
		
		Card c = _cards.get(0); // On récupére la carte avant de la supprimer
		_cards.remove(0); // Suppresion de la carte
		
		return c;
	}
	
	/**
	 * @return le nombre de carte dans la pile
	 */
	public int count()
	{
		return _cards.size();
	}
	
	/**
	 * @action Vide la pile
	 */
	private void clear()
	{
		_cards = new ArrayList<Card>();
	}
	
	/**
	 * @param Carte c
	 * @return true si la pile contient la carte
	 */
	public boolean contains(Card c)
	{
		return _cards.contains(c);
	}
	
	/**
	 * @param Int id carte
	 * @return true si la pile contient la carte
	 */
	public boolean contains(int idCard)
	{
		for(Card c : _cards)
		{
			if(c.getId() == idCard)
				return true;
		}
		return false;
	}
	
	/**
	 *  @action Mélange les cartes
	 */
	public void shuffle()
	{
		Collections.shuffle(_cards);
	}
	
	public ArrayList<Card> cards()
	{
		return _cards;
	}
	
	/**
	 * @param displayCards à true si on veut afficher les cartes
	 */
	public String toString(boolean displayCards)
	{
		String s = "Pile de "+count()+" cartes\n";
		
		if (displayCards)
			for (Card c : cards())
			{
				s += "\t\t"+c+"\n";
			}
		
		return s;
	}
	
	public String toString()
	{
		return toString(false);
	}
	
	/**
	 * @param id de la carte
	 * @return Objet carte s'il elle existe, null sinon.
	 */
	public Card getACard(int id)
	{
		for(Card c : _cards)
		{
			if(c.getId() == id)
				return c;
		}
		return null;
	}

	public boolean isEmpty() {
		return (count() <= 0);
	}
	
}
