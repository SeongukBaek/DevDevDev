# [15961] 회전 초밥

## :pushpin: **Algorithm**

투 포인터, 슬라이딩 윈도우

## :round_pushpin: **Logic**

```java
int max = 0;
int size = 0;
int[] eat = new int[d + 1];
int[] storeFood = new int[N];
Deque<Integer> foods = new ArrayDeque<>();

for (int index = 0; index < N; index++) {
    int sushi = Integer.parseInt(br.readLine());
    storeFood[index] = sushi;
    eat[sushi]++;
    if (eat[sushi] == 1) {
        size++;
    }
    foods.addLast(sushi);

    if (index >= k - 1) {
        int tempSize = size;
        if (eat[c] == 0) {
            tempSize++;
        }

        if (max < tempSize) {
            max = tempSize;
        }

        int food = foods.pollFirst();

        eat[food]--;
        if (eat[food] == 0) {
            size--;
        }
    }
}
```

- 숫자를 입력받으면서, k개만큼을 Deque에 넣는다.
- 이때 번호별 빈도를 구해서 중복을 제거한 개수를 추적한다.
- Deque에 4개가 들어왔다면, 최대 초밥 가짓수를 갱신한다.

```java
for (int end = 0; end < k - 1; end++) {
    int sushi = storeFood[end];
    eat[sushi]++;
    if (eat[sushi] == 1) {
        size++;
    }
    foods.addLast(sushi);

    int tempSize = size;
    if (eat[c] == 0) {
        tempSize++;
    }

    if (max < tempSize) {
        max = tempSize;
    }

    int food = foods.pollFirst();

    eat[food]--;
    if (eat[food] == 0) {
        size--;
    }
}
```

- 회전 초밥이므로, 가장 마지막 초밥에서 끝나는 것이 아니라, Deque의 첫번째로 마지막 초밥이 위치하고, 그 이후에 다시 첫번째 초밥부터 저장될 수 있다.
- 따라서 이 경우에 대해 최대 초밥 가짓수를 갱신한다.

## :black_nib: **Review**

- 처음 아이디어는 Deque와 Set을 이용해 중복 여부와 가짓수를 구하려했는데, 시간초과가 발생했다.
- 아무래도 Set을 이용하는 것이 오버헤드가 컸던 것이라 판단해 중복 여부와 포함 여부를 함께 확인하기 위해 배열을 사용하는 것으로 변경했다.