테스트 작성 시, 외부 요인이 필요한 시점이 있다.
- 테스트 대상에서 파일 시스템 사용
- 테스트 대상에서 DB로부터 데이터를 조회하거나 추가
- 테스트 대상에서 외부 HTTP 서버와 통신

위와 같은 외부 요인에 테스트 대상이 의존하면, 테스트 작성 및 실행이 어려워진다.
- 또한 결과도 예측하기 어려워진다.

> 테스트 대상에서 의존하는 요인 때문에 테스트가 어려울 때, 외부 요인을 대신하는 "대역" 을 사용해 테스트를 수행한다.

