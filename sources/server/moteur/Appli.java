package moteur;

import java.io.IOException;
import utils.Constants;
import java.net.UnknownHostException;
import java.util.ArrayList;

import parties.GameManager;

import server.ServerThread;


public class Appli implements Constants {
	
	/**
	 * @author doelia
	 * Application conteneuse du main
	 *
	 */
	
	public final static boolean DISPLAYDEBUG = true;
	public static ArrayList<Game> games;
	
	public static void echo(String s)
	{
		System.out.println(s);
	}

	public static void main(String[] args) throws UnknownHostException, IOException
	{
		Logs l = new Logs();
		l.createDirLog();
		
		System.out.println("-- Dominia project --");
		System.out.println("\tBy Doelia & Maluna");
		
		GameManager.getInstance().createGame(2);
		
		new ServerThread(HOST, PORT);
	}
	

}
