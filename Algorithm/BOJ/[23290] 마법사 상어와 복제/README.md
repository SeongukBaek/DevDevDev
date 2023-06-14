# [23290] 마법사 상어와 복제 - Java

## :pushpin: **Algorithm**

구현, 시뮬레이션

## :round_pushpin: **Logic**

```java
for (int time = 0; time < S; time++) {
  // 1. 물고기 복제 마법
  ArrayList<Fish> copy = copy(orgFishes);
  
  // 2. 물고기 이동
  for (Fish fish : orgFishes)
    fish = moveFish(fish);
  
  // 2-1. 물고기 이동한 후 map에 배치
  setMap();
  
  // 3. 상어 연속 이동(백트래킹)
  fishNum = Integer.MIN_VALUE; // 해당 방향으로 갈 때 먹는 물고기 수 초기화
  sharkBacktracking(0); // 가장 많이 먹고, 사전순으로 적은 방향 찾기 by 중복순열
  moveShark();
  
  // 4. 물고기 냄새 격자에서 사라짐
  smellRemove();
  
  // 5. 복제마법 map에 처리
  setCopyMap(copy);
  
  // 6. map에 있는 내용 list에 담기(물고기 개수도 세기)
  reset();
}
```

- 로직이다. 순서대로 구현하면 되는 문제였다.
- 내가 사용한 방법은 ArrayList<Fish> 가 아니라 Queue<Fish> 를 사용한 방법이었는데, 굳이 삽입과 삭제가 필요없이 인덱스 접근만 하면 되는 List를 쓰는 방법이 나은 것 같다.
- 또 내가 사용한 방법은 DFS로 최대 물고기 루트를 구하려 했었는데, 중복순열을 사용한 위 방식을 참고하는 것이 좋았다.

## :black_nib: **Review**

- 아이디어는 쉬웠으나 구현에서 또 오래 걸렸다.
- 정답 참고하지 않고 해결하려고 했으나, 문제점을 찾지 못했다.
  - 의심스러운 부분은 복제를 위해 Queue를 새로 만드는 부분이거나, DFS를 통해 최대의 루트를 구하는 부분 정도일 것 같다...
  - 순열, 조합을 구하는 로직을 공부해야겠다.

### 참고
https://velog.io/@bobae1998/%EB%B0%B1%EC%A4%80-23290-%EB%A7%88%EB%B2%95%EC%82%AC-%EC%83%81%EC%96%B4%EC%99%80-%EB%B3%B5%EC%A0%9C-JAVA