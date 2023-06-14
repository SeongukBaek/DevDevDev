# [2239] 스도쿠

## :pushpin: **Algorithm**

백트래킹

## :round_pushpin: **Logic**

```java
private static void fillBoard(int row, int col) {
    if (isDone) {
        return;
    }

    // 다음 0 위치 찾아서 재귀
    int[] next = findZero(row);
    // 만약 0이 더 없다면, 스도쿠를 다 채운 상태이므로 종료
    if (next[0] == -1) {
        print();
        isDone = true;
        return;
    }

    for (int number = 1; number <= SIZE; number++) {
        // 해당 행, 열에 해당 숫자를 넣을 수 있는지 확인
        // 해당 네모에 해당 숫자를 넣을 수 있는지 확인
        if (!hasSameInRow(next[0], number) && !hasSameInCol(next[1], number) && !hasSameInSquare(next[0], next[1], number)) {
            board[next[0]][next[1]] = number;
            fillBoard(next[0], next[1]);
            board[next[0]][next[1]] = 0;
        }
    }
}
```

- 백트래킹을 통해 가장 작은 수부터 빈 자리에 채워넣는다.
- 만약 불가능한 경우가 있다면 가지치기를 수행하고 되돌아가서 다음 숫자를 넣어본다.

## :black_nib: **Review**

- 스도쿠를 실제로 어떻게 해결하는지 생각해보면 금방 풀리는 백트래킹 문제였다!
