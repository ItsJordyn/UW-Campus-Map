package sets;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

/**
 * FiniteSetTest is a glassbox test of the FiniteSet class.
 */
public class FiniteSetTest {

  /** Test creating sets. */
  @Test
  public void testCreation() {
    assertEquals(Arrays.asList(),
        FiniteSet.of(new float[0]).getPoints());         // empty
    assertEquals(Arrays.asList(1f),
        FiniteSet.of(new float[] {1}).getPoints());      // one item
    assertEquals(Arrays.asList(1f, 2f),
        FiniteSet.of(new float[] {1, 2}).getPoints());   // two items
    assertEquals(Arrays.asList(1f, 2f),
        FiniteSet.of(new float[] {2, 1}).getPoints());   // two out-of-order
    assertEquals(Arrays.asList(-2f, 2f),
        FiniteSet.of(new float[] {2, -2}).getPoints());  // negative
  }

  // Some example sets used by the tests below.
  private static FiniteSet S0 = FiniteSet.of(new float[0]);
  private static FiniteSet S1 = FiniteSet.of(new float[] {1});
  private static FiniteSet S12 = FiniteSet.of(new float[] {1, 2});

  /** Test set equality. */
  @Test
  public void testEquals() {
    assertTrue(S0.equals(S0));
    assertFalse(S0.equals(S1));
    assertFalse(S0.equals(S12));

    assertFalse(S1.equals(S0));
    assertTrue(S1.equals(S1));
    assertFalse(S1.equals(S12));

    assertFalse(S12.equals(S0));
    assertFalse(S12.equals(S1));
    assertTrue(S12.equals(S12));
  }

  /** Test set size. */
  @Test
  public void testSize() {
    assertEquals(S0.size(), 0);
    assertEquals(S1.size(), 1);
    assertEquals(S12.size(), 2);
  }
  
  // TODO: Feel free to initialize (private static) FiniteSet objects here
  //       if you plan to use them for the tests below
  private static FiniteSet exampleSet1 = FiniteSet.of(new float[] {3, 4});
  private static FiniteSet exampleSet2 = FiniteSet.of(new float[] {1, 2, 3, 4});
  private static FiniteSet exampleSet3 = FiniteSet.of(new float[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
  private static FiniteSet exampleSet4 = FiniteSet.of(new float[] {3, 4, 5, 6});
  private static FiniteSet exampleSet5 = FiniteSet.of(new float[] {5, 6});

  /** Tests forming the union of two finite sets. */
  @Test
  public void testUnion() {
    // TODO: implement this

    assertTrue(S12.union(exampleSet1).equals(exampleSet2));
    assertTrue(exampleSet1.union(S12).equals(exampleSet2));
    assertTrue(S0.union(S12).equals(S12));
    assertTrue(S12.union(S0).equals(S12));
    assertTrue(exampleSet1.union(exampleSet2).equals(exampleSet2));
    assertTrue(exampleSet2.union(exampleSet1).equals(exampleSet2));

    assertFalse(S12.union(exampleSet1).equals(S12));
    assertFalse(exampleSet1.union(S12).equals(S12));
    assertFalse(S0.union(S12).equals(S0));
    assertFalse(S12.union(S0).equals(S0));
    assertFalse(exampleSet1.union(exampleSet2).equals(exampleSet1));
    assertFalse(exampleSet2.union(exampleSet1).equals(exampleSet1));


    
  }

  /** Tests forming the intersection of two finite sets. */
  @Test
  public void testIntersection() {
    // TODO: implement this
    assertTrue(S12.intersection(exampleSet3).equals(S12));
    assertTrue(exampleSet3.intersection(S12).equals(S12));
    assertTrue(exampleSet4.intersection(exampleSet2).equals(exampleSet1));
    assertTrue(exampleSet2.intersection(exampleSet4).equals(exampleSet1));
    assertTrue(exampleSet1.intersection(exampleSet5).equals(S0));
    assertTrue(S0.intersection(S1).equals(S0));
    assertTrue(S1.intersection(S0).equals(S0));
    assertTrue(exampleSet3.intersection(exampleSet3).equals(exampleSet3));

    assertFalse(exampleSet5.intersection(exampleSet4).equals(exampleSet1));
    assertFalse(S0.intersection(S1).equals(S1));


    
  }

  /** Tests forming the difference of two finite sets. */
  @Test
  public void testDifference() {
    // TODO: implement this
    assertTrue(exampleSet4.difference(exampleSet1).equals(exampleSet5));
    assertTrue(exampleSet1.difference(exampleSet4).equals(S0));
    assertTrue(S0.difference(S0).equals(S0));
    assertTrue(exampleSet1.difference(S0).equals(exampleSet1));
    assertTrue(S0.difference(exampleSet1).equals(S0));
    assertTrue(exampleSet4.difference(exampleSet5).equals(exampleSet1));





  }

}
