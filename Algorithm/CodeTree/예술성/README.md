# [삼성 SW 역량테스트] 예술성

## :pushpin: **Algorithm**

구현, 시뮬레이션

## :round_pushpin: **Logic**

**소요 시간** : 3시간 40분

```java
// 주어진 맵 저장
for (int i = 0; i < N; i++) {
    st = new StringTokenizer(br.readLine());
    for (int j = 0; j < N; j++)
        map[i][j] = Integer.parseInt(st.nextToken());
}

// 각 칸에서 BFS 수행해서 그룹 정보를 int[][] 에 저장
getGroupInfo();

// 초기 예술 점수 계산
computeArt();

for (int c = 0; c < 3; c++) {
    // 그림 회전 수행   
    // 십자 모양 회전 수행
    crossRotate(); 

    // 4개의 정사각형 회전
    // 시작점 좌표와 끝점 좌표를 인자로 받는 함수 구현
    squareRotate(0, 0, N / 2 - 1, N / 2 - 1);
    squareRotate(0, N / 2 + 1, N / 2 - 1, N - 1);
    squareRotate(N / 2 + 1, 0, N - 1, N / 2 - 1);
    squareRotate(N / 2 + 1, N / 2 + 1, N - 1, N - 1);
    
    // 각 칸에서 BFS 수행해서 그룹 정보를 int[][] 에 저장
    getGroupInfo();

    // n회전 예술 점수 계산
    computeArt();
}
```

- 주요 로직은 다음과 같다.
  - 맵 저장
  - 각 칸에서 BFS를 수행해서 그룹 정보를 `int[][] group`에 저장한다.
    - 이때 그룹 정보는 1부터 시작해서 중복되지 않는다.
  - 초기 예술 점수를 계산한다. 예술 점수는 `static int artScore`에 바로바로 누적된다.
    - 예술 점수 계산 시, 맞닿아있는 변의 개수는 각 칸에서의 상하좌우를 탐색해 현재 비교하는 대상 그룹의 번호를 가진 칸의 개수를 카운트한다.
  - 이제 3회전을 수행한다.
    - 먼저 십자 모양 회전을 수행한다.
    - 그리고 4개의 정사각형에 대한 회전을 수행한다.
    - 새로 바뀐 맵에 대해 다시 그룹을 나누고, 예술 점수를 계산한다.

```java
// 행 기준으로 큐에 모두 삽입하고,
// 열 기준으로 큐를 팝하면서 맵에 대입
private static void squareRotate(int x1, int y1, int x2, int y2) {
    Queue<Integer> queue = new LinkedList<>();

    for (int i = x1; i <= x2; i++)
        for (int j = y1; j <= y2; j++) 
            queue.add(map[i][j]);
    
    for (int j = y2; j >= y1; j--)
        for (int i = x1; i <= x2; i++)
            map[i][j] = queue.poll();
}
```

- 가장 시간을 오래 뺏긴 부분이다.
  - 처음에는 한 자리씩 시계 방향으로 옮기는 것으로 착각했고,
  - 이후에는 시계 방향으로 값을 큐에 넣고, 이를 다시 맵에 대입하는 방식으로 구현했다.
  - 둘다 잘못되었음을 알아차리고, 싹 다 큐에 넣고 열 기준으로 다시 뱉어내는 구현으로 옮겼다.

## :black_nib: **Review**
- 시계 방향 회전에 대한 구현을 좀만 더 빨리 알아차렸다면 더 시간을 단축시켰을 수 있을 것 같다.
- 회전에 대해서 Queue를 사용하는 것을 꼭 기억하게 된 문제이다.