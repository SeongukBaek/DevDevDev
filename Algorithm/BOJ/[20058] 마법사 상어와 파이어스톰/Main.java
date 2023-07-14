import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

	private static int[][] map;
	private static int size;
	private static int sum;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] info = br.readLine().split(" ");

		int N = Integer.parseInt(info[0]);

		size = 1 << N;

		map = new int[size][size];

		for (int x = 0; x < size; x++) {
			map[x] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			sum += Arrays.stream(map[x]).sum();
		}

		int[] Ls = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

		for (int L : Ls) {
			fireStorm(L);
		}

		computeMaxLump();

		System.out.println(sum);
		System.out.println(maxLump);
	}

	private static void fireStorm(int L) {
		// 맵 분할
		int splitSize = 1 << L;

		if (L != 0) {
			// 각 90도 회전
			rotateMap(splitSize);
		}

		// 각 좌표별 탐색
		traverseMap();
	}

	private static void rotateMap(int splitSize) {
		for (int x = 0; x <= size - splitSize; x += splitSize) {
			for (int y = 0; y <= size - splitSize; y += splitSize) {
				rotateMap(x, y, splitSize);
			}
		}
	}

	private static void rotateMap(int startX, int startY, int splitSize) {
		Queue<Integer> numbers = new ArrayDeque<>();
		for (int y = startY; y < startY + splitSize; y++) {
			for (int x = startX + splitSize - 1; x >= startX; x--) {
				numbers.add(map[x][y]);
			}
		}

		for (int x = startX; x < startX + splitSize; x++) {
			for (int y = startY; y < startY + splitSize; y++) {
				map[x][y] = numbers.poll();
			}
		}
	}

	private static final int[][] directions = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

	private static void traverseMap() {
		List<int[]> removes = new ArrayList<>();

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (map[x][y] == 0) {
					continue;
				}

				int count = 0;
				for (int[] direction : directions) {
					int nx = x + direction[0];
					int ny = y + direction[1];

					// 4방 탐색 시 다음 좌표가 맵 안에 있고, 얼음이 존재하는 칸
					if (isIn(nx, ny) && map[nx][ny] >= 1) {
						count++;
					}
				}

				if (count >= 3) {
					continue;
				}

				removes.add(new int[] { x, y });
			}
		}

		sum -= removes.size();
		for (int[] location : removes) {
			map[location[0]][location[1]]--;
		}
	}

	private static boolean isIn(int x, int y) {
		return 0 <= x && x < size && 0 <= y && y < size;
	}

	private static int maxLump;
	private static boolean[][] isVisited;

	private static void computeMaxLump() {
		isVisited = new boolean[size][size];

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (isVisited[x][y] || map[x][y] == 0) {
					continue;
				}

				computeMaxLump(x, y);
			}
		}
	}

	private static void computeMaxLump(int x, int y) {
		Queue<int[]> locations = new ArrayDeque<>();
		locations.add(new int[] { x, y });
		int lump = 1;
		isVisited[x][y] = true;

		while (!locations.isEmpty()) {
			int[] current = locations.poll();

			for (int[] direction : directions) {
				int nx = current[0] + direction[0];
				int ny = current[1] + direction[1];

				if (!isIn(nx, ny) || isVisited[nx][ny] || map[nx][ny] == 0) {
					continue;
				}

				lump++;
				isVisited[nx][ny] = true;
				locations.add(new int[] { nx, ny });
			}
		}

		maxLump = Math.max(maxLump, lump);
	}
}