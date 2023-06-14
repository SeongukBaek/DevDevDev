import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());

		int max = 0;
		int size = 0;
		int[] eat = new int[d + 1];
		int[] storeFood = new int[N];
		Deque<Integer> foods = new ArrayDeque<>();

		for (int index = 0; index < N; index++) {
			int sushi = Integer.parseInt(br.readLine());
			storeFood[index] = sushi;
			eat[sushi]++;
			if (eat[sushi] == 1) {
				size++;
			}
			foods.addLast(sushi);

			if (index >= k - 1) {
				int tempSize = size;
				if (eat[c] == 0) {
					tempSize++;
				}

				if (max < tempSize) {
					max = tempSize;
				}

				int food = foods.pollFirst();

				eat[food]--;
				if (eat[food] == 0) {
					size--;
				}
			}
		}

		for (int end = 0; end < k - 1; end++) {
			int sushi = storeFood[end];
			eat[sushi]++;
			if (eat[sushi] == 1) {
				size++;
			}
			foods.addLast(sushi);

			int tempSize = size;
			if (eat[c] == 0) {
				tempSize++;
			}

			if (max < tempSize) {
				max = tempSize;
			}

			int food = foods.pollFirst();

			eat[food]--;
			if (eat[food] == 0) {
				size--;
			}
		}

		System.out.println(max);
	}
}