import java.util.*;
import java.io.*;

class Pair {
    int x;
    int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    static int[][] map;
    static int[][] group;
    static int groupCount;
    static boolean[][] visited;
    static int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static int N;
    static Map<Integer, List<Pair>> groupMap;
    static int artScore;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        // 주어진 맵 저장
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // 각 칸에서 BFS 수행해서 그룹 정보를 int[][] 에 저장
        getGroupInfo();

        // 초기 예술 점수 계산
        computeArt();

        for (int c = 0; c < 3; c++) {
            // 그림 회전 수행
            // 십자 모양 회전 수행
            crossRotate();

            // 4개의 정사각형 회전
            // 시작점 좌표와 끝점 좌표를 인자로 받는 함수 구현
            squareRotate(0, 0, N / 2 - 1, N / 2 - 1);
            squareRotate(0, N / 2 + 1, N / 2 - 1, N - 1);
            squareRotate(N / 2 + 1, 0, N - 1, N / 2 - 1);
            squareRotate(N / 2 + 1, N / 2 + 1, N - 1, N - 1);

            // 각 칸에서 BFS 수행해서 그룹 정보를 int[][] 에 저장
            getGroupInfo();

            // n회전 예술 점수 계산
            computeArt();
        }

        System.out.println(artScore);
    }

    private static void getGroupInfo() {
        group = new int[N][N];
        visited = new boolean[N][N];
        groupMap = new HashMap<>();
        groupCount = 0;

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (!visited[i][j]) {
                    groupCount++;
                    bfs(i, j);
                }
    }

    private static void bfs(int x, int y) {
        Queue<Pair> queue = new LinkedList<>();
        Pair start = new Pair(x, y);
        queue.add(start);
        visited[x][y] = true;
        int number = map[x][y];
        group[x][y] = groupCount;
        List<Pair> list = new ArrayList<>();
        list.add(start);

        while(!queue.isEmpty()) {
            Pair cur = queue.poll();
            int curX = cur.x;
            int curY = cur.y;

            for (int d = 0; d < 4; d++) {
                int nx = curX + dir[d][0];
                int ny = curY + dir[d][1];

                if (!isIn(nx, ny)) continue;
                if (!visited[nx][ny] && map[nx][ny] == number) {
                    Pair next = new Pair(nx, ny);
                    visited[nx][ny] = true;
                    group[nx][ny] = groupCount;
                    list.add(next);
                    queue.add(next);
                }
            }
        }

        groupMap.put(groupCount, list);
    }

    private static boolean isIn(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }

    // 이중 for문으로 각 그룹별 조화로움 계산
    private static void computeArt() {
        for (int i = 1; i < groupCount; i++) {
            List<Pair> group1 = groupMap.get(i);

            for (int j = i + 1; j <= groupCount; j++) {
                List<Pair> group2 = groupMap.get(j);

                int touchCount = 0;

                for (Pair p : group1) {
                    int x = p.x;
                    int y = p.y;

                    for (int d = 0; d < 4; d++) {
                        int nx = x + dir[d][0];
                        int ny = y + dir[d][1];

                        if (!isIn(nx, ny)) continue;
                        if (group[nx][ny] == j) touchCount++;
                    }
                }

                if (touchCount == 0)
                    continue;

                int a = group1.size();
                int b = group2.size();
                Pair aP = group1.get(0);
                int aNum = map[aP.x][aP.y];
                Pair bP = group2.get(0);
                int bNum = map[bP.x][bP.y];

                artScore += (a + b) * aNum * bNum * touchCount;
            }
        }
    }

    // 상, 좌, 하, 우 방향으로 큐에 넣고, 좌, 하, 우, 상 방향으로 다시 대입
    private static void crossRotate() {
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < N / 2; i++)
            queue.add(map[i][N / 2]);

        for (int j = 0; j < N / 2; j++)
            queue.add(map[N / 2][j]);

        for (int i = N / 2 + 1; i < N; i++)
            queue.add(map[i][N / 2]);

        for (int j = N / 2 + 1; j < N; j++)
            queue.add(map[N / 2][j]);

        // 큐 삽입 종료

        for (int j = 0; j < N / 2; j++)
            map[N / 2][j] = queue.poll();

        for (int i = N - 1; i > N / 2; i--)
            map[i][N / 2] = queue.poll();

        for (int j = N / 2 + 1; j < N; j++)
            map[N / 2][j] = queue.poll();

        for (int i = N / 2 - 1; i >= 0; i--)
            map[i][N / 2] = queue.poll();
    }

    // 행 기준으로 큐에 모두 삽입하고,
    // 열 기준으로 큐를 팝하면서 맵에 대입
    private static void squareRotate(int x1, int y1, int x2, int y2) {
        Queue<Integer> queue = new LinkedList<>();

        for (int i = x1; i <= x2; i++)
            for (int j = y1; j <= y2; j++)
                queue.add(map[i][j]);

        for (int j = y2; j >= y1; j--)
            for (int i = x1; i <= x2; i++)
                map[i][j] = queue.poll();
    }
}