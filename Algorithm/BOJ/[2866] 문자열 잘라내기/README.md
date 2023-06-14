# [2866] 문자열 잘라내기

## :pushpin: **Algorithm**

문자열, 집합

## :round_pushpin: **Logic**

```java
private static boolean isDuplicate() {
    Set<String> candidates = new HashSet<>();

    for (int index = 0; index < C; index++) {
        StringBuilder word = words.get(index);
        word.deleteCharAt(0);
        words.set(index, word);
        candidates.add(word.toString());
    }

    return words.size() != candidates.size();
}
```

- 만들어 둔 문자열의 첫 문자를 모두 제거하고 `Set`에 추가한다.
- `Set`의 크기가 문자열의 개수와 다르다면 중복이 발생한 것이다.

## :black_nib: **Review**
- 전에 풀다가 방법을 모르겠어서 포기했던 문제였다.
- 다시 풀어보려고 고민해보다가 세로 기준으로 문자열을 만들어 두고 하나씩 제거해가면서 동일 비교를 하면 될 것 같아 구현했다.