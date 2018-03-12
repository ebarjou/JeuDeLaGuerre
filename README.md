# Implémentation du Jeu de la Guerre de Debord
## Liens
- Site du PdP : [lien](http://dept-info.labri.fr/~narbel/PdP/)
- Cahier des besoins : [rapport/.pdf](https://github.com/ebarjou/JeuDeLaGuerre/blob/master/rapports/PDP%20Wargame%20-%20Cahier%20des%20besoins.pdf) 

## Dépendances
Le projet dépend de plusieurs modules externes, en plus de JavaFX et JUnit 4, inclus dans le JDK 8.
- Mockito ([lien](https://mvnrepository.com/artifact/org.mockito/mockito-all/1.10.19))
- Cliche ([lien](https://code.google.com/archive/p/cliche/))

### Ant Build
Les dépendances sont intégrées dans le dossier `lib` du dépôt, elles sont incluses à la compilation automatiquement en utilisant [Ant](http://ant.apache.org/)

### IDE
Installation sous IDEA : **Project Structure > Modules > JeuDeLaGuerre > Dependencies >  ➕  > JAR or directories...**
Les `.jar` du dossier `lib` peuvent être également importées de cette manière.

## Compilation
Le projet peut être compilé sous n'importe quel IDE Java, ou bien via Ant à la racine du projet.
