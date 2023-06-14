import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main {
    private static final Set<String> vowelSet = new HashSet<>(Arrays.asList("a", "e", "i", "o", "u"));
    private static final int LEAST_VOWEL_COUNT = 1;
    private static final int LEAST_CONSONANT_COUNT = 2;
    private static int L;
    private static int C;
    private static List<String> words;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        words = Arrays.stream(br.readLine().split(" ")).sorted().collect(Collectors.toList());

        makePossiblePasswords(new StringBuilder(), new boolean[C], 0, 0, 0);
    }

    /**
     * 가능한 암호를 생성
     *
     * @param password 생성되는 암호
     * @param isVisited 특정 문자가 암호에 포함되는지 여부
     * @param vowelCnt 암호에 포함된 모음의 개수
     * @param consonantCnt 암호에 포함된 자음의 개수
     * @param now 현재 확인해야 하는 문자열 인덱스
     */
    private static void makePossiblePasswords(StringBuilder password, boolean[] isVisited, int vowelCnt, int consonantCnt, int now) {
        if (password.length() == L && vowelCnt >= LEAST_VOWEL_COUNT && consonantCnt >= LEAST_CONSONANT_COUNT) {
            print(password);
            return;
        }

        for (int index = now; index < C; index++) {
            if (isVisited[index]) {
                continue;
            }

            String current = words.get(index);
            password.append(current);
            isVisited[index] = true;
            if (vowelSet.contains(current)) {
                makePossiblePasswords(password, isVisited, vowelCnt + 1, consonantCnt, index + 1);
            } else {
                makePossiblePasswords(password, isVisited, vowelCnt, consonantCnt + 1, index + 1);
            }
            isVisited[index] = false;
            password.deleteCharAt(password.length() - 1);
        }
    }

    /**
     * 문자열 출력
     *
     * @param password 출력해야 하는 암호
     */
    private static void print(StringBuilder password) {
        System.out.println(password);
    }
}