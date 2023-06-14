import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	private static int N;
	private static int M;
	private static int D;
	private static int firstY;
	private static int secondY;
	private static int thirdY;
	private static int max;
	
	private static int[][] map;
	private static int[][] copyMap;
	private static List<int[]> selectedEnemy;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		
		for (int x = 0; x < N; x++) {
			st = new StringTokenizer(br.readLine());
			for (int y = 0; y < M; y++) {
				map[x][y] = Integer.parseInt(st.nextToken());
			}
		}
		
		startWar(new int[3], new boolean[M], 0, 0);
		
		System.out.println(max);
	}
	
	/**
	 * 증가하는 조합으로 궁수들의 y좌표를 생성
	 * */
	private static void startWar(int[] archors, boolean[] isSelected, int start, int r) {
		if (r == 3) {
			copy();
			max = Math.max(max, spaceArchors(archors));
			return;
		}
		
		for (int index = start; index < M; index++) {
			if (isSelected[index]) {
				continue;
			}
			archors[r] = index;
			isSelected[index] = true;
			startWar(archors, isSelected, index + 1, r + 1);
			isSelected[index] = false;
		}
	}
	
	/**
	 * 궁수들의 위치 정보가 주어지면, 적을 처치하고, 죽인 횟수를 반환
	 * @param archors 궁수들의 y 좌표 정보
	 * @return int
	 * */
	private static int spaceArchors(int[] archors) {
		int sum = 0;
		firstY = archors[0];
		secondY = archors[1];
		thirdY = archors[2];
		
		// 턴을 의미
		for (int x = N; x >= 1; x--) {
			selectedEnemy = new ArrayList<>();
			
			// 3명의 궁수가 적을 모두 선택한 후, 제거
			selectEnemy(x, firstY);
			selectEnemy(x, secondY);
			selectEnemy(x, thirdY);
			
			sum += killEnemy();
		}
		
		return sum;
	}
	
	/**
	 * 각 궁수별로 가장 가까운 적을 선택하고 리스트에 저장
	 * @param x 탐색할 맵의 x 좌표 시작점
	 * @param archorY 궁수의 y 좌표
	 * */
	private static void selectEnemy(int x, int archorY) {
		int[] near = null;
		int minDistance = Integer.MAX_VALUE;
		
		for (int y = 0; y < M; y++) {
			for (int enemy = x - 1; enemy >= x - D && enemy >= 0; enemy--) {
				if (copyMap[enemy][y] == 0) {
					continue;
				}
				int distance = computeDistance(x, archorY, enemy, y);
				if (distance <= D && distance < minDistance) {
					minDistance = distance;
					near = new int[] {enemy, y};
				}
			}
		}
		
		if (near != null) {
			selectedEnemy.add(near);
		}
	}
	
	/**
	 * 궁수들이 선택한 모든 적을 제거하고 제거한 횟수 반환
	 * @return int
	 * */
	private static int killEnemy() {
		int sum = 0;
		// 여기서 한 번에 죽이기
		for (int[] enemy : selectedEnemy) {
			if (copyMap[enemy[0]][enemy[1]] == 0) {
				continue;
			}
			sum++;
			copyMap[enemy[0]][enemy[1]] = 0;
		}
		return sum;
	}
	
	private static void copy() {
		copyMap = new int[N][M];
		for (int x = 0; x < N; x++) {
			System.arraycopy(map[x], 0, copyMap[x], 0, M);
		}
	}
	
	private static int computeDistance(int archorX, int archorY, int x, int y) {
		return Math.abs(archorX - x) + Math.abs(archorY - y);
	}
}