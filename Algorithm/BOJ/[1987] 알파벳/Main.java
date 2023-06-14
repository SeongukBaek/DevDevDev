import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	private static int R;
	private static int C;
	private static char[][] map;
	private static int max;
	private static int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
	private static boolean[] passedAlphabets;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		map = new char[R][C];
		passedAlphabets = new boolean[26];
		
		for (int x = 0; x < R; x++) {
			String line = br.readLine();
			for (int y = 0; y < C; y++) {
				map[x][y] = line.charAt(y);
			}
		}
		
		passedAlphabets[charToInt(map[0][0])] = true;
		findMaxCount(0, 0, 1);
		
		System.out.println(max);
	}
	
	private static void findMaxCount(int x, int y, int length) {
		for (int[] direction : directions) {
			int nx = x + direction[0];
			int ny = y + direction[1];
			
			if (!isInBoundary(nx, ny)) {
				continue;
			}
			
			if (passedAlphabets[charToInt(map[nx][ny])]) {
				if (max < length) {
					max = length;
				}
				continue;
			}
			
			passedAlphabets[charToInt(map[nx][ny])] = true;
			findMaxCount(nx, ny, length + 1);
			passedAlphabets[charToInt(map[nx][ny])] = false;
		}
	}
	
	private static int charToInt(char value) {
		return value - 'A';
	}
	
	private static boolean isInBoundary(int x, int y) {
		return x >= 0 && x < R && y >= 0 && y < C;
	}
}
