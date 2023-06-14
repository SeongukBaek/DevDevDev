# [1260] DFS, BFS - Java

## :pushpin: **Algorithm**

그래프 이론, 그래프 탐색, DFS, BFS

## :round_pushpin: **Logic**

```java
class Graph {
	public ArrayList<ArrayList<Integer>> graph;
    public boolean[] visited = new boolean[1001];
    public StringBuilder path = new StringBuilder();
}
```

- graph를 저장하기 위한 동적 2차원 배열
- 정점에 대한 방문 여부 저장을 위한 `boolean` 배열
- `DFS` 와 `BFS` 의 방문 순서를 저장하는 `stringBuilder`

```java
public void dfs(int start) {
	if (!visited[start]) {
		path.append(start);
		path.append(" ");
		visited[start] = true;
		for (int i = 0; i < graph.get(start).size(); i++) {
			if (!visited[graph.get(start).get(i)]) {
				this.dfs(graph.get(start).get(i));
			}
		}
	}
}
```

- 재귀 호출을 이용한 DFS 구현

```java
public void bfs(int start) {
	Queue<Integer> q = new LinkedList<>();
	q.offer(start);
	visited[start] = true;
	path.append("\n");
	path.append(start);
	while (!q.isEmpty()) {
		int cur = q.poll();
		for (int i = 0; i < graph.get(cur).size(); i++) {
			if (!visited[graph.get(cur).get(i)]) {
				visited[graph.get(cur).get(i)] = true;
				path.append(" ");
				path.append(graph.get(cur).get(i));
				q.offer(graph.get(cur).get(i));
			}
		}
	}
}
```

- Queue를 이용한 BFS 구현

```java
for (int i = 1; i <= n; i++) {
	Collections.sort(g.graph.get(i));
}
```

- 번호가 낮은 정점부터 방문하라는 문제의 조건을 위한 정렬

## :black_nib: **Review**

- 성공했었다가 실패로 재채점되어 이번 기회에 `JAVA` 로 변환하면서 다시 푼 문제
- `class Graph` 와 같이 클래스를 만드는 방식을 이용해 Graph와 관련된 처리들을 최대한 클래스 내부에서 구현하도록 했다.
- 처음에는 `int[] path` 를 두 번 사용하여 `DFS` 의 결과를 출력하고, `BFS` 의 결과를 출력하는 방식으로 구현했었는데, 다른 방식을 찾아보다가 `StringBuilder` 클래스라는 것을 발견하여 이를 사용해보았다.
  - `StringBuilder` : 여러 문자열을 붙이는 연산을 수행하는 경우 효율적인 클래스로, 해당 문제에서는 정점의 방문 순서를 순차적으로 저장하기 위해 사용했다.
- 구현 방식은 틀린 것이 없어보였는데 자꾸 **틀렸습니다** 가 떠서 화가 날 때 즈음 발견한 한 반례로 디버깅을 해보니, 정렬 시 **반복 횟수가 1번** 모자란 것이었다. 