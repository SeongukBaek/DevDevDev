# [1764] 듣보잡 - C++

## :pushpin: **Algorithm**

문자열, 정렬

## :round_pushpin: **Logic**

```c++
set <string> list;
```

- 이름을 저장하기 위한 `set`자료형 선언

```c++
for (int i = 0; i < N; i++) {
    cin >> name;
    list.insert(name);
}
for (int i = 0; i < M; i++) {
    cin >> name;
    if (list.erase(name) == 1)
        ans.push_back(name);
}
```

- 듣도 못한 사람들의 이름을 먼저 저장한 다음, 보도 못한 사람들의 이름을 입력받음
  - 이때 `list`에 있는 사람이라면, 듣보잡에 해당되므로 `list.erase(name)`의 리턴값을 확인
  - `1`을 리턴한다면, `erase`가 된 것이므로 정답을 저장하는 `ans`에 저장
  - `0`을 리턴한다면, `list`에 없어서 `erase`하지 못한 경우이므로 `pass`

```c++
for (const auto& str : ans)
    cout << str << "\n";
```

- 정답을 출력하는 부분, 새로운 코드인 `const auto& str`을 이용하여 `string`만을 저장하는 `ans` 에 자료형 지정없이 접근 가능
- 그리고 `endl` 대신 `"\n"`를 사용하여 **실행시간 감소효과**

## :black_nib: **Review**

- 역시 어려운 문제는 아니었지만 **새로운 자료형**인 `set`과 `const auto& str : ans`와 같은 **새로운 표현**을 사용했기에 리드미 작성 ... !

