import java.io.*;
import java.util.*;

class Main {
    static class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int computeTime(Pair p) {
            return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
        }
    }

    static class Teleport {
        Pair from;
        Pair to;

        public Teleport(Pair from, Pair to) {
            this.from = from;
            this.to = to;
        }
    }

    private static final List<Teleport> teleports = new ArrayList<>();
    private static long answer = Long.MAX_VALUE;
    private static Pair end;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        Pair now = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

        st = new StringTokenizer(br.readLine());
        end = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());

            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            teleports.add(new Teleport(new Pair(x1, y1), new Pair(x2, y2)));
        }

        search(0, 0, now, new boolean[3]);

        System.out.println(answer);
    }

    private static void search(int depth, long time, Pair now, boolean[] visited) {
        if (depth == 3) {
            answer = Math.min(answer, time + now.computeTime(end));
            return;
        }

        for (int i = 0; i < 3; i++) {
            if (visited[i])
                continue;
            Teleport teleport = teleports.get(i);

            visited[i] = true;
            // 앞 좌표로 텔을 타는 경우
            search(depth + 1, time + now.computeTime(teleport.from) + 10, new Pair(teleport.to.x, teleport.to.y),
                    visited);
            // 뒷 좌표로 텔을 타는 경우
            search(depth + 1, time + now.computeTime(teleport.to) + 10, new Pair(teleport.from.x, teleport.from.y),
                    visited);
            // 텔을 안 타는 경우
            search(depth + 1, time, now, visited);
            visited[i] = false;
        }
    }
}