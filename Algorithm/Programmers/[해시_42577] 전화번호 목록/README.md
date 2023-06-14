# [42577] 위장

## :pushpin: **Algorithm**

해시

## :round_pushpin: **Logic**

```java
for (String phone : phone_book) {
    phoneMap.add(phone);
}

for (int index = 0; index < phone_book.length; index++) {
    for (int range = 1; range < phone_book[index].length(); range++) {
        String compareString = phone_book[index].substring(0, range);
        if (phoneMap.contains(compareString)) {
            return false;
        }
    }
}
```

- 모든 전화번호는 중복이 없다. 따라서 Set에 저장할 수 있다.
- 전화번호를 하나씩 탐색하면서 Set에 포함되어 있는지를 확인한다.
  - 이때 전화번호 전체가 아닌 `1자리` 부터 `(전화번호 길이 - 1)자리` 를 추출해 비교한다.
  - 만약 포함되어 있다면 다른 번호의 접두어가 존재한다는 의미이다.

## :black_nib: **Review**
- 트라이를 사용해야 할 것 같았는데, 좀 더 간단한 방법이 있었다.
- 이건 좀 신기하네.