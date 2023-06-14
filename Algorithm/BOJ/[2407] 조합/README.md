# [2407] 조합

## :pushpin: **Algorithm**

조합, 큰 수 연산

## :round_pushpin: **Logic**

```java
for (int i = 1; i <= n; i++) {
    for (int j = 0; j <= i; j++) {
        if (i == j || j == 0) {
            combiDp[i][j] = BigInteger.ONE;
        } else {
            combiDp[i][j] = combiDp[i - 1][j - 1].add(combiDp[i - 1][j]);
        }
    }
}
```

- DP를 이용해서 조합의 경우의 수를 구하는 시간을 효율적으로 사용할 수 있다.

## :black_nib: **Review**
- 조합을 구하려는 N이 커질 수록, 2^N의 시간복잡도를 가지기에 시간초과를 피할 수 없었다.
- 사실 DP를 사용하는 건 생각해보지도 못했다..
- 이번 기회에 DP를 사용함으로써 효율적으로 조합의 경우의 수를 구할 수 있음을 알 수 있었다. 유용할 것 같다.