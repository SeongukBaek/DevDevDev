package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;

class Solution {
    static class Pair {
        int x;
        int y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int size;
    static int[][] map;
    static int[][] dir = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    static int[][] costs;
    static Queue<Pair> pairs;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {

        int T = Integer.parseInt(br.readLine());

        for (int test = 1; test <= T; test++) {
            System.out.print("#" + test + " ");
            findSupply();
        }

        br.close();
    }

    private static void findSupply() throws Exception {
        // 숫자 입력 받고, 그에 맞는 배열을 생성
        size = Integer.parseInt(br.readLine());

        initialize(size);

        for (int row = 0; row < size; row++) {
            String[] numbers = br.readLine().split("");
            for (int col = 0; col < size; col++) {
                map[row][col] = Integer.parseInt(numbers[col]);
                costs[row][col] = Integer.MAX_VALUE;
            }
        }

        // 시작점으로부터 BFS를 수행, 각 점에서 갈 수 있는 상하좌우 점에 대해 모두 탐색 후 최솟값을 출력
        System.out.println(findMinRecovery(0, 0));
    }

    private static void initialize(int size) {
        map = new int[size][size];
        costs = new int[size][size];
        pairs = new LinkedList<>();
    }

    private static int findMinRecovery(int startX, int startY) {
        pairs.add(new Pair(startX, startY));
        costs[startX][startY] = map[startX][startY];

        while (!pairs.isEmpty()) {
            searchMap(pairs.poll());
        }

        return costs[size - 1][size - 1];
    }

    private static void searchMap(Pair now) {
        int nowX = now.x;
        int nowY = now.y;
        int nowCost = costs[nowX][nowY];

        for (int d = 0; d < 4; d++) {
            int newX = nowX + dir[d][0];
            int newY = nowY + dir[d][1];

            if (validate(newX, newY)) {
                continue;
            }

            int newCost = nowCost + map[newX][newY];

            if (isUpdated(newX, newY) && costs[newX][newY] <= newCost) {
                continue;
            }

            costs[newX][newY] = newCost;

            if (!isEnd(newX, newY)) {
                pairs.add(new Pair(newX, newY));
            }
        }
    }

    private static boolean isUpdated(int x, int y) {
        return costs[x][y] != Integer.MAX_VALUE;
    }

    private static boolean validate(int x, int y) {
        return isStart(x, y) || !isIn(x, y);
    }

    private static boolean isStart(int x, int y) {
        return x == 0 && y == 0;
    }

    private static boolean isIn(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    private static boolean isEnd(int x, int y) {
        return x == size - 1 && y == size - 1;
    }
}