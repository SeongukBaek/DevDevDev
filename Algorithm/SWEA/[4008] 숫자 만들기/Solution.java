import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Solution {
    private static int[] numbers;
    private static int[] operators;
    private static int N;
    private static final int SIZE = 4;
    private static Stack<Integer> orders;
    private static Set<Integer> values;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        StringTokenizer st;

        for (int test_case = 1; test_case <= T; test_case++) {
            answer.append("#").append(test_case).append(" ");
            N = Integer.parseInt(br.readLine());
            operators = new int[SIZE];
            numbers = new int[N];
            values = new HashSet<>();

            st = new StringTokenizer(br.readLine());
            for (int index = 0; index < SIZE; index++) {
                operators[index] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int index = 0; index < N; index++) {
                numbers[index] = Integer.parseInt(st.nextToken());
            }

            orders = new Stack<>();
            makeOrders(0);

            answer.append(Collections.max(values) - Collections.min(values)).append("\n");
        }
        System.out.print(answer);
    }

    private static void makeOrders(int depth) {
        if (depth == N - 1) {
            values.add(computeExpression(orders));
            return;
        }

        for (int index = 0; index < SIZE; index++) {
            if (operators[index] == 0) {
                continue;
            }
            orders.push(index);
            operators[index]--;
            makeOrders(depth + 1);
            operators[index]++;
            orders.pop();
        }
    }

    private static int computeExpression(Stack<Integer> orders) {
        int result = numbers[0];
        for (int index = 0; index < orders.size(); index++) {
            int number = numbers[index + 1];
            switch (orders.get(index)) {
                case 0:
                    result += number;
                    break;
                case 1:
                    result -= number;
                    break;
                case 2:
                    result *= number;
                    break;
                case 3:
                    result /= number;
                    break;
            }
        }
        return result;
    }
}