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

class Main {
    static int[][] dir = {{0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}};
    static int[][] map;
    static boolean[][] isCloud;
    static int N;
    static int M;
    static Queue<Pair> queue;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][N];

        queue = new LinkedList<>();
        queue.add(new Pair(N - 1, 0));
        queue.add(new Pair(N - 1, 1));
        queue.add(new Pair(N - 2, 0));
        queue.add(new Pair(N - 2, 1));

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        int answer = 0;
        for (int i = 0; i < M; i++) {
            isCloud = new boolean[N][N];
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken()) - 1;
            int s = Integer.parseInt(st.nextToken());

            Queue<Pair> copyClouds = new LinkedList<>();

            while (!queue.isEmpty()) {
                Pair cur = queue.poll();
                int x = cur.x;
                int y = cur.y;

                int nx = x;
                int ny = y;

                for (int c = 0; c < s; c++) {
                    nx += dir[d][0];
                    ny += dir[d][1];

                    if (nx < 0) nx = N - 1;
                    else if (nx == N) nx = 0;
                    if (ny < 0) ny = N - 1;
                    else if (ny == N) ny = 0;
                }

                if (!isIn(nx, ny)) continue;

                map[nx][ny] += 1;
                isCloud[nx][ny] = true;
                copyClouds.add(new Pair(nx, ny));
            }

            // 3번까지 완료 !
            // copyCloud에 물이 증가한 좌표가 저장
            // dir의 1,3,5,7 확인
            while (!copyClouds.isEmpty()) {
                Pair copyC = copyClouds.poll();
                int x = copyC.x;
                int y = copyC.y;

                int count = 0;
                for (int c = 1; c <= 7; c += 2) {
                    int tx = x + dir[c][0];
                    int ty = y + dir[c][1];

                    if (!isIn(tx, ty)) continue;
                    if (map[tx][ty] != 0) count++;
                }

                map[x][y] += count;
            }

            answer = 0;
            for (int a = 0; a < N; a++) {
                for (int b = 0; b < N; b++) {
                    if (map[a][b] >= 2 && !isCloud[a][b]) {
                        queue.add(new Pair(a, b));
                        map[a][b] -= 2;
                    }
                    answer += map[a][b];
                }
            }
        }

        System.out.println(answer);
    }

    private static boolean isIn(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }
}