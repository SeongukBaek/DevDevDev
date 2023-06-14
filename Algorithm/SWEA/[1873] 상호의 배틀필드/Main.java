import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
    static class Tank {
        int x;
        int y;
        int direction;
        Tank(int x, int y, int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }
    private static char[][] map;
    private static int H;
    private static int W;
    private static Tank tank;
    // 상 : 0, 우 : 1, 하 : 2, 좌 : 3
    private static int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();

        for (int test = 1; test <= T; test++) {
            st = new StringTokenizer(br.readLine());
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            map = new char[H][W];

            for (int x = 0; x < H; x++) {
                String line = br.readLine();
                for (int y = 0; y < W; y++) {
                    char current = line.charAt(y);
                    map[x][y] = current;

                    if (current == '^') {
                        tank = new Tank(x, y, 0);
                    }

                    if (current == 'v') {
                        tank = new Tank(x, y, 2);
                    }

                    if (current == '<') {
                        tank = new Tank(x, y, 3);
                    }

                    if (current == '>') {
                        tank = new Tank(x, y, 1);
                    }
                }
            }

            br.readLine();
            String commands = br.readLine();

            for (char command : commands.toCharArray()) {
                operateCommand(command);
            }

            answer.append("#").append(test).append(" ");
            answer.append(mapToString());
        }

        System.out.print(answer);
    }

    private static void operateCommand(char command) {
        if (command == 'S') {
            shooting();
            return;
        }

        tank.direction = getDirection(command);

        int nx = tank.x + directions[tank.direction][0];
        int ny = tank.y + directions[tank.direction][1];
        // 평지라면 이동
        if (isInBoundary(nx, ny) && map[nx][ny] == '.') {
            map[tank.x][tank.y] = '.';
            tank.x = nx;
            tank.y = ny;
        }
        map[tank.x][tank.y] = getShape(tank.direction);
    }

    private static int getDirection(char command) {
        if (command == 'U') {
            return 0;
        }
        if (command == 'D') {
            return 2;
        }
        if (command == 'L') {
            return 3;
        }
        return 1;
    }

    private static char getShape(int direction) {
        if (direction == 0) {
            return '^';
        }
        if (direction == 2) {
            return 'v';
        }
        if (direction == 3) {
            return '<';
        }
        return '>';
    }

    private static void shooting() {
        int shotX = tank.x;
        int shotY = tank.y;
        int shotD = tank.direction;

        // 맵 밖이거나, 강철벽이라면 소멸
        while (isInBoundary(shotX, shotY) && map[shotX][shotY] != '#') {// 벽돌로 된 벽이라면 벽 부수고 소멸
            if (map[shotX][shotY] == '*') {
                map[shotX][shotY] = '.';
                break;
            }

            shotX += directions[shotD][0];
            shotY += directions[shotD][1];
        }
    }

    private static String mapToString() {
        StringBuilder result = new StringBuilder();
        for (char[] row : map) {
            for (char item : row) {
                result.append(item);
            }
            result.append("\n");
        }
        return result.toString();
    }

    private static boolean isInBoundary(int x, int y) {
        return x >= 0 && x < H && y >= 0 && y < W;
    }
}