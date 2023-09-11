/*
 * Copyright (C) 2022 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder;

import graph.CreateGraph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

public class CampusMap implements ModelAPI {

    // RI: buildings is not empty and buildingNames is not empty
    // AF(this): represents a map of campus buildings with a long and short name
    private Map<String, String> buildingNames;
    private List<CampusBuilding> buildings;

    private void checkRep() {
        assert buildings != null;
        Set<String> shortNamesList = buildingNames.keySet();
        assert shortNamesList != null;
        for (String shortName : shortNamesList) {
            assert shortName != null;
            assert buildingNames.get(shortName) != null;
        }
    }

    /**
     * @spec.affects creates an empty map
     */
    public CampusMap() {
        buildingNames = new HashMap<String, String>();
        buildings = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        for (CampusBuilding building : buildings) {
            buildingNames.put(building.getShortName(), building.getLongName());
        }
        checkRep();
    }

    /**
     * checks if a short name exists
     * @param shortName The short name of a building to query.
     * @return true if short name exists, false otherwise
     */
    @Override
    public boolean shortNameExists(String shortName) {
        checkRep();
        return buildingNames.containsKey(shortName);
    }

    /**
     * finds the long name to a short name
     * @param shortName The short name of a building to look up.
     * @return the long name associated to a specific short name
     */
    @Override
    public String longNameForShort(String shortName) {
        checkRep();
        if (!shortNameExists(shortName)) {
            throw new IllegalArgumentException();
        }
        checkRep();
        return buildingNames.get(shortName);
    }

    /**
     * @return the map of short names and long names
     */
    @Override
    public Map<String, String> buildingNames() {
        checkRep();
        return buildingNames;
    }

    /**
     * finds the shortest path between two buildings
     * @param startShortName The short name of the building at the beginning of this path.
     * @param endShortName   The short name of the building at the end of this path.
     * @return the shortest path between two buildings
     */
    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        checkRep();
        if (startShortName == null || endShortName == null || !shortNameExists(startShortName) || !shortNameExists(endShortName)) {
            throw new IllegalArgumentException();
        }

        List<CampusPath> pathList = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        CreateGraph<Point, Double> campusGraph = new CreateGraph<Point, Double>();

        for (CampusPath path : pathList) {
            campusGraph.addNode(new Point(path.getX1(), path.getY1()));
        }

        for (CampusPath path : pathList) {
            Point parent = new Point(path.getX1(), path.getY1());
            Point child = new Point(path.getX2(), path.getY2());
            campusGraph.addEdge(parent, child, path.getDistance());
        }

        Point startNode = null;
        Point endNode = null;

        for (CampusBuilding building : buildings) {
            if (building.getShortName().equals(startShortName)) {
                startNode = new Point(building.getX(), building.getY());
            }
            if (building.getShortName().equals(endShortName)) {
                endNode = new Point(building.getX(), building.getY());
            }
        }

        Path<Point> path = DijkAlgo.minCostPath(startNode, endNode, campusGraph);
        checkRep();
        return path;
    }

}
