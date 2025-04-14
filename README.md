# 🎲 Colt Express

Implémentation du jeu de société français **Colt Express**, un jeu de **tactique et de stratégie** se déroulant dans l'Ouest américain de la fin du XIXᵉ siècle.

Chaque joueur incarne un bandit rival, déterminé à piller un train en marche pour amasser le plus de butin possible. Le jeu se distingue par sa **mécanique de programmation d'actions** : les joueurs planifient leurs mouvements à l'avance, ce qui peut entraîner des retournements de situation imprévus et souvent hilarants.

Entre déplacements sur le toit du train, échanges de coups de feu et confrontations avec le marshal, l'objectif est clair : **devenir le hors-la-loi le plus riche de l'Ouest**.

---

## 🕹️ Déroulement d'une partie

- **Phase de programmation des actions** :  
  Chaque joueur programme 4 actions parmi : se déplacer, voler, tirer.

- **Phase d'action** :  
  Une fois que tout le monde a planifié ses actions, chaque joueur, à tour de rôle, exécute une de ses actions dans l'ordre prévu.

---

## 🚀 Lancement du jeu

Le jeu se lance à partir de la classe : `src/Main.java`

---

## 🌐 Multijoueur

Pour jouer à plusieurs sur des machines différentes :

1. Tous les joueurs doivent être connectés au même réseau local.
2. Un joueur doit *héberger* la partie, c’est-à-dire lancer le serveur depuis sa machine, puis les autres s’y connectent.
3. Pour se connecter au serveur, il faut saisir l'adresse IP de la machine hôte et le port sur lequel il a été lancé (`ifconfig` pour voir son adresse IP sur Linux ou `ipconfig` sur Windows).
