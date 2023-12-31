# [42891] 무지의 먹방 라이브 - Java

## :pushpin: **Algorithm**

우선순위 큐

## :round_pushpin: **Logic**

```java
while (totalTime + ((pq.peek().time - prevTime) * len) <= k) {
    int now = pq.poll().time;
    totalTime += (now - prevTime) * len;
    len -= 1;
    prevTime = now;
}
```

- 현재 음식을 먹을 수 있다면, `Queue` 에서 제외하고 전체 음식 먹은 시간을 증가시킨다.
- 현재 음식을 먹을 수 없는 경우까지 이를 반복하고, 이후 `totalTime` 과 남은 음식 개수 `len` 을 이용하여 방송 중단 이후 먹을 음식을 찾는다.

## :black_nib: **Review**
- 처음엔 `K` 와 음식 개수를 이용해 각 음식이 `K` 초 이후 남아있는 양을 계산하여 몇 번째 음식을 먹을지 결정하는 방식을 택했는데, 잘못된 접근법이었다.
- 참고를 하니 "음식량이 적은 것을 다 먹게 된다면 어차피 그 보다 음식량이 큰 것들도 이미 다 먹은 음식만큼 먹게 되는 점"을 이용한 접근법을 사용했었다. 
  - 이 말이 잘 이해되지 않아 시간이 걸렸다.
