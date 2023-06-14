# [140108] 문자열 나누기

## :pushpin: **Algorithm**

문자열

## :round_pushpin: **Logic**

```java
private static int searchString(char x, int index) {
    for (int next = index + 1; next < size; next++) {
        if (isSame(x, s.charAt(next))) {
            xCount++;
        } else {
            otherCount++;
        }

        if (xCount == otherCount) {
            answer++;
            return next + 1;
        }

        if (next == size - 1) {
            return next;
        }
    }
    
    return index;
}
```

- 문자열을 탐색하면서 x의 개수와 x가 아닌 문자의 개수를 계산한다.
  - 같아지면 answer를 증가시키고 다음 x를 찾는다.
- 만약 더 이상 읽을 문자가 없는 경우, answer를 증가시키고 종료한다.

## :black_nib: **Review**

- 간단한 문자열 문제였다.
