# [5656] 벽돌 깨기

## :pushpin: **Algorithm**

구현, 중복 조합, 백트래킹

## :round_pushpin: **Logic**

```java
private static void tryBrickBreaker(int[][] tempMap, int count) {
    if (count == N) {
        minBlocks = Math.min(minBlocks, getRemainBlocks(tempMap));
        return;
    }
    
    for (int index = 0; index < W; index++) {
        tryBrickBreaker(moveBlocks(bomb(copy(tempMap), index)), count + 1);
    }
}
```

- 백트래킹 방식으로, 모든 열에 대해 벽돌 깨기를 수행해보고 최소블록을 만드는 경우를 찾는다.

```java
private static int[][] moveBlocks(int[][] tempMap) {
    Queue<Integer> blocks;
    for (int y = 0; y < W; y++) {
        // 하나의 열 기준으로 큐에 0이 아닌 블록들을 삽입
        int x;
        blocks = new LinkedList<>();
        for (x = H - 1; x >= 0; x--) {
            int block = tempMap[x][y];
            if (block != 0) {
                blocks.add(block);
                tempMap[x][y] = 0;
            }
        }
        
        // 하나의 열 기준으로 W - 1부터 큐에 있는 값을 poll해서 저장
        x = H - 1;
        while (!blocks.isEmpty()) {
            tempMap[x--][y] = blocks.poll();
        }
    }
    return tempMap;
}
```
- 벽돌 깨기를 수행한 후 벽돌들을 옮기는데에는 큐를 사용했다.

## :black_nib: **Review**
- 처음에는 항상 최소의 블록을 만드는 경우가 존재할 거라 생각해, 특정 벽돌을 찾은 후 벽돌 깨기를 수행하려 했는데, 같은 행에 동일한 숫자를 가진 벽돌이 있는 경우, 어떤 벽돌에 대한 우선 순위를 둬야할지 생각해보니 모든 경우를 돌아보지 않고서는 불가능할 거라 판단했다. (주어진 인풋 또한 범위 내에서 가능할 거라 판단했다.)
    - 따라서 백트래킹 방식을 이용해 모든 경우를 다 해보고 최소 블럭을 찾도록 수정했다.