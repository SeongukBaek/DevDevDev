**TOC**
- [정보 보여주기](#정보-보여주기)
  - [도메인 설정하기](#도메인-설정하기)
  - [컨트롤러 클래스 생성하기](#컨트롤러-클래스-생성하기)
    - [GET 요청 처리하기](#get-요청-처리하기)
  - [뷰 디자인하기](#뷰-디자인하기)
- [폼 제출 처리하기](#폼-제출-처리하기)
- [폼 입력 유효성 검사하기](#폼-입력-유효성-검사하기)
  - [유효성 검사 규칙 선언하기](#유효성-검사-규칙-선언하기)
  - [폼과 바인딩될 때 유효성 검사 수행하기](#폼과-바인딩될-때-유효성-검사-수행하기)
  - [유효성 검사 에러 보여주기](#유효성-검사-에러-보여주기)
- [뷰 컨트롤러로 작업하기](#뷰-컨트롤러로-작업하기)

---

# 정보 보여주기
**작성할 것들**
- 타코 식자재의 속성을 정의하는 도메인 클래스
- 식자재 정보를 가져와 뷰에 전달하는 스프링 MVC 컨트롤러 클래스
- 식자재의 내역을 사용자의 브라우저에 보여주는 뷰 템플릿

## 도메인 설정하기
도메인: 애플리케이션의 이해에 필요한 개념을 다루는 영역.

현재 애플리케이션의 도메인에 포함되는 정보
- 고객이 선택한 타코 디자인
- 디자인을 구성하는 식자재 -> `Ingredient`
- 고객의 타코 주문

```java
@Data
@RequiredArgsConstructor
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;

    public static enum Type {
        WARP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
```
- `@Data` == `@Getter + @Setter + @RequiredArgsConstructor + @ToString + @EqualsAndHashCode.`
- **Lombok**: 런타임에 필요한 메소드들을 자동으로 생성하도록 돕는 라이브러리
  - 컴파일 타임에 동작!

JDK 14에서 도입된 [`record` 클래스](https://docs.oracle.com/en/java/javase/17/language/records.html#GUID-6699E26F-4A9B-4393-A08B-1E47D4B2D263)로 변경도 가능
```java
@Data
@RequiredArgsConstructor
public record Ingredient(String id, String name, Type type) {
    public static enum Type {
        WARP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
```

추가적으로 이후에 필요한 클래스를 하나 더 추가
```java
@Data
public class Taco {
    private String name;
    private List<String> ingredients;
}
```

## 컨트롤러 클래스 생성하기
컨트롤러는 스프링 MVC에서 중심 역할을 수행한다.
- HTTP 요청을 처리하고,
- 브라우저에 보여줄 HTML을 뷰에 요청하거나,
- REST 형태의 응답 몸체에 직접 데이터를 추가한다.

**여기서 필요한 컨트롤러의 역할**
- 요청 경로가 `/design` 인 HTTP GET 요청 처리
- 식자재 내역 생성
- 식자재 데이터의 HTML 작성을 뷰 템플릿에 요청하고, 이를 웹 브라우저에 전송

```java
@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
    @GetMapping
    public String showDesignForm(Model model) {
        // 식자재 리스트 생성
        List<Ingredient> ingredientList = List.of(
                new Ingredient("FLTO", "Flour Tortilla", Type.WARP),
                new Ingredient("COTO", "Corn Tortilla", Type.WARP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterry Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );
        
        Type[] types = Ingredient.Type.values();
        
        // 식자재 유형을 Model에 추가
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredientList, type));
        }
        
        model.addAttribute("taco", new Taco());
        
        return "design";
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

- `@Slf4j` 는 아래의 필드를 만들어낸다.
  - `private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(현재 클래스.class);`
  - `lombok.config` 를 통해 해당 상수 필드 이름을 변경할 수 있다.
- `@RequestMapping`: 웹 요청을 메소드로 매핑시켜주는 역할, 클래스 적용 시 해당 컨트롤러가 처리하는 요청의 종류 의미.

### GET 요청 처리하기
`@GetMapping`: 해당 경로로 GET 요청이 수신될 때, 이를 처리할 메소드를 지정.
- 해당 어노테이션 이전에는 `@RequestMapping(method=RequestMethod.GET)` 으로 처리

## 뷰 디자인하기
스프링은 뷰를 정의하는 여러 방법을 제공.
- JSP
- Thymeleaf
- FreeMarker
- Mustache
- Groovy 기반의 템플릿 등.

이들과 같은 뷰 라이브러리들은, 어떤 웹 프레임워크와도 사용 가능하도록 설계되었다.
- 이말인즉슨, 어떤 웹 프레임워크에도 종속되지 않은 형태.
- 따라서 그 중 하나인 스프링의 추상화 모델을 알지 못하며, 위에서 컨트롤러가 데이터를 넣는 Model을 그대로 사용하는 것이 아니라, 서블릿 요청 속성들을 사용한다.
  - 스프링 -> 뷰 템플릿들이 사용하는 속성으로 Model 데이터 복사

Thymeleaf는, 요청 데이터를 나타내는 요소 속성이 추가된 HTML이다.
```html
<p th:text="${message}">placeholder message</p>
```
- 템플릿이 표현될 때, 키가 "message"인 서블릿 요청 속성의 값으로 해당 값이 대체된다.

추가적으로 위에서 전달한 `List<Ingredient> ingredientList` 를 표현하기 위해, `th:each` 문법을 사용할 수도 있다.

```html
<div th:each="ingredient : ${wrap}">
  <input name="ingredients" type="checkbox" th:value="${ingredient.id}" />
  <span th:text="${ingredient.name}">INGREDIENT</span><br/>
</div>
```

# 폼 제출 처리하기
`<form>` 에 `method="POST"` 로 명시했지만, `action` 에 대해 지정하지 않았다면, 브라우저가 폼의 모든 데이터를 모아 GET 요청과 같은 경로 (현재의 경우, `/design`)로 서버에 HTTP POST 요청을 보내게 된다.
- 즉, **이를 처리할 새로운 컨트롤러 메소드가 필요하다.**

```java
@PostMapping
public String processDesign(Taco design) {
    LOG.info("Processing Design: {}", design);

    return "redirect:/orders/current";
}
```
- `redirect:` : 변경된 경로로 재접속
- 위 로직이 끝난 후, 사용자의 브라우저가 `/orders/current` 상대 경로로 재접속되어야 함을 의미한다.
  - 이제 이를 처리할 새로운 컨트롤러를 생성한다.

```java
@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());
        return "orderForm";
    }
}
```
- Order는 추후에 작성하고, 먼저 반환될 뷰인 `orderForm.html` 생성.
- 해당 HTML에서는 주문 정보를 작성해 다시 서버로 POST 요청을 보내는 폼이 존재하므로, 이를 처리할 컨트롤러 메소드도 함께 작성

```java
@Data
public class Order {
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;
}
```

현재 애플리케이션은 사용자의 입력에 대한 어떠한 처리도 없이 그대로 전달되거나 노출되고 있다. 이제 이에 대한 검사를 수행해보자.

# 폼 입력 유효성 검사하기
사용자가 유효하지 않은 정보를 입력하지 않는 경우를 막을 코드가 현재는 없다.
- 이를 `if/then` 블록을 통해 추가하는 것은 번거롭고, 유지 보수가 어렵다.

스프링은 자바의 빈 유효성 검사를 지원한다.
- 또한 스프링 부트를 통해 유효성 검사 라이브러리를 쉽게 추가할 수 있다.
- 해당 라이브러리와 이를 구현한 Hibernate가 웹 스타터 의존성으로 자동 추가되기 때문이다.

## 유효성 검사 규칙 선언하기
`Taco` 클래스의 경우, 
- `name` 속성 값이 없거나, `null` 인지 확인하고, 
- 최소한 하나 이상의 식자재 항목을 선택했는지 확인해야 한다.

=> **`@NotNull`, `@Size` 사용**

> 현재 Javax -> Jakarta로 변경되어, 해당 어노테이션을 사용하기 위해서는 `implementation 'org.springframework.boot:spring-boot-starter-validation'` 의존성 추가가 필요.
> - 추가로, 스프링 부트 2.3 버전 이후부터는 javax.validation은 웹 스타터에 포함되지 않음.

```java
@NotNull
@Size(min = 5, message = "Name must be at least 5 characters long")
private String name;

@Size(min = 1, message = "You must choose at least 1 ingredient")
private List<String> ingredients;
```

`Order` 클래스의 경우, 
- 배달 주소에 관한 속성들은 사용자가 입력을 하지 않은 경우만 검사하면 된다.
- 대금 지불의 경우,
  - `ccNumber`: 값이 있는지, 입력 값이 유효한 신용 카드 번호인지
  - `ccExpiration`: MM/YY 형식의 값인지
  - `ccCVV`: 세자리 수인지

=> **`@NotBlank`, `@CreditCardNumber`, `@Pattern`, `@Digit` 사용**

> `@NotNull`, `@NotEmpty`, `@NotBlank`
> - `@NotNull`: `null` 만 허용하지 않음. `""`, `" "` 허용
> - `@NotEmpty`: `null` 과 `""`를 허용하지 않음. `" "` 허용
> - `@NotBlank`: `null`, `""`, `" "` 모두 허용하지 않음.

> `@CreditCardNumber`
> - 룬 알고리즘 검사를 통과한 유효한 신용 카드 번호인지 확인.
> - 금융망과 연동된 검사까지는 수행하지 못함.

## 폼과 바인딩될 때 유효성 검사 수행하기
컨트롤러를 수정해서, 요청 관련 메소드에서 처리될 때, 유효성 검사가 수행되도록 하자.
- 이를 위해 `@Valid` 추가.

```java
@PostMapping
public String processDesign(@Valid Taco design, Errors errors) {
    if (errors.hasErrors()) {
        return "design";
    }
    
    LOG.info("Processing Design: {}", design);

    return "redirect:/orders/current";
}
```
- `Taco` 에 어노테이션을 적용해, 해당 객체의 유효성 검사를 수행함을 의미.
  - 제출된 폼 데이터와 `Taco` 객체가 바인딩된 후, 해당 메소드가 실행되기 전에 유효성 검사를 수행.
  - 에러가 발생하면, 이는 `Errors` 객체에 저장되어 전달됨.

현재 위 방식은 에러가 발생하면, 해당 요청이 다시 뷰에 보내진다. (redirection을 통해)
- **사용자는 어떤 값이 잘못되었는지 알 방법이 없다.**
- 사용자가 어떤 점이 잘못되었는지 알 수 있도록 하자.

## 유효성 검사 에러 보여주기
Thymeleaf는 `fields` 와 `th:errors` 속성을 통해 `Errors` 객체의 편리한 사용방법을 제공한다.
- 에러 참조를 사용하는 태그 요소를 템플릿에 추가할 수 있다.

```html
<span class="validationError"
  th:if="${#fields.hasErrors('name')}"
  th:errors="*{name}">Name Error
</span>
```
- `th:if` 로 해당 정보의 표시 여부 결정
- `fields` 속성의 `hasErrors()` 로 필드 에러 검사.
- 폼 제출 시, 해당 에러 발생 문구를 보여주게 된다.

# 뷰 컨트롤러로 작업하기
