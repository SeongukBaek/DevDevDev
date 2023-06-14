import java.util.*;
import java.io.*;

class Solution
{
    public static void main(String args[]) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int i = 1; i <= T; i++) {
            int N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] array = new int[N];

            for (int n = 0; n < N; n++)
                array[n] = Integer.parseInt(st.nextToken());

            long sum = 0;
            for (int n = N - 1; n >= 0; n--) {
                int s = 0;
                int count = 0;
                int me = array[n];
                for (int k = n - 1; k >= 0; k--)
                    if (array[k] < me) {
                        count++;
                        s += array[k];
                    } else break;
                sum += me * count - s;
                n -= count;
            }

            System.out.println("#" + i + " " + sum);
        }
    }
}