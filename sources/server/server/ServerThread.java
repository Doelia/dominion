package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import moteur.Controleur;

public class ServerThread extends Thread
{
	public ArrayList<Controleur> _clients; // Liste des clients cr�s
	ServerSocket _serverSocket; // Socket serveur HTTP
	
	public ServerThread(String host, int port) throws UnknownHostException, IOException
	{
		_clients = new ArrayList<Controleur>();
		_serverSocket = new ServerSocket (port, 10, InetAddress.getByName(host));
		System.out.println("En attente de connexion sur "+InetAddress.getByName(host)+":"+port);
		this.run();
	}
	
	public ArrayList<Controleur> get_clients() {
		return _clients;
	}
	
	/**
	 * @param key priv� du client
	 * @return Le client correspodant � la cl� envoy�, null si pas trouv�
	 */
	public Controleur getClient(String key)
	{
		for (Controleur c : _clients)
			if (c.getKey().equals(key))
				return c;
		return null;
	}
	
	public Controleur getControleurFromPseudo(String pseudo)
	{
		for (Controleur c : _clients)
		{
			System.out.println(c.get_pseudo()+" vs "+pseudo);
			if (c.get_pseudo().equals(pseudo))
				return c;
		}
		return null;
	}
	
	/**
	 * @return Reference vers le client cr�e
	 * @ction Cr�e un nouveau client (avec sa cl�)
	 */
	public Controleur createClient(String pseudo)
	{
		Controleur c = new Controleur(this, pseudo);
		_clients.add(c);
		return c;
	}
	
	/**
	 * @param le packet envoy�
	 * @return La r�ponse � retourner au client
	 * @on Quand un client envoi une requette HTTP (GET)
	 */
	public Reponse onPacket(String packet, InetAddress ip)
	{
		/*
		 * Structure d'un packet :
		 * [1] : doit valoir "_" (Protol LIA)
		 * [2] : key private client
		 * [3] : R (recv) ou S (send)
		 * [4] : Le packet si [3] vaut S
		 */
		
		if (packet.split("/").length == 0)
			return new Reponse("/ manquant");
		
		packet = packet.split("/")[1];
		
		String[] ex = packet.split(":");
		
		if (ex.length <= 1)
			return new Reponse("Pas assez de parametres");
		
		if (!ex[0].equals("_"))
		{
			return new Reponse("Protocole non respect�");
		}
		
		// Demande de nouevau client
		if (ex[1].equals("NEW") && ex.length == 3)
		{
			Controleur c = getControleurFromPseudo(ex[2]);
			
			if (c == null)
				c = createClient(ex[2]);
			
			return new Reponse(c.getKey());
		}
		
		// Recherche du client
		Controleur c = getClient(ex[1]);
		if (c == null)
		{
			// Client introuvable !
			return new Reponse("Client introuvable");
		}
		
		if (ex.length <= 2)
			return new Reponse("Pas assez de parametres");
		
		if (ex[2].equals("R"))
		{
			String msg = c.getMessage();
			//System.out.println("server >> "+msg+" >> "+c.getKey());
			
			return new Reponse(msg, c);
		}
		else if (ex[2].equals("S"))
		{
			if (ex.length <= 3)
				return new Reponse("Pas de message");
			
			c.onMessage(ex[3]);
			return new Reponse("1");
		}
		
		return new Reponse("Erreur inconnue");
		
	}
	
	
	public void run()
	{
		try
		{
			while(true)
			{      	   	      	
				Socket connected = _serverSocket.accept();
	            new WebConnexion(connected, this).start();
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}