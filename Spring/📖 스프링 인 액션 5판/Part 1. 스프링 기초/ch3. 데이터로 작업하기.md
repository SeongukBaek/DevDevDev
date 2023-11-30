**TOC**
- [JDBC를 사용해서 데이터 읽고 쓰기](#jdbc를-사용해서-데이터-읽고-쓰기)
  - [퍼시스턴스를 고려한 도메인 객체 수정하기](#퍼시스턴스를-고려한-도메인-객체-수정하기)
  - [JdbcTemplate 사용하기](#jdbctemplate-사용하기)
    - [JDBC 레포지토리 정의하기](#jdbc-레포지토리-정의하기)
    - [데이터 추가하기](#데이터-추가하기)
  - [스키마 정의하고 데이터 추가하기](#스키마-정의하고-데이터-추가하기)
  - [각 테이블의 역할은 다음과 같다.](#각-테이블의-역할은-다음과-같다)
---

# JDBC를 사용해서 데이터 읽고 쓰기
여기서는 스프링과 JDBC로 관계형 데이터베이스를 사용할 것이다.

스프링의 JDBC 지원은 `JdbcTemplate` 클래스를 기반으로 시작한다.
- 해당 클래스는 JDBC 사용 시 필요한 형식적이고, 상투적인 코드 없이 개발자가 SQL 연산을 수행하는 것을 돕는다.

`JdbcTemplate` 을 사용하지 않고, 자바로 SQL 쿼리를 수행하려면, 
- DB 연결
- 실행할 SQL과 파라미터 매핑
- SQL 결과 매핑
- 예외 처리를 위한 catch 블록

위와 같은 코드들이 필요하고, 이는 실제 수행하는 SQL 쿼리를 더 찾기 힘들게 만든다.

하지만 `JdbcTemplate` 을 사용하게 되면, 
- DB 연결
- 예외 처리를 위한 catch 블록

과 같은 코드들을 모두 제거해낼 수 있어, **실제 쿼리 수행과 그 결과에 대한 코드에만 초점을 둘 수 있게 된다.**

## 퍼시스턴스를 고려한 도메인 객체 수정하기
객체를 DB에 저장할 때는 해당 객체를 식별하기 위한 식별자가 있어야 한다.
- `Taco`, `Order` 에 `id` 필드를 추가하자.
- 추가로, 객체가 저장된 날짜와 시간(+ 수정된 날짜와 시간)을 갖는 필드를 추가해, 해당 객체의 생성(+ 수정)에 대한 추적이 가능하도록 하자.

```java
@Data
public class Taco {
    private Long id;

    ...

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
```

## JdbcTemplate 사용하기
우선 의존성부터 추가.
- jdbc, mysql
```
implementation 'org.springframework.boot:spring-boot-starter-jdbc'
implementation 'mysql:mysql-connector-java:8.0.33'
```

### JDBC 레포지토리 정의하기
식자재 데이터를 가져오고, 저장하는 레포지토리(`IngredientRepository`)를 생성한다.
- DB의 모든 식자재 데이터를 조회해 `List<Ingredient>` 로 관리
- `id` 를 사용해 하나의 `Ingredient` 조회
- `Ingredient` 저장

```java
public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findById(String id);
    Ingredient save(Ingredient ingredient);
}
```
- 왜 `Iterable` 를 사용?
  - 단순히 얻은 데이터를 `foreach()` 로 반복하기 위함을 나타내기 위해서?
- 왜 `save` 의 반환형이 `save` 한 객체 ?

이제 이 인터페이스의 구현체를 생성한다.

```java
@Repository
@RequiredArgsConstructor
public class JdbcIngredientRepository implements IngredientRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("SELECT id, name, type FROM Ingredient", this::mapRowToIngredient);
    }

    @Override
    public Ingredient findById(String id) {
        return jdbcTemplate.queryForObject("SELECT id, name, type FROM Ingredient WHERE id = ?", this::mapRowToIngredient, id);
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type"))
        );
    }
}
```
- `query()`: SQL의 결과로 `List<T>` 반환
- `queryForObject()`: SQL의 결과가 mapper에 의해 `List<T>` 로 반환되는데, 여기서 제일 앞의 한 건만 반환
  - 이때 해당 SQL의 반환 수가 1개라고 기대하고 있기에 제일 앞의 한 건만 반환
- `mapRowToIngredient()`: 쿼리의 결과물(`ResultSet`)의 행 개수만큼 호출, 각 행을 객체로 매핑하고, 이를 `List` 로 저장
  - 해당 메소드를 자바 8에서 등장한 메소드 참조, 람다를 활용해 `query()`, `queryForObject()` 로 전달!
  - 이를 사용하지 않으면 `RowMapper` 를 구현한 익명 클래스를 해당 메소드 참조 위치에 대입하면 되는데, 이런 방식은 메소드 호출마다, 익명 클래스 인스턴스가 계속 생성된다는 문제가 있다.

### 데이터 추가하기
`save()` 메소드 구현

```java
@Override
public Ingredient save(Ingredient ingredient) {
    jdbcTemplate.update(
        "INSERT INTO Ingredient (id, name, type) VALUE (?, ?, ?)", 
        ingredient.getId(),
        ingredient.getName(),
        ingredient.getType().name()
    );
    return ingredient;
}
```

이제 이전에 하드코딩한 코드를 대체하자.
```java
List<Ingredient> ingredientList = new ArrayList<>();
ingredientRepository.findAll().forEach(ingredientList::add);
```

## 스키마 정의하고 데이터 추가하기
이제 만들어진 레포지토리를 활용하려면, DB에 해당 테이블이 존재해야 하고, 데이터에 존재해야 한다.
각 테이블의 역할은 다음과 같다.
- 