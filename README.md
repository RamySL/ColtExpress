# ColtExpress


## 1.Les parties du sujet que vous avez traitées.
- Pour les PDF de diagramme des classes mit, on a décidé de les séparer sinon c'était trop ilisible mais au lieu des fleches on a laissé la attributs qui pointaient vers les différentes
  classes entre modele vue et controleur

### Description générale du jeu
Notre jeu se déroule bien à bord d'un train qui se compose d'une locomotive, de wagons et d'un dernier wagon. On y retrouve initialement
à bord des butins placés aléatoirement entre 1 et 4 par wagon sauf pour la locomotive qui ne possède seulement 1 butin qui est un ensemble
de magot. Il est possible pour le bandit qui intialement sont tous sur un toit aleatoire du train de braquer et récuperer un butin, se déplacer entre les cabines et les toits et tirer sur un autre
bandit. Concernant le marshall, il peut se déplacer entre la locomotive et le reste des wagons en essayant de chasser les bandits, il ne peut pas aller sur le toit. Il tire sur les bandit lorsqu'ils 
viennent à son emplacement. Notre jeu obéit à l'idée de planification et d'action. Les bandits planifient un à un leurs actions dans un premier temps pour chaque manche puis arrive 
la phase d'action. La consigne qui stipule d'effectuer toutes les actions numéro 1, puis toutes les numéro 2, et ce jusqu’au bout est respectée.
Le jeu se déroule en un nombre de manches et a la fin le gagnant est le bandit ayant recolté le plus de butins

### Modèle-Vue-Contrôleur
Le jeu est organisé selon l'architecture MVC. On a la partie modèle qui regroupe le code concernant la structure des objets constituant le jeu,le train et tout ce qui le compose,
pour les bandit, le marshall, les différents buttins, les actions des bandit (braquer, se deplacer et tirer) sont aussi des classes.
Pour la partie vue, on y retrouve la partie visuelle avec EcranFin, EcranLancement, Fenetre, la classe Jeu pour l'affichage, la classe Accueil et enfin la classe Bouttons pour les différents 
bouttons présents dans le jeu. 
Pour la partie controleur, on retrouve le ControleurAccueil, ControleurJeu, ControleuFinJeu et notamment la classe JouerSon pour les différents sons
qui compose le jeu. 

### Modèle détailles
- le tarain ayant composé par un nombre donné wagons.
- l'hiearchie de classes a été choisi en essayant de composer un train de la maniere la plus naturelle et proche de réalité que possible (avec par exemple un wagon de train est relié à ses deux voisins) 
ce qui a donné la structure d'une liste doublement chainée, on a decider de prendre la locomotive et le dernier wagon comme des objets eux aussi la locmotive se distinguant avec le marshall
et le magot et le dernierWagon représenté son symetrique, les deux possedant qu'un seul voisin à la diference des Wagons nous conduit à les faire heriter d'une classe Extremite.
- on a décidé aussi que le toit a le mérite d'etre un objet donc on a fait une classe pour, pour differencier le toit et les l'interieur du train on a introduit la classe Interieur dont herite
Extremite et Wagon, un objet Interieur à sa cration est lié à son toit qui lui même pointe vers lui.
Et dans le sommet de la hiearchie des composantes de train se trouve la classe abstraite ComposanteTrain qui permet par exemple de factoriser le fait que le toit et Interieur peuvent les deux avoir 
des bandits et des butins etc.
- naturellement les bijoux, magot et boursres sont tous des butins donc il heritent d'une classe abstrainte Butin
- Bandit est Marshall herite par leur tours de Personnage
- c'est la classe personnage qui etend Observable parceque tout les changement dans le modele passent par des action de la part des bandit ou du marshall 

### Une belle vue
Notre jeu se compose de plusieurs vues. Une première avec une page d'accueil dans laquelle on retrouve le titre du jeu, une seconde dans laquelle on retrouve toutes une liste de choix. On a le choix entre
l'icone du bandit et du surnom, nombre de balles pour les bandits, nombre de wagons, nombre d'actions, nombre de manche et enfin le choix pour la nervosite du Marshall entre calme, enervé et furieux. Le jeu se 
lance officiellement à la troisième fenêtre avec tous les paramètres choisits précedemment, on y retrouve les différents éléments du train et les bandits à leur emplacement avec leur icone et leur surnom ainsi que les butins
sous la forme de bijou ou de bouse et de magot dans la locomotive. 
On a la partie en haut à gauche dédiée aux actions c'est a dire se déplacer, braquer, tirer et le bouton action. Il est possible de jouer au jeu avec le clavier, les flèches pour se deplacer, 
les lettres "Z","Q","S","D" pour le tir, "B" pour braquer et entré pour exécuter.  
La partie en haut à droite presente une petit partie pour les informations des joueurs ( icone, nbr de balles restantes et score qui correspond aux butins). Puis on retrouve
le compte rendu qui precise les differentes actions des joueurs, la phase dans laquelle on se situe et enfin la manche dans laquelle on est. 
Pour terminer, on a une quatrième fenetre qui montre quel bandit a gagné avec son score et son icone, et un bouton "rejoué" pour les joueurs. 
- c'est Jeu.Vue qui implemente Observer pour actualiser l'affichage pendant la partie

### Une poignée de dollars
- On a l'intérieur de chaque wagon entre 1 et 4 butins de type bourse ou bijou aléatoire. Ils sont representés des images. Avant chaque action du bandir, le marshall se deplace avec une probabilité
choisit selon la nervosité (calme = 0.2, enerve = 0.4 et furieux = 0.7)
- Si le Marshall atteint la position du bandit, le bandit lâche un des butins qu’il a ramassés tiré au hasard (s’il en a) et se déplace sur le toit. Le butin est ajouté 
à l’ensemble des butins de la position dont le bandit vient d’être chassé, et peut être récupéré à nouveau.
Mais lorsque c'est le bandit qui atteint la position du Marshall, il se fait tirer dessus et perd à nouveau un butin et monte sur le toit.  
- Le score des bandits est actualisé pendant le jeu, le bijou apporte 500, la bourse entre 0 et 500 aléatoirement et le magot 1000. 

### Echange de plombs entre amis
- On choisit le nombre de joueurs sur la fenêtre accueil.
- Les joueurs jouent toujours dans le même ordre
- Lors de la phase de planification, chaque joueur à son tour donne tous ses ordres à la fois
- Lors de la phase d’action, on effectue d’abord toutes les premières actions (dans l’ordre des joueurs), puis toutes les deuxièmes actions (dans le mêeme ordre), et ainsi de suite
- Chaque tir utilise une balle. Chaque bandit possède un nombre de balles donné par le joueur lors du choix des paramètres.
- Le compte-rendu textuel mentionne ces actions de tir et leurs conséquences

### les controleurs 
pour gerer les evenements utilisateurs et lier entre modèle et nos multiples vue on introduit trois controleurs
- un controleur pour l'accueil c'est lui qui possede le main du jeu et qui permet de le lancer, il lance l'ecran de départ et gere les evenement d'ecran d'accueil qui donne 
a l'utilisateur un panneau avec toutes les options pour personaliser le jeu (nombre de wagons, de balles, de manches, nervosité marshall, choix badit), il gere et assure que tout
les parametre saisie sont correcte.
- à la fin de la saisie le controleur d'accueil lance le jeu quand le bouton est appuié, pour ça on eu un peu de difficulté :
on a eu un probleme qui etait quand la boucle etait lancée dans ControleurAcuueil à partir de controleurJeu, l'écran freez et l'affichage n'est plus actualisé même si le jeu tournait 
(on fait des print pour le verifier), en ayant deja fait des recherches sur les thread utilisé en swing en s'interressant à invokeLater() (qui son but est de rajouter un Runnable a la fin de la file d'attente 
du thread) qui était dans conway (le jeu du prof), on a trouvé que c'était un probleme commun, et que ce qui se passé c'est que la boucle du jeu
tournait sur l'EDT (Event Dispatch Thread) qui est responsable pour les mise à jour graphique et pour gerer les evenment, EDT etant bloqué par l'execution de la boucle qui tournait 
dessus n'actualisé plus l'affichage
et on recherchant on a trouvé deux solution une qui est intuitif c'est de séparer l'execution de la boucle du jeu sur un autre thread comme ça l'EDT n'est pas bloqué
La deuxieme qui était de souvenir utilisé pour la boucle du jeu FeuFurieux du premier semestre et qui été donnée en squelette cette solution est d'utilise javax.swing.Timer, cette solution permet d'executer tout sur l'EDT 
parceque elle permet de synchroniser l'execution de la boucle chaque intervalle de temps préciser, donc la boucle du jeu ne prend plus l'EDT pendant une période continue mais son execution est discontinu 
et donc l'actualisation de l'affichage n'est pas bloquée. On a choisit l'option du thread séparé parceque c'est la plus simple en ayant deja initié la boucle du jeu sans penser au timer
  pour faire ça on a trouvé la classe abstraite SwingWorker qui utiliser pour faire tourner des taches en arriere plans et donc dans un thread séparé que EDT
- ControleurJeu : Une autre notion qu'on a appris c'est la notion de "busy waiting", en voulant faire la logique d'attendre la planification de tout les actions des bandits on a introduit
des boucles while qui leurs seul but été d'attendre les saisie utilisateur et prendant ce temps là le processeur souffre avec des calculs pas necessaire, donc on utilise Thread.sleep pour arreter
les calculs une periode donnée en milliseconde comme ça on reduit les calculs. 
Donc ControleurJeu possede la boucle pricnipale et c'est lui qui ecoute pour les evenement pendant le déroulement de la partie, à la fin 
il calcule la liste des gagnant et l'envoi au ControleurFinJeu
- ControleurFinJeu : il permet de relancer une partie si le boutton rejouer dans la vue EcranFinJeu est appuié


### Expression libre 
- Ce qu'on a rajouté comme plus :
- différentes vue (lancement, accueil, jeu, fin de jeu)
- saisie de tous les parametres par le joueur
- utilisation d'images et icones pour les butins pour les personnages et butins
- jouer avec les touches clavier, deplacement avec les fleches haut bas droite gauche,braquage avec B, tirs : q,z,s,d et action : entré
- différent son pour le jeu
- afficher le ou les gagnants selon le score
- affichage du feedback des actions dans le jeu au lieu de la console



### Partage des rôles 
Concernant le partage des rôles, nous avons créé un serveur sur discord dans lequel on echangeait sur les idées ou pour s'appeler afin de travailler ensemble
lors de partage d'ecran. Nous nous sommes quelques fois vus à la bibliothèque ou lors de pause déjeuner. 
Le partage du code s'est fait en manière de préférence c'est a dire selon l'envie. 

### Détails des classes 
Diagramme de classe : "https://lucid.app/lucidchart/13bc1b04-0832-41a9-b564-53e2586f50f3/edit?invitationId=inv_4a7964f2-4bec-43cf-9799-22bedca664ad&page=0_0#" 
## 3. Les problèmes qui sont présents et que vous n’avez pas pu éliminer.
Aucun
## 4. Les morceaux de code écrits à plusieurs ou empruntés ailleurs.
- Classe pour le Son empreinté à un amis qui l'a utilisé lui même pour un projet swing
- beaucoup d'aides de stackOverflow
   




