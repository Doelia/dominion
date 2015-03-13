package server;

public class Reponse {

	public Client client;
	public String m;
	
	public Reponse(String p_m)
	{
		client = null;
		m = p_m;
	}

	public Reponse(String p_m, Client p_client)
	{
		client = p_client;
		m = p_m;
	}
	
}