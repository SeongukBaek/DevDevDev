import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static int[] parents;
    private static boolean[] visited;
    private static List<List<Integer>> adjList;
    private static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        parents = new int[N + 1];
        visited = new boolean[N + 1];

        init();
        for (int index = 0; index < N - 1; index++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int one = Integer.parseInt(st.nextToken());
            int two = Integer.parseInt(st.nextToken());

            // 무향이므로 양방향으로 저장
            adjList.get(one).add(two);
            adjList.get(two).add(one);
        }

        searchParents();

        System.out.println(makeString());
    }

    private static void init() {
        adjList = new ArrayList<>();
        for (int index = 0; index <= N; index++) {
            adjList.add(new ArrayList<>());
        }
    }

    private static void searchParents() {
        Queue<Integer> nodes = new LinkedList<>();
        nodes.add(1);

        while(!nodes.isEmpty()) {
            int now = nodes.poll();
            visited[now] = true;

            for (int next : adjList.get(now)) {
                if (visited[next]) {
                    continue;
                }

                parents[next] = now;
                nodes.add(next);
            }
        }
    }

    private static String makeString() {
        StringBuilder answer = new StringBuilder();
        for (int index = 2; index <= N; index++) {
            answer.append(parents[index]).append("\n");
        }
        return answer.toString();
    }
}