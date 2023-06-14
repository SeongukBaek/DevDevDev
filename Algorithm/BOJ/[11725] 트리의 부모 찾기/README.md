# [11725] 트리의 부모 찾기

## :pushpin: **Algorithm**

BFS

## :round_pushpin: **Logic**

```java
private static void searchParents() {
    Queue<Integer> nodes = new LinkedList<>();
    nodes.add(1);

    while(!nodes.isEmpty()) {
        int now = nodes.poll();
        visited[now] = true;

        for (int next : adjList.get(now)) {
            if (visited[next]) {
                continue;
            }

            parents[next] = now;
            nodes.add(next);
        }
    }
}
```

- 큐를 사용해 BFS를 수행한다.

## :black_nib: **Review**
- 처음 접근은 부모 찾기라고 해서 Union-Find를 사용해서 입력을 받음과 동시에 부모를 찾아가는 방식으로 구현하려 했다.
- 구현하다보니, 모든 입력 정보가 필요한 후 부모를 탐색해야 함을 깨닫고, BFS를 사용하는 방식으로 변경했다.