package com.taro.tarodayo.dto;

import com.taro.tarodayo.domain.TarotCard;
import lombok.Getter;

@Getter
public class DrawnCard {

    private final TarotCard card;
    private final boolean reversed;

    public DrawnCard(TarotCard card, boolean reversed) {
        this.card = card;
        this.reversed = reversed;
    }

    public String getKeywords() {
        return reversed ? card.getReversedKeywords() : card.getUprightKeywords();
    }

    public String getDirection() {
        return reversed ? "역방향" : "정방향";
    }
}
