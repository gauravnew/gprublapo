#This page contains a general overview of the client-server communications protocol.

# Introduction #

This page contains a general overview of the client-server communications protocol.

The following format is followed throughout :

```

(DIRECTION)[OPCODE]([LENGTH...])([...]...VARIABLES)  //Description

```

# Data Types Specification #

```

INT: 4 Byte Integer.

SHORT: 2 Byte Integer.

FLOAT: 4 Byte Single Precision IEEE Floating Point Number.

BINARY(N): N Byte Binary String.

```

# Protocol Specification #

```

[C->S]["PG"]	                                //Ping Request from client.

[S->C]["PG"]	                                //Ping Reply from Server.

[S->C]["MI"][INT Length][BINARY(Length) Data]   //Map Image file of given length. (.png)

[S->C]["MD"][INT Length][BINARY(Length) Data]   //Map Data file of given length. (.png)

[S->C]["MV"][INT actorID][FLOAT x][FLOAT y]     //Move actor with given ID to location (x,y)

[C->S]["LG"][BINARY(24) Name]                   //Name of the client logging in.

[C->S]["MV"][FLOAT x][FLOAT y]                  //Request a move to the given location.

```