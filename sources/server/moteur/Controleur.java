package moteur;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import parties.GameManager;

import server.Client;
import server.ServerThread;
import utils.Functions;

public class Controleur extends Client
{
	Semaphore _choix;
	String _choixFait;
	
	private Player _p;
	
	public Game game()
	{
		return _p.getGame();
	}
	
	public Player getPlayer() {
		return _p;
	}
	
	public Controleur(ServerThread server, String pseudo)
	{
		super(server, pseudo);
		System.out.println("Nouveau controleur !");
		_p = null;
		_choix = new Semaphore(0, true);
	}
	
	@Override
	public void onMessage(String s)
	{
		System.out.println("server << "+s);
		String[] args = s.split("-");
		
		if (args[0].equals("J")) // Join [J-nGame]
		{
			if (args.length <= 1)
			{
				send("ER_J_1_parametre manquant");
				return;
			}
			
			for (Game g : GameManager.getInstance().getGames())
			{
				if (g.getID() == Functions.toInteger(args[1]))
				{
					if (g.isIn(get_pseudo()))
					{
						send("OK");
						sendEtatJeuAndLogs();
					}
					else
					{
						
						Player pPlace= g.getPlacePlayer();
						if (pPlace != null)
						{
							setPlayer(pPlace);
							send("OK");
							sendEtatJeuAndLogs();
						}
						else
						{
							send("ER_J_2_pas de place");
							return;
						}
					}
				}
			}
			
			send("ER_J_4_Game non trouve");
			return;
				
		}
		
		if (args[0].equals("Z")) // Demande la liste des parties
		{
			System.out.println("On demande la liste");
			send("Z-"+GameManager.getInstance().getPacketGames(this));
			return;
		}
		
		if (args[0].equals("N")) // Cr�er une partie
		{
			if (args.length < 1)
				return;
			
			try {
				int nbrPlayers = Integer.parseInt(args[1]);
				GameManager.getInstance().createGame(nbrPlayers);
				send("Z-"+GameManager.getInstance().getPacketGames(this));
				return;
			}
			catch (NumberFormatException e)
			{ }
			
			send("ER_N_1_Nombre de joueur invalide");
		}
		
		if (args[0].equals("C")) // Choix C-packetChoix
		{
			if (args.length <= 1)
			{
				send("ER_C_1_parametre manquant");
				return;
			}
			
			_choixFait = args[1];
			System.out.println("Choix fait = "+_choixFait);
			_choix.release();
			
			return;
		}
		
		if (args[0].equals("A")) // Jouer carte A-idCartJouee
		{
			if (args.length <= 1)
			{
				send("ER_A_1_parametre manquant");
				return;
			}
		
			int idCarteJouee = Functions.toInteger(args[1]);
			
			_p.onPlayCard(idCarteJouee);
			_p.getGame().sendEtatJeu();
			
			return;
		}
		
		if (args[0].equals("P")) // passer tour
		{
			_p.onEndTurn();
			send("OK");
			_p.getGame().sendEtatJeu();
			return;
		}
		
		if (args[0].equals("B")) // Acheter une carte [B-idCard]
		{
			if (args.length <= 1)
			{
				send("ER_B_1_parametre manquant");
				return;
			}
			
			int idCard = Functions.toInteger(args[1]);
			
			if (_p.onBuyCard(idCard))
				send("B-"+idCard);
			
			_p.getGame().sendEtatJeu();
			
			return;
		}
		
		if (_p != null)
			_p.getGame().sendEtatJeu();
		
		send("ER_0_parsePacket");
		return;
	}
	
	/**
	 * @action Envoi l'état complet du jeu
	 */
	public void sendEtatJeu()
	{
		send("E-" + getPacket(false));
	}
	
	public void sendEtatJeuAndLogs()
	{
		send("E-" + getPacket(true));
	}
	
	public void sendLineLog(String l)
	{
		send("L-"+l);
	}
	
	public String getPacket(boolean withLogs)
	{	
		//withLogs = true;
		//long start = System.currentTimeMillis();
		
		try {
			FileWriter file = new FileWriter("packet.xml", false);
			BufferedWriter output = new BufferedWriter(file);
			Card c;
			
			output.write("<game id=\"" + this.game().getId() + "\">\n\t");
						
			output.write("<me id=\"" + getPlayer().getN() + "\">\n\t\t");
	
			// Cartes de la main.
			output.write("<main>\n\t\t");
			for(Card card : getPlayer().getMain().cards())
			{
				output.write("\t<card value=\"" + card.getId() + "\" />\n\t\t");
			}
			output.write("</main>\n\t\t");
					
					
			// Nombre achats, nombre actions, nombre money.
			output.write("<nachat value=\"" + getPlayer().getBuysDispos() + "\" />\n\t\t"
						+ "<naction value=\"" + getPlayer().getActionsDispos() + "\" />\n\t\t"
						+ "<nmoney value=\"" + getPlayer().getMoneyDispo() + "\" />\n\t\t");
					
			
			// Cartes inPlay
			output.write("<inplay>\n\t\t");
			for(Card card : getPlayer().getInPlay().cards())
			{
				output.write("\t<card value=\"" + card.getId() + "\" />\n\t\t");
			}
			output.write("</inplay>\n\t\t");
			
			
			// Nombre cartes defausse + dernière carte.
			output.write("<defausse n=\"" + getPlayer().getDefausse().count() + "\" last=\"");
			if((c = getPlayer().getLastCardDefausse()) != null)
				output.write(c.getId()+"");
			else
				output.write("0");
			output.write("\" />\n\t\t");
			
			// Nombre cartes deck.
			output.write("<deck n=\"" + getPlayer().getDeck().count() + "\" />\n\t\t");
			
			// Kingdom
			output.write("<kingdom>\n\t\t");
			for(Pile p : getPlayer().getGame().getKingdomPile())
			{
				if(!p.isEmpty())
					output.write("\t<card value=\"" + p.cards().get(0).getId() + "\" n=\"" + p.count() + "\" />\n\t\t");
			}
			output.write("</kingdom>\n\t\t");
				
			// Other
			output.write("<other>\n\t\t");
			for(Pile p : getPlayer().getGame().getOtherPile())
			{
				if(!p.isEmpty())
					output.write("\t<card value=\"" + p.cards().get(0).getId() + "\" n=\"" + p.count() + "\" />\n\t\t");
			}
			output.write("</other>\n\t");
			output.write("</me>\n");
			
			// Autres joueurs.
			output.write("\t<joueurs>\n\t\t");
			
			int id = 0;
			for(Player p : getPlayer().getGame().getPlayers())
			{
				if(!p.equals(getPlayer()))
				{
					output.write("\t<joueur id=\"" + id + "\" name=\""+p.getName()+"\">\n\t\t");
					output.write("<main n=\"" + p.getMain().count() + "\" />\n\t\t");
					output.write("<deck n=\"" + p.getDeck().count() + "\" />\n\t\t");
					output.write("<defausse n=\"" + p.getDefausse().count() + "\" last=\"");
							
					if((c = p.getLastCardDefausse()) != null)
						output.write(c.getId()+"");
					else
						output.write("0");
					output.write("\" />\n\t</joueur>\n");
				}
				id++;
			}
			output.write("\t</joueurs>\n\t\t");
			
			output.write("<nactif value=\"" + game().getNActif() + "\" />\n\t");
			
			if (withLogs)
			{
				output.write("<logs>\n\t");
				ArrayList<String> logs = getPlayer().getGame().get_log().getLines();
				for(String s : logs)
					output.write("\t<log value=\"" + s + "\" />\n\t");
				output.write("</logs>\n");
			}
			
			output.write("</game>\n");
			output.flush();
			
			BufferedInputStream in = new BufferedInputStream(new FileInputStream("packet.xml"));
		    StringWriter out = new StringWriter();
		    int b;
		    while ((b=in.read()) != -1)
		        out.write(b);
		    out.flush();
		    out.close();
		    in.close();
		    file.close();
		    output.close();
		    
		    //long end = System.currentTimeMillis();
		    //System.out.println("Temps ms paquet:"); 
		    //System.out.println(end - start); 
		    
		    return out.toString();
		
		} catch (IOException e) {
				e.printStackTrace();
		}
		return "";
	}

	/**
	 * @param Joueur controlé
	 * @action Définit le joueur controlé par le controleur (dans les deux sens)
	 */
	private void setPlayer(Player p)
	{
		p.set_c(this);
		_p = p;
	}
	
	/**
	 * @action Libère le controleur : Il ne controle plus personne
	 * @action Envoi un packet au controleur pour le prévenir
	 */
	public void liberer()
	{
		_p = null;
		send("L");
	}
	
	@Override
	public String toString()
	{
		return getKey();
	}
	
	public void prevenirDebutTour()
	{
		_p.getGame().sendEtatJeu();
		send("S");
	}
	
	/**
	 * @action Attends que le controleur envoi son choix
	 * @return Le choix du controleur
	 */
	public String waitChoix()
	{
		try
		{
			_choix.acquire();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return _choixFait;
	}
	
	/**
	 * @param coutMax de la carte action
	 * @return La carte piochée de la pile choisie par le controleur
	 */
	public Card needChooseAActionCard(int coutMax)
	{	
		send("C-1-"+coutMax+"_(envoyer num pile <= coutMax)");
		
		String choixFait = waitChoix();
		
		int numPile = Functions.toInteger(choixFait);
		
		Card c = game().getAKingdom(numPile);
		
		if (c == null)
		{
			send("ER_C_A_1_Card null");
			return needChooseAActionCard(coutMax);
		}
		
		if (c.getCurrentPrice() > coutMax)
		{
			send("ER_C_A_2_trop chere");
			return needChooseAActionCard(coutMax);
		}
		
		send("OK");
		return c;
	}
	
	
	/**
	 * @param question
	 * @return Choix.
	 * @action Demande une réponse oui/non à la question passée en paramètres.
	 */
	public boolean needChoice(String question)
	{
		send("C-4-" + question + "_(repondre par oui ou non)");
		String choixFait = waitChoix();
		
		return choixFait.equals("o");
	}
	
	
	public Card needChooseTreasureCard(int coutMax)
	{
		send("C-1-"+coutMax+"_(envoyer num pile <= coutMax)");
		
		String choixFait = waitChoix();
		
		int numPile = Functions.toInteger(choixFait);
		Card c = game().getABaseCard(numPile);
		
		if(c == null)
		{
			send("ER_C_A_1_Card null");
			return needChooseTreasureCard(coutMax);
		}
		
		if (c.getCurrentPrice() > coutMax)
		{
			send("ER_C_A_2_trop chere");
			return needChooseTreasureCard(coutMax);
		}
		if(!c.getTypes().contains(Card.TYPE_TREASURE))
		{
			send("ER_C_A_3_Pas tresor");
			return needChooseTreasureCard(coutMax);
		}
			
		
		send("OK");
		return c;
	}
	
	/**
	 * @param Une liste de cartes proposées
	 * @return Une liste de cartes choisis
	 * @action Demande au controleur de choisir 0, 1 ou plusieurs cartes parmis les cartes envoyées
	 */
	public ArrayList<Card> needChooseCards(ArrayList<Card> choix, int maximum)
	{	
		String listCards = "";
		for (Card c : choix)
			listCards += c.getId()+",";
		
		if (listCards.length() >= 2)
			listCards = Functions.deleteLastChar(listCards);
		
		if(maximum > 0)
			send("C-3-"+maximum+"-"+listCards+"_(envoyer num parmis liste avec maximum)");
		else
			send("C-2-"+listCards+"_(envoyer num parmis liste)");
		
		String choixFait = waitChoix();
		ArrayList<Card> cardsChoisies = new ArrayList<Card>();
		
		for (String numChoisis : choixFait.split("| su"))
		{
			int n = Functions.toInteger(numChoisis);
			if (n < choix.size() && n >= 0)
			{
				System.out.println("Carte choisis : "+n);
				System.out.println(choix.get(n));
				cardsChoisies.add(choix.get(n));
			}
		}
		
		System.out.println("Fin de la boucle");
		return cardsChoisies;
	}

	public void abandonnerChoix() {
		_choix.release();
	}
}
