package com.taro.tarodayo.controller;

import com.taro.tarodayo.dto.DrawnCard;
import com.taro.tarodayo.dto.ReadingResult;
import com.taro.tarodayo.service.GeminiService;
import com.taro.tarodayo.service.TarotCardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TarotController {

    private final TarotCardService tarotCardService;
    private final GeminiService geminiService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/draw")
    public String draw(@RequestParam String question, HttpSession session, Model model) {
        List<DrawnCard> drawnCards = tarotCardService.drawFiveCards();
        session.setAttribute("drawnCards", drawnCards);
        session.setAttribute("question", question);
        model.addAttribute("drawnCards", drawnCards);
        model.addAttribute("question", question);
        return "card-select";
    }

    @PostMapping("/result")
    public String result(@RequestParam(required = false) int[] selectedIndexes,
                         HttpSession session, Model model) {

        List<DrawnCard> drawnCards = (List<DrawnCard>) session.getAttribute("drawnCards");
        String question = (String) session.getAttribute("question");

        // 세션 만료 또는 잘못된 요청 → 홈으로 리다이렉트
        if (drawnCards == null || question == null
                || selectedIndexes == null || selectedIndexes.length != 3) {
            return "redirect:/";
        }

        List<DrawnCard> selectedCards = Arrays.stream(selectedIndexes)
                .mapToObj(drawnCards::get)
                .toList();

        String interpretation;
        try {
            interpretation = geminiService.interpret(question, selectedCards);
        } catch (Exception e) {
            interpretation = "AI 해석 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
        }

        ReadingResult readingResult = new ReadingResult(question, selectedCards, interpretation);
        model.addAttribute("result", readingResult);
        return "result";
    }
}
