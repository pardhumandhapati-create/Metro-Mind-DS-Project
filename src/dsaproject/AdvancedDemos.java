package dsaproject;

import java.util.*;
import java.util.concurrent.*;

/** CO4, CO5 and CO6 educational demonstrations using public-transport examples. */
public final class AdvancedDemos {
    private AdvancedDemos() {}

    public static void networkFlow() {
        System.out.println("\n=== CO4: PASSENGER NETWORK FLOW ===");
        // A->B 8, A->C 5, B->C 3, B->D 5, C->D 8
        int[][] cap = new int[4][4];
        cap[0][1]=8; cap[0][2]=5; cap[1][2]=3; cap[1][3]=5; cap[2][3]=8;
        int[][] original = copy(cap);
        int ek = edmondsKarp(cap, 0, 3);
        boolean[] reachable = residualReachable(cap, 0);
        int cut = 0;
        String[] names = {"Station A", "Station B", "Station C", "Station D"};
        System.out.println("Edmonds-Karp maximum passenger flow: " + ek);
        System.out.print("Minimum-cut edges: ");
        boolean first=true;
        for (int i=0;i<4;i++) for (int j=0;j<4;j++) if (reachable[i] && !reachable[j] && original[i][j]>0) {
            if (!first) System.out.print(", ");
            System.out.print(names[i]+" -> "+names[j]+" (capacity "+original[i][j]+")");
            cut += original[i][j]; first=false;
        }
        System.out.println("\nMinimum-cut capacity: " + cut);
        System.out.println("Max-flow = min-cut: " + (ek == cut));
        System.out.println("Dinic maximum passenger flow: " + dinic(original, 0, 3));
        System.out.println("Capacity constraint: flow on each connection cannot exceed its capacity.");
    }

    private static int edmondsKarp(int[][] residual, int s, int t) {
        int n=residual.length, flow=0;
        while (true) {
            int[] parent=new int[n]; Arrays.fill(parent,-1); parent[s]=s;
            Queue<Integer> q=new ArrayDeque<>(); q.add(s);
            while(!q.isEmpty() && parent[t]==-1) {
                int u=q.remove();
                for(int v=0;v<n;v++) if(parent[v]==-1 && residual[u][v]>0) {parent[v]=u;q.add(v);}
            }
            if(parent[t]==-1) break;
            int add=Integer.MAX_VALUE;
            for(int v=t;v!=s;v=parent[v]) add=Math.min(add,residual[parent[v]][v]);
            for(int v=t;v!=s;v=parent[v]) {int u=parent[v];residual[u][v]-=add;residual[v][u]+=add;}
            flow+=add;
        }
        return flow;
    }
    private static boolean[] residualReachable(int[][] r,int s) {
        boolean[] seen=new boolean[r.length]; Queue<Integer> q=new ArrayDeque<>();q.add(s);seen[s]=true;
        while(!q.isEmpty()){int u=q.remove();for(int v=0;v<r.length;v++)if(!seen[v]&&r[u][v]>0){seen[v]=true;q.add(v);}}
        return seen;
    }
    private static int[][] copy(int[][] a){int[][] b=new int[a.length][];for(int i=0;i<a.length;i++)b[i]=a[i].clone();return b;}
    private static int dinic(int[][] capacity,int s,int t) {
        int n=capacity.length, total=0; int[][] r=copy(capacity);
        while(true){int[] level=new int[n];Arrays.fill(level,-1);level[s]=0;Queue<Integer> q=new ArrayDeque<>();q.add(s);
            while(!q.isEmpty()){int u=q.remove();for(int v=0;v<n;v++)if(level[v]<0&&r[u][v]>0){level[v]=level[u]+1;q.add(v);}}
            if(level[t]<0)break;int[] next=new int[n];int f;while((f=send(r,level,next,s,t,Integer.MAX_VALUE))>0)total+=f;
        }return total;
    }
    private static int send(int[][] r,int[] level,int[] next,int u,int t,int pushed){if(u==t)return pushed;for(;next[u]<r.length;next[u]++){int v=next[u];if(r[u][v]>0&&level[v]==level[u]+1){int f=send(r,level,next,v,t,Math.min(pushed,r[u][v]));if(f>0){r[u][v]-=f;r[v][u]+=f;return f;}}}return 0;}

    public static void passengerRouteMatching() {
        System.out.println("\n=== CO4: PASSENGER GROUP TO ROUTE MATCHING ===");
        System.out.println("This toy model assigns each travel group to one distinct suitable route option.");
        String[] groups={"Group A", "Group B", "Group C"};
        String[] routes={"Route 1", "Route 2", "Route 3"};
        boolean[][] eligible={{true,true,false},{true,false,false},{false,true,true}};
        int[] routeOwner=new int[routes.length];Arrays.fill(routeOwner,-1);int matched=0;
        for(int g=0;g<groups.length;g++)if(match(g,eligible,routeOwner,new boolean[routes.length]))matched++;
        for(int r=0;r<routes.length;r++)if(routeOwner[r]>=0)System.out.println(groups[routeOwner[r]]+" -> "+routes[r]);
        System.out.println("Matched groups: "+matched+" of "+groups.length);
        System.out.println("Note: distinct-route matching is a simplified planning example, not a model of ordinary shared passenger routes.");
    }
    private static boolean match(int g,boolean[][] e,int[] owner,boolean[] seen){for(int r=0;r<owner.length;r++)if(e[g][r]&&!seen[r]){seen[r]=true;if(owner[r]<0||match(owner[r],e,owner,seen)){owner[r]=g;return true;}}return false;}

    public static void complexityAndApproximation() {
        System.out.println("\n=== CO5: COMPLEXITY AND APPROXIMATION ===");
        System.out.println("P: solvable in polynomial time.");
        System.out.println("NP: proposed solutions can be verified in polynomial time.");
        System.out.println("NP-complete: in NP and every NP problem reduces to it.");
        System.out.println("NP-hard: at least as hard as NP-complete problems; need not be in NP.");
        System.out.println("Reduction chain studied: 3-SAT -> Vertex Cover -> Independent Set (polynomial transformations).");
        int[][] edges={{0,1},{1,2},{2,3},{3,0},{1,3}}; boolean[] covered=new boolean[4];List<Integer> cover=new ArrayList<>();
        for(int[] e:edges)if(!covered[e[0]]&&!covered[e[1]]){cover.add(e[0]);cover.add(e[1]);covered[e[0]]=covered[e[1]]=true;}
        System.out.println("Vertex Cover 2-approximation selected vertices: "+cover);
        System.out.println("Approximation guarantee: cover size <= 2 x optimal cover size.");
    }

    public static void randomizedAlgorithms() {
        System.out.println("\n=== CO6: RANDOMIZED ALGORITHMS ===");
        int[] values={14,3,21,8,5,11,2}; int k=3; int selected=quickselect(values.clone(),k,new Random());
        System.out.println("Las Vegas Randomized Quickselect: 4th-smallest value = "+selected+" (result is always correct; runtime varies).");
        Random random=new Random();int inside=0,n=100000;
        for(int i=0;i<n;i++){double x=random.nextDouble(),y=random.nextDouble();if(x*x+y*y<=1)inside++;}
        System.out.printf(Locale.US,"Monte Carlo estimate of pi (%d random points): %.5f%n",n,4.0*inside/n);
        System.out.println("Monte Carlo estimate varies between runs; accuracy generally improves with more samples.");
    }
    private static int quickselect(int[] a,int k,Random rnd){int lo=0,hi=a.length-1;while(lo<=hi){int p=lo+rnd.nextInt(hi-lo+1),pivot=a[p];int i=lo,j=hi;while(i<=j){while(a[i]<pivot)i++;while(a[j]>pivot)j--;if(i<=j){int t=a[i];a[i++]=a[j];a[j--]=t;}}if(k<=j)hi=j;else if(k>=i)lo=i;else return a[k];}return a[k];}

    public static void parallelAlgorithms() {
        System.out.println("\n=== CO6: PARALLEL ALGORITHM PRIMITIVES ===");
        int[] a={1,2,3,4,5,6,7,8};int[] prefix=new int[a.length];int sum=0;for(int i=0;i<a.length;i++){sum+=a[i];prefix[i]=sum;}
        System.out.println("Input:         "+Arrays.toString(a));
        System.out.println("Prefix sum:    "+Arrays.toString(prefix));
        System.out.println("Reduce (sum):  "+sum);
        int work=a.length-1, span=(int)Math.ceil(Math.log(a.length)/Math.log(2));
        System.out.println("Illustrative balanced reduction work = n-1 = "+work+" operations");
        System.out.println("Illustrative balanced reduction span = log2(n) = "+span+" levels for n=8");
        System.out.println("Note: calculations above show the primitives and theoretical work/span; these loops are sequential, not a parallel speed test.");
    }
}
