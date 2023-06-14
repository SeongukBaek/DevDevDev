# [2636] 치즈

## :pushpin: **Algorithm**

시뮬레이션, BFS

## :round_pushpin: **Logic**

```java
int hour = 0;
int prev = cheeses;

while (cheeses != 0) {
    hour++;

    searchEdge();

    // 직전 치즈 개수 저장
    prev = cheeses;
    cheeses -= meltCheese.size();

    melt();
}
```

- 치즈 개수를 기준으로 반복을 수행한다.
- 지워질 치즈를 찾아 Deque에 저장하고, 치즈를 녹인다.

```java
while (!locations.isEmpty()) {
    int[] current = locations.poll();

    for (int[] direction : directions) {
        int nx = current[0] + direction[0];
        int ny = current[1] + direction[1];

        if (!isIn(nx, ny) || isVisited[nx][ny]) {
            continue;
        }

        isVisited[nx][ny] = true;
        int[] next = {nx, ny};

        if (board[nx][ny] == 1) {
            meltCheese.add(next);
            continue;
        }

        locations.add(next);
    }
}
```

- 0,0에서 출발해서, 치즈를 만나게 되면 해당 치즈는 녹을 치즈이다.

## :black_nib: **Review**

- 처음에는 0과 인접한 모든 치즈가 녹는 줄 알고 코드를 작성했는데, 다르게 접근한 풀이였다.
- 골드 4... 정도는 아닌 거 같다.
