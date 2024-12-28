테스트 작성 시, 외부 요인이 필요한 시점이 있다.
- 테스트 대상에서 파일 시스템 사용
- 테스트 대상에서 DB로부터 데이터를 조회하거나 추가
- 테스트 대상에서 외부 HTTP 서버와 통신

위와 같은 외부 요인에 테스트 대상이 의존하면, 테스트 작성 및 실행이 어려워진다.
- 또한 결과도 예측하기 어려워진다.

### 자동이체 기능 예시
외부 업체가 제공하는 API를 이용해 카드번호의 유효성을 확인하고, 그에 따른 자동이체 정보 등록 수행
- 기능 테스트를 위해 외부 업체로부터 받아야 하는 정보 : 정상 카드번호, 도난 카드번호, 만료된 카드번호
- 만약, 카드 정보 검사 대행업체에서 제공해준 카드번호의 유효 기간이 1달일 경우, 1달 뒤 정상 카드번호에 대한 테스트는 실패할 것.

> **즉, 외부 요인(외부 업체)에 테스트가 의존하게 된다.**
> 또한 외부 서비스가 개발 환경을 제공하지 않는 경우, 테스트는 더 어려워진다.

<img width="591" alt="스크린샷 2024-12-28 17 04 39" src="https://github.com/user-attachments/assets/cd465ba1-fc62-4d6e-96fa-8b44ec1ea4e3" />

- `CardNumberValidator` : `외부 API` 를 이용해서 카드번호 유효성 확인
- `AutoDebitRegister` : `CardNumberValidator` 를 이용해 카드번호 유효성 검사 후, 결과에 따른 자동이체 정보 등록

> 테스트 대상에서 의존하는 요인 때문에 테스트가 어려울 때, 외부 요인을 대신하는 "대역" 을 사용해 테스트를 수행한다.

# 대역을 이용한 테스트
> 대역을 이용해 `AutoDebitRegister` 를 테스트하는 코드 작성
> 1. `CardNumberValidator` 대역 클래스 생성

```kotlin
class StubCardNumberValidator : CardNumberValidator() {
    // 유효하지 않은 번호
    private lateinit var invalidNo: String
    // 도난 번호
    private lateinit var theftNo: String

    fun setInvalidNo(invalidNo: String) {
        this.invalidNo = invalidNo
    }

    override fun validate(cardNumber: String): CardValidity {
        if (invalidNo.isNotBlank() && invalidNo == cardNumber) {
            return CardValidity.INVALID
        }
        return CardValidity.VALID
    }
}
```
- 실제 카드번호 검증 기능을 구현하지 않고, setter 를 통해 지정된 `invalidNo` 와의 비교를 통해 카드번호 검증 결과를 반환한다.
- 테스트 코드에서도, `CardNumberValidator` 가 아닌 `StubCardNumberValidator` 를 통해 테스트를 수행하도록 한다.
- 또한 도난 카드번호에 대해서도 `theftNo` 필드 및 메소드를 추가해 대응 가능하다.


> 대역을 이용해 `AutoDebitRegister` 를 테스트하는 코드 작성
> 1. ~~`CardNumberValidator` 대역 클래스 생성~~
> 2. DB 연동 대역 클래스 생성

```kotlin
interface AutoDebitInfoRepository {
    fun save(info: AutoDebitInfo)

    fun findOne(userId: String)
}

class MemoryAutoDebitInfoRepository : AutoDebitInfoRepository {
    override fun save(info: AutoDebitInfo) {
        infos[info.userId] = info
    }

    override fun findOne(userId: String): AutoDebitInfo? = infos.get(userId)

    companion object {
        val infos = HashMap<String, AutoDebitInfo>()
    }
}
```
- DB 대신 Map을 이용해 자동이체 정보를 저장한다.
  - 메모리에만 저장하기에 DB와 같은 영속성은 제공되지 않지만, 테스트는 가능하다.
