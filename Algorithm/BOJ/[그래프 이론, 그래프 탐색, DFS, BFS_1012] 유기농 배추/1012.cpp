#include <iostream>
#include <vector>
#include <queue>
using namespace std;

int x_ary[4] = { 0, 0, -1, 1 }, y_ary[4] = { -1, 1, 0, 0 };
int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL); cout.tie(NULL);
	int T, M, N, K;
	cin >> T;
	while (T--) {
		cin >> M >> N >> K;
		vector <vector<int>> map(M, vector<int> (N));
		vector <pair <int, int>> worm;
		int visited[50][50] = { 0 , };
		int worms = 0;

		for (int i = 0; i < K; i++) {
			int x, y;
			cin >> x >> y;
			map[x][y] = 1;
			worm.push_back(make_pair(x, y));
		}
		
		for (int a = 0; a < worm.size(); a++) {
			if (visited[worm[a].first][worm[a].second] == 0) {
				queue <pair<int, int>> q;
				q.push(worm[a]);
				visited[worm[a].first][worm[a].second] = 1;
				while (!q.empty()) {
					pair<int, int> cur = q.front();
					q.pop();
					for (int i = 0; i < 4; i++) {
						int nx = cur.first + x_ary[i], ny = cur.second + y_ary[i];
						if (nx >= 0 && ny >= 0 && nx < M && ny < N) {
							if (visited[nx][ny] == 0 && map[nx][ny] == 1) {
								visited[nx][ny] = 1;
								q.push(make_pair(nx, ny));
							}
						}
					}
				}
				worms++;
			}	
		}

		cout << worms << "\n";
	}

	return 0;
}