import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	private static int N;
	private static boolean[][] isConnected;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		isConnected = new boolean[N][N];

		int m = Integer.parseInt(st.nextToken());

		// 연결여부만 저장
		for (int index = 0; index < m; index++) {
			st = new StringTokenizer(br.readLine());
			int small = Integer.parseInt(st.nextToken()) - 1;
			int tall = Integer.parseInt(st.nextToken()) - 1;

			isConnected[small][tall] = true;
		}

		// 연결 여부를 이용해 모든 정점 쌍들의 연결 여부를 확인
		for (int k = 0; k < N; k++) {
			for (int from = 0; from < N; from++) {
				for (int to = 0; to < N; to++) {
					if (isConnected[from][k] && isConnected[k][to]) {
						isConnected[from][to] = true;
					}
				}
			}
		}

		// N - 1개의 좌표들과 현재 좌표가 연결되어 있는지 확인
		// 등수를 매길 수 있다 = 모든 좌표와 연결되어 있다
		int count = 0;
		for (int from = 0; from < N; from++) {
			int connectedCount = 0;
			for (int to = 0; to < N; to++) {
				if (isConnected[from][to] || isConnected[to][from]) {
					connectedCount++;
				}
			}
			if (connectedCount == N - 1) {
				count++;
			}
		}

		System.out.println(count);
	}
}
