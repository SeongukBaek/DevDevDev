# [20058] 마법사 상어와 파이어스톰

## :pushpin: **Algorithm**

구현, 시뮬레이션, BFS

## :round_pushpin: **Logic**

```java
private static void rotateMap(int startX, int startY, int splitSize) {
    Queue<Integer> numbers = new ArrayDeque<>();
    for (int y = startY; y < startY + splitSize; y++) {
        for (int x = startX + splitSize - 1; x >= startX; x--) {
            numbers.add(map[x][y]);
        }
    }

    for (int x = startX; x < startX + splitSize; x++) {
        for (int y = startY; y < startY + splitSize; y++) {
            map[x][y] = numbers.poll();
        }
    }
}
```

- 배열에 대한 시계 방향 90도 회전은 큐를 이용해 구현했다.

## :black_nib: **Review**
- 특별한 알고리즘이 필요하지 않은 문제였고, 딱 1시간만에 풀었다.