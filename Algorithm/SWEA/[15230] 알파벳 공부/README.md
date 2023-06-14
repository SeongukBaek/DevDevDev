# [15230] 알파벳 공부

## :pushpin: **Algorithm**

문자열

## :round_pushpin: **Logic**

```java
int prev = 96;
for (int i = 0; i < size; i++) {
    char ch = str.charAt(i);
    
    if (prev + 1 != ch) break;
    answer++;
    prev++;
}
```

- 주어진 문자열을 하나씩 읽으면서, 이전 단어보다 아스키코드가 1 크지 않다면 바로 종료한다.
- 여기서 초기의 `prev` 값은 `소문자 a의 아스키 코드인 97` - `1` 한 값이다.

## :black_nib: **Review**
- D3라고 되어있어서 어느 정도 난이도가 있으려나 했는데, D1이라고 해도 될 정도 문제여서 당황했다.