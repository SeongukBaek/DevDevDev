# [1249] 보급로

## :pushpin: **Algorithm**

구현

## :round_pushpin: **Logic**

```java
private static void searchMap(Pair now) {
    int nowX = now.x;
    int nowY = now.y;
    int nowCost = costs[nowX][nowY];
    
    for (int d = 0; d < 4; d++) {
        int newX = nowX + dir[d][0];
        int newY = nowY + dir[d][1];
        
        if (validate(newX, newY)) {
            continue;
        }
        
        int newCost = nowCost + map[newX][newY];
        
        if (isUpdated(newX, newY) && costs[newX][newY] <= newCost) {
            continue;
        }
        
        costs[newX][newY] = newCost;
        
        if (!isEnd(newX, newY)) {
            pairs.add(new Pair(newX, newY));
        }
    }
}
```

- 해당 지점까지의 비용을 저장하는 costs 배열을 사용해 BFS를 수행한다.
- 갔던 지점이라도 해당 지점까지의 비용보다 더 작은 비용으로 이동할 수 있다면 갱신한다.

## :black_nib: **Review**
- 처음에는 DFS를 사용해서 탐색하려했는데 다들 DFS로는 시간초과가 난다길래 BFS로 변경해서 구현했다. 
- 재방문 가능이라는 것을 간과해 시간이 오래 걸렸다.