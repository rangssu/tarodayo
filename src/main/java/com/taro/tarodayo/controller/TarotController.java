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
    public String draw(@RequestParam(defaultValue = "") String question,
                       @RequestParam(defaultValue = "three") String spreadType,
                       HttpSession session, Model model) {
        if (question.isBlank() && "today".equals(spreadType)) {
            question = "오늘 하루를 위한 카드";
        }
        List<DrawnCard> drawnCards = tarotCardService.drawFiveCards();
        int maxSelect = "today".equals(spreadType) ? 1 : 3;
        session.setAttribute("drawnCards", drawnCards);
        session.setAttribute("question", question);
        session.setAttribute("spreadType", spreadType);
        model.addAttribute("drawnCards", drawnCards);
        model.addAttribute("question", question);
        model.addAttribute("maxSelect", maxSelect);
        model.addAttribute("spreadType", spreadType);
        return "card-select";
    }

    @PostMapping("/result")
    public String result(@RequestParam(required = false) int[] selectedIndexes,
                         HttpSession session, Model model) {

        List<DrawnCard> drawnCards = (List<DrawnCard>) session.getAttribute("drawnCards");
        String question     = (String) session.getAttribute("question");
        String spreadType   = (String) session.getAttribute("spreadType");
        if (spreadType == null) spreadType = "three";

        int expectedCount = "today".equals(spreadType) ? 1 : 3;

        if (drawnCards == null || question == null
                || selectedIndexes == null || selectedIndexes.length != expectedCount) {
            return "redirect:/";
        }

        List<DrawnCard> selectedCards = Arrays.stream(selectedIndexes)
                .mapToObj(drawnCards::get)
                .toList();

        String interpretation;
        try {
            interpretation = "today".equals(spreadType)
                    ? geminiService.interpretOneCard(question, selectedCards.get(0))
                    : geminiService.interpret(question, selectedCards);
        } catch (Exception e) {
            interpretation = "AI 해석 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
        }

        ReadingResult readingResult = new ReadingResult(question, selectedCards, interpretation);
        model.addAttribute("result", readingResult);
        model.addAttribute("spreadType", spreadType);
        return "result";
    }
}
