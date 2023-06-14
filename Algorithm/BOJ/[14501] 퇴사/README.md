# [14501] 퇴사

## :pushpin: **Algorithm**

DP, 브루트포스

## :round_pushpin: **Logic**

```java
for (int i = N - 1; i > 0; i--) {
    int t = consults[i].t;
    int p = consults[i].p;

    int prev = dp[i + 1];
    int after = 0;
    if (i + t <= N)
        after = dp[i + t] + p;
    else if (i + t == N + 1)
        after = p;
    dp[i] = Math.max(prev, after);
}
```

- `consults`에는 각 날짜별 상담을 하는데 필요한 기간과 금액이 저장되어 있다.
- 뒤에서부터 읽으면서, 두 가지 경우로 나눠 계산한 후 최댓값을 취한다.
  - 오늘말고 내일 상담을 하는 경우
  - 오늘도 하고, 오늘한 상담이 필요한 기간 이후 또 상담을 하는 경우

## :black_nib: **Review**
- 예전에 알고리즘 강의에서 다룬 문제와 비슷해서 두 가지 경우로 나누는 아이디어는 쉽게 생각했다.
- 근데 구현에서 뭘 빠트렸는지 자꾸 틀려서 시간을 많이 썼다.
- 침착하게 풀자.