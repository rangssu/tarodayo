package com.taro.tarodayo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ReadingResult {

    private final String question;
    private final List<DrawnCard> selectedCards;
    private final String interpretation;

    public ReadingResult(String question, List<DrawnCard> selectedCards, String interpretation) {
        this.question = question;
        this.selectedCards = selectedCards;
        this.interpretation = interpretation;
    }
}
