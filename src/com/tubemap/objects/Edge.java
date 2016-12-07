package com.tubemap.objects;

/**
 * The edge contains a tube line information
 *
 */
public class Edge
{
	public Edge(Vertex start, Vertex end, String line) {
		this.start = start;
		this.end = end;
		this.line = line;
	}

	public Vertex start;
	public Vertex end;
	public String line;

}
