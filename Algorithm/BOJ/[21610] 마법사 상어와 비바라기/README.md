# [21610] 마법사 상어와 비바라기 - Java

## :pushpin: **Algorithm**

구현, 시뮬레이션

## :round_pushpin: **Logic**

- 주요 로직은 다음과 같다.
  - `Queue`에 저장한 모든 비구름을 이동시킨다.
  - 이동한 후, 물의 양을 증가시키고, 해당 좌표의 boolean[][] 값을 true로 바꿔 이후 과정에서 새로운 구름이 생기지 않도록 확인한다.
  - 물의 양이 증가된 구름은 또 `Queue`에 저장되어 있다. 물복사버그 마법을 시전한다.
  - 전체 맵을 돌면서 물의 양이 2이상인 칸을 첫번째 과정에서 사용할 `Queue`에 담고, 이와 동시에 모든 바구니에 들어있는 물의 양을 누적한다.

## :black_nib: **Review**

- 다른 마법사 상어보다 훨씬 쉬웠다.