# [1208] Flatten

## :pushpin: **Algorithm**

구현

## :round_pushpin: **Logic**

```java
while (dump > 0) {
    Collections.sort(boxes, Collections.reverseOrder());
    
    int maxHeight = boxes.get(0);
    maxHeight--;
    boxes.set(0, maxHeight);
    
    int minHeight = boxes.get(boxes.size() - 1);
    minHeight++;
    boxes.set(boxes.size() - 1, minHeight);

    Collections.sort(boxes, Collections.reverseOrder());
    
    maxHeight = boxes.get(0);
    minHeight = boxes.get(boxes.size() - 1);
    
    difference = maxHeight - minHeight;
    
    dump--;
}
```

- 내림차순 정렬한 후, 최대 최소 값을 가져와서 증감한다.
- 이후 다시 정렬하고 최대 최소 값을 가져와 차이를 구한다.

## :black_nib: **Review**
- 문제는 쉬워서 아이디어는 금방 잡았으나 문제를 급하게 풀려다가 몇 가지 조건을 빼먹었다..