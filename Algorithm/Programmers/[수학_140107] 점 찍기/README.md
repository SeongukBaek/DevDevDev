# [140107] 점 찍기

## :pushpin: **Algorithm**

수학

## :round_pushpin: **Logic**

```java
long distance = squareInt(d);

int x = 0;
long squareX = squareInt(x);
while (distance >= squareX) {
    long diff = distance - squareX;
    int maxY = (int) Math.sqrt(diff);

    answer += maxY / k + 1;

    x += k;
    squareX = squareInt(x);
}
```
- x좌표가 정해졌을때, 가능한 Y좌표의 개수를 계산한다.

## :black_nib: **Review**

- X좌표가 정해지면, 가능한 Y좌표를 알 수 있기에 굳이 다 계산할 필요 없었다.
