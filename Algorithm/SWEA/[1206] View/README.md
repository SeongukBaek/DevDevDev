# [1206] View

## :pushpin: **Algorithm**

구현

## :round_pushpin: **Logic**

```java
private static void fillLeft() {
    for (int index = 2; index < buildings - 2; index++) {
        int prev = Math.max(buildingHeights.get(index - 2), buildingHeights.get(index - 1));
        int current = buildingHeights.get(index);
        
        if (current > prev) {
            left[index] = current - prev;
        }
    }
}

private static void fillRight() {
    for (int index = buildings - 3; index >= 2; index--) {
        int prev = Math.max(buildingHeights.get(index + 1), buildingHeights.get(index + 2));
        int current = buildingHeights.get(index);
        
        if (current > prev) {
            right[index] = current - prev;
        }
    }
}

private static int findView() {
    int view = 0;
    
    for (int index = 2; index < buildings - 2; index++) {
        view += Math.min(left[index], right[index]);
    }
    
    return view;
}
```

- 왼쪽 조망권을 확인하는 로직과 오른쪽 조망권을 확인하는 로직을 분리했다.
- 두 조망권을 모두 확인한 뒤, 둘 중 최소 조망권을 누적한다.

## :black_nib: **Review**
- 기능 분리를 위해 왼쪽 조망권과 오른쪽 조망권 확인을 분리했다.