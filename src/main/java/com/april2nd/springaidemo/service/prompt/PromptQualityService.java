package com.april2nd.springaidemo.service.prompt;

import com.april2nd.springaidemo.dto.BeforeAfterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptQualityService {
    public static final String SAMPLE_ARTICLE = """
            한국은행이 기준금리를 연 3.00%로 동결했다. 3회 연속 동결이다.
            금융통화위원회는 물가 상승률이 목표치에 근접했으나 가계부채 증가세와
            원/달러 환율 변동성을 고려해 신중한 기조를 유지하기로 했다.
            시장에서는 하반기 인하 가능성을 점치고 있으나, 총재는 기자회견에서
            "인하 시점을 예단하기 이르다"고 말했다. 이날 코스피는 0.4% 상승 마감했다.
            """;
    private final PromptRunner promptRunner;
    @Value("classpath:/prompts/system-customer-center.st")
    private Resource customerCenterResource;

    public BeforeAfterResponse summaryBeforeAfter(String article) {
        String before = "이 기사 요약해줘\n\n" + article;
        String after = """
                당신은 경제 뉴스 에디터입니다.
                아래 기사를 핵심 사실 중심으로
                3개의 불릿, 각 40자 이내로 요약하세요.
                의견이나 전망은 포함하지 마세요.
                
                기사:
                %s
                """.formatted(article);

        var beforeResult = promptRunner.userWithTokens(before);
        var afterResult = promptRunner.userWithTokens(after);

        return new BeforeAfterResponse(
                "",
                "before - 막연한 지시", before, beforeResult.answer(), beforeResult.totalTokens(),
                "After - 역할·형식·제약 명시", after, afterResult.answer(), afterResult.totalTokens()
        );
    }

    public String roleSeparated(String question) {
        return promptRunner.client()
                .prompt()
                .system("당신은 Spring 전문가입니다. 코드 예시와 함께 한국어로 답하세요.")
                .user(question)
                .call().content();
    }

    public BeforeAfterResponse roleSeparationAntiPattern(String question) {
        // 사용자 입력과 시스템 메시지가 혼합된 경우
        String mixed = "당신은 Spring 전문가입니다. 코드 예시와 함께 한국어로 답하세요. \n" + question;
        var bad = promptRunner.userWithTokens(mixed);

        // 잘된 케이스
        var good = promptRunner.systemAndUserWithTokens("당신은 Spring 전문가입니다. 코드 예시와 함께 한국어로 답하세요.", question);

        return new BeforeAfterResponse(
                "역할 구조( System/User/Assistant )",
                "안티패턴 - 하나의 프롬프트로 전달", mixed, bad.answer(), bad.totalTokens(),
                "프롬프트 분리 - System / User", "[System] Spring 전문가 규칙, [User] " + question, good.answer(), good.totalTokens()
        );
    }
}
