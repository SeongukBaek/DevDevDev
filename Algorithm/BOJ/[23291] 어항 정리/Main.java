import java.io.*;
import java.util.*;

public class Main {
    static int[][] map;
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int N;
    static int K;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        int answer = 0;

        map = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++)
            map[N][i] = Integer.parseInt(st.nextToken());

        while (!isFinish()) {
            answer++;

            // 구한 최소 물고기 수를 가지는 어항에 물고기 한 마리 추가
            pushFish();

            // 어항 쌓기
            // 가장 왼쪽의 어항을 오른쪽 어항 위로 쌓기
            stackBowl();

            // 물고기 수 조절
            controlFishes();

            // 어항을 일렬로 배치
            arrageBowl();

            // 공중부양
            levitation();

            // 물고기 수 조절
            controlFishes();

            // 어항을 일렬로 배치
            arrageBowl();
        }

        System.out.println(answer);
    }

    private static boolean isFinish() {
        int max = 0;
        int min = 987654321;

        for (int y = 1; y <= N; y++) {
            for (int x = 1; x <= N; x++) {
                int n = map[y][x];
                if (n == 0)
                    continue;
                if (max < n)
                    max = n;
                if (min > n)
                    min = n;
            }
        }

        return max - min <= K;
    }

    private static void pushFish() {
        List<Integer> posList = new ArrayList<>();
        int min = 987654321;

        for (int i = 1; i <= N; i++) {
            if (map[N][i] < min) {
                min = map[N][i];
                posList.clear();
                posList.add(i);
            } else if (map[N][i] == min)
                posList.add(i);
        }

        for (int idx : posList)
            map[N][idx]++;
    }

    private static void stackBowl() {
        int pivotX = 1;
        int w = 1;
        int h = 1;
        int idx = 0;

        while (pivotX - 1 + w + h <= N) {
            idx++;

            // x좌표는 w만큼 움직이고,
            for (int x = pivotX; x < pivotX + w; x++) {
                // y좌표는 N부터 h만큼 움직인다.
                for (int y = N; y > N - h; y--) {
                    int ny = N - w + x - pivotX;
                    int nx = pivotX + w + N - y;
                    map[ny][nx] = map[y][x];
                    map[y][x] = 0;
                }
            }

            pivotX += w;

            if (idx % 2 == 0) w++;
            else h++;
        }
    }

    private static void controlFishes() {
        // 물고기의 이동을 동시에 진행하기 위해 임시 저장 배열에 저장해두고, 한 번에 옮긴다.
        // 행, 열순이 아니라, 열, 행순으로 탐색하기 위해 뒤집어 수행한다.
        int[][] tmpFish = new int[N + 1][N + 1];

        for (int y = 1; y <= N; y++) {
            for (int x = 1; x <= N; x++) {
                if (map[y][x] == 0) continue;

                int nowF = map[y][x];
                for (int d = 0; d < 4; d++) {
                    int ny = y + dir[d][1];
                    int nx = x + dir[d][0];

                    if (nx < 1 || nx > N || ny < 1 || ny > N || map[ny][nx] == 0) continue;

                    int nFish = map[ny][nx];

                    int controlF = (nowF - nFish) / 5;
                    if (controlF > 0) {
                        tmpFish[ny][nx] += controlF;
                        tmpFish[y][x] -= controlF;
                    }
                }
            }
        }

        // 임시 배열에 있던 값을 map에 반영
        for (int y = 1; y <= N; y++)
            for (int x = 1; x <= N; x++)
                map[y][x] += tmpFish[y][x];
    }

    private static void arrageBowl() {
        Queue<Integer> queue = new LinkedList<>();

        for (int x = 1; x <= N; x++) {
            for (int y = N; y >= 1; y--) {
                if (map[y][x] == 0) break;
                queue.add(map[y][x]);
                map[y][x] = 0;
            }
        }

        for (int i = 1; i <= N; i++)
            map[N][i] = queue.poll();
    }

    private static void levitation() {
        List<Integer> list = new ArrayList<>();
        int pivotX = 1;
        int yCnt = 1;

        for (int cnt = 1; cnt <= 2; cnt++) {
            int _y= N - yCnt * 2 + 1;
            for (int y = N; y > N - yCnt; y--) {
                list.clear();
                for (int x = pivotX; x < pivotX + (N - pivotX + 1) / 2; x++) {
                    list.add(map[y][x]);
                    map[y][x] = 0;
                }
                for (int idx = 0; idx < list.size(); idx++)
                    map[_y][N - idx] = list.get(idx);
                _y++;
            }
            yCnt *= 2;
            pivotX += N / 2;
        }
    }
}