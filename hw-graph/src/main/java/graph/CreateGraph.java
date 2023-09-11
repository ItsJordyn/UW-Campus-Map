package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Creates a mutable graph with nodes and edges
 */
public class CreateGraph<N, E> { // CHANGED TO GENERIC
    public static final boolean DEBUG = false;

    // RI: graphNodes != null, graphEdges != null and there are no duplicate nodes or edges
    // AF(This): a graph represented by a list of nodes this.graphNodes and edges this.graphEdges
    private List<N> graphNodes; // CHANGED STRING TO GENERIC N
    private List<Edge<N, E>> graphEdges; // CHANGED EDGE TO GENERIC E

    private void checkRep() {
        assert graphNodes != null;
        assert graphEdges != null;
        if (DEBUG) {
            for (int i = 0; i < graphEdges.size(); i++) {
                assert graphEdges.get(i) != null;
                assert graphEdges.indexOf(graphEdges.get(i)) == i;
            }
            for (int i = 0; i < graphNodes.size(); i++) {
                assert graphNodes.get(i) != null;
                assert graphNodes.indexOf(graphNodes.get(i)) == i;
            }
        }
    }

    /**
     * Creates an empty graph
     * @spec.effects creates an empty graph
     */
    public CreateGraph() {
        graphNodes = new ArrayList<N>(); // Changed string to generic N
        graphEdges = new ArrayList<Edge<N, E>>(); // changed Edge to generic
        checkRep();
    }

    /**
     * Adds a node to the graph
     * @param node the label of the node being added to the graph
     * @spec.modifies this
     * @spec.effects adds a node to this graph
     */
    public void addNode(N node) { // changed string to generic N
        checkRep();
        if (!containsNode(node)) {
            graphNodes.add(node);
        }
        checkRep();
    }

    /**
     * Adds an edge to the graph
     * @param parent the node an edge is stemming from
     * @param child the node the edge is pointing to
     * @param label the name given to the edge
     * @spec.modifies this
     * @spec.effects adds an edge to this graph
     */
    public void addEdge(N parent, N child, E label) {
        checkRep();
        Boolean exists = false;
        if (graphNodes.contains(parent) && graphNodes.contains(child)) {
            for (Edge<N, E> current : graphEdges) {
                if (current.getParent().equals(parent) && current.getChild().equals(child) && current.getLabel().equals(label)) {
                    exists = true;
                }
            }
        }
        if (!exists) {
            graphEdges.add(new Edge<N, E>(parent, child, label));
        }
        checkRep();
    }

    /**
     * Retrieves all the children from a singular node
     * @param parent the node the children nodes are stemming from
     * @return a list of all the children from a singular parent node
     * @spec.requires this graph must have nodes (children and parent) with connecting edges
     */
    public List<N> getChildren(N parent) {
        // Traverse through all the edges that contain the parent,
        // then if it exists, get the child and put it in the list
        // once done traversing return the list
        checkRep();
        List<N> nodeChildren = new ArrayList<N>();
        for (Edge<N, E> current : graphEdges) {
            if (current.getParent().equals(parent)) {
                nodeChildren.add(current.getChild());
            }
        }
        checkRep();
        return nodeChildren;

    }

    /**
     * Retrieves all the nodes within the graph
     * @return a list of all the nodes in the graph
     */
    public List<N> getAllNodes() {
        // return the graphNodes list
        checkRep();
        return graphNodes;
    }

    /**
     * Retrieves all the edges within the graph
     * @return a list of all the edges in the graph
     */
    public List<Edge<N, E>> getAllEdges() {
        // return the graphEdges list
        checkRep();
        return graphEdges;
    }

    /**
     * Retrieves all the edges pointing out from a node
     * @return a list of the outgoing edges from a single node
     */
    public List<Edge<N, E>> edgesOut(N parent) {
     // traverse list of edges
     // if the edges parent is the target node then you add it to the list
     // return that list
        List<Edge<N, E>> outgoingEdges = new ArrayList<Edge<N, E>>();
        for (Edge<N, E> current : graphEdges) {
            if (current.getParent().equals(parent)) {
                outgoingEdges.add(current);
            }
        }
       // Collections.sort(outgoingEdges);
        return outgoingEdges;
    }

    /**
     * Checks if the graph has a particular node
     * @param node the node being questioned if it resides in the graph
     * @return boolean true if graph contains a specified node, false otherwise
     */
    public boolean containsNode(N node) {
        // traverse through the list graphNodes.
        // if node exists, return yes, otherwise no
        checkRep();
        return graphNodes.contains(node);
    }

    /**
     * Creates an Edge
     */
    public static class Edge<N, E> {
        // RI: edgeName != null and parentName != null and childName != null
        // AF(This): an edge represented by a parent node this.parentName, a child node this.childName
        // and a label this.edgeName
        private final E edgeName;
        private final N parentName;
        private final N childName;

        private void checkRep() {
            assert edgeName != null;
            assert parentName != null;
            assert childName != null;
        }

        /**
         * Creates a new edge
         * @param parent the node an edge is stemming from
         * @param child the node the edge is pointing to
         * @param label the name given to the edge
         * @spec.effects Creates a new edge
         */
        public Edge(N parent, N child, E label) {
            this.edgeName = label;
            this.parentName = parent;
            this.childName = child;
            checkRep();
        }

        /**
         * Retrieves the source of an edge
         * @return the node an edge points from
         */
        public N getParent() {
            checkRep();
            return parentName;
        }

        /**
         * Retrieves the node(s) the edge is pointing to
         * @return the node an edge points to
         */
        public N getChild() {
            checkRep();
            return childName;
        }

        /**
         * Retrieves the label assigned to an edge
         * @return a Label for an edge
         */
        public E getLabel() {
            checkRep();
            return edgeName;
        }
    }
}
