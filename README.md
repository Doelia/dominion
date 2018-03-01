# Dominion

Clone libre en ligne du jeu de carte Dominion

Réalisé en 2012 en collaboration avec Manuel Chataigner (2éme année DUT informitaque) 

Rapport de projet universitaire : [https://github.com/Doelia/dominion/raw/master/rapport/Rapport%20Dominia%20FINAL.pdf]()

## Composants
* Serveur en JAVA
* Communication en HTTP (échange client/serveur synchrone)
* Client en HTML / Javascript
* La plupart des cartes de la version de base du jeu implémenté
* Système multi parties à 4 joueurs simultannés, avec lobby

## Usage

Avec docker :
```
docker-compose up -d
```

Puis ouvrir un navigateur sur http://localhost:8000/
