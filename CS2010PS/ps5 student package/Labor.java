import java.util.*;
import java.io.*;

// write your matric number here:
// write your name here:
// write list of collaborators here (reading someone's post in Facebook Group and using the idea is counted as collaborating):

class Labor {
  private int V; // number of vertices in the graph (number of junctions in Singapore map)
  private Vector < Vector < IntegerPair > > AdjList; // the weighted graph (the Singapore map), the length of each edge (road) is stored here too, as the weight of edge

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  // --------------------------------------------



  // --------------------------------------------

  public Labor() {
    // Write necessary codes during construction
    //
    // write your answer here



  }

  int Query() {
    int ans = 0;

    // You have to report the shortest path from Steven and Grace's home (vertex 0)
    // to reach their chosen hospital (vertex 1)
    //
    // PS: this query means different thing for the Subtask E (R-option) and also the recreational (non-graded) Subtask
    //
    // write your answer here



    //------------------------------------------------------------------------- 

    return ans;
  }

  // You can add extra function if needed
  // --------------------------------------------



  // --------------------------------------------

  void run() throws Exception {
    // you can alter this method if you need to do so
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      V = sc.nextInt();

      // clear the graph and read in a new graph as Adjacency List
      AdjList = new Vector < Vector < IntegerPair > >();
      for (int i = 0; i < V; i++) {
        AdjList.add(new Vector<IntegerPair>());

        int k = sc.nextInt();
        while (k-- > 0) {
          int j = sc.nextInt(), w = sc.nextInt();
          AdjList.get(i).add(new IntegerPair(j, w)); // edge (road) weight (in minutes) is stored here
        }
      }

      pr.println(Query());
    }

    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    Labor ps5 = new Labor();
    ps5.run();
  }
}



class IntegerScanner { // coded by Ian Leow, using any other I/O method is not recommended
  BufferedInputStream bis;
  IntegerScanner(InputStream is) {
    bis = new BufferedInputStream(is, 1000000);
  }
  
  public int nextInt() {
    int result = 0;
    try {
      int cur = bis.read();
      if (cur == -1)
        return -1;

      while ((cur < 48 || cur > 57) && cur != 45) {
        cur = bis.read();
      }

      boolean negate = false;
      if (cur == 45) {
        negate = true;
        cur = bis.read();
      }

      while (cur >= 48 && cur <= 57) {
        result = result * 10 + (cur - 48);
        cur = bis.read();
      }

      if (negate) {
        return -result;
      }
      return result;
    }
    catch (IOException ioe) {
      return -1;
    }
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
