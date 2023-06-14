import java.util.Scanner;
import java.io.*;

class Solution
{
    // 0은 우, 1은 하, 2는 좌, 3은 상
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int N;
    public static void main(String args[]) throws IOException
    {
        Scanner sc = new Scanner(System.in);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int T;
        T=sc.nextInt();

        for (int i = 1; i <= T; i++) {
            N = sc.nextInt();
            int[][] map = new int[N][N];

            int x = 0;
            int y = 1;
            int d = 0;
            int n = 1;
            map[0][0] = n++;

            while(n <= N*N) {
                if (isIn(x, y) && map[x][y] == 0)
                    map[x][y] = n++;
                else {
                    x -= dir[d][0];
                    y -= dir[d][1];
                    d = (d + 1) % 4;
                }

                x += dir[d][0];
                y += dir[d][1];
            }

            bw.write("#" + i + "\n");
            for (int a = 0; a < N; a++) {
                for (int b = 0; b < N; b++)
                    bw.write(map[a][b] + " ");
                bw.write("\n");
            }
            bw.flush();
        }
        bw.close();
        sc.close();
    }
    private static boolean isIn(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }
}