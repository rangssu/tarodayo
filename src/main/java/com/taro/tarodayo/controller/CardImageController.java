package com.taro.tarodayo.controller;

import com.taro.tarodayo.domain.TarotCard;
import com.taro.tarodayo.repository.TarotCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardImageController {

    private final TarotCardRepository tarotCardRepository;

    @GetMapping(value = "/card-image/{id}", produces = "image/svg+xml")
    public ResponseEntity<String> cardImage(@PathVariable Long id) {
        TarotCard card = tarotCardRepository.findById(id).orElseThrow();
        String svg = buildSvg(card);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/svg+xml"))
                .body(svg);
    }

    private String buildSvg(TarotCard card) {
        String symbol = card.getSymbol() != null ? card.getSymbol() : "✦";
        String roman = card.getRomanNumeral() != null ? card.getRomanNumeral() : "";
        String name = card.getNameKo();

        return """
                <svg xmlns="http://www.w3.org/2000/svg" width="200" height="320" viewBox="0 0 200 320">
                  <defs>
                    <linearGradient id="bg" x1="0" y1="0" x2="1" y2="1">
                      <stop offset="0%%" stop-color="#1e0d38"/>
                      <stop offset="50%%" stop-color="#2d1b4e"/>
                      <stop offset="100%%" stop-color="#1a1040"/>
                    </linearGradient>
                    <linearGradient id="border" x1="0" y1="0" x2="1" y2="1">
                      <stop offset="0%%" stop-color="#d4a8ff"/>
                      <stop offset="100%%" stop-color="#7c3aed"/>
                    </linearGradient>
                    <filter id="glow">
                      <feGaussianBlur stdDeviation="3" result="blur"/>
                      <feMerge><feMergeNode in="blur"/><feMergeNode in="SourceGraphic"/></feMerge>
                    </filter>
                  </defs>

                  <!-- 배경 -->
                  <rect width="200" height="320" rx="14" fill="url(#bg)"/>

                  <!-- 외부 테두리 -->
                  <rect x="2" y="2" width="196" height="316" rx="13" fill="none"
                        stroke="url(#border)" stroke-width="2" opacity="0.8"/>

                  <!-- 내부 장식 테두리 -->
                  <rect x="10" y="10" width="180" height="300" rx="9" fill="none"
                        stroke="#d4a8ff" stroke-width="0.8" opacity="0.2"/>

                  <!-- 모서리 장식 -->
                  <text x="18" y="30" font-size="12" fill="#d4a8ff" opacity="0.5" font-family="serif">✦</text>
                  <text x="172" y="30" font-size="12" fill="#d4a8ff" opacity="0.5" font-family="serif">✦</text>
                  <text x="18" y="312" font-size="12" fill="#d4a8ff" opacity="0.5" font-family="serif">✦</text>
                  <text x="172" y="312" font-size="12" fill="#d4a8ff" opacity="0.5" font-family="serif">✦</text>

                  <!-- 로마 숫자 -->
                  <text x="100" y="48" font-size="13" fill="#9d85c4" text-anchor="middle"
                        font-family="Georgia, serif" letter-spacing="4">%s</text>

                  <!-- 구분선 -->
                  <line x1="40" y1="56" x2="160" y2="56" stroke="#d4a8ff" stroke-width="0.5" opacity="0.3"/>

                  <!-- 심볼 -->
                  <text x="100" y="185" font-size="80" text-anchor="middle" dominant-baseline="middle"
                        filter="url(#glow)" opacity="0.9">%s</text>

                  <!-- 구분선 -->
                  <line x1="40" y1="248" x2="160" y2="248" stroke="#d4a8ff" stroke-width="0.5" opacity="0.3"/>

                  <!-- 카드 이름 -->
                  <text x="100" y="272" font-size="15" fill="#e8d5ff" text-anchor="middle"
                        font-family="serif" font-weight="bold">%s</text>
                  <text x="100" y="292" font-size="10" fill="#7a6d96" text-anchor="middle"
                        font-family="serif">%s</text>
                </svg>
                """.formatted(roman, symbol, name, card.getNameEn());
    }
}
