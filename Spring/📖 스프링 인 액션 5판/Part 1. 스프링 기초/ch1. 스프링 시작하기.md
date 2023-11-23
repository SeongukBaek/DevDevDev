**TOC**
- [스프링이란?](#스프링이란)
  - [스프링 애플리케이션 컨텍스트](#스프링-애플리케이션-컨텍스트)
  - [자동 구성(Auto Configuration), 컴포넌트 스캔(Component Scan)](#자동-구성auto-configuration-컴포넌트-스캔component-scan)
- [스프링 애플리케이션 초기 설정하기](#스프링-애플리케이션-초기-설정하기)
  - [스프링 프로젝트 구조 살펴보기](#스프링-프로젝트-구조-살펴보기)
    - [빌드 도구 Maven, Gradle](#빌드-도구-maven-gradle)
    - [애플리케이션의 부트스트랩(구동)](#애플리케이션의-부트스트랩구동)
    - [애플리케이션 테스트](#애플리케이션-테스트)

---

# 스프링이란?
## 스프링 애플리케이션 컨텍스트
**스프링 애플리케이션 컨텍스트**라는 **컨테이너**를 제공하고, 이를 통해 애플리케이션 컴포넌트(**빈**)들을 생성하고 관리한다.
- 그리고 이러한 빈들은 **의존성 주입**이라는 패턴을 기반으로 상호 연결된다.
- 의존성 주입 또한 컨테이너가 담당한다.

**즉, 스프링 애플리케이션 컨텍스트가 빈들의 생성, 관리 및 서로간의 상호 연결(의존성 주입)까지 모두 수행해준다.**

> [**애플리케이션 컨텍스트?**](https://mangkyu.tistory.com/151)

> **Configuration** vs. **Component**
> - 둘다 @Bean이 적용된 클래스를 빈으로 등록한다는 점은 같지만, Component를 사용했을 때는 다음과 같은 주의점이 있다.
> - 등록할 빈 메소드를 호출하는 구조가 되면, 실제로는 새 인스턴스를 만드는 꼴이 된다.
> - [Proxy Bean, Lite Mode Bean](https://jhkimmm.tistory.com/18)

## 자동 구성(Auto Configuration), 컴포넌트 스캔(Component Scan)
스프링은 자동 구성과 컴포넌트 스캔을 사용해서,
- 자동으로 애플리케이션의 클래스패스에 지정된 컴포넌트를 찾고, 이를 컨테이너의 빈으로 생성
- 이들에 대한 의존성 주입을 수행한다.

> `@EnableAutoConfiguration`
> - 위에서 말한 자동 구성 기능을 사용하겠다. 라는 설정 어노테이션
> - 그리고 해당 어노테이션은 ... 우리가 자주 보는 `@SpringBootApplication` 에 다음과 같이 존재한다.
> ```java
> @SpringBootConfiguration
> @EnableAutoConfiguration
> @ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
> @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
> public @interface SpringBootApplication { ... }
> ```

# 스프링 애플리케이션 초기 설정하기
![setting](./images/setting.png)
- [JDK 17](https://adoptium.net/temurin/releases/?version=17)
- [인텔리제이 JDK 변경](https://binux.tistory.com/92)

## 스프링 프로젝트 구조 살펴보기
### 빌드 도구 Maven, Gradle
**Maven**
- Maven 이전의 Ant의 불편함을 해소
- 정형화된 XML 구조 사용

**Gradle**
- XML 문법이 아닌 Groovy 문법 사용
- 더 좋은 가독성
- 최대 100배 빠른 속도.
- [비교](https://gradle.org/maven-vs-gradle/)

### 애플리케이션의 부트스트랩(구동)
**@SpringBootApplication**
- 이 코드가 스프링 부트 애플리케이션임을 나타냄.
- 추가적으로 아래 3개의 어노테이션이 결합된 형태.
  - `@SpringBootConfiguration`: 현재 클래스를 구성 클래스로 지정
  - `@EnableAutoConfiguration`: 위에서 언급한 자동 구성 기능 활성화
  - `@ComponentScan`: 컴포넌트 스캔 활성화

### 애플리케이션 테스트
**@SpringBootTest**
- 스프링 부트 기능으로 테스트를 시작하라는 것을 JUnit에 알려주는 어노테이션.
- 실제 애플리케이션을 로컬에 올려서, 포트 주소를 할당받고, 실제 DB와 Connection까지 진행.