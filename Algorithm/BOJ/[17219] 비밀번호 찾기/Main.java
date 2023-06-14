import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    private static final Map<String, String> sitePassword = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        for (int index = 0; index < N; index++) {
            st = new StringTokenizer(br.readLine());
            sitePassword.put(st.nextToken(), st.nextToken());
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int index = 0; index < M; index++) {
            st = new StringTokenizer(br.readLine());
            bw.append(sitePassword.get(st.nextToken())).append("\n");
        }
        bw.flush();
    }
}