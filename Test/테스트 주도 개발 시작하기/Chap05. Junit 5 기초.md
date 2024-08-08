JUnit 5 최신 버전 : 5.10.3
- JUnit 플랫폼 : 테스팅 프레임워크 구동을 위한 launcher와 테스트 엔진을 위한 API 제공
- JUnit Jupiter : 테스트 API, 실행 엔진 제공
- JUnit Vintage : JUnit 3, 4로 작성된 테스트를 JUnit 5 플랫폼에서 실행하기 위한 모듈 제공

JUnit 5 사용을 위해서는 의존성 추가 필요
- junit-jupiter : junit-jupiter-api + junit-jupiter-params + junit-jupiter-engine

`@Test` 어노테이션으로 테스트를 실행할 메소드를 구분
- 해당 메소드는 `private` 이어서는 안됨.
- `protected` 가능.

JUnit 은 여러 종류의 단언 메소드 (ex. `assertEquals`) 를 제공
- 주요 타입별로 `assertEquals` 제공
  - 해당 메소드는 전달받은 인자 클래스의 `equals()` 를 비교에 사용
 
`fail()` : 테스트에 실패했음을 알리는 메소드
- 예외가 던져져서 더 이상 진행되지 않아야 하는데, 해당 범위까지 진행되는 경우 사용 가능
- 그런데 이 메소드보다는 `assertThrows`, `assertDoesNotThrow` 사용하는 것이 더 직관적

`assert` 메소드는 실패 시, 곧바로 예외를 발생시켜 테스트가 실패함을 알림.
- 만약 모든 경우를 다 수행한 후, 실패한 케이스가 있는지 확인하고 싶을 때는 `assertAll()` 에 인자로 수행할 `assert()` 를 전달할 수 있다.

JUnit 은 각 테스트 메소드마다, 테스트 메소드를 포함한 객체를 생성한다.
- 2개의 테스트 메소드가 있다면 해당 클래스의 생성자가 2번 호출된다는 의미

가장 중요한 점 : 각 테스트 메소드는 독립적으로 동작해야 한다. 즉, 순서나, 다른 테스트 메소드의 결과에 의존해서는 안된다.
