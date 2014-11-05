import java.util.*;
import java.io.*;

// write your matric number here: A0105649B
// write your name here: LIU RENSHENG
//

class Supermarket {
    private int N; // number of items in the supermarket. V = N+1
    private int K; // the number of items that Steven has to buy
    private int[] shoppingList; // indices of items that Steven has to buy
    private int[][] T; // the complete weighted graph that measures the direct walking time to go from one point to another point in seconds
    private int[][] processedT;
    private int[][] memo;
    
    // if needed, declare a private data structure here that
    // is accessible to all methods in this class
    // --------------------------------------------
    
    
    
    public Supermarket() {
    }
    
    int Query() {
        preprocess();
        return TSP(0, 0);
    }
    
    void preprocess(){
        processedT = new int[K+1][K+1];
        for(int i=0; i<K; i++){
            SSSP(i);
        }
        for(int i=1; i<K+1; i++){
            processedT[0][i] = processedT[i][0];
        }
        
        memo = new int[K+1][(1<<K+1)-1];
        for (int i = 0; i < K+1 ; i++)
            Arrays.fill(memo[i], -1);
    }
    
    void SSSP(int index){
        int startV = shoppingList[index];
        int d, u;
        int dist[] = new int[N+1];
        PriorityQueue<IntegerPair> pq = new PriorityQueue<IntegerPair>();
        Arrays.fill(dist, Integer.MAX_VALUE) ;
        dist[startV] = 0;
        pq.add(new IntegerPair(0,startV));
        while(!pq.isEmpty()){
            IntegerPair front = pq.poll();
            d = front._first; //dist
            u = front._second; //to
            if(d > dist[u])
                continue;
            for(int j=0; j<N+1; j++){
                if(dist[u] + T[u][j] < dist[j]){
                    dist[j] = dist[u] + T[u][j];
                    pq.add(new IntegerPair(dist[j], j));
                }
            }
            processedT[index+1][0] = dist[0];
            for(int k=0; k<K; k++){
                processedT[index+1][k+1] = dist[shoppingList[k]];
            }
        }
    }
    
    int TSP(int current, int visited)
    {
        if (visited == ((1<<(K+1))-1))
            return processedT[current][0];
        if (memo[current][visited] != -1)
            return memo[current][visited];
        
        memo[current][visited] = Integer.MAX_VALUE ;
        for (int v=0; v<K+1; v++) {
            if ((visited&(1<<v)) == 0)
                memo[current][visited] = Math.min(memo[current][visited], processedT[current][v] + TSP(v, (visited|(1<<v))));
        }
        return memo[current][visited];
    }
    
    
    
    void run() throws Exception {
        // do not alter this method to standardize the I/O speed (this is already very fast)
        IntegerScanner sc = new IntegerScanner(System.in);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        
        int TC = sc.nextInt(); // there will be several test cases
        while (TC-- > 0) {
            // read the information of the complete graph with N+1 vertices
            N = sc.nextInt(); K = sc.nextInt(); // K is the number of items to be bought
            
            shoppingList = new int[K];
            for (int i = 0; i < K; i++)
                shoppingList[i] = sc.nextInt();
            
            T = new int[N+1][N+1];
            for (int i = 0; i <= N; i++)
                for (int j = 0; j <= N; j++)
                    T[i][j] = sc.nextInt();
            
            pw.println(Query());
        }
        
        pw.close();
    }
    
    public static void main(String[] args) throws Exception {
        // do not alter this method
        Supermarket ps7 = new Supermarket();
        ps7.run();
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
