# [1600] 말이 되고픈 원숭이

## :pushpin: **Algorithm**

BFS

## :round_pushpin: **Logic**

```java
// 해당 좌표까지 k번 말처럼 이동한 경우의 수
costs = new int[W][H][K + 1];
```

- 주요 자료구조는 3차원 int 배열이다.

```java
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
```

- 아직 말처럼 이동할 수 있는 경우에 대한 처리이다.
- 이전 좌표까지 k번 말처럼 이동했다면, 현재 좌표까지 k + 1번 말처럼 이동한 경우의 수는 이전 좌표까지 이동한 경우의 수 + 1이다.

```java
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
```

- 그냥 이동하는 경우이다.

## :black_nib: **Review**
- 정답률이 20%인 거치고는 ... 바로 풀었다.
- 벽 부수고 이동하기와 비슷한 유형인 거 같아서 3차원 배열을 사용해야겠다는 생각이 바로 들었다.