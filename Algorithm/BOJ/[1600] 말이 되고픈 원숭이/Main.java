import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
	private static int W;
	private static int H;
	private static int K;
	private static int[][] map;
	private static int[][][] costs;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		K = Integer.parseInt(br.readLine());

		String[] line = br.readLine().split(" ");

		W = Integer.parseInt(line[1]);
		H = Integer.parseInt(line[0]);

		map = new int[W][H];
		// 해당 좌표까지 k번 말처럼 이동한 경우의 수
		costs = new int[W][H][K + 1];

		for (int x = 0; x < W; x++) {
			map[x] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		}

		if (W == 1 && H == 1) {
			System.out.println(0);
			return;
		}

		traverse();

		System.out.println(getMin());
	}

	private static final int[][] horseMoves = { { -2, 1 }, { -1, 2 }, { 1, 2 }, { 2, 1 }, { 2, -1 }, { 1, -2 },
			{ -1, -2 }, { -2, -1 } };
	private static final int[][] monkeyMoves = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

	private static void traverse() {
		Queue<int[]> locations = new ArrayDeque<>();
		locations.add(new int[] { 0, 0, 0 });

		while (!locations.isEmpty()) {
			int[] current = locations.poll();
			int w = current[0];
			int h = current[1];
			int k = current[2];

			// 말처럼 이동할 수 있는 경우
			if (k < K) {
				for (int[] horseMove : horseMoves) {
					int nw = w + horseMove[0];
					int nh = h + horseMove[1];

					if (!isIn(nw, nh) || map[nw][nh] == 1 || costs[nw][nh][k + 1] != 0) {
						continue;
					}

					costs[nw][nh][k + 1] = costs[w][h][k] + 1;

					if (nw == W - 1 && nh == H - 1) {
						continue;
					}

					locations.add(new int[] { nw, nh, k + 1 });
				}
			}

			// 그냥 이동하는 경우
			for (int[] monkeyMove : monkeyMoves) {
				int nw = w + monkeyMove[0];
				int nh = h + monkeyMove[1];

				if (!isIn(nw, nh) || map[nw][nh] == 1 || costs[nw][nh][k] != 0) {
					continue;
				}

				costs[nw][nh][k] = costs[w][h][k] + 1;

				if (nw == W - 1 && nh == H - 1) {
					continue;
				}

				locations.add(new int[] { nw, nh, k });
			}
		}
	}

	private static boolean isIn(int x, int y) {
		return 0 <= x && x < W && 0 <= y && y < H;
	}

	private static int getMin() {
		return Arrays.stream(costs[W - 1][H - 1]).filter(value -> value > 0).min().orElse(-1);
	}
}