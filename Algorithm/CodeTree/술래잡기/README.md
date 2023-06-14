# [삼성 SW 역량테스트] 술래잡기

## :pushpin: **Algorithm**

구현, 시뮬레이션

## :round_pushpin: **Logic**

```java
private static void makeTaggerDirection() {
  int size = N * N - 1;
  taggerDirection = new int[size * 2];

  int zero = 1;
  int one = 1;
  int two = 2;
  int three = 2;

  int index = 0;
  while (index < size) {
    int temp = 0;
    while (temp++ < zero && index < size) {
      taggerDirection[index++] = 0;
    }
    temp = 0;
    while (temp++ < one && index < size) {
      taggerDirection[index++] = 1;
    }
    temp = 0;
    while (temp++ < two && index < size) {
      taggerDirection[index++] = 2;
    }
    temp = 0;
    while (temp++ < three && index < size) {
      taggerDirection[index++] = 3;
    }

    zero += 2;
    one += 2;
    two += 2;
    three += 2;
  }

  int prev = index;
  for (; index < size * 2; index++) {
      taggerDirection[index] = rotateOppoosite(taggerDirection[--prev]);
  }
}
```

- 달팽이 모양으로 회전하기 위해서 방향 정보를 배열로 저장한다.
- 0, 1, 2, 2, 3, 3 -> 0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3 과 같이 각 방향의 개수가 2개씩 증가하는 규칙이 있어 이를 이용했다.

```java
for (int turn = 1; turn <= k; turn++) {
// 도망자 이동
// 술래랑 거리를 저장해야 함. -> 이동 시 계산할 지, 술래 이동 후 저장할지 생각
// 격자 밖으로 나가는 경우
// - 술래가 없어야 이동, 있으면 이동 불가

// 격자 밖으로 나가지 않는 경우
// - 방향을 반대로 ! 0 <-> 2, 1 <-> 3
// - 술래가 없으면 이동, 있으면 이동 불가
moveRunners();

// 술래 이동
// - 방향 설정을 어떻게 할 것인가?
// - 이동 후, 방향을 틀어야하는 지점이면 틀기
moveTagger();

// 도망자 잡기
// - 나무가 있는 지점은 확인 X, 나무가 없고 범위 내라면 잡기
// 점수 계산
// - 턴 * 이번에 잡은 도망자 수
computeScore(catchRunners(), turn);
}
```

- 주어진 순서대로 로직을 실행한다.

## :black_nib: **Review**

- 어제 팩맨 문제와 거의 유사했다. 만약 팩맨을 못 풀었다면 훨씬 더 오래 걸렸을 것 같다.
- 이런 구현, 시뮬레이션 문제의 경우에는 List를 사용하는 것보다 다차원 배열을 활용하는 것이 더 효율적일 때가 있는 것 같다.
