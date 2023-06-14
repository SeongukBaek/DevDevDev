# [5644] 무선 충전

## :pushpin: **Algorithm**

BFS

## :round_pushpin: **Logic**

```java
computeChargeAmount();
for (int time = 0; time < M; time++) {
    // 사용자 이동
    moveUserA(moveA[time] - 1);
    moveUserB(moveB[time] - 1);

    computeChargeAmount();
}
```

- 0초에도 충전이 가능하기 때문에 먼저 충전을 시도해보고, 주어진 시간별로 사용자를 이동하고 충전을 수행한다.

```java
private static void computeChargeAmount() {
    // 충전기와의 거리 계산 후, 각 사용자가 사용할 수 있는 충전기 저장
    Queue<Charger> userA = new PriorityQueue<>();
    Queue<Charger> userB = new PriorityQueue<>();

    for (Charger charger : chargers) {
        if (computeDistance(aX, aY, charger) <= charger.c) {
            userA.add(charger);
        }
        if (computeDistance(bX, bY, charger) <= charger.c) {
            userB.add(charger);
        }
    }

    // 둘다 하나 이상의 무선 충전이 가능하고, 그 값이 다 다른 경우
    if (userA.size() > 0 && userB.size() > 0 && userA.peek().p != userB.peek().p) {
        sum += userA.peek().p + userB.peek().p;
        return;
    }

    // 한 명의 사용자만 무선 충전이 가능한 경우
    if (userA.size() == 0 && userB.size() > 0) {
        sum += userB.peek().p;
        return;
    }
    if (userA.size() > 0 && userB.size() == 0) {
        sum += userA.peek().p;
        return;
    }

    // 두 개 이상의 무선충전이 가능해서, 최대 값을 찾아야 하는 경우
    Set<Charger> chargerSet = new HashSet<>();
    chargerSet.addAll(userA);
    chargerSet.addAll(userB);

    Queue<Charger> allChargers = new PriorityQueue<>();
    allChargers.addAll(chargerSet);

    for (int count = 0; count < 2; count++) {
        if (allChargers.isEmpty()) {
            break;
        }
        sum += allChargers.poll().p;
    }
}
```

- 사용자별로 사용할 수 있는 충전기 정보를 우선순위 큐에 저장한다.
- 둘 다 현재 충전할 수 있는 가장 성능이 좋은 충전기가 다르다면, 충전을 수행한다.
  - 또한 둘 중 한 명만 충전이 가능한 경우도 한 명만 충전을 수행한다.
- 하지만 그렇지 않은 경우들에 대해서는, 두 사용자가 사용할 수 있는 충전기를 Set에 삽입하고, 중복을 제거한 후 다시 우선순위 큐에 넣고 최대 2개의 충전기를 사용한다.

## :black_nib: **Review**
- 사용자별로 사용 가능한 충전기가 여러 개 있을 수 있고, 이에 대해 최대값을 찾는 로직을 생각해내는 것이 어려웠다.
- 우선순위 큐와 Set을 사용하면서 코드가 불필요하게 길고 지저분해보여서 다른 깔끔한 방법이 있다면 참고해야겠다.