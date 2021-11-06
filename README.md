# PyCraft Backend Server Bukkit Plugin

This is a more modern back end server for [pycraft](https://github.com/mcfletch/pycraft) that does not 
attempt to provide support for older scripts written
to the mcpi api, instead it attempts to provide a 
solid foundation for writing Minecraft manipulation
code from Python.

PycraftServer the plugin is a Java-coded bridge that uses a JSON-RPC
like system to semi-automatically expose the Bukkit API across a network
port. There is nothing particularly Python-specific about the plugin,
it just happens to have been written for use from Python.

## What it Lets You Do

Provides access to a significant subset of the [Bukkit API](https://hub.spigotmc.org/javadocs/spigot/index.html)
as maintained by the Spigot project. That means that your
Python (or any JSON capable) code can create and manipulate:

* BlockData
* Itemstacks (inventory)
* Enchantments
* Entities
* Events

and the like from outside of the server process.

Basically, if you can imagine doing something from within a
Spigot/Bukkit plugin you can likely do it through the interface.
However, note that this is just a hobby project, so areas of the
API I haven't myself used may not be wrapped particularly well.

## How it Works

Uses Java Reflection APIs (mostly) to provide a set of 
namespaces of methods which can be invoked over a network
socket. Uses (hand-coded) `Converter` classes which allow
for lookup of target classes via key, path, etc.

On the client side (in the pycraft project), uses the introspected API to provide 
a relatively directly exposure of the Java API to the Python
callers. The network protocol is very close to JSON, though
it is currently hand coded on the Java side (to avoid
a dependency that might not work under Bukkit Server).

## Build/Install Process

Note: If you are using Pycraft to setup your server, you don't need
to do this as it is done during the `run.py` script.

To *use* PycraftServer on a Spigot server, [download](https://github.com/mcfletch/pycraft-server/releases/) the `PycraftServer-*.jar` to your server's
plugin directory. **DO NOT** do this on a server that is running exposed to 
the Internet! PycraftServer is intended to let you control *anything* on the
server and you do **NOT** want that being done by random people on the 
internet.

To build from source:
```
mvn -e package
cp target/PycraftServer-1.0.2.jar ${YOUR_WORLD}/plugins/
../run.py -e -d ${YOUR_WORLD}
```

## Helping Out

Maven should setup the environment and run the build for you.
We're using [MockBukkit](https://github.com/MockBukkit/MockBukkit) to
provide the basic test framework. The current set of tests are
all in `AppTest.java`. Note that there are parts of the Bukkit API
that can't be tested under MockBukkit, so you will often have to 
actually load a Minecraft server with Spigot to test.

Pull requests are welcome, but please keep in mind that I have
a pretty long response-time on most of my hobby projects.
