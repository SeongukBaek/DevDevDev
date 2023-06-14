# [13458] 시험 감독

## :pushpin: **Algorithm**

수학, 사칙연산

## :round_pushpin: **Logic**

```java
for (int i = 0; i < N; i++) {
    int person = Integer.parseInt(st.nextToken()) - B;

    if (person > 0)  {
        if (person % C == 0) answer += person / C;
        else answer += (person / C) + 1;
    }
}
```

- 한 강의실의 인원에 총감독관이 가능한 인원을 제한다.
- 제한 후에도 남았다면 부감독관으로 처리한다.

## :black_nib: **Review**
- 간단한 문제였고, 하나하나 독립적으로 계산하면 되는 문제였기에 배열로 따로 저장하고 자시고 할 것 없었다.
- 근데 `long` 때문에 계속 틀렸다.