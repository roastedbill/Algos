import java.util.*;
import java.io.*;

// write your matric number here: A0105649
// write your name here: Liu Rensheng
// Discussed with Halim

class SchedulingDeliveries {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  public BinaryHeap pregnants = new BinaryHeap();
  public HashMap<String,Integer> nameMap = new HashMap<String,Integer>();
  public int comeInIndex;
  
  private class pregnant{
    public int dilation;
    public String name;
    public int index;
    
    public pregnant(int dilation, String name, int index){
      this.dilation = dilation;
      this.name = name;
      this.index = index; 
    }
  }
  
  class BinaryHeap {
    private Vector<pregnant> women;
    private int BinaryHeapSize;

    BinaryHeap() {
      women = new Vector<pregnant>();
      women.add(new pregnant(0,"",0)); // dummy
      BinaryHeapSize = 0;
    }

    int parent(int i) {
      return i >> 1;
    } // shortcut for i / 2, round down

    int left(int i) {
      return i << 1;
    } // shortcut for 2 * i

    int right(int i) {
      return (i << 1) + 1;
    } // shortcut for 2 * i + 1

    void shiftUp(int i) {
      while (i > 1 && (women.get(parent(i)).dilation < women.get(i).dilation 
          || (women.get(parent(i)).dilation == women.get(i).dilation && women.get(i).index < women.get(parent(i)).index))) {
        pregnant temp = women.get(i);
        swapMapKey(women.get(i).name,women.get(parent(i)).name);
        women.set(i, women.get(parent(i)));
        women.set(parent(i), temp);
        i = parent(i);
      }
    }
    
    void Insert(int key, String name, int index) {
      BinaryHeapSize++;
      pregnant newPregnant = new pregnant(key,name,index);
      if (BinaryHeapSize >= women.size()){
        women.add(newPregnant);
        nameMap.put(name,BinaryHeapSize);
      }
      else{
        women.set(BinaryHeapSize, newPregnant);
        nameMap.put(name,BinaryHeapSize);
      }
      shiftUp(BinaryHeapSize);
    }
    
    void swapMapKey(String a, String b){
      int temp = nameMap.get(a);
      nameMap.put(a,nameMap.get(b));
      nameMap.put(b, temp);
    }

    void shiftDown(int i) {
      while (i <= BinaryHeapSize) {
        int maxV = women.get(i).dilation, max_id = i;
        if (left(i) <= BinaryHeapSize && (maxV < women.get(left(i)).dilation 
            || (maxV == women.get(left(i)).dilation && women.get(max_id).index > women.get(left(i)).index))) { 
          maxV = women.get(left(i)).dilation;
          max_id = left(i);
        }
        if (right(i) <= BinaryHeapSize && (maxV < women.get(right(i)).dilation 
            || (maxV == women.get(right(i)).dilation && women.get(max_id).index > women.get(right(i)).index))) { 
          maxV = women.get(right(i)).dilation;
          max_id = right(i);
        }

        if (max_id != i) {
          pregnant temp = women.get(i);
          swapMapKey(women.get(i).name,women.get(max_id).name);
          women.set(i, women.get(max_id));
          women.set(max_id, temp);  
          i = max_id;
        } else
          break;
      }
    }

    int ExtractIndex(int index) {
      int maxV = women.get(index).dilation;
      swapMapKey(women.get(index).name,women.get(BinaryHeapSize).name);
      women.set(index, women.get(BinaryHeapSize));
      BinaryHeapSize--; // virtual decrease
      shiftDown(index);
      shiftUp(index);
      return maxV;
    }
    
    String CheckMax(){
      if(BinaryHeapSize<1)
        return "-1";
      return women.get(1).name;
    }

    int size() {
      return BinaryHeapSize;
    }

    boolean isEmpty() {
      return BinaryHeapSize == 0;
    }
  }

  public SchedulingDeliveries() {
    comeInIndex = 0;
  }

  void ArriveAtHospital(String womanName, int dilation) {
    comeInIndex ++;
    pregnants.Insert(dilation,womanName,comeInIndex);
  }

  void UpdateDilation(String womanName, int increaseDilation) {
    int index = nameMap.get(womanName);
    pregnants.women.get(index).dilation += increaseDilation;  
    pregnants.shiftUp(index);
  }

  void GiveBirth(String womanName) {
    int index = nameMap.get(womanName);
    pregnants.ExtractIndex(index);  
  }

  String Query() {
    String ans = "The delivery suite is empty";

    if(pregnants.CheckMax() == "-1")
      return ans;
    else 
      return pregnants.CheckMax();
  }

  void run() throws Exception {
      // do not alter this method
      // various AC solutions from several TAs and myself are in the region of [0.2-0.3]s in Mooshak and thus the 1s time limit is generous enough

      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
      int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
      while (numCMD-- > 0) {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int command = Integer.parseInt(st.nextToken());
        switch (command) {
          case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
          case 1: UpdateDilation(st.nextToken(), Integer.parseInt(st.nextToken())); break;
          case 2: GiveBirth(st.nextToken()); break;
          case 3: pr.println(Query()); break;
        }
      }
      pr.close();
    }

    public static void main(String[] args) throws Exception {
      // do not alter this method
      SchedulingDeliveries ps2 = new SchedulingDeliveries();
      ps2.run();
    }
}
