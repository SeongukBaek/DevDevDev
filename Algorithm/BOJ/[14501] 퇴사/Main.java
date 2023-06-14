import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Consult {
    int t;
    int p;

    public Consult(int t, int p) {
        this.t = t;
        this.p = p;
    }
}

public class Main {
    static Consult[] consults;
    static int[] dp;
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        consults = new Consult[N + 1];
        dp = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            consults[i] = new Consult(t, p);
        }

        if (consults[N].t == 1) dp[N] = consults[N].p;

        for (int i = N - 1; i > 0; i--) {
            int t = consults[i].t;
            int p = consults[i].p;

            int prev = dp[i + 1];
            int after = 0;
            if (i + t <= N)
                after = dp[i + t] + p;
            else if (i + t == N + 1)
                after = p;
            dp[i] = Math.max(prev, after);
        }

        System.out.println(dp[1]);
    }
}