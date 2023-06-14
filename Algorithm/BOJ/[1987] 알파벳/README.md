# [1987] 알파벳

## :pushpin: **Algorithm**

DFS

## :round_pushpin: **Logic**

```java
private static void findMaxCount(int x, int y, int length) {
    for (int[] direction : directions) {
        int nx = x + direction[0];
        int ny = y + direction[1];
        
        if (!isInBoundary(nx, ny)) {
            continue;
        }
        
        if (passedAlphabets[charToInt(map[nx][ny])]) {
            if (max < length) {
                max = length;
            }
            continue;
        }
        
        passedAlphabets[charToInt(map[nx][ny])] = true;
        findMaxCount(nx, ny, length + 1);
        passedAlphabets[charToInt(map[nx][ny])] = false;
    }
}
```

- DFS로 사방탐색을 하면서, 이미 지나온 알파벳이 아닌 경우만 재귀 호출을 수행한다.

## :black_nib: **Review**
- 중복 확인 로직을 Set 자료구조를 활용해 구현했다. 물론 정답은 나왔지만, 메모리가 생각보다 많이 잡아먹었다.
- 그렇다면 다른 자료구조를 사용할 수 없을까 하다가, boolean 배열을 사용할 수 있겠다 싶었고, 메모리를 매우 많이 줄일 수 있었다!