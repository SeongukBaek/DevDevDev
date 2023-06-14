# [1859] 백만 장자 프로젝트

## :pushpin: **Algorithm**

구현, 시뮬레이션

## :round_pushpin: **Logic**

```java
long sum = 0;
for (int n = N - 1; n >= 0; n--) {
    int s = 0;
    int count = 0;
    int me = array[n];
    for (int k = n - 1; k >= 0; k--)
        if (array[k] < me) {
            count++;
            s += array[k];
        } else break;
    sum += me * count - s;
    n -= count;
}
```

- 배열의 뒤부터 탐색하면서,
  - 포인트로 잡은 현재 수보다 작지 않은 수가 나올 떄까지 누적합한다.
  - 크거나 작은 수가 나오면 **(현재 수 * 누적합한 개수) - 누적합**하면 수익이 나온다.

## :black_nib: **Review**
- 처음 시도는 앞에서부터 값을 읽으면서 더 커지는 경우가 발생하면 계산하는 방식으로 하려다가 바로 안되는 것을 알고 뒤에서부터 읽는 방법을 생각했다.
- 뒤에서부터 읽으면서 본인보다 작지 않은 수가 나올 즉시 누적 합을 멈추고 수익 계산을 하는 방식으로 하니 바로 풀리긴 했다.
- 처음 SW Expert Academy 문제를 풀어본거여서 입출력 방식이 상당히 낯설었던 관계로 정리...