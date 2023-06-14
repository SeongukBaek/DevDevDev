import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        int[] x_ary = {0, 0, -1, 1};
        int[] y_ary = {-1, 1, 0, 0};
        int[][] map = null;
        int[][] visited = null;
        visited = new int[50][50];

        while (T > 0) {
            int M = sc.nextInt();
            int N = sc.nextInt();
            int K = sc.nextInt();
            int worms = 0;

            map = new int[M][N];
            ArrayList<Pair> worm = new ArrayList<>();

            for (int[] tmp : visited)
                Arrays.fill(tmp, 0);

            for (int i = 0; i < K; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                map[x][y] = 1;
                worm.add(new Pair(x, y));
            }

            for (int a = 0; a < worm.size(); a++) {
                int vF = worm.get(a).getF();
                int vS = worm.get(a).getS();
                if (visited[vF][vS] == 0) {
                    Queue<Pair> q = new LinkedList<>();
                    q.offer(worm.get(a));
                    visited[vF][vS] = 1;
                    while (!q.isEmpty()) {
                        Pair cur = q.poll();
                        for (int i = 0; i < 4; i++) {
                            int nx = cur.getF() + x_ary[i], ny = cur.getS() + y_ary[i];
                            if (nx >= 0 && ny >= 0 && nx < M && ny < N) {
                                if (visited[nx][ny] == 0 && map[nx][ny] == 1) {
                                    visited[nx][ny] = 1;
                                    q.offer(new Pair(nx, ny));
                                }
                            }
                        }
                    }
                    worms++;
                }
            }

            System.out.println(worms);
            T -= 1;
        }
        sc.close();
    }
}
class Pair {
    private int first;
    private int second;

    Pair(int x, int y) {
        this.first = x;
        this.second = y;
    }

    public int getF() {
        return first;
    }

    public int getS() {
        return second;
    }
}
