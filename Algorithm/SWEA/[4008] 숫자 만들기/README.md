# [4008] 숫자 만들기

## :pushpin: **Algorithm**

DFS

## :round_pushpin: **Logic**

```java
private static void makeOrders(int depth) {
    if (depth == N - 1) {
        values.add(computeExpression(orders));
        return;
    }

    for (int index = 0; index < SIZE; index++) {
        if (operators[index] == 0) {
            continue;
        }
        orders.push(index);
        operators[index]--;
        makeOrders(depth + 1);
        operators[index]++;
        orders.pop();
    }
}
```

- N - 1개의 연산자 순서를 만들어야 한다.
  - 완성되면 바로 계산해서, 최댓값과 최솟값을 구하기 위해 Set에 추가한다.

## :black_nib: **Review**
- 순열을 생각했다가, 중복 순열을 생각했다가, 중복이 제한된 순열을 구하려 했다.
- 하지만 시간초과가 발생했고... 참고를 보니 DFS를 활용해 백트래킹을 수행해야 한다고 했다.