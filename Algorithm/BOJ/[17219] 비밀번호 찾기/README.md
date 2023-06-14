# [17219] 비밀번호 찾기

## :pushpin: **Algorithm**

해시맵

## :round_pushpin: **Logic**

```java
BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
for (int index = 0; index < M; index++) {
    st = new StringTokenizer(br.readLine());
    bw.append(sitePassword.get(st.nextToken())).append("\n");
}
bw.flush();
```

- 주어진 사이트에 대한 비밀번호 출력을 `BufferedWriter`를 사용해, 시간이나 메모리 측면에서 효율적이었다.

## :black_nib: **Review**
- 해시맵을 이용한 간단한 문제
- `BufferedWriter`를 왜 사용하는지에 대해 생각해본 문제.
  - 버퍼를 이용해 많은 양의 출력데이터를 처리하는데 `System.out.println()`보다 효율적인 처리가 가능했다.