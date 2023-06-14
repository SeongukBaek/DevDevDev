# [2805] 농작물 수확하기

## :pushpin: **Algorithm**

구현

## :round_pushpin: **Logic**

```java
// 가장 가운데 행의 위쪽 농작물
for (int i = 0; i < N / 2; i++) {
    int range = N / 2 - i;
    for (int j = range; j < N - range; j++)
        sum += map[i][j];
}

// 가운데 행 농작물
for (int j = 0; j < N; j++) 
    sum += map[N / 2][j];

// 가운데 행의 아래쪽 농작물
for (int i = N / 2 + 1; i < N; i++) {
    int range = i - N / 2;
    for (int j = range; j < N - range; j++)
        sum += map[i][j];
}
```

- 구역을 3개로 나눠 생각했다.
- 가운데 행을 기준으로 위, 가운데, 아래 구역을 나눠 인덱스 접근을 구현했다.

## :black_nib: **Review**
- 쉬운 문제였으나 적은 이유는, 하반기 라인 코테에서 이와 같은 인덱스 접근을 활용한 문제를 본 기억이 있었는데, 시간이 모자라서 해결하지 못했었다.
- 지금 다시 보니, BFS를 쓰기 보다는 **구역을 나눈 인덱스 접근 방법** 또한 적절한 방법이 되지 않을까 싶다.
  - 여기서 반복되는 구간이 있다면 함수로 추출한다면 더 가독성이 좋을 것 같다.