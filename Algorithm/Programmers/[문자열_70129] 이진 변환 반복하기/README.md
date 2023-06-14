# [70129] 이진 변환 반복하기

## :pushpin: **Algorithm**

문자열

## :round_pushpin: **Logic**

```java
private static int removeZero(String s) {
    int length = 0;
    for (int index = 0; index < s.length(); index++) {
        if (s.charAt(index) != '0') {
            length++;
        }
    }
    return length;
}
```

- 1만 카운트해서 반환하는 함수

```java
private static String intToBinaryString(int number) {
    StringBuilder result = new StringBuilder();
    
    while (number >= 1) {
        result.append(number % 2);
        number /= 2;
    }
    
    return result.reverse().toString();
}
```

- 정수를 이진 변환하고, 반환하는 함수

## :black_nib: **Review**

- 각 기능을 수행할 함수만 구현하면 되는 문제