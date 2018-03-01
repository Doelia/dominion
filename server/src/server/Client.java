package server;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import utils.Functions;

public class Client
{
	private Semaphore _waitMessage; // Attends qu'un message arrive
	private Semaphore _feuVert; // Peut recevoir un message (pour attendre que le pr�cedent soit bien envoy�)
	
	private ArrayList<String> _buffer; // Buffer des messages � envoyer
	
	@SuppressWarnings("unused")
	private ServerThread _server; // Serveur g�rant ce client
	
	private String _key; // Cl� priv� au client
	private String _pseudo;
	
	public Client(ServerThread server, String pseudo)
	{
		_key = "K"+Functions.alea(10000, 99999);
		
		_waitMessage = new Semaphore(0, true);
		_feuVert = new Semaphore(1, true);
		
		_buffer = new ArrayList<String>();
		_server = server;
		
		_pseudo = pseudo;
	}
	
	public String get_pseudo() {
		return _pseudo;
	}
	
	/**
	 * @param s le message
	 * @ction Ajoute dans le buffer un message � envoyer au client
	 */
	public void send(String s)
	{
		if (s.charAt(0) != 'E')
			System.out.println("Server >> "+s);
		
		_buffer.add(s);
		_waitMessage.release();
	}
	
	/**
	 * @on Quand le client envoi un message
	 */
	public void onMessage(String s)
	{
		
	}

	/**
	 * @return la cl� priv� du client
	 */
	public String getKey() {
		return _key;
	}
	
	/**
	 * @return La semaphore d'attente d'un message
	 */
	private Semaphore get_waitMessage() {
		return _waitMessage;
	}
	
	/**
	 * @return Un message
	 * @info Attends qu'un message arrive
	 * @strategie Semaphore
	 */
	public String getMessage()
	{
		try
		{
			attendreFeuVert(); // Attendre le feu vert pour recevoir un message
			get_waitMessage().acquire(); // Attendre un message
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		return _buffer.get(0);
	}
	
	/**
	 * Attends le feu vert pour pouvoir attendre un message
	 */
	public void attendreFeuVert()
	{
		try {
			_feuVert.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param reussi � true si le client � re�u le message
	 * callback d'un envoi de message, on r�envoi le message s'il n'a pas r�u
	 */
	public void retour(boolean reussi)
	{
		if (!reussi)
		{
			_waitMessage.release(); // On réenvoi
		}
		else
		{
			_buffer.remove(0);
		}
		
		_feuVert.release();
	}
}