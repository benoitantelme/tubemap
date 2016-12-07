package com.tubemap;

import java.util.List;

public interface RouteFinder
{
	List<String> anyOptimalRoute(String originStation, String destinationStation);
}
