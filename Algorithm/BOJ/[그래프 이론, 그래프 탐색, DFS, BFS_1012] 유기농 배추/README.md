# [1012] 유기농 배추 - C++

## :pushpin: **Algorithm**

그래프 이론, 그래프 탐색, BFS, DFS

## :round_pushpin: **Logic**

```c++
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
```

## :black_nib: **Review**

- 그래프 문제는 어느 정도 감을 잡은 것 같다 ... 물론 어려운 문제를 만나면 헤매겠지만 기초는 어느 정도 익힌 듯 하다
