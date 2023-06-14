# [1873] 상호의 배틀필드

## :pushpin: **Algorithm**

구현, BFS

## :round_pushpin: **Logic**

```java
private static void operateCommand(char command) {
    if (command == 'S') {
        shooting();
        return;
    }

    tank.direction = getDirection(command);

    int nx = tank.x + directions[tank.direction][0];
    int ny = tank.y + directions[tank.direction][1];
    // 평지라면 이동
    if (isInBoundary(nx, ny) && map[nx][ny] == '.') {
        map[tank.x][tank.y] = '.';
        tank.x = nx;
        tank.y = ny;
    }
    map[tank.x][tank.y] = getShape(tank.direction);
}
```

- 명령어에 따라, 포탄을 발사하거나, 전차를 이동시킨다.
- 이동명령 시에는 전차는 평지로만 이동할 수 있으므로, 이에 대해서만 이동을 처리하지만, 전차의 방향은 항상 바뀌어야 한다.

## :black_nib: **Review**
- 하라는 대로 구현만 하면 되는 문제였다.