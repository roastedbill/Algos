import java.util.*;

class LaborVerifier {
  private static Vector < Vector < IntegerPair > > AdjList;
  private static Vector < Integer > visited;

  private static void DFSrec(int u) {
    visited.set(u, 1);
    for (int j = 0; j < AdjList.get(u).size(); j++) {
      IntegerPair v = AdjList.get(u).get(j);
      if (visited.get(v.first()) == 0)
        DFSrec(v.first());
    }
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int i, j, k, w, TC = sc.nextInt();
    long V, E; // the values of V * E can be quite big for Subtask D

    while (TC-- > 0) {
      V = sc.nextLong();
      AdjList = new Vector < Vector < IntegerPair > > ();
      for (E = i = 0; i < V; i++) {
        AdjList.add(new Vector<IntegerPair>());
        k = sc.nextInt();
        E += k;

        while (k-- > 0) {
          j = sc.nextInt(); w = sc.nextInt();
          if (w < 0 || w > 1000) {
            System.out.println("ERROR AT " + TC + "-th/nd/st TESTCASE FROM END, EDGE WEIGHT MUST BE NON-NEGATIVE AND AT MOST 1000");
            return;
          }
          AdjList.get(i).add(new IntegerPair(j, w)); // edge (road) weight (length of road) is stored here
        }
      }

      if (1 <= V * E && V * E <= 1000000)
        System.out.println("V = " + V + " and E = " + E + ", valid for Subtask C as V * E = " + (V * E) + " <= 1000000");
      else
        System.out.println("V = " + V + " and E = " + E + ", NOT valid for Subtask C as V * E = " + (V * E) + " > 1000000");

      if (1 <= V + E && V + E <= 250000)
        System.out.println("V = " + V + " and E = " + E + ", valid for Subtask D as V + E = " + (V + E) + " <= 250000");
      else
        System.out.println("V = " + V + " and E = " + E + ", NOT valid for Subtask D as V + E = " + (V + E) + " > 250000");

      // Check if there is at least one path from vertex 0 to 1
      visited = new Vector < Integer > ();
      visited.addAll(Collections.nCopies((int)V, 0));
      DFSrec(0);
      if (visited.get(1) == 0) {
        System.out.println("ERROR AT " + TC + "-th/nd/st TESTCASE FROM END, THERE MUST BE AT LEAST ONE PATH FROM VERTEX 0->1");
        return;
      }
    }

    System.out.println("Test data is valid :)");
  }
}



class IntegerPair implements Comparable<IntegerPair> {
  Integer _first, _second;

  public IntegerPair(Integer f, Integer s) {
    _first = f;
    _second = s;
  }

  public int compareTo(IntegerPair o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else
      return this.second() - o.second();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
}
