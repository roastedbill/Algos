import java.util.*;
import java.io.*;

// write your matrix number here: A0105649B
// write your name here: Liu Rensheng
//

class HospitalTour {
	private int V; // number of vertices in the graph (number of rooms in the hospital)
	private List<ArrayList<Integer>> AdjList; // the graph (the hospital)
	private int[] RatingScore; // the weight of each vertex (rating score of each room)
	// if needed, declare a private data structure here that
	// is accessible to all methods in this class
    
	boolean isImportant(int v){
		
		int[] Visited = new int[V];
		for (int i=0;i<V;i++)
            Visited[i] = 0;
		
		int start = 0;
		if(v == 0)
			start = 1;
		Visited[start] = 1;
		Visited[v] = 1;
		int nodeCount = 2;
		
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.add(0,start);
        
		while(q.size()>0){
			int current = q.removeLast();
            
			for (int i : AdjList.get(current)){
				if(Visited[i] == 0){
					Visited[i]=1;
					nodeCount++;
					q.add(0,i);
				}
			}
		}
		return (nodeCount<V);
	}
	
	public HospitalTour() {
	}
    
	int Query() {
		int ans = -1;
		if (V == 1) return ans;
		
		for(int i=0; i<V; i++){
			if(isImportant(i))
				if(ans == -1 || RatingScore[i] < ans)
					ans = RatingScore[i];
		}
        
		return ans;
	}
    
	void run() throws Exception {
		// for this PS3, you can alter this method as you see fit
        
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int TC = Integer.parseInt(br.readLine()); // there will be several test cases
		while (TC-- > 0) {
			br.readLine(); // ignore dummy blank line
			V = Integer.parseInt(br.readLine());
            
			StringTokenizer st = new StringTokenizer(br.readLine());
			// read rating scores, A (index 0), B (index 1), C (index 2), ..., until the V-th index
			RatingScore = new int[V];
			for (int i = 0; i < V; i++)
				RatingScore[i] = Integer.parseInt(st.nextToken());
            
			// clear the graph and read in a new graph as Adjacency List
			AdjList = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i < V; i++){
                ArrayList<Integer> adj = new ArrayList<Integer> ();
                AdjList.add(adj);
            }
            
            
            
			for (int i = 0; i < V; i++) {
				st = new StringTokenizer(br.readLine());
				int k = Integer.parseInt(st.nextToken());
				while (k-- > 0) {
					int j = Integer.parseInt(st.nextToken());
					AdjList.get(i).add(j);// edge weight is always 1 (the weight is on vertices now)
				}
			}
            
            
            
			pr.println(Query());
		}
		pr.close();
	}
    
	public static void main(String[] args) throws Exception {
		// do not alter this method
		HospitalTour ps3 = new HospitalTour();
		ps3.run();
	}
}


class IntegerPair implements Comparable {
    Integer _first, _second;
    
    public IntegerPair(Integer f, Integer s) {
        _first = f;
        _second = s;
    }
    
    public int compareTo(Object o) {
        if (!this.first().equals(((IntegerPair)o).first()))
            return this.first() - ((IntegerPair)o).first();
        else
            return this.second() - ((IntegerPair)o).second();
    }
    
    Integer first() { return _first; }
    Integer second() { return _second; }
}