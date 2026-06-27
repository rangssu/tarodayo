package com.taro.tarodayo.service;

import com.taro.tarodayo.dto.DrawnCard;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = """
            당신은 수십 년의 경험을 가진 신비로운 타로 마스터입니다.
            깊은 통찰력과 공감 능력으로 카드가 전하는 우주의 메시지를 시적이고 따뜻하게 풀어냅니다.
            단순히 키워드를 나열하는 것이 아니라, 질문자의 상황을 감지하고 카드 사이의 흐름과 연결을 읽어냅니다.
            """;

    public String interpret(String question, List<DrawnCard> selectedCards) {
        DrawnCard past    = selectedCards.get(0);
        DrawnCard present = selectedCards.get(1);
        DrawnCard future  = selectedCards.get(2);

        String userPrompt = """
                [질문자의 고민]
                %s

                [타로 카드 배열 — 과거·현재·미래]
                - 과거:  %s (%s) — %s
                - 현재:  %s (%s) — %s
                - 미래:  %s (%s) — %s

                [해석 지침]
                1. 과거, 현재, 미래 각각을 3~4문장으로 해석해주세요. 카드의 상징과 키워드를 질문자의 고민과 연결해 풀어주세요.
                2. 세 카드가 함께 만들어내는 흐름을 2~3문장으로 분석해주세요. 과거의 에너지가 현재에 어떤 영향을 미쳤는지, 현재의 선택이 미래를 어떻게 열어가는지 읽어주세요.
                3. 질문자에게 전하는 따뜻한 메시지를 한 문장으로 마무리해주세요.
                4. 시적이고 신비로운 문체로, 한국어로 답변해주세요.
                """.formatted(
                question,
                past.getCard().getNameKo(),    past.getDirection(),    past.getKeywords(),
                present.getCard().getNameKo(), present.getDirection(), present.getKeywords(),
                future.getCard().getNameKo(),  future.getDirection(),  future.getKeywords()
        );

        return chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user(userPrompt)
                .call()
                .content();
    }

    public String interpretOneCard(String question, DrawnCard card) {
        String userPrompt = """
                [질문자의 고민]
                %s

                [오늘의 카드]
                %s (%s) — %s

                [해석 지침]
                1. 이 카드가 오늘 질문자에게 전하는 에너지와 메시지를 3~4문장으로 해석해주세요. 카드의 상징과 키워드를 질문자의 고민과 연결해 풀어주세요.
                2. 오늘 하루 이 카드가 질문자에게 건네는 따뜻한 한 마디로 마무리해주세요.
                3. 시적이고 신비로운 문체로, 한국어로 답변해주세요.
                """.formatted(
                question,
                card.getCard().getNameKo(), card.getDirection(), card.getKeywords()
        );

        return chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user(userPrompt)
                .call()
                .content();
    }
}
