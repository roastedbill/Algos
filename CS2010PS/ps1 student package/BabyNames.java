import java.util.*;
import java.io.*;

// A0105649B
// Liu Rensheng
// HuiHui, XieKai, http://users.cis.fiu.edu/~weiss/dsaajava/code/DataStructures/AvlTree.java

class BabyNames {
  protected BabyName rootM, rootF; // build two trees for different genders 
  
  // Global variables, faster than variable putting into recursion
  protected int queryCount;
  protected boolean isBigger;

  // Inner class as Vertex
  private class BabyName {
  
    public BabyName(String n) {
      name = n;

      left = right = null;
      height = 0;
      rank = 1; // use rank to calculate the nodes in a certain interval with logN complexity
    }

    public String name;
    public int height, rank;
    public BabyName left, right;

  }

  public BabyNames() {
    rootM = null;
    rootF = null;
    queryCount = 0;
    isBigger = false;
  }

  protected BabyName insert(BabyName N, String babyName) {
    if (N == null)
      N = new BabyName(babyName); // insertion point

    else if (N.name.compareTo(babyName) < 0) { // search to the right
      N.right = insert(N.right, babyName);
      if (height(N.right) - height(N.left) == 2) // check for rotation
        if (babyName.compareTo(N.right.name) > 0)
          N = rotateWithRightChild(N);
        else
          N = doubleWithRightChild(N);
    } else { // search to the left
      N.left = insert(N.left, babyName);
      if (height(N.left) - height(N.right) == 2) // check for rotation
        if (babyName.compareTo(N.left.name) < 0)
          N = rotateWithLeftChild(N); // new leaf on left side
        else
          N = doubleWithLeftChild(N); // new leaf on right side
    }
    N.height = max(height(N.left), height(N.right)) + 1; // update height
    N.rank = rank(N.left) + rank(N.right) + 1; // update rank
    return N; // return the updated BST
  }

  private static int rank(BabyName N){
    return N == null? 0 : N.rank;
  }
  
  private static int height(BabyName N) {
    return N == null ? -1 : N.height;
  }

  private static int max(int a, int b) {
    return a > b ? a : b;
  }

  private static BabyName rotateWithLeftChild(BabyName k2) {
    BabyName k1 = k2.left;
    k2.left = k1.right;
    k1.right = k2;
    k2.height = max(height(k2.left), height(k2.right)) + 1;
    k1.height = max(height(k1.left), k2.height) + 1;
    k2.rank = rank(k2.left) + rank(k2.right) + 1;
    k1.rank = rank(k1.left) + rank(k1.right) + 1;
    return k1;
  }

  private static BabyName rotateWithRightChild(BabyName k1) {
    BabyName k2 = k1.right;
    k1.right = k2.left;
    k2.left = k1;
    k1.height = max(height(k1.left), height(k1.right)) + 1;
    k2.height = max(height(k2.right), k1.height) + 1;
    k1.rank = rank(k1.left) + rank(k1.right) + 1;
    k2.rank = rank(k2.left) + rank(k2.right) + 1;
    return k2;
  }

  private static BabyName doubleWithLeftChild(BabyName k3) {
    k3.left = rotateWithRightChild(k3.left);
    return rotateWithLeftChild(k3);
  }

  private static BabyName doubleWithRightChild(BabyName k1) {
    k1.right = rotateWithLeftChild(k1.right);
    return rotateWithRightChild(k1);
  }

  public void AddSuggestion(String babyName, int genderSuitability) {
    if(genderSuitability == 1){
      rootM = insert(rootM, babyName);
    }else if(genderSuitability == 2){
      rootF = insert(rootF,babyName);
    }else{
      
    }
  }

  protected void startWithStr(BabyName N, String sta) {
    if (N == null)
      return; // not found
    
    // add or sub when isBigger toggers
      if(N.name.compareTo(sta)>=0){
        if(!isBigger){
          isBigger = true;
          queryCount += N.rank;
        }
        startWithStr(N.left,sta); 
      }else{
        if(isBigger){
          isBigger = false;
          queryCount -= N.rank;
        }
        startWithStr(N.right,sta);  
      }
  }
  
  int Query(String START, String END, int genderPreference) {
    int temp = 0;
    queryCount = 0;
    isBigger = false;
    
    // using global variable to save answer
    if(genderPreference == 1){
      startWithStr(rootM,START);
      temp = queryCount;
      queryCount = 0;
      isBigger = false;
      startWithStr(rootM,END);
      queryCount = temp - queryCount;
    }else if(genderPreference == 2){
      startWithStr(rootF,START);
      temp = queryCount;
      queryCount = 0;
      isBigger = false;
      startWithStr(rootF,END);
      queryCount = temp - queryCount;
    }else if(genderPreference == 0){
      startWithStr(rootM,START);
      temp = queryCount;
      queryCount = 0;
      isBigger = false;
      startWithStr(rootM,END);
      temp = temp - queryCount;
      queryCount = 0;
      isBigger = false;
      startWithStr(rootF,START);
      temp = temp + queryCount;
      queryCount = 0;
      isBigger = false;
      startWithStr(rootF,END);
      queryCount = temp - queryCount;
    }
  
    return queryCount;
  }

  void run() throws Exception {
    // do not alter this method to avoid unnecessary errors with the
    // automated judging
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(
        new OutputStreamWriter(System.out)));
    while (true) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      if (command == 0) // end of input
        break;
      else if (command == 1) // Add Suggestion
        AddSuggestion(st.nextToken(), Integer.parseInt(st.nextToken()));
      else
        // if (command == 2) // Query
        pr.println(Query(st.nextToken(), // START
            st.nextToken(), // END
            Integer.parseInt(st.nextToken()))); // GENDER
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method to avoid unnecessary errors with the
    // automated judging
    BabyNames ps1 = new BabyNames();
    ps1.run();
  }
}
