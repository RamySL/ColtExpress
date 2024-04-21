# ColtExpress


## 1.Les parties du sujet que vous avez traitées.

### Description générale du jeu
Notre jeu se déroule bien à bord d'un train qui se compose d'une locomotive, de wagons et d'un dernier wagon. On y retrouve initialement
à bord des butins placés aléatoirement entre 1 et 4 par wagon sauf pour la locomotive qui ne possède seulement 1 butin qui est un ensemble
de magot. Il est possible pour le bandit de braquer et récuperer un butin, se déplacer entre les cabines et les toits et tirer sur un autre
bandit. Concernant le marshall, il peut se déplacer entre la locomotive et le reste des wagons en essayant de chasser les bandits, il ne peut pas aller sur le toit. Il tire sur les bandit lorsqu'ils 
viennent à son emplacement. Notre jeu obéit à l'idée de planification et d'action. Les bandits planifient un à un ses actions dans un premier temps pour chaque manche puis arrive 
la phase d'action. La consigne qui stipule d'effectuer toutes les actions numéro 1, puis toutes les numéro 2, et ce jusqu’au bout est respectée.

### Modèle-Vue-Contrôleur
Le jeu est organisé selon l'architecture MVC. On a la partie modèle qui regroupe le code concernant la fonctionnement de Train et tout ce qui le compose. 
Il regroupe la classe concernant les actions avec les différentes classes Braquer, seDeplacer et Tirer ; les classes pour les butins comme 
Bourse, Bijou ou encore Magot; les classes pour les personnages donc Bandit et Marshall; et pour terminer on y retrouve aussi les classes telles que DernierWagon, Locomotive, 
Wagon, Toit, ComposanteTrain, Extremite et Interieur. Il y a notamment Oberservable et Observer qui permettent XXX.
Pour la partie vue, on y retrouve la partie visuelle avec EcranFin, EcranLancement, Fenetre, la classe Jeu pour l'affichage, la classe Accueil et enfin la classe Bouttons pour les différents 
bouttons présents dans le jeu. 
Pour la partie controleur, on retrouve le ControleurAccueil, ControleurJeu, ControleuFinJeu et notamment la classe JouerSon pour les différents sons
qui compose le jeu. 

Voici le lien vers le diagramme de classe : "https://lucid.app/lucidchart/13bc1b04-0832-41a9-b564-53e2586f50f3/edit?invitationId=inv_4a7964f2-4bec-43cf-9799-22bedca664ad&page=0_0#"

### Modèle réduit
- Notre NB_WAGONS est un choix des joueurs, ils peuvent le definir dans la fenetre de lancement. 
- Un déplacement vers l’arrière lorsque l’on est dans le dernier wagon ou en avant lorsque l’on est dans la locomotive n’a pas d’effet. De même pour un déplacement
  vers le toit ou vers l’intérieur lorsque l’on y est déjà.
- Les tests unitaires pour les actions des bandits sont réalisés.
- On affiche un compte rendu à chaque action qui se déroule dans ( le prtit carré a droite là)

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

### Une poignée de dollars
- On a l'intérieur de chaque wagon entre 1 et 4 butins de type bourse ou bijou aléatoire. Ils sont representés des images. Avant chaque action du bandir, le marshall se deplace avec une probabilité
choisit selon la nervosité (calme = 0.3, enerve = 0.6 et furieux = 0.9)
- Si le Marshall atteint la position du bandit, le bandit lâche un des butins qu’il a ramassés tiré au hasard (s’il en a) et se déplace sur le toit. Le butin est ajouté 
à l’ensemble des butins de la position dont le bandit vient d’être chassé, et peut être récupéré à nouveau.
Mais lorsque c'est le bandit qui atteint la position du Marshall, il se fait tirer dessus et perd à nouveau un butin et monte sur le toit.  
- Le score des bandits est actualisé pendant le jeu, le bijou apporte 500, la bourse entre 0 et 500 aléatoirement et le magot 1000. 

### Echange de plombs entre amis
- On choisit le nombre de joueurs sur la fenêtre accueil.
- Les joueurs jouent toujours dans le même ordre
- Lors de la phase de planification, chaque joueur à son tour donne tous ses ordres à la fois
- Lors de la phase d’action, on effectue d’abord toutes les premières actions (dans l’ordre des joueurs), puis toutes les deuxièmes actions (dans le mêeme ordre), et ainsi de suite
- 



## 2. Vos choix d’architectures.
## 3. Les problèmes qui sont présents et que vous n’avez pas pu éliminer.
## 4. Les morceaux de code écrits à plusieurs ou empruntés ailleurs.
   




