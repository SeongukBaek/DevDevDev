**TOC**
- [JDBC를 사용해서 데이터 읽고 쓰기](#jdbc를-사용해서-데이터-읽고-쓰기)
  - [퍼시스턴스를 고려한 도메인 객체 수정하기](#퍼시스턴스를-고려한-도메인-객체-수정하기)
  - [JdbcTemplate 사용하기](#jdbctemplate-사용하기)
    - [JDBC 레포지토리 정의하기](#jdbc-레포지토리-정의하기)
    - [데이터 추가하기](#데이터-추가하기)
  - [스키마 정의하고 데이터 추가하기](#스키마-정의하고-데이터-추가하기)
  - [타코와 주문 데이터 추가하기](#타코와-주문-데이터-추가하기)
    - [JdbcTemplate을 사용해서 데이터 저장하기](#jdbctemplate을-사용해서-데이터-저장하기)
    - [SimpleJdbcInsert를 사용해서 데이터 추가하기](#simplejdbcinsert를-사용해서-데이터-추가하기)
  - [이슈](#이슈)
- [스프링 데이터 JPA를 사용해서 데이터 저장하고 사용하기](#스프링-데이터-jpa를-사용해서-데이터-저장하고-사용하기)
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
- `Ingredient`: 식자재 정보 저장
- `Taco`: 사용자가 식자재를 선택하여 생성한 타코 디자인에 관한 정보 저장
- `Taco_Ingredients`: Taco와 Ingredient 테이블 간의 관계를 나타내며, Taco 테이블의 각 행에 대해 하나 이상의 행 포함
  - 타코에는 하나 이상의 식자재가 포함될 수 있다.
- `Taco_Order`: 주문 정보 저장
- `Taco_Order_Tacos`: `Taco_Order` 와 `Taco` 테이블 간의 관계를 나타내며, Taco_Order 테이블의 각 행에 대해 하나 이상의 행 포함
  - 한 건의 주문에는 하나 이상의 타코가 포함될 수 있다.

스키마를 생성하고, `Ingredient` 에 대한 데이터만 추가한다.

## 타코와 주문 데이터 추가하기
`JdbcTemplate` 을 사용해서 데이터를 저장하는 방법은 아래 2가지가 있다.
- 직접 `update()` 를 사용한다.
- `SimpleJdbcInsert` 래퍼 클래스를 사용한다.

### JdbcTemplate을 사용해서 데이터 저장하기
`Taco` 와 `Order` 객체를 저장하는 인터페이스를 정의한다.
- 하지만 위 테이블에만 데이터를 저장한다고 끝나는 것이 아니다.

사용자가 식자재를 선택해 생성한 타코 디자인을 저장하려면, 해당 타코와 연관된 식자재 데이터도 `Taco_Ingredients` 테이블에 저장해야 한다.
- 또한, 주문을 저장하려면, 해당 주문과 연관된 타코 데이터를 `Taco_Order_Tacos` 테이블에 저장해야 한다.

> 즉, 데이터 간 연관관계를 고려해야 데이터의 저장 로직을 수행해야 한다.

```java
@Override
public Taco save(Taco design) {
    long tacoId = saveTacoInfo(design);
    design.setId(tacoId);
    for (Ingredient ingredient : design.getIngredients()) {
        saveIngredientToTaco(ingredient, tacoId);
    }
    return design;
}
```
- `Taco` 저장 과정은 다음과 같다.
  - `Taco` 테이블에 각 식자재를 저장하는 `saveTacoInfo()` 를 호출한다.
  - 해당 메소드에서 반환된(저장된) 타코 ID를 사용해 타코와 식자재의 연관 정보를 저장하는 `saveIngredientToTaco()` 를 호출한다.

이때, 직전에 저장한 데이터의 ID를 가져오는 로직이 필요한데, 이를 위해
- `PreparedStatementCreator`, `KeyHolder` 를 사용했다.
- `PreparedStatementCreator` : `INSERT`, `UPDATE`, `DELETE` 쿼리 실행 메서드인 `update()` 메서드에 사용되는 클래스
- `KeyHolder`: JDBC insert 문에 의해 반환되는 자동 증가되는 키 필드 조회에 사용되는 인터페이스

> 이제 이 코드를 가지고 `DesignTacoController` 를 수정하자.

```java
@Slf4j
@Controller
@RequestMapping("/design")
@RequiredArgsConstructor
@SessionAttributes("order")
public class DesignTacoController {
    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    @ModelAttribute("order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute("taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredientList::add);

        Type[] types = Ingredient.Type.values();

        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredientList, type));
        }

        model.addAttribute("taco", new Taco());

        return "design";
    }

    @PostMapping
    public String processDesign(
            @Valid Taco design,
            Errors errors,
            @ModelAttribute Order order
    ) {
        if (errors.hasErrors()) {
            return "design";
        }

        LOG.info("Processing Design: {}", design);

        Taco saved = tacoRepository.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredientList,
            Type type
    ) {
        return ingredientList.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
```
- `@SessionAttributes("order")` : 해당 모델 객체를 세션에 보존해서, 다수의 HTTP 요청에 걸쳐 모델 객체를 사용할 수 있도록 해준다.
  - 여기서 주문 객체는 여러 세션에서 접근할 수 있어야 하는데, 다수의 타코들은 각 세션에서 생성되지만, 이러한 다수의 타코들을 하나의 주문으로 추가하려면 이 주문은 다수의 세션에서 접근할 수 있어야 한다.
- `@ModelAttribute("order")` : 해당 메소드가 반환하는 객체를 모델에 생성되도록 해준다.
- `processDesign()` : 실제로 타코 디자인을 저장하는 역할 수행.
  - `@ModelAttribute` 을 통해, Order 객체는 모델로부터 받아옴을 의미.

이제 이렇게 완성되는 `Order` 를 저장할 레포지토리를 생성하자.

### SimpleJdbcInsert를 사용해서 데이터 추가하기
주문 저장 또한 타코의 경우와 유사하다.
- `Taco_Order` 에 주문 데이터 저장
- 해당 주문의 각 타코에 대한 `id` 도 `Taco_Order_Tacos` 에 저장

> 여기서는 데이터를 더 쉽게 테이블에 추가하기 위해 `JdbcTemplate` 을 래핑한 `SimpleJdbcInsert` 를 활용한다.

```java
private final SimpleJdbcInsert orderInserter;
private final SimpleJdbcInsert orderTacoInserter;
private final ObjectMapper objectMapper;

public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
    this.orderInserter = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("Taco_Order")
            .usingGeneratedKeyColumns("id");

    this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("Taco_Order_Tacos");

    this.objectMapper = new ObjectMapper();
}
```
- 첫번째 `SimpleJdbcInsert`: `Taco_Order` 테이블용
  - 이때 `Order` 객체의 `id` 속성 값은 DB가 생성해주는 것을 사용.
- 두번째 `SimpleJdbcInsert`: `Taco_Order_Tacos` 테이블용

```java
@Override
public Order save(Order order) {
    order.setCreatedAt(LocalDateTime.now());
    long orderId = saveOrderDetails(order);
    order.setId(orderId);

    for (Taco taco : order.getTacoList()) {
        saveTacoToOrder(taco, orderId);
    }

    return order;
}

private long saveOrderDetails(Order order) {
    Map<String, Object> values = objectMapper.convertValue(order, Map.class);
    values.put("createdAt", order.getCreatedAt());

    return orderInserter.executeAndReturnKey(values).longValue();
}

private void saveTacoToOrder(Taco taco, long orderId) {
    Map<String, Object> values = new HashMap<>();
    values.put("tacoOrder", orderId);
    values.put("taco", taco);
    orderTacoInserter.execute(values);
}
```

- 먼저 주문을 저장하고, 그 결과로 받은 `orderId` 를 기반으로 `Taco_Order_Tacos` 테이블에 저장한다.
- `SimpleJdbcInsert` 의 `execute()` , `executeAndReturnKey()` 는 `Map` 을 인자로 받는다.
  - 이때, `ObjectMapper.convertValue()` 를 통해 매개변수로 받은 `Order` 객체를 해당 메소드들이 필요로 하는 `Map` 형태로 변환한다.

> 만든 레포지토리를 컨트롤러에 적용.

```java
@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(
            @Valid Order order,
            Errors errors,
            SessionStatus sessionStatus
    ) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        LOG.info("Order submitted: {}", order);

        orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
```
- 폼으로 받은 주문 객체를 DB에 저장하기 위해 세션에 보존한다.
- 하지만 DB에 저장된 이후에도 세션에 남아 있게 되면, 다음 주문 시에도 이전 주문 정보가 남아 있게 된다.
  - `sessionStatus.setComplete();` : 현재 세션을 완료처리해서, 세션 `attributes` 를 cleanup 하도록 한다.

데이터 타입을 변환해주는 **컨버터** 구현.
- `String` 타입의 식자재 ID를 사용해 DB에 저장된 특정 식자재 데이터를 읽은 후, `Ingredient` 객체로 변환하기 위해 사용.

```java
@Component
@RequiredArgsConstructor
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private final IngredientRepository ingredientRepository;

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findById(id);
    }
}
```

## 이슈
예제의 `JdbcTacoRepository` 코드를 그대로 사용하면, 아래의 문제가 발생했다.
```
java.sql.SQLException: Generated keys not requested. You need to specify Statement.RETURN_GENERATED_KEYS to Statement.executeUpdate(), Statement.executeLargeUpdate() or Connection.prepareStatement().
```
- 이는 `GeneratedKeyHolder` 를 사용하면서, 생성된 키를 다시 요청하지 않아서 발생하는 문제였다고 한다.
- 그리고 에러에 보면 적혀있듯, `Statement.RETURN_GENERATED_KEYS` 를 명시하라고 한다.

```java
private long saveTacoInfo(Taco taco) {
    taco.setCreatedAt(LocalDateTime.now());
    PreparedStatementCreatorFactory pscFactory =
            new PreparedStatementCreatorFactory(
                    "insert into Taco (name, createdAt) values (?, ?)",
                    Types.VARCHAR, Types.TIMESTAMP
            );
    pscFactory.setReturnGeneratedKeys(true);
    pscFactory.setGeneratedKeysColumnNames("id");

    PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(
            Arrays.asList(
                    taco.getName(),
                    Timestamp.valueOf(taco.getCreatedAt())
            )
    );

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(psc, keyHolder);

    return keyHolder.getKey().longValue();
}
```
- 따라서 위와 같이, `setReturnGeneratedKeys()` , `setGeneratedKeysColumnNames()` 를 통해 가져올 필드명을 지정했다.

# 스프링 데이터 JPA를 사용해서 데이터 저장하고 사용하기
- [serialVersionUID](https://hjjungdev.tistory.com/187)

```
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.type.descriptor.sql=trace
```
- 사용하는 SQL을 보기 좋게, 그리고 파라미터에 어떤 값들이 들어가는지 보여주는 속성

```java
@Enumerated(EnumType.STRING)
private final Type type;
```
- `enum` 에 대해서, 위와 같은 어노테이션이 없다면, `enum` 값을 인덱스(`double`)로 가져와 SQL에서 사용하기 때문에 `NumberFormatException` 이 발생한다.
- `enum` 에 대해서 그 값을 그대로 저장하고 사용하려면 꼭 해당 어노테이션이 필요.