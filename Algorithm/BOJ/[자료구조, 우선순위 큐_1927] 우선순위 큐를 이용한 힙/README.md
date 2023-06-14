# [1927] 최소 힙 - C ++

## :pushpin: **Algorithm**

자료구조, 우선순위 큐

## :round_pushpin: **Logic**

```c++
priority_queue <int, vector<int>, greater<int>> cards;
```

- 오름차순으로 `int`형 값들을 저장하는 우선순위 큐 선언

```c++
for (int i = 0; i < N; i++) {
    cin >> n;
    if (n == 0) {
        if (cards.size() == 0)
            cout << 0 << "\n";
        else {
            cout << cards.top() << "\n";
            cards.pop();
        }
    }
    else
        cards.push(n);
}
```

- 입력값이 `0`인 경우와 아닌 경우로 구분
  - 가장 최소인 원소가 맨 앞에 위치

## :black_nib: **Review**

- 우선순위 큐를 오름차순으로 선언

- 최대 힙을 원하는 경우, 우선순위 큐 선언 시 `greater<int>`를 제거

- 또는, 비교 연산자인 `greater<int>`를 사용자 정의형으로 구현 가능

  - ```c++
    struct compare {
    	bool operator()(int a, int b) {
    		if (abs(a) == abs(b))
    			return a > b;
    		return abs(a) > abs(b);
    	}
    };
    
    priority_queue <int, vector<int>, compare> arr;
    ```

    위와 같이 절댓값을 비교에 사용할 수 있도록 **사용자 정의 비교 연산자**를 지정 가능