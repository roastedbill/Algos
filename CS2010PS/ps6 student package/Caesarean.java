import java.io.*;
import java.util.*;

// write your matric number here: A0105649B
// write your name here: Liu Rensheng
// Discussed with Mi Chengyi, Gu Junchao

class Caesarean {
    private int V; // number of vertices in the graph (steps of a Caesarean section surgery)
    private int E; // number of edges in the graph (dependency information between various steps of a Caesarean section surgery)
    private ArrayList < IntegerPair > EL; // the unweighted graph, an edge (u, v) in EL implies that step u must be performed before step v
    private ArrayList < Integer > estT; // the estimated time to complete each step
    private ArrayList <Integer> topo;
    private ArrayList <ArrayList<IntegerPair>> AdjList;
    private ArrayList <Integer> longestPath;
    private ArrayList <IntegerTriple> sameLength;
    private int visited[];
    private int dist[];
    private int p[];
    
    public Caesarean() {
        topo = new ArrayList <Integer>();
        visited = new int[200000];
        dist = new int[200000];
        p = new int[200000];
        Arrays.fill(p, 200001);
        Arrays.fill(dist, Integer.MIN_VALUE);
        AdjList = new ArrayList<ArrayList<IntegerPair>>();
        longestPath = new ArrayList <Integer>();
        sameLength= new ArrayList <IntegerTriple>();
    }
    
    int Query() {
        if(E<V)
            return 0;
        int ans = 0;
        int i = 0;
        covToAdjList();
        toposort(0);
        Collections.reverse(topo);
        dist[0]=estT.get(0);
        p[0]=-1;
        while(i<V-1){
            for(int j=0; j<AdjList.get(topo.get(i)).size(); j++){
                stretch(topo.get(i),AdjList.get(topo.get(i)).get(j)._first);
            }
            i++;
        }
        backTrack();
        ans = V - countCannotSlow();
        
        Arrays.fill(visited, 0);
        Arrays.fill(dist, 0);
        topo.clear();
        AdjList.clear();
        longestPath.clear();
        sameLength.clear();
        
        return ans;
    }
    
    int countCannotSlow(){
        for(int i=sameLength.size()-1; i>=0; i--){
            if(longestPath.contains(sameLength.get(i)._second) &&
               sameLength.get(i)._third == dist[sameLength.get(i)._second]){
                if(!longestPath.contains(sameLength.get(i)._first))
                    longestPath.add(sameLength.get(i)._first);
                int index = sameLength.get(i)._first;
                while(!longestPath.contains(p[index])){
                    index=p[index];
                    if(!longestPath.contains(index))
                        longestPath.add(index);
                }
            }
        }
        return longestPath.size();
    }
    
    void stretch(int u, int v){ //u:from v:to
        if(dist[v] < dist[u]+estT.get(v)){
            dist[v] = dist[u]+estT.get(v);
            p[v] = u;
        }
        else if (dist[v] == dist[u]+estT.get(v)){
            sameLength.add(new IntegerTriple(u,v,dist[v]));
        }
    }
    
    void backTrack(){
        int i=V-1;
        while(p[i]!=-1){
            longestPath.add(i);
            i=p[i];
        }
        longestPath.add(0);
    }
    
    void covToAdjList() {
        for(int i=0; i<V; i++){
            AdjList.add(new ArrayList<IntegerPair>());
        }
        for (int i=0; i<EL.size(); i++) {
            IntegerPair temp = EL.get(i);
            AdjList.get(temp.first()).add(
                                          new IntegerPair(temp.second(), estT.get(temp.first()))); // 1st:v, 2nd:weight
        }
    }
    
    void toposort(int index){
        visited[index] = 1;
        for (int j = 0; j < AdjList.get(index).size(); j++) {
            int v = AdjList.get(index).get(j).first();
            if (visited[v] == 0) {
                toposort(v);
            }
        }
        topo.add(index);
    }
    
    void run() throws Exception {
        IntegerScanner sc = new IntegerScanner(System.in);
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        
        int TC = sc.nextInt(); // there will be several test cases
        while (TC-- > 0) {
            V = sc.nextInt(); E = sc.nextInt(); // read V and then E
            
            estT = new ArrayList < Integer > ();
            for (int i = 0; i < V; i++)
                estT.add(sc.nextInt());
            
            // clear the graph and read in a new graph as an unweighted Edge List (only using IntegerPair, not IntegerTriple)
            EL = new ArrayList < IntegerPair > ();
            for (int i = 0; i < E; i++)
                EL.add(new IntegerPair(sc.nextInt(), sc.nextInt())); // just directed edge (u -> v)
            
            pr.println(Query());
        }
        
        pr.close();
    }
    
    public static void main(String[] args) throws Exception {
        // do not alter this method
        Caesarean ps6 = new Caesarean();
        ps6.run();
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
    int _first, _second;
    
    public IntegerPair(int f, int s) {
        _first = f;
        _second = s;
    }
    
    public int compareTo(IntegerPair o) {
        if (this.first() != o.first())
            return this.first() - o.first();
        else
            return this.second() - o.second();
    }
    
    int first() { return _first; }
    int second() { return _second; }
}

class IntegerTriple implements Comparable<IntegerTriple> {
    Integer _first, _second, _third;
    
    public IntegerTriple(Integer f, Integer s, Integer t) {
        _first = f;
        _second = s;
        _third = t;
    }
    
    public int compareTo(IntegerTriple o) {
        if (!this.first().equals(o.first()))
            return this.first() - o.first();
        else if (!this.second().equals(o.second()))
            return this.second() - o.second();
        else
            return this.third() - o.third();
    }
    
    Integer first() { return _first; } 
    Integer second() { return _second; }
    Integer third() { return _third; } // parent
}

