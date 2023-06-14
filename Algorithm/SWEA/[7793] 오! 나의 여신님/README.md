# [7793] 오! 나의 여신님

## :pushpin: **Algorithm**

BFS

## :round_pushpin: **Logic**

```java
int time = 1;
Queue<Location> suyeon = new LinkedList<>();
suyeon.add(new Location(startX, startY));

while (time <= N * M) {
    // 수연 이동해보기
    int suyeonCount = suyeon.size();
    if (suyeonCount == 0) {
        return -1;
    }
    
    for (int count = 0; count < suyeonCount; count++) {
        // 수연이 이동가능한 좌표로 이동해보고, 큐에 좌표 삽입
    }
    
    // 악마 확장
    int devilCount = devils.size();
    for (int count = 0; count < devilCount; count++) {
        // 악마 확장 후, 큐에 좌표 삽입
        // 악마 확장은 수연이 이동한 좌표를 덮어쓴다!
    }
}
```

- 수연을 먼저 이동시켜본 후, 악마를 확장한다.
- 악마는 수연이 이동한 좌표로 확장될 수 있고, 이 경우를 처리하기 위해 수연의 좌표 큐에서 값을 찾아서 제거하는 것은 어려워서, 수연 이동시에 큐에서 좌표를 꺼낸 후 해당 좌표의 값을 확인했다.

## :black_nib: **Review**
- 명시된 난이도보다 체감 난이도가 낮았다. 수연과 악마에 대해 각각 BFS를 수행하는 것이 주요했다.
- 나름대로 시간을 아껴보겠다고 while문의 종료 조건을 빡빡하게 해둔 것이 오히려 테스트케이스를 다 맞추지 못한 원인이었다.