# [154539] 뒤에 있는 큰 수 찾기

## :pushpin: **Algorithm**

DP

## :round_pushpin: **Logic**

```java
int max = numbers[size - 1];
for (int index = size - 2; index >= 0; index--) {
    int current = numbers[index];

    // 뒤에 있는 가장 큰 값보다 현재가 크거나 같은 경우, max 갱신
    if (current >= max) {
        max = current;
        continue;
    }

    // 뒤에 있는 가장 큰 값보다 현재가 작은 경우
    // 무조건 max로 갱신하면 안되는 경우가 존재함
    for (int back = index + 1; back < size; back++) {
        if (current < numbers[back]) {
            answer[index] = numbers[back];
            break;
        }
        if (current < answer[back]) {
            answer[index] = answer[back];
            break;
        }
    }
}
```

- 뒤에서부터 뒷 큰수를 찾아 저장한다.
- max는 뒷 큰수 중 가장 큰 값을 저장한다.

## :black_nib: **Review**

- 뒤에서부터 접근해서 뒷 큰수를 찾아야한다는 생각으로 접근했고, 시간초과때문에 다른 자료구조는 사용하지 않으려 했다.
- 질문 게시판을 보니 스택을 사용하라는 말도 있었는데 굳이 스택까진 필요 없을 것 같았다.
