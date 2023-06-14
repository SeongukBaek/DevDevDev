import java.util.*;
import java.io.*;

class Solution
{
    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T;
        T=Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            int N = Integer.parseInt(br.readLine());
            int[][] map = new int[N][N];
            int sum = 0;

            for (int i = 0; i < N; i++) {
                String[] info = br.readLine().split("");
                for (int j = 0; j < N; j++)
                    map[i][j] = Integer.parseInt(info[j]);
            }

            for (int i = 0; i < N / 2; i++) {
                int range = N / 2 - i;
                for (int j = range; j < N - range; j++)
                    sum += map[i][j];
            }

            for (int j = 0; j < N; j++)
                sum += map[N / 2][j];

            for (int i = N / 2 + 1; i < N; i++) {
                int range = i - N / 2;
                for (int j = range; j < N - range; j++)
                    sum += map[i][j];
            }

            System.out.println("#" + test_case + " " + sum);
        }
    }
}