Tubemap
=============

The goal of this exercise is to calculate the shortest path to go from one station to another in a defined tube map.

Rules
-------

* It takes 2 minutes to travel between adjacent stations on the same line, whatever branch you take.
* It takes 6 minutes to travel between adjacent stations while changing line.


Map definition
-------

Tube maps are defined in a text file with records separated by newlines and record fields separated by pipes, as in a csv like file. Each record holds the name of a tube line and the names of a sequence of two or more stations lying on that line. A tube line can appear multiple times where it has multiple branches (see example below). 

```
Jubilee|Waterloo|Southwark|London Bridge
Northern|Oval|Kennington|Elephant & Castle|Borough|London Bridge
Northern|Kennington|Waterloo|Embankment
Bakerloo|Elephant & Castle|Lambeth North|Waterloo|Embankment
```
 


Method
-------

The method used is an implementation of Dijkstra's algorithm used to calculate the shortest path between tube stations.
The distance/weight is calculated by checking if we are changing line between two stations.