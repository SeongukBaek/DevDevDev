**TDD** : Test Driven Development
- 테스트 코드 작성
- 컴파일 오류가 발생하니, 이를 해결하기 위한 클래스와 메소드를 최소한으로 작성
- 테스트 실행 후, 테스트를 성공시키기 위해 코드 구현
- 테스트 성공 후, 새로운 테스트 추가
- 반복

**테스트 코드를 작성하면서, 프로덕션 코드에 대해 고민할 점**
- 메소드 이름을 어떻게 지을까?
- 메소드의 파라미터 수, 타입, 반환 타입은?
- 메소드를 정적으로 구현할지, 인스턴스 메소드로 구현할지?
- 메소드를 제공할 클래스 이름은?

**첫번째 테스트가 매우 중요.**
- 가장 쉽거나, 가장 예외적인 상황을 선택할 것.

암호 강도 검사기 예제에서, 메소드의 반환 타입을 **열거 타입**으로 한 이유
- 0, 1, 2와 같은 정수형으로도 약함, 보통, 강함을 표현할 수 있지만, `STRONG` 과 같은 열거 타입이 강도를 더 잘 표현하기 때문

테스트 메소드도 코드이기에, **유지 보수 대상**이다.
- 여러 개의 테스트 코드가 작성되었다면, 발생하는 중복을 제거하거나, 의미를 잘 드러내도록 수정할 필요가 있다.
- 하지만 오히려 가독성을 떨어뜨리거나, 수정을 용이하지 않게 하는 경우 수정을 되돌려야 한다.

<details><summary>세번째 테스트까지 수행하면서, 구현한 프로덕션 코드와 테스트 코드</summary>

```kotlin
class PasswordStrengthMeter {
    fun meter(input: String): PasswordStrength {
        if (input.length < 8) {
            return PasswordStrength.NORMAL
        }
        val containsNumber = meetsContainingNumberCriteria(input)
        if (!containsNumber) {
            return PasswordStrength.NORMAL
        }
        return PasswordStrength.STRONG
    }

    private fun meetsContainingNumberCriteria(input: String): Boolean {
        for (ch: Char in input) {
            if (ch in '1'..'9') {
                return true
            }
        }
        return false
    }
}

class PasswordStrengthMeterTest {
    private val meter = PasswordStrengthMeter()

    @DisplayName("모든 규칙을 충족하는 경우, 암호 강도 강함")
    @Test
    fun meetsAllCriteria_Then_Strong() {
        assertStrength("ab12!@AB", PasswordStrength.STRONG)
        assertStrength("abc1!Add", PasswordStrength.STRONG)
    }

    @DisplayName("길이만 8글자 미만이고, 나머지 조건은 충족하는 경우, 암호 강도 보통")
    @Test
    fun meetsOtherCriteria_except_for_Length_Then_Normal() {
        assertStrength("ab12!@A", PasswordStrength.NORMAL)
        assertStrength("Ab12!c", PasswordStrength.NORMAL)
    }

    @DisplayName("숫자를 포함하지 않고, 나머지 조건은 충족하는 경우, 암호 강도 보통")
    @Test
    fun meetsOtherCriteria_except_for_number_Then_Normal() {
        assertStrength("ab!@ABqwer", PasswordStrength.NORMAL)
        assertStrength("Ab!@zxcvasdf", PasswordStrength.NORMAL)
    }

    private fun assertStrength(
        password: String,
        expStrength: PasswordStrength,
    ) {
        val result = meter.meter(password)
        assertEquals(expStrength, result)
    }
}
```

</details>
