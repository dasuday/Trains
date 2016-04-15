# Trains Project: Solution to http://www.icstars.org/node/285 
# By Udayan Das, 4/15/2016

# External Libraries
This project uses the Weighted Directed Graph implementation from the JGraphT project library. 

Project webpage: http://jgrapht.org/
GitHub: https://github.com/jgrapht
SourceForge: https://sourceforge.net/projects/jgrapht/
JavaDocs: http://jgrapht.org/javadoc/

JGraphT version 0.9.2

#JRE System Library
Built on JavaSE-1.7 using Eclipse IDE

# Basic Description
As indicated on the page on i.c.stars (http://www.icstars.org/node/285) this is a graph problem looking at path traversal, discovery, and searches on a Weighted Directed Graph. 

# Input
Input here is being defined in a textfile called "input.txt." Each line of the text file contains information regarding one edge of the graph. 

Each line of the input file has three comma separated values. The first value is the name of the origin vertex, the second value is the incident vertex and the third value is the edge weight.

Thus the following line in the input file would represent the edge A-->E with a weight of 5:
A,E,5

# Notes on present versions
Trains.java: This implementation is simple aimed at responding to the questions on the problem page. 
Trains2.java: Modified Trains.java to have a method based implementation. 

The next stage is to drive the process with a testfile. 
