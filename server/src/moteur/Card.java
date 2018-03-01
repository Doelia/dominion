package moteur;


import java.util.ArrayList;
import cards.*;


abstract public class Card {
	
	/**
	 * @author Doelia
	 * @author Maluna
	 * Classe abstraite d'une carte
	 * 
	 */
	
	public final static int TYPE_ACTION = 1;
	public final static int TYPE_TREASURE = 2;
	public final static int TYPE_VICTORY = 3;
	public final static int TYPE_CURSE = 4;
	public final static int TYPE_ATTACK = 5;
	public final static int TYPE_REACTION = 6;
	
	
	/**
	 * @return Identifiant unique propre à la carte
	 */
	abstract public int		getId();	
	
	
	/**
	 * @return Nom complet de la carte (affichage)
	 */
	abstract public String	getName();	
	
	
	/**
	 * @return Prix inscrit sur la carte.
	 */
	abstract public int getFixedPrice();
	
	
	/**
	 * @return Les types de la carte (Utiliser les constantes)
	 */
	abstract public ArrayList<Integer> getTypes();
	
	
	/**
	 * @return Prix actuel de la carte.
	 */
	public int getCurrentPrice()			
	{
		return getFixedPrice();
	}
	
	/**
	 * @return Vrai si l'attaque de la carte affecte aussi le lanceur.
	 */
	public boolean attackIncludeLanceur()
	{
		return false;
	}
	
	
	/**
	 * @action Effectue une attaque.
	 */
	public void attaque(Player lanceur, Player cible)
	{
		return;
	}
	
	
	/**
	 * @action Effectue une réaction face à une attaque.
	 */
	public void reaction()
	{
		return;
	}
	
	
	/**
	 * @action Effectue les actions.
	 */
	public void action(Player p) 		
	{
		return;
	}
	
	
	public int tresor()
	{
		return 0;
	}
	
	
	/**
	 * @return Nombre de points victoires d'une carte.
	 */
	public int victory()			
	{
		return 0;
	}
	
	
	/**
	 * @return : String infos carte.
	 */
	public String toString()
	{
		return "Carte " + getName() + " (" + getId() 
				+ ") Prix fixe=" + getFixedPrice() + " Prix=" + getCurrentPrice()
				+ " // Vict=" + victory();
	}
	
	
	public static Card getInstance(int ind)
	{
		switch(ind)
		{
			case 1: return new Copper();
			case 2: return new Silver();
			case 3: return new Gold();
			case 4: return new Estate();
			case 5: return new Duchy();
			case 6: return new Province();
			case 7: return new Curse();
			case 8: return new Cellar();
			case 9: return new Chapel();
			case 10: return new Village();
			case 11: return new Smithy();
			case 12: return new Laboratory();
			case 13: return new Witch();
			case 14: return new Market();
			case 15: return new Woodcutter();
			case 16: return new Chancellor();
			case 17: return new Festival();
			case 18: return new Workshop();
			case 19: return new Feast();
			case 20: return new Moneylender();
			case 21: return new Remodel();
			case 22: return new Throneroom();
			case 23: return new Councilroom();
			case 24: return new Library();
			case 25: return new Gardens();
			case 26: return new Militia();
			case 27: return new Spy();
			case 28: return new Bureaucrat();
			case 29: return new Thief();
			case 30: return new Mine();
			case 31: return new Adventurer();
			case 32: return new Moat();
			default: return null;
		}
	}
}
