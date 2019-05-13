# goose-maze-sbs  
Questo progetto contiene l'implementazione del gioco dell'oca, questa implentazione si basa sul modello inventato [Matteo Vaccari](https://github.com/xpmatteo), le specifiche originali possono essere trovate [qui](https://www.slideshare.net/pierodibello/il-dilettevole-giuoco-delloca-coding-dojo).  
  
## Implementazione  
L'implementazione di questo gioco si basa sull'utilizzo del framework [Spring Boot](https://spring.io/projects/spring-boot) e nello specifico il framework [Spring Shell](https://projects.spring.io/spring-shell/).   
Questo framework consente di avviare una shell con il quale sarà possibile giocare.  
  
## Compilazione e avvio  
Questo progetto è stato utilizzando [Apache Maven](https://maven.apache.org/), una volta clonata questa repository:  
~~~~  
git clone https://github.com/andreamaggioni/goose-maze-sbs.git  
~~~~  
attraverso il comando maven sotto riportato è possibile compilare il codice:  
~~~~  
mvn clean install -DskipTests  
~~~~  
il parametro *-DskipTests* permette di bloccare l'esecuzione dei test configurati.  
  
Una volta compilato il codice attraverso il comando sottostante è possibile avviare il programma:  
~~~~  
java -jar target\goosemaze-0.0.1-SNAPSHOT.jar  
~~~~  
## Comandi  
### Player  
I comandi sotto riportati riguardano i giocatori che possono partecipare ad una partita:  
1. **add player / ap**: consente di aggiunge un giocatore:  
~~~~  
add player 'player name'  
~~~~  
### Match  
I comandi sotto riportati consentono di giocare:  
1. **start match**: questo comando consente di avviare una partita; una partita può partire solo se sono stati aggiunti almeno due giocatori: 
`start match`  
2. **move**: permette di muovere un giocatore: `move 'player' 'roll1,roll2'`, *player* rappresenta il giocatore da muovere, *roll1 e roll2* rappresenta il valore dei due dadi lanciati dal giocatore.
	2.1. è presente una versione del comando **move** che consente al gioco di simulare il lancio dei due dati; senza doverli avere in ingresso: `move 'player'`
3. **status**: permette di mostrare la posizione dei giocatori durante il gioco.
4. **stop match**: permette di interrompere la partita in corso.

##Esempio
Una volta avviato il programma sarà necessario aggiungere almeno due giocatori per poter avviare la partita:
```
goose-maze:>add player pippo
players: pippo

goose-maze:>add player pluto
players: pippo, pluto

goose-maze:>start match
Game started!

goose-maze:>move pippo 3,3
pippo rolls 3, 3. pippo moves from Start to The Bridge. pippo jumps to 12

goose-maze:>move pluto 2,2
pluto rolls 2, 2. pluto moves from Start to 4.

goose-maze:>move pippo
pippo rolls 3, 3. pippo moves from 12 to 18, The Goose.  pippo  moves again and goes to 24.

goose-maze:>goose-maze:>move pluto
pluto rolls 4, 2. pluto moves from 4 to 10. 

goose-maze:>move pippo
pippo rolls 1, 1. pippo moves from 24 to 26.

goose-maze:>move pluto
pluto rolls 2, 1. pluto moves from 10 to 13. 

goose-maze:>move pippo 6,6
pippo rolls 6, 6. pippo moves from 26 to 38.

...

goose-maze:>move pippo
pippo rolls 1, 5. pippo moves from 61 to 63. Pippo bounces! Pippo returns to 59. 

goose-maze:>move pippo 2,2
pippo rolls 2, 2. pippo moves from 59 to 63. pippo win!!! Match end!!!

```
**NB**: 