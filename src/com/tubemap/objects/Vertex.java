package com.tubemap.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * The vertex contains the distance (sum of weights), precedent vertex to get here by a shortest path and line that path is coming from
 * hashcode and equals methods use name, predecessor and distance in order to identify a path and not go into an infinite loop in some tricky cases
 *
 */
public class Vertex
{

	public Vertex(String name) {
		this.name = name;
	}

	public List<Edge> connections = new ArrayList<>();
	public String name;
	public int distance = Integer.MAX_VALUE;
	public String predecessor;
	public String comingFrom;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + distance;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((predecessor == null) ? 0 : predecessor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (distance != other.distance)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (predecessor == null) {
			if (other.predecessor != null)
				return false;
		}
		else if (!predecessor.equals(other.predecessor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vertex [name=" + name + ", distance=" + distance + ", predecessor=" + predecessor + ", comingFrom=" + comingFrom + "]";
	}

}
