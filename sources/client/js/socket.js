
var key;
var pseudo;

function connexion(pseudo)
{
	echo("Connexion...");

	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			echo("Key = "+xhr.responseText);
			echo("Connexion réalisée avec succès !");
			key = xhr.responseText;

			$('#connect').hide();
			
			send('Z');
			waitMsg();
		}
	}

	var query = "http://"+host+":"+port+"/_:NEW:"+pseudo;
	
	xhr.open("GET",query,true);
	xhr.send(null);
}

function waitMsg()
{
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			if (xhr.responseText != "OK")
				parsePacket(xhr.responseText);
			waitMsg();
		}
	}

	var query = "http://"+host+":"+port+"/_:"+key+":R";
	
	xhr.open("GET",query,true);
	xhr.send(null);
}

function send(msg)
{	
	echo("Send: "+msg);
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			if (xhr.responseText != '1')
				echo("Err send "+xhr.responseText);
		}
	}

	var query = "http://"+host+":"+port+"/_:"+key+":S:"+msg;
	xhr.open("GET",query,true);
	xhr.send(null);
}

function parsePacket(packet)
{
	var startTime = new Date().getTime();
	var elapsedTime = 0;

	if (packet.substr(0,2) == 'E-')
	{
		echo("Reçu état");
		onEtat(packet.substr(2,packet.length-1));
	}
	else if (packet.substr(0,2) == 'Z-')
	{
		echo("Reçu liste");
		onListe(packet.substr(2,packet.length-1));
	}
	else
	{
		echo ("Packet reçu : "+packet);
		if (packet.substr(0,9) == 'ToInPlay-')
		{
			deplaceCardToInPlay(
				main.getElementCardFromId(packet.substr(9,packet.length-1))
				);
		}
		else if (packet.substr(0,2) == 'L-')
		{
			addLog(packet.substr(2,packet.length-1));
		}
		else if (packet.substr(0,2) == 'B-')
		{
			onBuyCard(packet.substr(2,packet.length-1));
		}
		else if (packet.substr(0,2) == 'C-')
		{
			onPacketChoix(packet.substr(2,packet.length-1));
		}
	}
	

	elapsedTime = new Date().getTime() - startTime;
	//console.log("Temps de traitement du packet : "+elapsedTime+"ms");
}

function playCardFromMain(idCard)
{
	send("A-"+idCard);
}

function endTurn()
{
	send("P");
}

function buyCard(idCard)
{
	send("B-"+idCard);
}

