# [2458] 키 순서

## :pushpin: **Algorithm**

그래프 탐색, 플로이드 워셜

## :round_pushpin: **Logic**

```java
for (int index = 0; index < m; index++) {
    st = new StringTokenizer(br.readLine());
    int small = Integer.parseInt(st.nextToken()) - 1;
    int tall = Integer.parseInt(st.nextToken()) - 1;

    isConnected[small][tall] = true;
}
```

- 주어진 순서 정보를 받아, 두 사람의 단방향 연결 여부를 저장한다.

```java
for (int k = 0; k < N; k++) {
    for (int from = 0; from < N; from++) {
        for (int to = 0; to < N; to++) {
            if (isConnected[from][k] && isConnected[k][to]) {
                isConnected[from][to] = true;
            }
        }
    }
}
```

- 저장된 연결 여부를 이용해 모든 사람 쌍들의 연결 여부를 확인한다.

```java
int count = 0;
for (int from = 0; from < N; from++) {
    int connectedCount = 0;
    for (int to = 0; to < N; to++) {
        if (isConnected[from][to] || isConnected[to][from]) {
            connectedCount++;
        }
    }
    if (connectedCount == N - 1) {
        count++;
    }
}
```

- 등수를 매길 수 있다는 말은, 해당 사람이 모든 다른 사람들과 연결되어 있다는 의미이다.
- 따라서 현재 사람과 다른 모든 사람이 연결되어 있는지를 확인한다.

## :black_nib: **Review**

- 불과 얼마전에 플로이드 워셜을 배웠고, 이해하고 있다고 생각했는데, 가중치 개념을 제외하고 연결 여부만을 가지고 이렇게 응용할 수 있다고는 생각해보지 못한 것 같다.