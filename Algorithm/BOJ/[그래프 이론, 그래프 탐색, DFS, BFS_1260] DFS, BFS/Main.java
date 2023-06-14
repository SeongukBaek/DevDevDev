import java.util.*;

class Graph {
    public ArrayList<ArrayList<Integer>> graph;
    public boolean[] visited = new boolean[1001];
    public StringBuilder path = new StringBuilder();

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

    public void dfs(int start) {
        if (!visited[start]) {
            path.append(start);
            path.append(" ");
            visited[start] = true;
            for (int i = 0; i < graph.get(start).size(); i++) {
                if (!visited[graph.get(start).get(i)]) {
                    this.dfs(graph.get(start).get(i));
                }
            }
        }
    }

    public void bfs(int start) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        visited[start] = true;
        path.append("\n");
        path.append(start);
        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int i = 0; i < graph.get(cur).size(); i++) {
                if (!visited[graph.get(cur).get(i)]) {
                    visited[graph.get(cur).get(i)] = true;
                    path.append(" ");
                    path.append(graph.get(cur).get(i));
                    q.offer(graph.get(cur).get(i));
                }
            }
        }
    }

    public StringBuilder getPath() {
        return path;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int start = sc.nextInt();

        Graph g = new Graph(n);

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            g.DoubleAdd(a,b);
        }

        for (int i = 1; i <= n; i++) {
            Collections.sort(g.graph.get(i));
        }

        Arrays.fill(g.visited,false);
        g.dfs(start);

        Arrays.fill(g.visited,false);
        g.bfs(start);

        System.out.print(g.getPath().toString());

        sc.close();
    }
}