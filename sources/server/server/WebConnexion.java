package server;

/*
 * myHTTPServer.java
 * Author: S.Prasanna
 * @version 1.00 
*/

import java.io.*;
import java.net.*;
import java.util.*;

public class WebConnexion extends Thread {
	
	Socket connectedClient = null;	
	BufferedReader inFromClient = null;
	DataOutputStream outToClient = null;
	
	ServerThread _server;
			
	public WebConnexion(Socket client, ServerThread s) {
		connectedClient = client;
		_server = s;
	}			
			
	public void run() {
		
		try {
		
			//InetAddress adresseIp = connectedClient.getInetAddress();
			//int port = connectedClient.getPort();
			inFromClient = new BufferedReader(new InputStreamReader (connectedClient.getInputStream()));
			outToClient = new DataOutputStream(connectedClient.getOutputStream());
			InetAddress ip = connectedClient.getInetAddress();
			
			String requestString = inFromClient.readLine();
            String headerLine = requestString;
            	if (headerLine == null) return;
            	
            StringTokenizer tokenizer = new StringTokenizer(headerLine);
			String httpMethod = tokenizer.nextToken();
			String httpQueryString = tokenizer.nextToken();
				
			if (httpMethod.equals("GET"))
			{
				Reponse reponse = _server.onPacket(httpQueryString, ip);
				sendResponse(200, reponse);
			}
			else
				sendResponse(200, new Reponse("0"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void sendResponse (int statusCode, Reponse reponse)  {
		
		String statusLine = null;
		String serverdetails = "Server: Java HTTPServer";
		String contentLengthLine = null;
		String contentTypeLine = "Content-Type: text/html" + "\r\n";
		
		if (statusCode == 200)
			statusLine = "HTTP/1.1 200 OK" + "\r\n";
		else
			statusLine = "HTTP/1.1 404 Not Found" + "\r\n";	
			
		contentLengthLine = "Content-Length: " + reponse.m.length() + "\r\n";	
		
		try {
			outToClient.writeBytes(statusLine);
		
			outToClient.writeBytes(serverdetails);
			outToClient.writeBytes(contentTypeLine);
			outToClient.writeBytes(contentLengthLine);
			outToClient.writeBytes("Connection: close\r\n");
			outToClient.writeBytes("Access-Control-Allow-Origin: *\r\n");
			
			outToClient.writeBytes("\r\n");		
			
			outToClient.writeBytes(reponse.m);
			
			outToClient.close();
			
			if (reponse.client != null)
				reponse.client.retour(true);
		
		}
		catch (SocketException e)
		{
			if (reponse.client != null)
				reponse.client.retour(false);
		}
		catch (IOException e)
		{
			if (reponse.client != null)
				reponse.client.retour(false);
		}
		
	}
	
}