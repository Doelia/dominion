package utils;

import java.util.Random;

/**
 * @author doelia
 * Boite à outils de fonctions diverses
 */


abstract public class Functions {

	private static Random generateur = new Random();
	
	/**
	 * @param min
	 * @param max
	 * @return Un nombre aléatoire compris entre min et max
	 */
	public static int alea(int min, int max)
	{
		int n = generateur.nextInt(max-min);
		n += min; // Ajout offset
		return n;
	}
	
	public static int toInteger( String input )
	{
	   try
	   {
	      return Integer.parseInt( input );
	   }
	   catch( Exception e)
	   {
	      return -1;
	   }
	}
	
	public static String deleteLastChar(String s)
	{
		return s.substring(0, s.length() - 1);
	}
}
