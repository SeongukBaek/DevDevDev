# [3273] 두 수의 합

## :pushpin: **Algorithm**

투 포인터

## :round_pushpin: **Logic**

```java
for (int index = 0; index < N; index++) {
    int number = Integer.parseInt(st.nextToken());
    
    if (number >= x) {
        continue;
    }
    numbers.add(number);
}
```

- 자연수의 쌍을 구하는 것이므로, 만약 주어진 숫자 중 x보다 크거나 같은 수가 있다면 탐색할 필요가 없으므로 추가하지 않는다.

```java
List<Integer> numbers = new ArrayList<>();

while (left < right) {
    int sum = numbers.get(left) + numbers.get(right);
    if (sum == x) {
        count++;
        left++;
        right--;
    } else if (sum < x) {
        left++;
    } else {
        right--;
    }
}
```

- `List`를 이용해 가능한 숫자 쌍을 찾는다.

```java
Deque<Integer> list = new ArrayDeque<>(numbers);
        
while (list.size() >= 2) {
    int sum = list.peekFirst() + list.peekLast();
    if (sum == x) {
        count++;
        list.pollFirst();
        list.pollLast();
    } else if (sum < x) {
        list.pollFirst();
    } else {
        list.pollLast();
    }
}
```

- `Deque`을 이용해 가능한 숫자 쌍을 찾는다.

## :black_nib: **Review**
- 투 포인터 알고리즘을 이용하는 간단한 문제였다.
- 문제를 풀고 나서, `left`와 `right`에서 하나씩 제거되는 모양을 보이고 있어 `Deque` 자료구조를 사용해도 풀이가 가능할 것 같았다.