# [1012] 유기농 배추 - Java

## :pushpin: **Algorithm**

그래프 이론, 그래프 탐색, BFS, DFS

## :round_pushpin: **Logic**

```java
class Pair {
    private int first;
    private int second;

    Pair(int x, int y) {
        this.first = x;
        this.second = y;
    }

    public int getF() {
        return first;
    }

    public int getS() {
        return second;
    }
}
```
- `Pair` 의 구현을 위해 위와 같은 **class** 를 생성했다.

```java
for (int a = 0; a < worm.size(); a++) {
    int vF = worm.get(a).getF();
    int vS = worm.get(a).getS();
    if (visited[vF][vS] == 0) {
        Queue<Pair> q = new LinkedList<>();
        q.offer(worm.get(a));
        visited[vF][vS] = 1;
        while (!q.isEmpty()) {
            Pair cur = q.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur.getF() + x_ary[i], ny = cur.getS() + y_ary[i];
                if (nx >= 0 && ny >= 0 && nx < M && ny < N) {
                    if (visited[nx][ny] == 0 && map[nx][ny] == 1) {
                        visited[nx][ny] = 1;
                        q.offer(new Pair(nx, ny));
                    }
                }
            }
        }
        worms++;
    }
}
```

## :black_nib: **Review**

- c++로 풀었던 문제를 java로 변환하여 다시 풀어본 문제
- 논리 자체는 기본적인 DFS를 사용한 문제여서 java 문법을 오랜만에 다시 써본 문제였다.