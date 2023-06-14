import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringTokenizer st1 = new StringTokenizer(br.readLine());
        int B = Integer.parseInt(st1.nextToken());
        int C = Integer.parseInt(st1.nextToken());
        long answer = N;

        for (int i = 0; i < N; i++) {
            int person = Integer.parseInt(st.nextToken()) - B;

            if (person > 0)  {
                if (person % C == 0) answer += person / C;
                else answer += (person / C) + 1;
            }
        }

        System.out.println(answer);
    }
}