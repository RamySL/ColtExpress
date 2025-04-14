# 🎲 ColtExpress

Implémentation du jeu de société français **Colt Express**, qui est un jeu de **tactique et de stratégie** se déroulant dans l'Ouest américain de la fin du XIXe siècle.

Chaque joueur incarne un bandit rival, déterminé à piller un train en marche pour amasser le plus de butin possible. Le jeu se distingue par sa **mécanique de programmation d'actions** : les joueurs planifient leurs mouvements à l'avance, ce qui peut entraîner des retournements de situation imprévus et souvent hilarants.

Entre déplacements sur le toit du train, échanges de coups de feu et confrontations avec le marshal, l'objectif est clair : **devenir le hors-la-loi le plus riche de l'Ouest**.

---

## 🕹️ Déroulement d'une partie de jeu

- **Phase de programmation des actions** :  
  Chaque joueur programme 4 actions entre : se déplacer, voler, tirer.

- **Phase d'action** :  
  Une fois que tout le monde a planifié ses actions, chaque joueur, à tour de rôle, exécutera une de ses actions dans l'ordre saisi.

---

## 🚀 Lancement du jeu

Le jeu se lance à partir de la classe : src/Main.java

---

## 🌐 Multijoueurs

Pour jouer à plusieurs sur des machines différentes :

1. Il faut que tous les joueurs se connectent au même réseau local.
2. Il faut qu'un joueur *host* la partie, c'est-à-dire lance le serveur depuis sa machine à lui, puis tout le monde se connecte dessus.
3. Pour se connecter au serveur, il faut saisir l'adresse IP de la machine hôte et le port sur lequel il a été lancé (`ifconfig` pour voir son adresse IP sur Linux ou `ipconfig` sur Windows).
