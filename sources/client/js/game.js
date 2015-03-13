

/* Fonctions rafraichisants l'interface */


function setKingdoms(noeud)
{
	$('#kingdomCards').html('');

	for (var k in noeud)
	{
		var e_k = noeud[k];
		if (e_k && e_k.nodeName == 'card')
			addKingdom(e_k.attributes.getNamedItem("value").nodeValue,
			e_k.attributes.getNamedItem("n").nodeValue);
	}
}

function addKingdom(idCard, qte)
{
	$('#kingdomCards')
		.append('<div class="card">')
	;

	$('#kingdomCards .card:last')
		.attr('src', 'cards/'+idCard+'.jpg')
		.attr('idcard', idCard)
		.append('<img src="cards/'+idCard+'.jpg" />')
		.append('<div class="qte">'+qte+'</div>')
		.click(function() {
			buyCard(idCard);
		})
	;

}

function onBuyCard(idCard)
{

	if ($('#kingdomCards .card[idcard='+idCard+'] img').length > 0)
	deplaceCard(
		$('#kingdomCards .card[idcard='+idCard+'] img'),
		$('#defausse').offset().top,
		$('#defausse').offset().left,
		100, 160);

	//setDefausse(idCard, 1);

}

function setBases(noeud)
{
	var anciens = new Array();
	for (var i=1; i <= 7; i++)
	{
		anciens.push($('#base'+i+" > .qte").html());
	}
	$('.case > .qte').html('0');

	for (var k in noeud)
	{
		var e_k = noeud[k];
		if (e_k && e_k.nodeName == 'card')
		{
			var valeur = e_k.attributes.getNamedItem("value").nodeValue;

			if (parseInt(anciens[valeur - 1]) != e_k.attributes.getNamedItem("n").nodeValue)
			{
				$('#base'+valeur)
					.css('color', 'white')
					.delay(500)
					.queue(function() {
						$(this)
							.css('color', '#64584c')
						;
						$(this).dequeue();
					})
				;
			}
				
			$('#base'+valeur+" > .qte").html(e_k.attributes.getNamedItem("n").nodeValue);

		}
		
	}

	$('#bases').dequeue();

}

var idMe; // Id du joueur client
var playerTurn; // id du joueur en train de jouer

function setPlayers(noeud)
{
	var n = 1;

	for (var k in noeud)
	{
		var e_k = noeud[k];
		if (e_k && e_k.nodeName == 'joueur')
		{
			if (e_k.attributes.getNamedItem("id").nodeValue != idMe)
			{
				$("#player"+n)
					.html('<div class="pseudo">'+getAttribut(e_k, 'name')+'</div>')
					.append('<div class="avatar"></div>')
					.attr("idPlayer", getAttribut(e_k, 'id'))
					.css("background", "none")
				;

				for (var i in e_k.childNodes)
				{
					var e_i = e_k.childNodes[i];

					if (e_i.nodeName == 'deck')
					{
						$("#player"+n)
							.append('<div class="card deck"></div>')

						if (+e_i.attributes.getNamedItem("n").nodeValue != 0)
						{
							$("#player"+n+" .deck")
								.html('<img class="card" src="cards/dos.png" />');

							$("#player"+n+" .deck")
								.append('<div class="qte">'+e_i.attributes.getNamedItem("n").nodeValue+'</div>');
							;
						}
					}

					if (e_i.nodeName == 'main')
					{
						$("#player"+n)
							.append('<div class="main">'+e_i.attributes.getNamedItem("n").nodeValue+'</div>')
					}

					if (e_i.nodeName == 'defausse')
					{
						$("#player"+n)
							.append('<div class="card defausse"></div>')

						if (e_i.attributes.getNamedItem("n").nodeValue != 0)
						{

							$("#player"+n+" .defausse").html('<img class="card" src="cards/'+e_i.attributes.getNamedItem("last").nodeValue+'.jpg" />');

							$("#player"+n+" .defausse")
								.append('<div class="qte">'+e_i.attributes.getNamedItem("n").nodeValue+'</div>');
							;

						}
					}
				}

				n++;
			}
		}
	}
}

function setTurn(idPlayer)
{
	if (idPlayer != idMe)
	{
		$("#players > .player[idPlayer="+idPlayer+"]")
			.css("background", "rgba(138, 111, 91, 0.4)")
	}
}


function setDefausse(idCard, nbrCarte)
{
	if (idCard > 0)
	{
		$('#defausse').
			html('<img class="card" src="cards/'+idCard+'.jpg" />')
		;
	}
	else {
		$('#defausse').
			html('')
		;
	}
}

function setNbrCardDeck(n)
{
	el('deck').innerHTML = "";

	if (n > 0)
	{
		$('#deck').
			append('<img class="card" src="cards/dos_100.png" />')
		;

	}
}

function onEndTurn()
{
	el('inplay').innerHTML = '';
}



function deplaceCard(element, x, y, width, height)
{
	$('#volante')
		.css({
			display: 'block',
			opacity: '1',
			top: element.offset().top+'px',
			left: element.offset().left+'px',
			width: element.css('width'),
			height: element.css('heigh')
		})
		.attr('src', element.attr('src'))
		.animate({
		    width: width+'px',
			height: height+'px',
			top: x+'px',
			left: y+'px'
		}, 500 ,'swing')
		.queue( function() {
			$(this)
				.css({
					display: 'none',
				})
			$(this).dequeue();
		})
	;
}

function getImgCardFromElement(element)
{
	var src = element.src;
	var img = src.split('cards/')[1];
	return img;
}

function getIdCardFromElement(element)
{
	return getImgCardFromElement(element).split('.jpg')[0];
}

function getIdCardFromUrlImage(string)
{
	var parts = string.split('cards/');
	parts = parts[1].split('.jpg');
	return parseInt(parts[0]);
}

function addLog(line)
{
	$('#logs_html').append('\n'+line);
	$('#logs_html').scrollTop(10000);
}

function InPlay()
{
	this.addCard = function(idCard)
	{
		console.log("add "+idCard+" inplay");

		$('#inplay')
			.queue(function() {
				$('#inplay')
					.append('<img />');

				$('#inplay > img:last')
					.addClass('card')
					.attr('src', 'cards/'+idCard+'.jpg')
					.attr('idCard', idCard)
					.css({
						'-webkit-transform': 'perspective(1000) rotateY(-81.5deg)'
					})
					.click( function() {
						playCardFromMain(idCard)
					})
					.delay(5)
					.queue( function() {
						$(this)
							.css({
								'-webkit-transform': 'perspective(1000) rotateY(0deg)'
							})
						$(this).dequeue();
					})
					.delay(100)
					.queue( function() {
						$('#inplay').dequeue();
					})
				;
			
			})
			
	}

	this.getArrayOfIdCard = function()
	{
		var idCartes = new Array();
		$('#inplay > img').each(function(i){
			idCartes.push($(this).attr('idCard'));
		});
		return idCartes;
	}

	this.delCard = function(idCard)
	{
		$('#inplay > img[idcard='+idCard+']:first')
			.remove()
	}

	this.resetCards = function(noeud)
	{
		$('#inplay')
			.queue(
				( function(el) {
						return function() {

								var nouvTable = new Array();
								for (var k in noeud)
								{
									var e_k = noeud[k];
									if (e_k && e_k.nodeName == 'card')
									{
										var idCard = e_k.attributes.getNamedItem("value").nodeValue;
										nouvTable.push(idCard);
									}
								}

								var r = division(el.getArrayOfIdCard(), nouvTable);

								for (var i in r.ajouts)
									inPlay.addCard(r.ajouts[i]);

								for (var i in r.retraits)
									el.delCard(r.retraits[i]);

								$('#inplay').dequeue();
								
							};
						} ) (this)

				)
		;
	}
}

var inPlay = new InPlay();


function Main()
{

	this.addCard = function(idCard)
	{

		$('#main')
			.queue(function() {
				$('#main')
					.append('<img />');

				$('#main > img:last')
					.addClass('card')
					.attr('src', 'cards/'+idCard+'.jpg')
					.attr('idCard', idCard)
					.css({
						'-webkit-transform': 'perspective(1000) rotateY(-81.5deg)'
					})
					.click( function() {
						playCardFromMain(idCard)
					})
					.delay(5)
					.queue( function() {
						$(this)
							.css({
								'-webkit-transform': 'perspective(1000) rotateY(0deg)'
							})
						$(this).dequeue();
					})
					.delay(100)
					.queue( function() {
						$('#main').dequeue();
					})
				;
			
			})
			
	}

	this.getArrayOfIdCardInMain = function()
	{
		var idCartes = new Array();
		$('#main > img').each(function(i){
			idCartes.push($(this).attr('idCard'));
		});
		return idCartes;
	}

	this.delCard = function(idCard)
	{
		$('img[idcard='+idCard+']:first')
			.remove()
	}

	this.resetCards = function(noeud)
	{
		$('#main')
			.queue(
				( function(el) {
						return function() {

								var nouvTable = new Array();
								for (var k in noeud)
								{
									var e_k = noeud[k];
									if (e_k && e_k.nodeName == 'card')
									{
										var idCard = e_k.attributes.getNamedItem("value").nodeValue;
										nouvTable.push(idCard);
									}
								}

								var r = division(el.getArrayOfIdCardInMain(), nouvTable);

								for (var i in r.ajouts)
									main.addCard(r.ajouts[i]);

								for (var i in r.retraits)
									el.delCard(r.retraits[i]);

								$('#main').dequeue();
								
							};
						} ) (this)

				)
		;
	}
}

var main = new Main();

function virerHD()
{
	$('#HD')
		.css({
			top: '200px',
			left: '500px',
			width: '0px',
			height: '0px'
		})
	
}

function agrandir(element)
{

	el('HD').style.display = 'none';
	el('HD').style.opacity = '1';
	el('HD').src = 'cards/'+getImgCardFromElement(element);
	el('HD').style.top = (getPositionDiv(element).y)+'px';
	el('HD').style.left = (getPositionDiv(element).x - getPositionDiv(el('game')).x)+'px';
	el('HD').style.width = "0px";
	el('HD').style.height = "0px";
	el('HD').style.display = 'block';

	setTimeout(function() {
		el('HD').style.width = '296px';
		el('HD').style.height = '473px';
		el('HD').style.top = '70px';
		el('HD').style.left = '350px';

	}, 50);

}


