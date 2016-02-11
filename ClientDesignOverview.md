#This page contains a general overview of the design of the game client.

# Introduction #

The client is based on a task based modularized design. There are multiple classes in each section and each of them performs functions which are called by other classes.

There are three independent subsets of the Client code:

  * Networking
  * Graphics
  * GameLogic

The Main module contains references to each of these subsets and manages their interaction.

# Details #

The following section gives details of the functioning of each of the above mentioned subsets at a deeper level.

## Networking ##

This sub-section of the code logically contains the following files:

  * [NetworkEngine.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGClient/src/rpgclient/NetworkEngine.java)
  * [NetworkStreamParser.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGClient/src/rpgclient/NetworkStreamParser.java)
  * [NetworkStreamWriter.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGClient/src/rpgclient/NetworkStreamWriter.java)

The network engine object extends Runnable which means that it is an independent thread.

The NetworkEngine object is responsible for interacting with the server on the clients behalf. The NetworkEngine has instances of NetworkStreamWriter and NetworkStreamParser which are the classes which actually understand the network protocol and the NetworkEngine just interfaces with them, performing different functions depending on network events that occur.

## Graphics ##

This sub-section of the code logically contains the following files:

  * [XCanvas.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGClient/src/rpgclient/XCanvas.java)
  * [UIEngine.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGClient/src/rpgclient/UIEngine.java)
  * [LoginScreen.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGClient/src/rpgclient/LoginScreen.java)
  * [LoadingScreen.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGClient/src/rpgclient/LoadingScreen.java)
  * [GameMap.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGClient/src/rpgclient/GameMap.java)
  * [AnimatedSprite.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGClient/src/rpgclient/AnimatedSprite.java)
  * [Actor.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGClient/src/rpgclient/Actor.java)

This is the largest subsection of the client code. All the objects part of this subsection are practically mutually exclusive; this means that they can operate independently of each other. Another thing as that each of there objects implement the Renderable interface which consists of the function void render().

Lets start with the AnimatedSprite object. This object represents a series of images which are part of a continuous progression of frames. When this object is rendered on the screen it draws on every render the logical next frame in the series of the animation depending on the time that has elapsed since the last call to render. the result is a smooth animation displayed on the screen.

Each object of type Actor, amongst other status variables also contains 4 different AnimatedSprite objects which serve as the view of the actor as seen from the LEFT, RIGHT, FRONT and BACK depending on which direction the actor is facing. Apart from this each actor contains its current position, the position that the actor is moving to, its name etc.

The GameMap object contains the actual map that is displayed on the screen and the map data used to detect the type of a tile on the map. This object has the ability to render this map to the screen given any coordinate set on the map that needs to be displayed at the center of the screen.

The LoadingScreen object represents a loading screen and calling its render function will display this loading screen.

The LoginScreen object represents a login screen and calling its render function will display this log-in screen. The screen asks the user for their name and the server IP. It then calls appropriate functions from the network engine to perform the actual log-in.

The UIEngine object is responsible for rendering the user interface displayed while the game is being played.

The XCanvas object extends java.awt.Panel and is responsible for updating the screen and receiving keyboard input/storing it. It calls functions witing GameLogic every frame which in turn decide which Renderable objects need to be rendered and in what order calling their render function as required.

## GameLogic ##

This sub-section of the code logically contains the following files:

  * [CoreGameLogic.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGClient/src/rpgclient/CoreGameLogic.java)
  * [ActorEngine.java](http://code.google.com/p/gprublapo/source/browse/trunk/RPGClient/src/rpgclient/ActorEngine.java)

This subsection contains all the graphics objects from the Graphics subsection and also contains an ActorEngine object.

The ActorEngine maintains a list of all actors that are currently present on the users screen and need to be displayed. It contains functions such as renderAll which calls the render function for each actor in the set. Actors which go off the screen are automatically deleted by the client (after confirmation by the server) since they are out of visible range and the client doesn't need to display them.

This class has a state variable which represents the current state of the game. the game could be in one of the following states:

  * LOGIN\_STATE
  * LOADING\_STATE
  * INGAME\_STATE

Depending on the state of the game the appropriate objects are created and rendered as and when required.

This class keeps track of global informations such as the name of the current client, the server IP address etc.

This class also receives keyboard input from the XCanvas object and processes it, performing the appropriate functions depending on the input.

# Conclusion #

You should now have a basic understanding of the functioning of the game client. For more detailed information, refer to issue solution document for the client or comments in the code. Don't forget to update any related documentation before you make any changes to the code.