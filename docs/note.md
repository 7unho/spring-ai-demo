## 현재 작성된 API의 문제점
- String만 반환 -> 파싱 필요
- 맥락이 유지되지 않음
- 응답이 생성될 때 까지 지연

## 실전 챗봇의 3요소
- [구조화된 응답 **Entity**](#구조화-응답)
- [대화 기억 (문맥 유지) **ChatMemory**]()
- [Streaming (Flux, SSE)]()

### 구조화 응답
- entity(SomeRecord.class) 옵션을 통해 바인딩을 진행
1. 단건 바인딩
- .entity(SomeRecord.class) 를 통해 내부적으로 record 객체에 매핑
2. 다건 바인딩
- .entity(new ParameterizedTypeReference<List<SomeRecord.class>> {});
  - List<SomeRecord>.class 가 아닌 이유
    - 제네릭은 런타입에 타입 정보가 지워진다 [**Type Erasure**]
    - 따라서 List<SomeRecord>.class 로는 요소 타입을 앓 수 없다.
  - Spring의 ParameterizedTypeReference는 제네릭의 타입을 보존하여 전달함
    - RestClient, RestTemplate 에서도 사용하는 패턴

#### Json 실무 함정 - MD Wrapping
> 프로덕션 파싱 장애의 단골 원인 
- Json 형식의 응답을 기대하지만, 실제 오는 응답은 MD로 오는 경우
  - 직접 Json을 요청하는 경우 프로프트에 'in JSON format without markdown tags'와 같이
    마크다운 문법을 제외한 JSON 형태를 응답하도록 요청
  - 가능하면 entity()를 사용하자