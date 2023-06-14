# [17135] 캐슬 디펜스

## :pushpin: **Algorithm**

구현

## :round_pushpin: **Logic**

```java
private static void startWar(int[] archors, boolean[] isSelected, int start, int r) {
    if (r == 3) {
        copy();
        max = Math.max(max, spaceArchors(archors));
        return;
    }
    
    for (int index = start; index < M; index++) {
        if (isSelected[index]) {
            continue;
        }
        archors[r] = index;
        isSelected[index] = true;
        startWar(archors, isSelected, index + 1, r + 1);
        isSelected[index] = false;
    }
}
```

- 증가하는 조합으로 궁수들이 위치할 수 있는 y 좌표들을 구한다. 

```java
private static int spaceArchors(int[] archors) {
    int sum = 0;
    firstY = archors[0];
    secondY = archors[1];
    thirdY = archors[2];
    
    // 턴을 의미
    for (int x = N; x >= 1; x--) {
        selectedEnemy = new ArrayList<>();
        
        // 3명의 궁수가 적을 모두 선택한 후, 제거
        selectEnemy(x, firstY);
        selectEnemy(x, secondY);
        selectEnemy(x, thirdY);
        
        sum += killEnemy();
    }
    
    return sum;
}
```

- 조합으로 생성한 궁수들의 y 좌표를 이용해 각 궁수들이 적을 선택하고, 한 번에 제거한다.

```java
private static void selectEnemy(int x, int archorY) {
    int[] near = null;
    int minDistance = Integer.MAX_VALUE;
    
    for (int y = 0; y < M; y++) {
        for (int enemy = x - 1; enemy >= x - D && enemy >= 0; enemy--) {
            if (copyMap[enemy][y] == 0) {
                continue;
            }
            int distance = computeDistance(x, archorY, enemy, y);
            if (distance <= D && distance < minDistance) {
                minDistance = distance;
                near = new int[] {enemy, y};
            }
        }
    }
    
    if (near != null) {
        selectedEnemy.add(near);
    }
}
```

- 각 궁수들은 왼쪽 하단부터 가장 가까이 있는 적을 찾는다.
- 이는 `List` 에 저장해두고 후에 한번에 제거할 수 있도록 했다.

```java
private static int killEnemy() {
    int sum = 0;
    for (int[] enemy : selectedEnemy) {
        if (copyMap[enemy[0]][enemy[1]] == 0) {
            continue;
        }
        sum++;
        copyMap[enemy[0]][enemy[1]] = 0;
    }
    return sum;
}
```

- `List` 에 저장된 적들을 제거한다.
- 이때 중복이 발생할 수 있어서 이를 확인했다.

## :black_nib: **Review**
- 그래프 탐색, BFS나 DFS를 사용해야 하는 문제인지 전혀 몰랐다.
- 현재 궁수 위치에서 가장 가까이 있는 적을 찾는 로직에서, 탐색의 시작에 우선 순위를 두고 가장 왼쪽 하단부터 탐색하면 항상 가장 왼쪽에 있는 가장 가까운 적을 찾을 수 있을 거라 생각했다.
- 또한, 상대적으로 N x M 맵을 턴마다 아래쪽으로 내리는 것보다, 3명의 궁수 좌표들을 증가시키는 것이 효율적이라 생각했다.