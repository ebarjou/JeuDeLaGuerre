# Implémentation du Jeu de la Guerre de Debord
## Liens
- Site du PdP : [lien](http://dept-info.labri.fr/~narbel/PdP/)
- Cahier des besoins : [rapport/.pdf](https://github.com/ebarjou/JeuDeLaGuerre/blob/master/rapports/PDP%20Wargame%20-%20Cahier%20des%20besoins.pdf) 

## Dépendances
Le projet dépend de plusieurs modules externes, en plus de JavaFX et JUnit 4, inclus dans le JDK 8.
- Mockito ([lien](https://mvnrepository.com/artifact/org.mockito/mockito-all/1.10.19))
- Cliche ([lien](https://code.google.com/archive/p/cliche/))

Les dépendances peuvent être intégrées aux sources manuellement en téléchargeant les `.jar` ou bien en exécutant la commande
```
ant resolve
```
qui nécessite l'installation de [Ant](http://ant.apache.org/) ainsi qu'[Ivy](http://ant.apache.org/ivy/). Les dépendances seront installées dans un répertoire `lib` créé à la racine du projet.

### IDE
Installation sous IDEA : **Project Structure > Modules > JeuDeLaGuerre > Dependencies >  ➕  > JAR or directories...**

### Ant build
Les dépendances sont récupérées et inclus à la compilation automatiquement et ne nécessite aucune intervention supplémentaire.

## Compilation
Le projet peut être compilé sous n'importe quel IDE Java, ou bien via Ant à la racine du projet.
