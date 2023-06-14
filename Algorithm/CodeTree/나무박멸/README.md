# [삼성 SW 역량테스트] 나무박멸

## :pushpin: **Algorithm**

구현, 시뮬레이션

## :round_pushpin: **Logic**

```java
class Tree {
    int x;
    int y;
    // 상하좌우에 나무가 없는 지역 방향 저장
    List<Integer> emptyDir;

    public Tree(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void setEmptyDir(List<Integer> emptyDir) {
        this.emptyDir = emptyDir;
    }
}
```

- 나무 정보를 저장하는 클래스
  - 상하좌우에 나무가 없는 지역 방향을 저장하여 성장과 번식에 사용했다.

```java
// 1. 나무 성장 - 각 나무별 상하좌우 카운트해서 성장, 카운트 정보도 함께 저장
for (Tree tree : treeList) {
    int treeCount = 0; 
    List<Integer> empty = new ArrayList<>();
    int tx = tree.x;
    int ty = tree.y;

    for (int i = 0; i < 4; i++) {
        ...
    }

    tree.setEmptyDir(empty);

    if (treeCount > 0)
        map[tx][ty] += treeCount;
}

// 2. 여전히 나무 위치를 저장해둠. 성장 로직에서 나무 수만 증가했음. 이를 이용해 번식을 진행
// 번식이 가능한 지역은 나무가 없고, 제초제도 없는 지역!
for (Tree tree : treeList) {
    int tx = tree.x;
    int ty = tree.y;
    List<Integer> emptyTreeDir = tree.emptyDir;
    int tec = emptyTreeDir.size();

    if (tec == 0) continue;

    // 주변에 번식되는 나무 수
    int giveTree = map[tx][ty] / tec;

    for (int d : emptyTreeDir) {
        ...
    }
}  

treeList = setTreeList();

// 3. 박멸 실행
// 각 나무 위치에서 대각선 방향으로 k칸만큼 약 쳐보고, 최대가 되는 칸 찾기
// 이때 가장 큰 좌표부터 수행해서 조건을 만족시키자.
int max = 0;
int maxX = n - 1;
int maxY = n - 1;
for (int t = treeList.size() - 1; t >= 0; t--) {
    ...
    for (int d = 0; d < 4; d++) {
        for (int c = 1; c <= k; c++) {
            ...
        }
    }

    if (max <= sum) {
        max = sum;
        maxX = tx;
        maxY = ty;
    }
}

answer += max;

// 제초제 -1!
for (int i = 0; i < n; i++)
    for (int j = 0; j < n; j++)
        if (herbicide[i][j] > 0) 
            herbicide[i][j]--;  

// 박멸 실행
herbicide[maxX][maxY] = c;
map[maxX][maxY] = 0;
for (int d = 0; d < 4; d++) {
    for (int dir = 1; dir <= k; dir++) {
        ...
    }
}
```

- 로직은 다음과 같다.

## :black_nib: **Review**
- 문제를 읽으면서 코드에 주석으로 구현해야 할 로직을 순서대로 나열했다.
  - 최대한 주석만 보고도 코드를 바로 짤 수 있게끔 했다.
- 짜잘한 조건 구현에서 착각해서 디버깅하는데 오래 걸렸다..