# [178870] 연속된 부분 수열의 합

## :pushpin: **Algorithm**

이진 탐색, 투 포인터

## :round_pushpin: **Logic**

```java
while (left < right) {
    int mid = (left + right) / 2;

    if (sequence[mid] == half) {
        left = mid;
        right = mid;
        break;
    }
    right = mid;
}
```

- 이진 탐색으로 부분 수열을 탐색할 시작 인덱스를 잡는다.

```java
int answerLeft = 0;
int answerRight = sequence.length;
int sum = sequence[left];
while (left <= right && right < sequence.length) {
    if (sum < k) {
        if (right == sequence.length - 1) {
            break;
        }
        sum += sequence[++right];
        continue;
    }
    if (sum > k) {
        sum -= sequence[left++];
        continue;
    }
    if (sum == k) {
        if (answerRight - answerLeft > right - left) {
            answerLeft = left;
            answerRight = right;
        }
        sum -= sequence[left++];
    }
}
```

- 투 포인터를 사용해 가장 짧고 맨 앞에 가까운 부분 수열을 찾는다.

## :black_nib: **Review**

- 이진 탐색과 투 포인터가 합쳐진 형태의 문제는 거의 처음 풀어보는 것 같다.
- 코딩 테스트에서 이 두 알고리즘은 자주 출제되니까 잘 생각하자.
  - 이진 탐색은 정렬된 범위 내에서 특정 숫자나 데이터를 찾을 때 효율적이다!
