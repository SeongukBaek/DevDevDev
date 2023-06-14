# [2630] 색종이 만들기

## :pushpin: **Algorithm**

분할 정복

## :round_pushpin: **Logic**

```java
private static void dividePaper(int x, int y, int size) {
    if (isSameColor(x, y, size)) {
        int color = map[x][y];
        
        if (color == 1) {
            bluePaper++;
        } else {
            whitePaper++;
        }
        
        return;
    }
    
    dividePaper(x, y, size / 2);
    dividePaper(x + size / 2, y, size / 2);
    dividePaper(x, y + size / 2, size / 2);
    dividePaper(x + size / 2, y + size / 2, size / 2);
}
```

- 현재 범위의 종이가 같은 숫자인지 확인하고, 그렇지 않은 경우는 분할을 시작한다.
- 무조건 4등분으로 분할되기에 재귀 호출을 4번 수행한다.

## :black_nib: **Review**
- 간단한 형태의 분할 정복이었다.