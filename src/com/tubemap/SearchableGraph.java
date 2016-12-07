package com.tubemap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tubemap.objects.Edge;
import com.tubemap.objects.Vertex;

/**
 * Usable graph of the tube network
 *
 */
public class SearchableGraph extends Graph implements RouteFinder
{
	private Set<Vertex> settledVertices;
	private Set<Vertex> unSettledVertices;

	public SearchableGraph(String tubeNetworkDescriptionFilePath) throws Exception {
		super(tubeNetworkDescriptionFilePath);
	}

	@Override
	public List<String> anyOptimalRoute(String originStation, String destinationStation) {
		cleanState();

		Vertex start = graphMap.get(originStation);
		Vertex end = graphMap.get(destinationStation);
		start.distance = 0;
		start.comingFrom = "start";
		unSettledVertices.add(start);

		while (!unSettledVertices.isEmpty() && !settledVertices.contains(end)) {
			Vertex node = getMinimum(unSettledVertices);
			settledVertices.add(node);
			unSettledVertices.remove(node);
			findMinimalDistances(node);
		}

		return getPath(end);
	}

	private void findMinimalDistances(Vertex node) {
		List<Vertex> adjacentNodes = getNeighbors(node);
		for (Vertex target : adjacentNodes) {
			if (target.distance > node.distance + getDistance(node, target)) {
				target.distance = node.distance + getDistance(node, target);
				target.predecessor = node.name;
				target.comingFrom = getOrigin(node, target);
				unSettledVertices.add(target);
			}
		}

	}

	private int getDistance(Vertex node, Vertex target) {
		for (Edge edge : node.connections) {
			if (edge.end.equals(target)) {
				return node.comingFrom.equals("start") || edge.line.equals(node.comingFrom) ? 2 : 6;
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private List<Vertex> getNeighbors(Vertex node) {
		List<Vertex> neighbors = new ArrayList<Vertex>();
		for (Edge edge : node.connections) {
			if (!settledVertices.contains(edge.end)) {
				neighbors.add(edge.end);
			}
		}
		return neighbors;
	}

	private Vertex getMinimum(Set<Vertex> vertexes) {
		Vertex minimum = null;
		for (Vertex vertex : vertexes) {
			if (minimum == null) {
				minimum = vertex;
			}
			else {
				if (vertex.distance < minimum.distance) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	private String getOrigin(Vertex start, Vertex end) {
		if (start.comingFrom != "start")
			return start.comingFrom;
		else {
			for (Edge edge : start.connections)
				if (edge.end.equals(end)) {
					return edge.line;
				}
		}
		throw new RuntimeException("Could not find edge between " + start.name + " and " + end.name);
	}

	private List<String> getPath(Vertex target) {
		List<String> path = null;
		Vertex step = target;

		if (step.predecessor != null) {
			path = new ArrayList<String>();
			path.add(step.name);

			while (step.predecessor != null) {
				step = graphMap.get(step.predecessor);
				path.add(step.name);
			}

			// Put it into the correct order
			Collections.reverse(path);
		}

		return path;
	}
	
	private void cleanState(){
		settledVertices = new HashSet<>();
		unSettledVertices = new HashSet<>();
		
		for(Vertex vtx : graphMap.values()){
			vtx.comingFrom = null;
			vtx.predecessor = null;
			vtx.distance = Integer.MAX_VALUE;
		}
	}

}
