# [2493] 탑

## :pushpin: **Algorithm**

스택

## :round_pushpin: **Logic**

```java
for (int index = 1; index < N; index++) {
    while (!topStack.empty() && top[topStack.peek() - 1] < top[index]) {
        int idx = topStack.pop();
        if (topStack.empty()) {
            receivers[idx - 1] = 0;
        } else {
            receivers[idx - 1] = topStack.peek();
        }
    }
    topStack.push(index + 1);
}

while (topStack.size() != 1) {
    int idx = topStack.pop();
    receivers[idx - 1] = topStack.peek();
}
```

- 스택의 peek보다 작은 수들을 넣는다. 이때, 삽입되는 수들은 내림차순이어야 한다.
- 스택의 peek보다 큰 수인 경우는, 스택의 내용을 꺼내면서 스택의 peek의 인덱스를 배열에 저장한다.
    - 내림차순으로 삽입되었기에 본인보다 먼저 스택에 들어간 탑에 레이저가 맞기 때문이다.

## :black_nib: **Review**
- 처음에는 DP방식으로 접근했다. 하지만 반례가 있었고, Union-Find방식과 스택을 사용하는 방식이 떠올랐는데, 스택을 사용하는 방식으로 구현해보았다.