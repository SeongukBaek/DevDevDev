# [1806] 부분합

## :pushpin: **Algorithm**

누적합, 투 포인터

## :round_pushpin: **Logic**

```java
private static void findMinLength() {
    int left = 0;
    int right = 0;
    int sum = numbers[right];

    while (left <= right) {
        if (sum >= S) {
            minLength = Math.min(minLength, right - left + 1);
            sum -= numbers[left++];
            continue;
        }

        if (right + 1 == N) {
            break;
        }
        sum += numbers[++right];
    }
}
```

- 투 포인터를 활용해서, 부분 수열의 합을 누적하면서 가장 짧은 길이를 갱신한다.

## :black_nib: **Review**

- 아이디어는 바로 떠올려서 바로 구현했으나, 1%에서 계속 틀렸다.
- 알고보니, minLength의 초깃값 세팅을 100001이 아닌 10001로 세팅해서 틀린 거였다..
