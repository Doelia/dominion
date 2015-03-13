/* Fonctions rafraichisants l'interface */

function refreshTurn()
{
	
}

var refreshAll = true;
function onEtat(packet)
{
	$('#join').hide();
	$('#game').show();

	//echo(packet);
	xml = StringtoXML(packet);
	var racine = xml.documentElement;
	
	for (var i in racine.childNodes)
	{
		var e_i = racine.childNodes[i];
		if (e_i.nodeName == 'me')
		{
			idMe = e_i.attributes.getNamedItem("id").nodeValue;

			for (var j in e_i.childNodes)
			{
				var e_j = e_i.childNodes[j];

				if (e_j.nodeName == 'inplay')
				{
					inPlay.resetCards(e_j.childNodes);
				}
					
				if (e_j.nodeName == 'main')
					main.resetCards(e_j.childNodes);
					
				if (e_j.nodeName == 'kingdom')
					setKingdoms(e_j.childNodes);

				if (e_j.nodeName == 'other')
					setBases(e_j.childNodes);

				if (e_j.nodeName == 'defausse')
					setDefausse(
						e_j.attributes.getNamedItem("last").nodeValue,
						e_j.attributes.getNamedItem("n").nodeValue
					);

				if (e_j.nodeName == 'deck')
						setNbrCardDeck(e_j.attributes.getNamedItem("n").nodeValue);

				if (e_j.attributes && e_j.attributes.getNamedItem("value"))
					var valueNode = e_j.attributes.getNamedItem("value").nodeValue;
				
				if (e_j.nodeName == 'nachat')
					el('buys').childNodes[0].innerHTML = valueNode;
				if (e_j.nodeName == 'naction')
					el('actions').childNodes[0].innerHTML = valueNode;
				if (e_j.nodeName == 'nmoney')
					el('money').childNodes[0].innerHTML = valueNode;
			
			}
		}

		
		if (e_i.nodeName == 'logs')
		{
			el('logs_html').innerHTML = '';
			for (var j in e_i.childNodes)
			{
				var e_j = e_i.childNodes[j];
				if (e_j.nodeName == 'log')
				{
					addLog(e_j.attributes.getNamedItem("value").nodeValue)
				}
			}
		}

		if (e_i.nodeName == 'joueurs')
		{
			setPlayers(e_i.childNodes);
		}

		if (e_i.nodeName == 'nactif')
			setTurn(e_i.attributes.getNamedItem("value").nodeValue);
			
	}

	refreshAll = false;
}

function setKingdoms(xml)
{
	el('kingdomCards').innerHTML = '';
	for (var k in xml)
	{
		var e_k = xml[k];
		if (e_k.nodeName == 'card')
			addKingdom(e_k.attributes.getNamedItem("value").nodeValue,
			e_k.attributes.getNamedItem("n").nodeValue);
	}
}

function addKingdom(idCard, qte)
{
	el('kingdomCards').innerHTML +=
	'<div class="card">'+
		'<img src="cards/'+idCard+'.jpg" />'+
		'<div class="qte">'+qte+'</div>'+
	'</div>';
}

