# [2800] 괄호 제거

## :pushpin: **Algorithm**

재귀, 조합

## :round_pushpin: **Logic**

```java
private static void countBracketPair() {
    Stack<Integer> stack = new Stack<>();

    for (int i = 0; i < line.length(); i++) {
        char c = line.charAt(i);
        if (c == '(') stack.push(i);
        else if (c == ')') bracketPair.add(new int[] { stack.pop(), i });
    }
}
```

- 스택을 사용해 각 괄호쌍의 인덱스 정보를 저장한다.
- 그리고 이 정보를 통해 총 괄호쌍의 개수를 알 수 있다.

```java
private static void makeComb(int depth, char[] chars) {
    if (depth == bracketCount) {
        list.add(makeExpression(chars));
        return;
    }

    makeComb(depth + 1, chars);

    int[] bracket = bracketPair.get(depth);
    check[bracket[0]] = true;
    check[bracket[1]] = true;
    makeComb(depth + 1, chars);
    check[bracket[0]] = false;
    check[bracket[1]] = false;
}
```

- 재귀함수와 `check` 배열을 통해 제거할 괄호쌍 조합을 구하고, 해당 괄호쌍을 제거한 식을 저장한다.

## :black_nib: **Review**
- 조합을 사용해 제거할 괄호쌍을 찾아야 한다는 아이디어는 쉽게 알 수 있었다. 괄호쌍의 개수도 최대 10개니까 시간적으로도 가능할 듯 했다.
- 하지만 괄호쌍의 정보를 저장하는 부분을 구현하는 데 있어 꼬였던 것 같다. 좀 더 많은 상태 정보를 가지고 여유있게 구현했다면 충분히 스스로 할 수 있었을 것 같다.