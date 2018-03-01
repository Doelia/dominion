/* Utilitaires */

function el(name)
{
	return document.getElementById(name);
}

function echo(msg)
{
	console.log(msg);
}

function StringtoXML(text)
{
	if (window.ActiveXObject)
	{
		var doc=new ActiveXObject('Microsoft.XMLDOM');
		doc.async='false';
		doc.loadXML(text);
	}
	else
	{
		var parser=new DOMParser();
		var doc=parser.parseFromString(text,'text/xml');
	}
	
	return doc;
}

function alea(max)
{
	return Math.floor(Math.random() * max+1);
}

function getPositionDiv(e)
{
	var offset = {x:0,y:0};
	while (e)
	{
	    offset.x += e.offsetLeft;
	    offset.y += e.offsetTop;
	    e = e.offsetParent;
	}
	return offset;
}


function removeByValue(arr, val) {
    for(var i=0; i<arr.length; i++) {
        if(arr[i] == val) {
            arr.splice(i, 1);
            break;
        }
    }
}

function division(tab1, tab2)
{
	var ajouts = new Array();
	var retraits = new Array();
	
	for(var i in tab1)		// Pour chaque élément dans 1,
	{
		var j = 0;
									// On cherche s'il existe dans 2.
		while(j < tab2.length && tab2[j] != tab1[i])	
			j++;
			
		if(j < tab2.length) // Si trouvé
		{
			tab2.splice(j, 1);		// On barre l'élément dans chaque tableau,
											// pour le 1 on passe à l'élément suivant
		}
		else // Si pas trouvé dans 2, on le met dans retraits
		{
			retraits.push(tab1[i]);
		}
	}
	
	for(var j in tab2)			// Tous les éléments restants sont les ajouts
	{
		ajouts.push(tab2[j]);
	}

	return {ajouts:ajouts, retraits:retraits};
}

function clone(srcInstance)
{
	/*Si l'instance source n'est pas un objet ou qu'elle ne vaut rien c'est une feuille donc on la retourne*/
	if(typeof(srcInstance) != 'object' || srcInstance == null)
	{
		return srcInstance;
	}
	/*On appel le constructeur de l'instance source pour crÃ©e une nouvelle instance de la mÃªme classe*/
	var newInstance = srcInstance.constructor();
	/*On parcourt les propriÃ©tÃ©s de l'objet et on les recopies dans la nouvelle instance*/
	for(var i in srcInstance)
	{
		newInstance[i] = clone(srcInstance[i]);
	}
	/*On retourne la nouvelle instance*/
	return newInstance;
}

function getAttribut(n, att)
{
	return n.attributes.getNamedItem(att).nodeValue;
}
