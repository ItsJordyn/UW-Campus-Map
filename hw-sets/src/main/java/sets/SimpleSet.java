package sets;

import java.util.List;

/**
 * Represents an immutable set of points on the real line that is easy to
 * describe, either because it is a finite set, e.g., {p1, p2, ..., pN}, or
 * because it excludes only a finite set, e.g., R \ {p1, p2, ..., pN}. As with
 * FiniteSet, each point is represented by a Java float with a non-infinite,
 * non-NaN value.
 */
public class SimpleSet {

  // TODO: fill in and document the representation
  //       Make sure to include the representation invariant (RI)
  //       and the abstraction function (AF).

  // This class represents either a finite set of points (hence field FiniteSet) or
  // all real numbers excluding a given finite set of points depending on if boolean
  // complement is true or false.

  // Rep Inv: points != null, does not contain, non numbers, infinities, or duplicates
  //          l = list of points (points.getPoints)
  //          negative infinity = l[0] < l[1] < ... < l[l.size - 1] = positive infinity

  // AF: this represents either a finite set of real numbers (FiniteSet) when complement is false
  //     or a set of all real numbers except that finite set when complement is true.
  private final FiniteSet points;
  private final boolean complement;

  /**
   * Creates a simple set containing only the given points.
   * @param vals Array containing the points to make into a SimpleSet
   * @spec.requires points != null and has no NaNs, no infinities, and no dups
   * @spec.effects this = {vals[0], vals[1], ..., vals[vals.length-1]}
   */
  public SimpleSet(float[] vals) {
    // TODO: implement this
    this.points = FiniteSet.of(vals);
    this.complement = false;
  }

  /**
   * Private constructor that directly fills in the fields above.
   * @param complement Whether this = points or this = R \ points
   * @param points List of points that are in the set or not in the set
   * @spec.requires points != null
   * @spec.effects this = R \ points if complement else points
   */
  private SimpleSet(boolean complement, FiniteSet points) {
    // TODO: implement this
    this.complement = complement;
    this.points = points;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SimpleSet))
      return false;

    SimpleSet other = (SimpleSet) o;
    return this.points.equals(other.points) && this.complement == other.complement;  // TODO: replace this with a correct check
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Returns the number of points in this set.
   * @return N      if this = {p1, p2, ..., pN} and
   *         infty  if this = R \ {p1, p2, ..., pN}
   */
  // This works because if it is true that this is representing the
  // complement, the size is an infinite float. Otherwise, if it is not
  // true that this is representing a complement, therefore representing a
  // finite set, it should return the number of elements in that set. Which
  // it does.
  public float size() {
    // TODO: implement this
    if (complement) {
      return Float.POSITIVE_INFINITY;
    } else {
      return this.points.size();
    }

  }

  /**
   * Returns a string describing the points included in this set.
   * @return the string "R" if this contains every point,
   *     a string of the form "R \ {p1, p2, .., pN}" if this contains all
   *        but {@literal N > 0} points, or
   *     a string of the form "{p1, p2, .., pN}" if this contains
   *        {@literal N >= 0} points,
   *     where p1, p2, ... pN are replaced by the individual numbers.
   */
  // Code works because it takes into account the different cases in which
  // this represents a complement or a finite set of points. Also takes into
  // account if this set represents all numbers, in which case it returns R, and
  // also an empty set, in which case it returns {}. Otherwise it builds up a string
  // starting with { and adding each float value in the set followed by a comma until the
  // last float value in the set in which it is followed by a }. If it is a complement set,
  // it will return a R \ and the finite set that it is excluding. This method accurately
  // implements this.
  public String toString() {
    // TODO: implement this with a loop. document its invariant
    //       a StringBuilder may be useful for creating the string

    StringBuilder result = new StringBuilder();

    if (this.complement && this.points.size() == 0) {
      return "R";
    } else if (!this.complement && this.points.size() == 0) {
      return "{}";
    } else if (this.complement && this.points.size() > 0) {
      result.append("R \\ ");
    }

    result.append("{");

    int i = 0;
    List<Float> listPoints = this.points.getPoints();

    // Inv: result = {listPoints.get(0), listPoints.get(1), ... listPoints.get(i-1), listPoints.get(i)}
    while (i != this.points.size() - 1) {
      result.append(listPoints.get(i));
      result.append(", ");
      i = i + 1;
    }

    result.append(listPoints.get(this.points.size() - 1));
    result.append("}");

    return result.toString();

  }

  /**
   * Returns a set representing the points R \ this.
   * @return R \ this
   */
  // This works because if it is true that this represents the complement of a
  // finite set of points, then the complement of the complement of a finite set
  // of points is simply that finite set of points, which it returns. Otherwise, if
  // this represents a finite set of points, it will return that set of points and
  // change the boolean complement to true to indicate that it represents all real
  // numbers but that finite set of points. Which it does.
  public SimpleSet complement() {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct (hint: cases)
    return new SimpleSet(!this.complement, this.points);
  }

  /**
   * Returns the union of this and other.
   * @param other Set to union with this one.
   * @spec.requires other != null
   * @return this union other
   */
  // The union method works because it takes into account the different cases,
  // whether this set of points is the complement or finite, and whether other set
  // of points is the complement or finite. Due to these different cases, it acts accordingly
  // and returns a set with the union of the points that this and other represent. Because
  // this and other represent different points, it implements other methods from FiniteSet such
  // as intersection, and difference, because those are needed to accurately create a set that represents
  // the union between this and other.
  public SimpleSet union(SimpleSet other) {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct (hint: cases)
    if (!this.complement && !other.complement) {
      return new SimpleSet(false, this.points.union(other.points));
    } else if (this.complement && other.complement) {
      return new SimpleSet(true, this.points.intersection(other.points));
    } else if (!this.complement && other.complement) {
      return new SimpleSet(true, other.points.difference(this.points));
    } else {
      return new SimpleSet(true, this.points.difference(other.points));
    }
  }

  /**
   * Returns the intersection of this and other.
   * @param other Set to intersect with this one.
   * @spec.requires other != null
   * @return this intersect other
   */
  // The union method works because it takes into account the different cases,
  // whether this set of points is the complement or finite, and whether other set
  // of points is the complement or finite. Due to these different cases, it acts accordingly
  // and returns a set with the intersection of this SimpleSet and other SimpleSet. Again, because
  // this and other represent different points, either a set of points or everything but a specific set
  // of points, other FiniteSet methods are implemented (union and difference) to accurately create a set
  // that the intersection of this and other represents.
  public SimpleSet intersection(SimpleSet other) {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct
    // NOTE: There is more than one correct way to implement this.

    if (!this.complement && !other.complement) {
      return new SimpleSet(false, this.points.intersection(other.points));
    } else if (this.complement && other.complement) {
      return new SimpleSet(true, this.points.union(other.points));
    } else if (!this.complement && other.complement) {
      return new SimpleSet(false, this.points.difference(other.points));
    } else {
      return new SimpleSet(false, other.points.difference(this.points));
    }
  }

  /**
   * Returns the difference of this and other.
   * @param other Set to difference from this one.
   * @spec.requires other != null
   * @return this minus other
   */
  // The union method works because it takes into account the different cases,
  // whether this set of points is the complement or finite, and whether other set
  // of points is the complement or finite. Due to these different cases, it acts accordingly
  // and returns a set with the difference of this SimpleSet and other SimpleSet. Again, because
  // this and other either represents a finite set or a set excluding a specified finite set,
  // the FiniteSet methods (union and intersection) are needed to create a SimpleSet that accurately
  // represents the difference of this SimpleSet and other SimpleSet.
  public SimpleSet difference(SimpleSet other) {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct
    // NOTE: There is more than one correct way to implement this.
    if (!this.complement && !other.complement) {
      return new SimpleSet(false, this.points.difference(other.points));
    } else if (this.complement && other.complement) {
      return new SimpleSet(false, other.points.difference(this.points));
    } else if (!this.complement && other.complement) {
      return new SimpleSet(false, this.points.intersection(other.points));
    } else {
      return new SimpleSet(true, this.points.union(other.points));
    }
  }

}
