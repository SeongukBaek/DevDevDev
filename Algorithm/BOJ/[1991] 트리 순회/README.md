# [1991] 트리 순회

## :pushpin: **Algorithm**

트리

## :round_pushpin: **Logic**

```java
void preOrder(Node current) {
    System.out.print(current.value);
    
    if (current.hasLeft()) {
        preOrder(current.leftChild);
    }
    if (current.hasRight()) {
        preOrder(current.rightChild);
    }
}

void inOrder(Node current) {
    if (current.hasLeft()) {
        inOrder(current.leftChild);
    }
    System.out.print(current.value);
    if (current.hasRight()) {
        inOrder(current.rightChild);
    }
}

void postOrder(Node current) {
    if (current.hasLeft()) {
        postOrder(current.leftChild);
    }
    if (current.hasRight()) {
        postOrder(current.rightChild);
    }
    System.out.print(current.value);
}
```

- VLR, LVR, LRV 방식의 전위, 중위, 후위 순회를 수행한다.

## :black_nib: **Review**
- 싸피에서 트리의 순회를 배우고, 직접 트리와 노드 클래스를 구현해보았다.