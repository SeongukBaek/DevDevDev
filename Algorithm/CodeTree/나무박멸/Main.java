import java.util.*;
import java.io.*;

class Tree {
    int x;
    int y;
    // 상하좌우에 나무가 없는 지역 방향 저장
    List<Integer> emptyDir;

    public Tree(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void setEmptyDir(List<Integer> emptyDir) {
        this.emptyDir = emptyDir;
    }
}

public class Main {
    static List<Tree> treeList;
    // 상 우 하 좌
    static int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static int[][] diagDir = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    static int[][] map;
    static int[][] herbicide;
    static int n;
    static int m;
    static int k;
    static int c;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        herbicide = new int[n][n];
        int answer = 0;

        // 맵 정보와 나무 위치 저장
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int t = Integer.parseInt(st.nextToken());
                map[i][j] = t;
            }
        }

        while (m > 0) {
            treeList = setTreeList();

            // 1. 나무 성장
            // 각 나무별 상하좌우에 있는 나무의 개수를 카운트해서 성장합니다.
            // 그리고 아무 것도 없는 지역에 대한 방향 또한 저장해서 번식에 사용할 수 있도록 합니다.
            for (Tree tree : treeList) {
                int treeCount = 0;
                List<Integer> empty = new ArrayList<>();
                int tx = tree.x;
                int ty = tree.y;

                for (int i = 0; i < 4; i++) {
                    int nx = tx + dir[i][0];
                    int ny = ty + dir[i][1];

                    if (!isIn(nx, ny)) continue;
                    if (herbicide[nx][ny] == 0) {
                        if (map[nx][ny] == 0) empty.add(i);
                        else if (map[nx][ny] > 0) treeCount++;
                    }
                }

                tree.setEmptyDir(empty);

                if (treeCount > 0)
                    map[tx][ty] += treeCount;
            }

            // 2. 번식 실행
            // 성장 로직을 수행하면서, 아무것도 없는 지역에 대한 방향을 함께 저장했습니다.
            // 따라서 번식 과정에서는 이 방향 리스트에 대해 번식을 수행하면 됩니다.
            for (Tree tree : treeList) {
                int tx = tree.x;
                int ty = tree.y;
                List<Integer> emptyTreeDir = tree.emptyDir;
                int tec = emptyTreeDir.size();

                if (tec == 0) continue;

                // 주변에 번식되는 나무 수
                int giveTree = map[tx][ty] / tec;

                for (int d : emptyTreeDir) {
                    int nx = tx + dir[d][0];
                    int ny = ty + dir[d][1];

                    if (herbicide[nx][ny] == 0)
                        map[nx][ny] += giveTree;
                }
            }

            treeList = setTreeList();

            // 3. 박멸 실행
            // 3-1. 각 나무 위치에서 대각선 방향으로 k칸만큼 제초제를 뿌려보고, 최대가 되는 위치를 찾습니다.
            // 이때 제일 큰 좌표부터 시작해서 조건에 부합하도록 합니다.
            int max = 0;
            int maxX = n - 1;
            int maxY = n - 1;
            for (int t = treeList.size() - 1; t >= 0; t--) {
                Tree tree = treeList.get(t);
                int tx = tree.x;
                int ty = tree.y;

                int sum = map[tx][ty];
                for (int d = 0; d < 4; d++) {
                    for (int c = 1; c <= k; c++) {
                        int nx = tx + diagDir[d][0] * c;
                        int ny = ty + diagDir[d][1] * c;

                        if (!isIn(nx, ny)) break;
                        if (map[nx][ny] <= 0) break;
                        sum += map[nx][ny];
                    }
                }

                if (max <= sum) {
                    max = sum;
                    maxX = tx;
                    maxY = ty;
                }
            }

            answer += max;

            // 제초제가 1년이 지났기 때문에 -1 해줍니다.
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (herbicide[i][j] > 0)
                        herbicide[i][j]--;

            // 3-2. 박멸 시작
            herbicide[maxX][maxY] = c;
            map[maxX][maxY] = 0;
            for (int d = 0; d < 4; d++) {
                for (int dir = 1; dir <= k; dir++) {
                    int nx = maxX + diagDir[d][0] * dir;
                    int ny = maxY + diagDir[d][1] * dir;

                    if (!isIn(nx, ny)) break;

                    herbicide[nx][ny] = c;

                    if (map[nx][ny] <= 0) break;

                    map[nx][ny] = 0;
                }
            }
            m--;
        }
        System.out.println(answer);
    }

    private static boolean isIn(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }

    // map을 탐색하면서 나무의 위치를 저장하는 List를 생성해서 반환합니다.
    private static List<Tree> setTreeList() {
        List<Tree> tmp = new ArrayList<>();

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (map[i][j] > 0)
                    tmp.add(new Tree(i, j));

        return tmp;
    }
}