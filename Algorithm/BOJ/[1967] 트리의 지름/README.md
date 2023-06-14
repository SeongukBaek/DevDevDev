# [1967] 트리의 지름

## :pushpin: **Algorithm**

트리, DFS

## :round_pushpin: **Logic**

```java
private static void findFar(boolean[] isVisited, int prevNode, int distance) {
    if (adjInfo.get(prevNode).size() == 1 && isVisited[adjInfo.get(prevNode).get(0)[0]]) {
        if (distance > howFar) {
            farNode = prevNode;
            howFar = distance;
        }
        return;
    }

    isVisited[prevNode] = true;
    for (int[] next : adjInfo.get(prevNode)) {
        if (isVisited[next[0]]) {
            continue;
        }
        findFar(isVisited, next[0], distance + next[1]);
    }
}
```

- DFS로, 주어진 시작노드로부터 가장 멀리 떨어진 노드와 그 거리를 찾는다.
- DFS로, 리프 노드를 하나 찾는다.
- 이후, 특정 리프 노드로부터 가장 멀리 떨어진 리프 노드를 찾는다.
- 하지만 이때 시작점이 되는 특정 리프 노드는 항상 트리의 지름이 되는 리프 노드가 아닐 수 있기 때문에, 찾은 가장 멀리 떨어진 리프 노드로부터 다시 DFS를 통해 트리의 지름을 구한다.

## :black_nib: **Review**

- 리프 노드를 활용하는 방식 .. 기억해야겠다.
