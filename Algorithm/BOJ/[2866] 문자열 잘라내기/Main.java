import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    private static List<StringBuilder> words;
    private static int R;
    private static int C;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        words = new ArrayList<>();
        for (int index = 0; index < C; index++) {
            words.add(new StringBuilder());
        }

        for (int x = 0; x < R; x++) {
            String line = br.readLine();
            for (int y = 0; y < C; y++) {
                words.get(y).append(line.charAt(y));
            }
        }

        int count = 0;
        while(!isDuplicate()) {
            count++;
        }

        System.out.println(count);
    }

    private static boolean isDuplicate() {
        Set<String> candidates = new HashSet<>();

        for (int index = 0; index < C; index++) {
            StringBuilder word = words.get(index);
            word.deleteCharAt(0);
            words.set(index, word);
            candidates.add(word.toString());
        }

        return words.size() != candidates.size();
    }
}
