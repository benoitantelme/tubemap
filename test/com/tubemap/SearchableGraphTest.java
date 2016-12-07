package com.tubemap;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.tubemap.SearchableGraph;

public class SearchableGraphTest
{

	@Test
	public void NoFileSearchTest() throws URISyntaxException {
		boolean failed = false;
		try {
			new SearchableGraph("");
		}
		catch (Exception e) {
			failed = true;
		}

		assertTrue(failed);
	}

	@Test
	public void EmptyFileSearchTest() throws Exception {
		boolean failed = false;
		try {
			new SearchableGraph("empty.txt");
		}
		catch (Exception e) {
			failed = true;
		}
		assertTrue(failed);

	}

	@Test
	public void SimpleSearchTest() throws Exception {
		SearchableGraph graph = new SearchableGraph("map.txt");

		List<String> result = graph.anyOptimalRoute("Oval", "Southwark");

		assertNotNull(result);
		assertEquals(4, result.size());
		assertEquals(Arrays.asList(new String[] { "Oval", "Kennington", "Waterloo", "Southwark" }), result);
	}
	
	@Test
	public void AlternativeSearchTest() throws Exception {
		SearchableGraph graph = new SearchableGraph("test.txt");

		List<String> result = graph.anyOptimalRoute("A", "B");

		assertNotNull(result);
		assertEquals(5, result.size());
		assertEquals(Arrays.asList(new String[] { "A", "3", "4", "5", "B" }), result);
	}
	
	@Test
	public void ReuseSearchTest() throws Exception {
		SearchableGraph graph = new SearchableGraph("map.txt");

		List<String> result = graph.anyOptimalRoute("Oval", "Southwark");

		assertNotNull(result);
		assertEquals(4, result.size());
		assertEquals(Arrays.asList(new String[] { "Oval", "Kennington", "Waterloo", "Southwark" }), result);
		
		result = graph.anyOptimalRoute("London Bridge", "Embankment");

		assertNotNull(result);
		assertEquals(4, result.size());
		assertEquals(Arrays.asList(new String[] { "London Bridge", "Southwark", "Waterloo", "Embankment" }), result);
	}

}
