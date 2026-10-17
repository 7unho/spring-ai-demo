# Prompt Engineering
> 같은 모델, 같은 비용이라도 프롬프트의 차이가 결과 품질을 결정

## Prompt
- 품질을 좌우하고, 팀이 공유하고, 리뷰와 버전 관리가 필요한 자산
- **엔지니어링의 대상**이다


## Spring AI prompt
```java
chatClient.prompt()
        .system("당신은 Spring 전문가입니다. 코드 예시와 함께 한국어로 답하세요")
        .user(question)
        .call().content();
```
- `System( 행동 규칙, 정체성 )`: 개발자가 통제 - 페르소나, 형식, 제약을 고정
- `User( 사용자 입력 )`: 최종 사용자의 질문 - 신뢰할 수 없는 입력
- `Assistant( 모델의 응답 )`: 이전 응답 이력 - Few-shot 예시 채널로도 홣용
  