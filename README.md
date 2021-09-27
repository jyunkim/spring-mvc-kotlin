# Spring Boot Web MVC with Kotlin
## 프로젝트 세팅
- Project: Gradle
- Spring Boot: 2.5.5
- Language: Kotlin
- Packaging: Jar
- Java: 11
- Dependencies: Spring Web

## 웹 개론
### REST
Representational State Transfer, 자원의 상태 전달   
네트워크 아키텍처 원리
1. Client, Server   
클라이언트와 서버가 서로 독립적으로 분리되어야 함
2. Stateless   
요청에 대해 클라이언트의 상태가 서버에 저장되지 않아야 함   
-> 요청 간 영향이 없어야 함
3. 캐시   
클라이언트는 서버의 응답을 캐시할 수 있어야 함   
-> 응답을 재사용하여 서버의 부하를 낮춤
4. 계층화   
서버와 클라이언트 사이에 방화벽, 게이트웨이, 프록시 등 다계층 형태를 구성할 수 있어야 함
5. 인터페이스 일관성   
아키텍처를 단순화시키고 작은 단위로 분리   
   - 자원 식별: 리소스 접근에 URI를 사용   
   https://foo.com/user/100 -> 리소스: user, 식별자: 100
   - 메시지를 통한 리소스 조작: 리소스의 타입을 알려주기 위해 header 부분에 content-type을 명시
   - 자기 서술적 메시지: 요청하는 데이터가 어떻게 처리되어야 하는지 충분한 정보를 포함   
   HTTP 메서드와 Header의 정보로 이를 표현
   - 애플리케이션 상태에 대한 엔진으로서 하이퍼미디어: 요청에 대한 응답으로 관련 리소스에 대한 링크 정보까지 포함

이러한 조건들을 잘 갖춘 경우를 RESTful 하다고 하고, REST를 적용한 API를 REST API라 함

### URI
Uniform Resource Identifier   
인터넷에서 특정 리소스를 나타내는 유일한 식별자

**URL**   
인터넷에서 특정 리소스가 위치한 주소   
URI의 하위 개념   
- https://foo.com/index.html -> URI, URL   
- https://foo.com/index -> URI

첫번째 주소는 실제 파일 위치를 나타내는 주소이지만,   
두번째 주소는 해당 파일을 식별할 수는 있지만 실제 파일을 나타내는 주소가 아니므로 URL이 아님(index라는 파일이 존재 x)

### URI 설계 원칙
- 슬래시 구분자는 계층 관계를 나타내는 데 사용
- URI의 마지막 문자로 /를 포함하지 않음
- 가독성을 높이기 위해 _ 대신 -을 사용
- 경로에는 소문자만 사용
- 파일 확장자는 포함하지 않음
- 구현에 의존적인 경로를 사용하지 않음   
ex) https://foo.com/spring/user/1
- 세션 ID를 포함하지 않음
- 특정 언어의 메서드 명을 사용하지 않음
- 컬렉션에 대한 표현은 복수형을 사용(명사는 대부분 복수형 사용)
- HTTP 메서드를 나타내는 단어를 포함하지 않음
- 필요한 경우(CRUD로 표현할 수 없는 경우) 컨트롤러 이름을 동사로 URI의 마지막에 포함   
ex) https://foo.com/items/order
- API 서브 도메인 관례   
ex) https://api.foo.com
- 클라이언트 개발자 서브 도메인 관례   
ex) https://dev-api.foo.com

### HTTP
Hyper Text Transfer Protocol   
웹에서 데이터를 주고 받는 규약   
TCP를 기반으로 한 REST의 특징을 모두 구현하고 있는 프로토콜   
메시지를 요청하고 응답을 받는 형태

### HTTP Method
GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS..

### HTTP 상태 코드
- 1xx: 처리중
- 2xx: 성공
  - 200: 성공
  - 201: 리소스 생성 성공
- 3xx: 리다이렉트
  - 301: 리소스가 다른 장소로 변경됨
  - 303: 클라이언트에서 자동으로 새로운 리소스로 요청
- 4xx: 클라이언트 에러
  - 400: 요청 오류, 파라미터 에러
  - 401: 권한 없음(인증 실패)
  - 404: 리소스 없음(페이지를 찾을 수 없음)
- 5xx: 서버 에러
  - 500: 서버 내부 에러(서버 동작 처리 에러)
  - 503: 서비스 정지(점검)

## Controller
### @Controller
**View를 반환하는 경우**
1. 클라이언트가 URI 형식으로 웹 서비스에 요청을 보냄
2. DispatcherServlet이 요청을 받고 매핑되는 Handler에 넘김
3. Controller가 요청을 처리하고 응답을 DispatcherServlet으로 반환
4. ViewResolver가 알맞은 view를 찾아 렌더링

**Data를 반환하는 경우**   
@ResponseBody 필요
ViewResolver 대신에 HttpMessageConverter가 동작하여 반환해야 하는 데이터에 맞는 Converter를 사용하여 처리

### @RestController
@Controller에 @ResponseBody가 추가된 것   
Jackson 라이브러리(Spring Web에 포함)를 통해 자동으로 자바 객체가 json으로 직렬화되거나, json 데이터가 자바 객체로 역직렬화됨
