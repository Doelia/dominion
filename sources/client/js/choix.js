function onPacketChoix(packetChoix)
{
	// packetChoix sans le C-

	/*
	Types de choix :
		2-1,1,4,9_(envoyer num parmis liste)
		3-4-1,1,9,9_(envoyer num parmis liste avec maximum) 

	*/

	$('#choix_content').html('');

	switch (packetChoix[0])
	{
		

		case '1': // Choisir une pile d'achat

			var html = "Choissisez une pile d'achat : <br /><br />";

			var z = 0;
			$('#kingdomCards .card').each(function(i) {
				
				$('#choix_content')
					.append('<img src="cards/'+$(this).attr('idcard')+'.jpg" onclick="send(\'C-'+z+'\'); cacherBoiteChoix()"/>'+" ");

				z++;
			});

			break;

		case '2': // 2-1,1,4,9_(envoyer num parmis liste)

			var html = "Choisisez une carte parmis la liste :<br /><br />";
			var packetSansIdChoix = packetChoix.substr(2,packetChoix.length-1); // 1,1,4,9_(envoyer num parmis liste)
			var parts = packetSansIdChoix.split("_");  // [0] => 1,1,4,9 [1] => (envoyer num parmis liste)

			var z = 0;
			var liste = parts[0].split(',');
			for (var i in liste)
			{
				html += '<img src="cards/'+liste[i]+'.jpg" onclick="send(\'C-'+z+'\'); cacherBoiteChoix()"/>'+" ";
				z++;
			}

			setHtml(html);

			break;

		case '3': // 3-4-1,1,9,9_(envoyer num parmis liste avec maximum) 
			
			var packetSansIdChoix = packetChoix.substr(2,packetChoix.length-1); // 4-1,1,9,9_(envoyer num parmis liste avec maximum) 
			var parts = packetSansIdChoix.split("_");  // [0] =>4-1,1,9,9    [1] => (envoyer num parmis liste avec maximum) 
			var parts2 = parts[0].split('-'); // [0] => 4     [1] => 1,1,9,9

			$('#choix_content')
				.html("Choisisez jusqu'a "+parts2[0]+" cartes parmis la liste :<br /><br />");

			var n = 0;
			var liste = parts2[1].split(',');
			for (var i in liste)
			{
				$('#choix_content')
					.append('<img src="cards/'+liste[i]+'.jpg" n="'+n+'"/>');

				$('#choix_content img:last')
					.attr('class', 'non')
					.click(function () {
						$(this).attr('class',
							($(this).attr('class') == 'oui')?'non':'oui'
						);
					})
				;
				n++;

			}

			$('#choix_content')
				.append('<br /><br /><input id="validerChoix" type="submit" value="Valider" />');

			$('#validerChoix')
				.click(function() {
					var listeDesCartes = '';
					$('#choix_content img[class=oui]').each(function(i) {
						listeDesCartes += $(this).attr('n')+'|';

					});
					send('C-'+listeDesCartes);
					cacherBoiteChoix();
				})

			;

			break;

		case '4': // Choix de type binaire

			var texte = packetChoix.substr(2,packetChoix.length-1); // 4-1,1,9,9_(envoyer num parmis liste avec maximum) 
			texte = texte.split('_')[0];
			
			$('#choix_content')
				.html(texte+"<br /><br /><br />");


			$('#choix_content')
				.append('<input id="direOui" type="submit" value="Oui" />')
				.append('<input id="direNon" type="submit" value="Non" />')
			;


			$('#direOui')
				.click(function() {
					send('C-o');
					cacherBoiteChoix();
				})

			$('#direNon')
				.click(function() {
					send('C-n');
					cacherBoiteChoix();
				})

			;

			break;

	}

	afficherBoiteChoix();
}

function setHtml(content)
{
	el('choix_content').innerHTML = content;
}

function afficherBoiteChoix()
{
	$('#choix')
		.show(1000)
}

function cacherBoiteChoix()
{
	$('#choix')
		.hide(1000);
}

