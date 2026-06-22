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

    public String interpret(String question, List<DrawnCard> selectedCards) {
        DrawnCard past = selectedCards.get(0);
        DrawnCard present = selectedCards.get(1);
        DrawnCard future = selectedCards.get(2);

        String prompt = """
                당신은 신비롭고 통찰력 있는 타로 리더입니다.
                사용자의 질문과 뽑힌 카드를 바탕으로 과거, 현재, 미래를 해석해주세요.

                [질문]
                %s

                [뽑힌 카드]
                - 과거: %s (%s) — 키워드: %s
                - 현재: %s (%s) — 키워드: %s
                - 미래: %s (%s) — 키워드: %s

                각 포지션(과거/현재/미래)을 2~3문장으로 자연스럽고 따뜻하게 해석해주세요.
                마지막에 전체적인 조언 한 문장을 덧붙여 주세요.
                한국어로 답변해주세요.
                """.formatted(
                question,
                past.getCard().getNameKo(), past.getDirection(), past.getKeywords(),
                present.getCard().getNameKo(), present.getDirection(), present.getKeywords(),
                future.getCard().getNameKo(), future.getDirection(), future.getKeywords()
        );

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
