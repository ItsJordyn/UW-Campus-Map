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

package pathfinder.scriptTestRunner;

import graph.CreateGraph;
import pathfinder.DijkAlgo;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
// This class does not represent an ADT
public class PathfinderTestDriver {

    // Leave this constructor public

    /**
     *
     * @param r
     * @param w
     */
    public PathfinderTestDriver(Reader r, Writer w) {
        // TODO: Implement this, reading commands from `r` and writing output to `w`.
        // See GraphTestDriver as an example.
        input = new BufferedReader(r);
        output = new PrintWriter(w);

    }



// START HERE!


    // ***************************
    // ***  JUnit Test Driver  ***
    // ***************************

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    // TODO for the student: Uncomment and parameterize the next line correctly:
    private final Map<String, CreateGraph<String, Double>> graphs = new HashMap<String, CreateGraph<String, Double>>();
    private final PrintWriter output;
    private final BufferedReader input;


    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "FindPath":
                    findPath(arguments);
                    break;
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        // TODO Insert your code here.
        graphs.put(graphName, new CreateGraph<String, Double>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        // TODO Insert your code here.
        CreateGraph<String, Double> currentGraph = graphs.get(graphName);

        currentGraph.addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        Double edgeLabel = Double.valueOf(arguments.get(3)); // UM IS THIS ALLOWED?? DOUBLE.VALUEOF

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {
        // TODO Insert your code here.
        // Maybe?

        CreateGraph<String, Double> currentGraph = graphs.get(graphName);
        // is this top part incorrect and this bottom part is what is needed?
        // graphs.get(graphName).addEdge(parentName, childName, edgeLabel);
        currentGraph.addEdge(parentName, childName, edgeLabel);
        String edgeLabel1 = String.format("%.3f", edgeLabel);
        output.println("added edge " + edgeLabel1 + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        // TODO Insert your code here.
        CreateGraph<String, Double> currentGraph = graphs.get(graphName);
        List<String> allNodes = currentGraph.getAllNodes();
        Collections.sort(allNodes);

        output.print(graphName + " contains:");
        for (String node: allNodes) {
            output.print(" " + node);
        }
        output.println();
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        // TODO Insert your code here.
        CreateGraph<String, Double> currentGraph = graphs.get(graphName);

        List<String> children = currentGraph.getChildren(parentName);
        Collections.sort(children);

        Comparator<CreateGraph.Edge<String, Double>> comparator = new Comparator<CreateGraph.Edge<String, Double>>() {
            @Override
            public int compare(CreateGraph.Edge<String, Double> o1, CreateGraph.Edge<String, Double> o2) {
                return o1.getLabel().compareTo(o2.getLabel());
            }
        };

        List<CreateGraph.Edge<String, Double>> edgesOut = currentGraph.edgesOut(parentName);
        Collections.sort(edgesOut, comparator);

        output.print("the children of " + parentName + " in " + graphName + " are:");
        for (CreateGraph.Edge<String, Double> edge : edgesOut) {
            String edgeLabel = String.format("%.3f", edge.getLabel());
            output.print(" " + edge.getChild() + "(" + edgeLabel + ")");
        }
        output.println();
    }


    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to findPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String startNode = arguments.get(1);
        String endNode = arguments.get(2);
        findPath(graphName, startNode, endNode);
    }

    private void findPath(String graphName, String startNode, String endNode) {
        CreateGraph<String, Double> currentGraph = graphs.get(graphName);

        Path<String> path = DijkAlgo.minCostPath(startNode, endNode, currentGraph);

        if (startNode.equals(endNode)) {
            output.println("path from " + startNode + " to " + endNode + ":");
            output.println("total cost: 0.000");
        } else if (!currentGraph.containsNode(endNode) && currentGraph.containsNode(startNode)) {     // come back to this later!!!! Why is this always false?
            output.println("unknown: " + endNode);
        } else if (!currentGraph.containsNode(startNode) && currentGraph.containsNode(endNode)) {
            output.println("unknown: " + startNode);
        } else if (!currentGraph.containsNode(startNode) && !currentGraph.containsNode(endNode)) {
            output.println("unknown: " + startNode);
            output.println("unknown: " + endNode);
        } else if (path == null) {
            output.println("path from " + startNode + " to " + endNode + ":");
            output.println("no path found");
        } else {
            output.println("path from " + startNode + " to " + endNode + ":");
            Iterator<Path<String>.Segment> itr = path.iterator();
            while (itr.hasNext()) {
               Path<String>.Segment seg = itr.next();
               String segCost = String.format("%.3f", seg.getCost());
               output.println(seg.getStart() + " to " + seg.getEnd() + " with weight " + segCost);
            }
            String pathCost = String.format("%.3f", path.getCost());
            output.println("total cost: " + pathCost);
        }


    }




    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
