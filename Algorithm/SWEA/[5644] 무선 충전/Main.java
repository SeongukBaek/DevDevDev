import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

class Solution {
    static class Charger implements Comparable<Charger> {
        int x;
        int y;
        int c;
        int p;
        Charger(int x, int y, int c, int p) {
            this.x = x;
            this.y = y;
            this.c = c;
            this.p = p;
        }

        @Override
        public boolean equals(Object obj) {
            Charger charger = (Charger) obj;
            return this.x == charger.x && this.y == charger.y && this.c == charger.c && this.p == charger.p;
        }

        @Override
        public int compareTo(Charger c) {
            return c.p - this.p;
        }
    }
    private static int M;
    private static int A;
    private static int aX;
    private static int aY;
    private static int bX;
    private static int bY;
    private static int sum;
    // 상 : 0, 우 : 1, 하 : 2, 좌 : 3
    private static int[][] directions = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    private static List<Charger> chargers;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();

        for (int test = 1; test <= T; test++) {
            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());
            aX = aY = 0;
            bX = bY = 9;
            sum = 0;
            chargers = new ArrayList<>();

            int[] moveA = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int[] moveB = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            for (int index = 0; index < A; index++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int p = Integer.parseInt(st.nextToken());
                chargers.add(new Charger(x - 1, y - 1, c, p));
            }

            computeChargeAmount();
            for (int time = 0; time < M; time++) {
                // 사용자 이동
                moveUserA(moveA[time] - 1);
                moveUserB(moveB[time] - 1);

                computeChargeAmount();
            }

            answer.append("#").append(test).append(" ");
            answer.append(sum).append("\n");
        }

        System.out.print(answer);
    }

    private static void computeChargeAmount() {
        // 충전기와의 거리 계산 후, 각 사용자가 사용할 수 있는 충전기 저장
        Queue<Charger> userA = new PriorityQueue<>();
        Queue<Charger> userB = new PriorityQueue<>();

        for (Charger charger : chargers) {
            if (computeDistance(aX, aY, charger) <= charger.c) {
                userA.add(charger);
            }
            if (computeDistance(bX, bY, charger) <= charger.c) {
                userB.add(charger);
            }
        }

        // 둘다 하나 이상의 무선 충전이 가능하고, 그 값이 다 다른 경우
        if (userA.size() > 0 && userB.size() > 0 && userA.peek().p != userB.peek().p) {
            sum += userA.peek().p + userB.peek().p;
            return;
        }

        // 한 명의 사용자만 무선 충전이 가능한 경우
        if (userA.size() == 0 && userB.size() > 0) {
            sum += userB.peek().p;
            return;
        }
        if (userA.size() > 0 && userB.size() == 0) {
            sum += userA.peek().p;
            return;
        }

        // 두 개 이상의 무선충전이 가능해서, 최대 값을 찾아야 하는 경우
        Set<Charger> chargerSet = new HashSet<>();
        chargerSet.addAll(userA);
        chargerSet.addAll(userB);

        Queue<Charger> allChargers = new PriorityQueue<>();
        allChargers.addAll(chargerSet);

        for (int count = 0; count < 2; count++) {
            if (allChargers.isEmpty()) {
                break;
            }
            sum += allChargers.poll().p;
        }
    }

    private static int computeDistance(int userX, int userY, Charger charger) {
        return Math.abs(charger.x - userX) + Math.abs(charger.y - userY);
    }

    private static void moveUserA(int move) {
        if (move == -1) {
            return;
        }
        aX += directions[move][0];
        aY += directions[move][1];
    }

    private static void moveUserB(int move) {
        if (move == -1) {
            return;
        }

        bX += directions[move][0];
        bY += directions[move][1];
    }
}