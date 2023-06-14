# [12905] 가장 큰 정사각형 찾기

## :pushpin: **Algorithm**

DP

## :round_pushpin: **Logic**

```java
int maxSize = 0;
for (int x = 0; x < row; x++) {
    for (int y = 0; y < col; y++) {
        if (x == 0 || y == 0) {
            dp[x][y] = board[x][y];
        } else if (board[x][y] == 1) {
            dp[x][y] = checkAround(x, y);
        }
        maxSize = Math.max(maxSize, dp[x][y]);
    }
}
```

- 정사각형인 경우, 주변의 정사각형 개수를 구해 해당 좌표까지의 가장 큰 정사각형의 개수를 계산한다.

```java
private static int checkAround(int x, int y) {
    int one = dp[x - 1][y - 1];
    int two = dp[x - 1][y];
    int three = dp[x][y - 1];
    
    if (one == two && two == three) {
        return one + 1;
    }
    
    return Math.min(Math.min(one, two), three) + 1;
}
```

- 상, 좌, 왼쪽 대각선 위 좌표까지의 정사각형 개수를 구한다.
- 모두 같은 값이라면 해당 값의 + 1을 리턴하고,
- 그렇지 않다면 그 값들 중 가장 작은 값의 + 1을 리턴한다.

## :black_nib: **Review**

- 처음 접근은, 가능한 최대 정사각형 크기를 구하고, 각 좌표별로 가능한 정사각형 크기를 모두 구하는 방식이었는데, 시간초과가 발생했다.
- 누적합을 써야 하나 고민해보다가 ... 질문하기 딱 들어가서 DP 쓰는 겁니다하는 힌트만 보고 바로 아차 싶었다.