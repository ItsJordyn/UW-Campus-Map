package graph.junitTests;

import graph.CreateGraph;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

/**
 * CreateGraphTest is a glassbox test of the CreateGraph class
 *
 */
public class CreateGraphTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private static CreateGraph<String, String> graph = new CreateGraph<String, String>();
    private static CreateGraph<String, String> graph1 = new CreateGraph<String, String>();
    private static CreateGraph<String, String> graph2 = new CreateGraph<String, String>();
    private static final String node1 = "hello";
    private static final String node2 = "goodbye";
    private static final String node3 = "yoyo";
    private static final String edge1 = "lovely";
    private static final String edge2 = "wonderful";
    private static final String edge3 = "Wow";
    private static CreateGraph.Edge<String, String> e1;
    private static CreateGraph.Edge<String, String> e2;

    @BeforeClass
    public static void setUp() {
        graph1.addNode(node1);
        graph2.addNode(node2);
        graph2.addNode(node1);
        e1 = new CreateGraph.Edge<String, String>(node1, node2, edge1);
        e2 = new CreateGraph.Edge<String, String>(node2, node1, edge2);
        graph2.addEdge(node2, node1, edge2);
    }

    @Test
    public void testContainsNode() {
        // Node doesn't exist
        assertFalse(graph.containsNode(node1));
        assertFalse(graph.containsNode(node2));

        // Node exists
        assertTrue(graph1.containsNode(node1));
        assertTrue(graph2.containsNode(node1));
    }

    @Test
    public void testGetParent() {
        // Correct parent
        assertTrue(e1.getParent().equals(node1));
        assertTrue(e2.getParent().equals(node2));

        // Incorrect parent
        assertFalse(e1.getParent().equals(node2));
        assertFalse(e2.getParent().equals(node1));
    }

    @Test
    public void testGetChild() {
        // Correct Child
        assertTrue(e1.getChild().equals(node2));
        assertTrue(e2.getChild().equals(node1));

        // Incorrect child
        assertFalse(e1.getChild().equals(node1));
        assertFalse(e2.getChild().equals(node2));

    }

    @Test
    public void testGetLabel() {
        // Correct label
        assertTrue(e1.getLabel().equals(edge1));
        assertTrue(e2.getLabel().equals(edge2));

        // Incorrect label
        assertFalse(e1.getLabel().equals(edge2));
        assertFalse(e2.getLabel().equals(edge1));
    }
}