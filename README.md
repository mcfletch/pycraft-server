# PyCraft Backend Server Bukkit Plugin

This is a more modern backend server for [pycraft](https://github.com/mcfletch/pycraft) that does not 
attempt to provide support for older scripts written
to the mcpi api, instead it attempts to provide a 
solid foundation for writing Minecraft manipulation
code from Python.

## What it Lets You Do

Provides access to a significant subset of the Bukkit API
as maintained by the Spigot project. That means that your
Python (or any JSON capable) code can create and manipulate:

* BlockData
* Itemstacks (inventory)
* Enchantments
* Entities
* World Generators (e.g. generating trees)

and the like from outside of the server process.

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
cp target/PycraftServer-1.0.1.jar ${YOUR_WORLD}/plugins/
../run.py -e -d ${YOUR_WORLD}
```