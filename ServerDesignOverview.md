#This page contains a general overview of the design of the game server.

# Introduction #

The server is based on a task based modularized design. There are multiple classes in each section and each of them performs functions which are called by other classes.

There are three independent subsets of the server code:

  * Networking
  * Database
  * GameLogic

The GameLogic, Database and Networking objects are at the same level and call functions withing each other to perform different tasks.

The GameLogic module is at the highest position in the hierarchy of classes and contains instances of all other important classes which are part of other subsections except for the Networking code which runs in separate threads.


# Details #

The following section gives details about the different parts of the Server and how they relate to the actual server code.

## Networking ##

This sub-section of the code logically contains the following files:

  * [ClientHandler.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGServer/src/rpgserver/ClientHandler.java)
  * [NetworkStreamParser.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGServer/src/rpgserver/NetworkStreamParser.java)
  * [NetworkStreamWriter.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGServer/src/rpgserver/NetworkStreamWriter.java)

The client handler object extends Runnable which means that it is an independent thread.

A new ClientHandler is created for every client that connects to the server. The ClientHandler has instances of NetworkStreamWriter and NetworkStreamParser which are the classes which actually understand the network protocol and the ClientHandler just interfaces with them, performing different functions depending on network events that occur.

## Database ##

This sub-section of the code logically contains the following files:

  * [GlobalGameDatabase.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGServer/src/rpgserver/GlobalGameDatabase.java)
  * [Actor.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGServer/src/rpgserver/Actor.java)
  * [NonPlayerCharacter.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGServer/src/rpgserver/NonPlayerCharacter.java)
  * [PlayerCharacter.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGServer/src/rpgserver/PlayerCharacter.java)

Every object in the game that can possibly be interacted with is called an 'Actor' the server maintains a record of the states of each actor and updates then as needed. This function is performed by the GlobalGameDatabase class. This class contains a HashTable containing all the actors in the game and also provides an interface for operating upon those actors.

In essence 'Actor's are of two types. PlayerCharacters and NonPlayerCharacters. The former being those actors which are being played remotely by a human such as different students. The latter is representing all other computer controlled Actors in the game. These range from Professors, H1N1 to classrooms , manholes and all other intractable elements of the game.

NonPlayerCharacters are distinguished from one another based on a type variable within them which identifies what type of NPC(NonPlayerCharacter) they are. The other values may also vary depending on the function of the NPC. Such as, professors may have a moving speed of 1 cell/second while the speed may be 0 in the case of manholes.

## GameLogic ##

This is the largest section of the server code and consists of the following files:

  * [GlobalGameLogic.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGServer/src/rpgserver/GlobalGameLogic.java)
  * [NPCEngine.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGServer/src/rpgserver/NPCEngine.java)
  * [GameMap.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGServer/src/rpgserver/GameMap.java)
  * [AIEngine.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGServer/src/rpgserver/AIEngine.java)

Here the GlobalGameLogic extends Runnable and is the master thread which contains references to all other classes in the server.

GlobalGameLogic should perform functions such as checking conditions on players. Checking if a 'PlayerCharacter' and a 'Classroom(NPC)' are close enough for the player to be attending the Class and other similar conditons with H1N1, professors, manholes etc.

The NPCEngine (under GlobalGameLogic) is responsible for loading from file all the initial NPCs into the GlobalGameDatabase and updating them in terms of movement, speed, locations and any other required aspects.

The GameMap (under GlobalGameLogic) object is responsible for loading a copy of the map data file and answer questions such as 'is coordinate x,y a walkable location?'

The AIEngine is responsible for Artifial Intellegence ie. actually calculating the path an actor will/is follow(ing) and determine if H1N1 or any other actor has 'interacted' with it in the process of the action. It also calculates the credit score / energy score and winning condition.

# Conclusion #

You should now have a basic understanding of the functioning of the game server. For more detailed information, refer to issue solution document for the server or comments in the code. Don't forget to update any related documentation before you make any changes to the code.