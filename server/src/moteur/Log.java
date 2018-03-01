package moteur;

import java.util.ArrayList;

class Log
{	
	public static final String T_BUYCARD = "# achète la carte #";
	public static final String T_DISCARD = "# défausse la carte #";
	public static final String T_DISCARDHAND = "# défausse sa main";
	public static final String T_TRASHCARD = "# trash la carte #";
	public static final String T_PLAYCARD = "# joue la carte #";
	public static final String T_STARTTURN = "# commence son tour";
	public static final String T_DRAW = "# pioche # carte(s)";
	public static final String T_ENDTURN = "# finit son tour";
	public static final String T_COUNTVICTORIES = "# a # points victoires";
	
	
	public static final String T_DRAW_PERSO = "Vous piochez un(e) #";
	
	
	private ArrayList<String> _lines;
	private ArrayList<Player> _recepteurs;
	
	public Log()
	{
		_lines = new ArrayList<String>();
		_recepteurs = new ArrayList<Player>();
	}
	
	public ArrayList<String> getLines()
	{
		return _lines;
	}
	
	public ArrayList<Player> getRecepteurs()
	{
		return _recepteurs;
	}
	
	public void add(String template, Player p)
	{
		add(template, p.getName(), "");
	}
	
	public void add(String template, Card c)
	{
		add(template, c.getName(), "");
	}
	
	public void add(String template, Player p, Card c)
	{
		add(template, p.getName(), c.getName());
	}
	
	public void add(String template, Player p, int n)
	{
		add(template, p.getName(), new String(n + ""));
	}
	
	public void add(String template, String arg0, String arg1)
	{
		String s = template;
		
		s = s.replaceFirst("#", arg0);
		s = s.replaceFirst("#", arg1);
		
		System.out.println("[ GAME LOG ] "+s);
		_lines.add(s);
		
		for(Player p : getRecepteurs())
		{
			if (p.getControleur() != null)
				p.getControleur().sendLineLog(s);
		}
	}
	
	
	public void addRecepteur(Player p)
	{
		_recepteurs.add(p);
	}
}