import java.util.*;

class Graph {
    public ArrayList<ArrayList<Integer>> graph;
    public int[] visited = new int[20001];
    private int dis = 1;

    public Graph(int size) {
        this.graph = new ArrayList<ArrayList<Integer>>();

        for (int i = 0; i < size + 1; i++) {
            graph.add(new ArrayList<Integer>());
        }
    }

    public void DoubleAdd(int x, int y) {
        graph.get(x).add(y);
        graph.get(y).add(x);
    }

    public boolean isOddCycle(int start) {
        this.dfs(start, start);

        return false;
    }

    public void dfs(int start, int now) {
        if (visited[start] == -1) {
            visited[start] = dis++;
            for (int i = 0; i < graph.get(start).size(); i++) {
                int idx = graph.get(start).get(i);
                if (visited[idx] == -1) {
                    this.dfs(now, idx);
                }
            }
        }
    }

//    public void bfs(int start) {
//        Queue<Integer> q = new LinkedList<>();
//        q.offer(start);
//        visited[start] = true;
//        while (!q.isEmpty()) {
//            int cur = q.poll();
//            for (int i = 0; i < graph.get(cur).size(); i++) {
//                if (!visited[graph.get(cur).get(i)]) {
//                    visited[graph.get(cur).get(i)] = true;
//                    q.offer(graph.get(cur).get(i));
//                }
//            }
//        }
//    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        while (T >= 0) {
            int v = sc.nextInt();
            int e = sc.nextInt();

            Graph g = new Graph(v);

            for (int i = 0; i < e; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();

                g.DoubleAdd(a,b);
            }

            Arrays.fill(g.visited,-1);

            for (int i = 1; i <= v; i++) {
                if(g.isOddCycle(i)) {
                    System.out.println("NO");
                    break;
                }
            }


            T-=1;
        }

        sc.close();
    }
}