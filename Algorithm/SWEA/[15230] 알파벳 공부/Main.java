import java.util.*;
import java.io.*;

class Solution
{
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++) {
            String str = br.readLine();
            int size = str.length();

            int answer = 0;
            int prev = 96;
            for (int i = 0; i < size; i++) {
                char ch = str.charAt(i);

                if (prev + 1 != ch) break;
                answer++;
                prev++;
            }

            System.out.println("#" + test_case + " " + answer);
        }
    }
}