import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Stack<Integer> topStack = new Stack<>();
        
        int N = Integer.parseInt(br.readLine());
        
        int[] top = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] receivers = new int[N];
        
        topStack.add(1);
        
        for (int index = 1; index < N; index++) {
        	while (!topStack.empty() && top[topStack.peek() - 1] < top[index]) {
        		int idx = topStack.pop();
        		if (topStack.empty()) {
        			receivers[idx - 1] = 0;
        		} else {
            		receivers[idx - 1] = topStack.peek();
        		}
        	}
        	topStack.push(index + 1);
        }
        
        while (topStack.size() != 1) {
        	int idx = topStack.pop();
			receivers[idx - 1] = topStack.peek();
        }
        
        StringBuilder answer = new StringBuilder();
        for (int receiver : receivers) {
        	answer.append(receiver).append(" ");
        }
        System.out.println(answer);
    }
}