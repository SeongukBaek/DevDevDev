# [1068] 트리

## :pushpin: **Algorithm**

트리

## :round_pushpin: **Logic**

```java
for (int index = 0; index < N; index++) {
    if (parents[index] == -1) {
        continue;
    }
    
    children.get(parents[index]).add(index);
}
```

- parents에는 인덱스 노드에 해당하는 부모 노드의 정보가 저장된다.
- 이를 이용해 자신을 부모로 가지는 자식 노드들을 저장하는 이중 리스트를 생성한다.
- -1, 즉 루트 노드의 경우는 자신을 자식으로 가지는 노드가 없기에 통과한다.

```java
Queue<Integer> deleteNodes = new LinkedList<>();
deleteNodes.add(deletion);
while (!deleteNodes.isEmpty()) {
    int delete = deleteNodes.poll();
    for (int child : children.get(delete)) { 
        deleteNodes.add(child);
    }
    children.get(delete).clear();
    children.get(delete).add(-1);
}
```

- 삭제할 노드 정보를 받아, 해당 노드와 연결된 모든 자식 노드들의 정보를 갱신한다.
- 리프 노드의 여부를 자식 노드 개수로 확인하기 때문에, -1 값을 추가한다.

```java
int count = 0;
for (int index = 0; index < children.size(); index++) {
    for (int child = 0; child < children.get(index).size(); child++) {
        if (children.get(index).get(child) == deletion) {
            children.get(index).remove((Integer) deletion);
        }
    }
    if (children.get(index).size() == 0) {
        count++;
    }
}
```

- 이제 부모 노드 중, 삭제할 노드를 자식으로 가진 경우에 대한 삭제를 수행한다.
- 이후 노드가 가진 자식 노드가 0개인 경우는 리프 노드로 간주한다.

## :black_nib: **Review**
- 처음 접근은 트리 정보를 이용해 트리를 생성한 후, 삭제 연산을 수행하려 했다.
- 하지만 트리의 루트가 0으로 고정적이지 않았고, 이 경우들에 대한 트리 생성 연산이 복잡했다.
- 따라서 자식 노드를 저장하는 이중 리스트를 사용하는 방식으로 해결할 수 있었다.