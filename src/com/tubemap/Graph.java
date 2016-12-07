package com.tubemap;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import com.tubemap.objects.Edge;
import com.tubemap.objects.Vertex;

/**
 * Base class to create graphs from input files
 *
 */
public class Graph
{
	protected Map<String, Vertex> graphMap = new HashMap<>();

	public Graph(String tubeNetworkDescriptionFilePath) throws Exception {
		super();

		Path filePath = checkFilePath(tubeNetworkDescriptionFilePath);

		try (Stream<String> stream = Files.lines(filePath)) {
			stream.forEach(str -> addALine(str));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		if (graphMap.isEmpty())
			throw new Exception("Graph is empty after creation from file: " + filePath);
	}

	protected void printGraph() {
		for (Entry<String, Vertex> entry : graphMap.entrySet()) {
			System.out.println("For station " + entry.getKey() + ", connections are:");

			Vertex vtx = entry.getValue();
			for (Edge edge : vtx.connections) {
				System.out.println(edge.line + " line to " + edge.end.toString());
			}
		}
	}

	private void addALine(String lineDescription) {
		String[] decomposition = lineDescription.split("\\|");

		String lineName = decomposition[0];
		String[] stations = Arrays.copyOfRange(decomposition, 1, decomposition.length);

		for (int i = 0; i < stations.length; i++) {
			if (i == 0) {
				updateVertex(stations[i], lineName, stations[i + 1]);
			}
			else if (i > 0 && i <= stations.length - 2) {
				updateVertex(stations[i], lineName, stations[i - 1], stations[i + 1]);
			}
			else {
				updateVertex(stations[i], lineName, stations[i - 1]);
			}

		}
	}

	private void updateVertex(String vertexName, String lineName, String... neighbours) {
		if (!graphMap.containsKey(vertexName)) {
			graphMap.put(vertexName, new Vertex(vertexName));
		}

		Vertex vtx = graphMap.get(vertexName);
		for (String neighbour : neighbours) {
			if (!graphMap.containsKey(neighbour)) {
				graphMap.put(neighbour, new Vertex(neighbour));
			}

			Vertex neighbourVtx = graphMap.get(neighbour);
			vtx.connections.add(new Edge(vtx, neighbourVtx, lineName));
		}
	}

	private Path checkFilePath(String tubeNetworkDescriptionFilePath) throws Exception {
		File file = null;
		URL resource = SearchableGraph.class.getClassLoader().getResource(tubeNetworkDescriptionFilePath);
		if (resource != null) {
			file = Paths.get(resource.toURI()).toFile();
		}
		else {
			file = new File(tubeNetworkDescriptionFilePath);
		}

		Path filePath = Paths.get(file.getAbsolutePath());

		if (!Files.exists(filePath) || !Files.isReadable(filePath) || Files.isDirectory(filePath))
			throw new Exception("Can not read file: " + filePath);

		return filePath;
	}

}
