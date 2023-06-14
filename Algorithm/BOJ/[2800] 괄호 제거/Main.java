import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static int bracketCount = 0;
    private static final Set<String> list = new TreeSet<>();
    private static String line;
    private static List<int[]> bracketPair;
    private static boolean[] check;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        line = br.readLine();
        bracketPair = new ArrayList<>();

        // 괄호쌍 개수 카운트
        countBracketPair();

        bracketCount = bracketPair.size();
        check = new boolean[line.length()];

        // 뺄 괄호쌍 조합을 만들고, 괄호쌍을 뺀 문자열을 list에 추가
        makeComb(0, line.toCharArray());

        // 만든 식을 사전순 출력
        print();
    }

    private static void countBracketPair() {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '(') stack.push(i);
            else if (c == ')') bracketPair.add(new int[] { stack.pop(), i });
        }
    }

    private static void makeComb(int depth, char[] chars) {
        if (depth == bracketCount) {
            list.add(makeExpression(chars));
            return;
        }

        makeComb(depth + 1, chars);

        int[] bracket = bracketPair.get(depth);
        check[bracket[0]] = true;
        check[bracket[1]] = true;
        makeComb(depth + 1, chars);
        check[bracket[0]] = false;
        check[bracket[1]] = false;
    }

    private static String makeExpression(char[] chars) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chars.length; i++)
            if (!check[i]) sb.append(chars[i]);

        return sb.toString();
    }

    private static void print() {
        // 원본 문자열 제거
        list.remove(line);
        list.forEach(System.out::println);
    }
}