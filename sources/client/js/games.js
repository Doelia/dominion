

// gére la liste des parties


function onListe(packet)
{
	console.log("liste : "+packet);
	$('#game').hide();
	$('#join').show();

	$('#join .liste').html('');

	var parts = packet.split('|');

	for (var i in parts)
	{
		if (parts[i] == '')
			break;

		var parts2 = parts[i].split(',');

		var idPartie = parts2[0];
		var nbrJoueurs = parts2[1];
		var imIn = (parts2[2] == '1');
		var nbrPlace = parts2[3];
		
		$('#join .liste')
			.append('<div class="partie"></div>');
		;

		$('#join .liste .partie:last')
			.append('<div class="num">'+idPartie+'</div>')
			.append('<div class="nbrJoueurs"> '+(nbrJoueurs - nbrPlace)+'/'+nbrJoueurs+'</div>')
		;

		if (imIn)
		$('#join .liste .partie:last')
			.append('<div class="votrePartie"></div>')

		if (imIn || (nbrPlace > 0))
		$('#join .liste .partie:last')
			.append('<div class="input '+(imIn?'isIn':'')+'" onclick="send(\'J-'+parts2[0]+'\')"></div>');
	}

}

