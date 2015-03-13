package moteur;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logs
{
	/**
	 * @param Partie g a décrire
	 * @action écrit dans un fichier log l'état complet de la partie (les cartes de chacun, etc).
	 * 
	 */
	
	public Logs()
	{
		createLog("etatPartie");
	}
	
	public void writeEtatPartie(Game g)
	{
		BufferedWriter f = getLog("etatPartie");
		if (f == null)
		{
			System.out.println("Erreur écriture fichier");
			return;
		}
		
		try
		{
			// TODO écrire l'état complet de la partie
			f.write("\n\n***** Tour n°" + g.getNTour() % g.getNombrePlayers()
					+ " - Actif : " + g.getNActif() + " *****\n\n");
			
			int i = 0;
			for(Player p : g.getPlayers())
			{
				f.write("Joueur " + i + " :\n");
				f.write("\n\tMain (" + p.getMain().count() + ") : ");
				for(Card c : p.getMain().cards())
					f.write(" - " + c.getId());
						
				f.write("\n\tDeck (" + p.getDeck().count() + ") : ");
				for(Card c : p.getDeck().cards())
					f.write(" - " + c.getId());
						
				f.write("\n\tDefausse (" + p.getDefausse().count() + ") : ");
				for(Card c : p.getDefausse().cards())
					f.write(" - " + c.getId());
								
				f.write("\n\tInPlay (" + p.getInPlay().count() + ") : ");
				for(Card c : p.getInPlay().cards())
					f.write(" - " + c.getId());
				
				f.write("\n\n");
				i++;
			}
			
			f.write("\nPiles Kingdoms :\n");
			for(Pile p : g.getKingdomPile())
			{
				if(!p.isEmpty())
					f.write("\t- " + p.cards().get(0).getId() + " (" + p.count() + ")\n");
			}
				
			f.write("\nPiles Base Cards :\n");
			for(Pile p : g.getOtherPile())
			{
				if(!p.isEmpty())
					f.write("\t- " + p.cards().get(0).getId() + " (" + p.count() + ")\n");
			}	
				
			f.flush();
			f.close();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @param nameFile
	 * @return Un flux d'entrée pour écrire du log
	 */
	private BufferedWriter getLog(String nameFile)
	{
		String name = "logs/"+nameFile+".txt";
		
		try
		{
			FileWriter fw = new FileWriter(name, true);
			BufferedWriter output = new BufferedWriter(fw);
			return output;
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void createLog(String nameFile)
	{
		String name = "logs/"+nameFile+".txt";
		
		try
		{
			new FileWriter(name);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void createDirLog()
	{
		new File("logs").mkdir();
	}
}
