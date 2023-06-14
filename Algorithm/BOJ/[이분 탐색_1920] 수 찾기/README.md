# [1920] 수 찾기 - C++

## :pushpin: **Algorithm**

이분 탐색

## :round_pushpin: **Logic**



## :black_nib: **Review**

- **이분 탐색**을 이용하는 문제로서, 어려운 문제는 아니었지만 이렇게 리드미를 쓰는 이유는

  - ```c++
    ios_base::sync_with_stdio(false);
    ```

    C 표준 stream과 C++ 표준 stream의 동기화를 끊어, 사용하는 버퍼의 수가 줄어들게 되고 이로 인해`cin, cout`의 **입출력 속도를 줄여주는 역할**을 하고, `cin/scanf`를 같이 쓰거나 `cout/printf`를 같이 쓰면 문제가 발생함

  - ```c++
    cin.tie(nullptr);
    ```

    `cin`을 `cout`으로부터 `untie`하는 역할

    기본적으로 `cin`과 `cout` 은 묶여있고 묶여있는 스트림들은 한 스트림이 다른 스트림에서  각 IO 작업을 진행하기 전에 자동으로 버퍼를 비움

    결과적으로는 **실행 속도의 향상**
